package com.latis.krcon.html.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.CharReader;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.charfilter.HTMLStripCharFilter;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.NullFragmenter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.latis.krcon.html.dto.SearchDTO;
import com.latis.krcon.html.dto.SearchResultDTO;
import com.latis.krcon.html.filter.HtmlFilter;
import com.latis.krcon.html.parser.CustomQueryParser;
import com.latis.krcon.html.sort.HtmlSort;



public class EnglishHtmlSearch {

	@Value("${fileindex}")
	private String dirPath;

	@Value("${textField}")
	private String textField;
	
	@Value("${htmlField}")
	private String htmlField;
	
	@Value("${categoryTreeField}")
	private String categoryTreeField;
	
	@Value("${breadcrumbField}")
	private String breadcrumbField;
	
	@Value("${categoryTitleField}")
	private String categoryTitleField;
	
	@Value("${rootCategoryTreeName}")
	private String rootCategoryTreeName;
	
	private IndexSearcher searcher;
	private Directory dir;
	private IndexReader reader;

	@Autowired
	private EnglishAnalyzer englishAnalyzer;
	@Autowired
	private StandardAnalyzer standardAnalyzer;

	
	private SearchDTO searchDTO;
	
	@Value("${highlightTag}")
	private String highlightTag;

	public EnglishHtmlSearch() {
		// TODO Auto-generated constructor stub
	}
	
	public void init() throws IOException {
		dir = FSDirectory.open(new File(dirPath));
		reader = IndexReader.open(dir);
		searcher = new IndexSearcher(reader);

	}

	public void close()  {
		try {
			if(searcher != null)
				searcher.close();
			if(reader != null)
				reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public TopDocs htmlSearchData() {
		
		TopDocs hits = null;
		try {
			Query query = totalSearchBuildQuery(textField, searchDTO.getAndWordSearch(), searchDTO.getOrWordSearch(), 
					searchDTO.getExactWordSearch(), searchDTO.getNotWordSearch());
			Filter filter = applyChainedFilter(searchDTO.getBreadcrumb(), searchDTO.getCategoryTitle(), searchDTO.getLocale());
			
			HtmlSort htmlSort = new HtmlSort();
			htmlSort.addSortList(new SortField(searchDTO.getSortFileName(), SortField.STRING)); //1 번. 
			
			Sort sort = htmlSort.getSort();
			
			if(filter != null && sort != null){
				hits = searcher.search(query,filter, searcher.maxDoc(), sort);
			}else if(filter != null && sort == null){
				hits = searcher.search(query,filter, searcher.maxDoc());
			}else if(sort != null && filter == null){
				hits = searcher.search(query, null, searcher.maxDoc(), sort);
			}else{
				hits = searcher.search(query, searcher.maxDoc());
			}
			
//			String highlight = getHighlightHTML(hits, andWordSearch,null, null, null );
			
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hits;
	}
	
	public String convertToText(String text){
		if(text != null){
			text = text.replaceAll("\\s\\s", "");
			return text.replaceAll("\\n", "");
		}
		return null;
	}
	
	public String getHighlightHTML(String text, String field, String andWordSearch,
			String orWordSearch, String exact, String non)
			throws CorruptIndexException, IOException, ParseException {
		String result = null;
			//
		Query query = totalSearchBuildQuery(field, andWordSearch, null,
				null, null);
		result = highlightHTML(englishAnalyzer, text, query,
				field);
			//System.out.println(result);
		return result;
	}
	
	public Query totalSearchBuildQuery(String fieldName, String andSearch, String orSearch, String exact, String non) throws IOException, ParseException{
		BooleanQuery booleanQuery = new BooleanQuery();
		if (andSearch != null && andSearch != "") {
			String andQueryStr = andAnalyze(andSearch, fieldName, englishAnalyzer);
			Query andQuery = new QueryParser(Version.LUCENE_36, fieldName,
					englishAnalyzer).parse(andQueryStr); // #B
			booleanQuery.add(andQuery, BooleanClause.Occur.MUST);
		}

		if (orSearch != null && orSearch != "") {
			String orQueryStr = orAnalyze(orSearch, fieldName, englishAnalyzer);

			Query orQuery = new QueryParser(Version.LUCENE_36, fieldName,
					englishAnalyzer).parse(orQueryStr);
			booleanQuery.add(orQuery, BooleanClause.Occur.MUST);
		}

		if (exact != null && exact != "") {
			CustomQueryParser queryParser = new CustomQueryParser(
					Version.LUCENE_36,fieldName, standardAnalyzer);
			Query exactQuery = queryParser.parse(exact);
			booleanQuery.add(exactQuery, BooleanClause.Occur.MUST);
			
		}

		if (non != null && non != "") {
			String notAndQueryStr = orAnalyze(non, fieldName, englishAnalyzer);
			Query notAndQuery = new QueryParser(Version.LUCENE_36, fieldName,
					englishAnalyzer).parse(notAndQueryStr);
			booleanQuery.add(notAndQuery, BooleanClause.Occur.MUST_NOT);
		}
		return booleanQuery;
		
		
	}
	
	public Filter applyChainedFilter(String breadcrumb, String categoryTitle, String locale) throws Exception{
		HtmlFilter htmlFilter = new HtmlFilter(breadcrumb, categoryTitle, locale);
		
		return   htmlFilter.getFilter();
	}
	
	
	
	public String highlightHTML(Analyzer analyzer, String htmlText, Query query,String field) {

		QueryScorer scorer = new QueryScorer(query, field);

		SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter(
				"<span class=\"highlight\">", "</span>");

		Highlighter highlighter = new Highlighter(htmlFormatter, scorer);

		// Nullfragmenter for highlighting entire document.
		highlighter.setTextFragmenter(new NullFragmenter());

		StringReader strReader = new StringReader(htmlText);
		TokenStream ts = analyzer.tokenStream(
				field,
				new HTMLStripCharFilter(CharReader.get(strReader
						.markSupported() ? strReader : new BufferedReader(
						strReader))));

		try {

			String highlightedText = highlighter.getBestFragment(ts, htmlText);

			if (highlightedText != null) {
				return highlightedText;
			}

		} catch (Exception e) {
			e.printStackTrace();
			// LOG.error("Failed to highlight query string "+ query, e);
		}

		return htmlText;

	}
	
	public ArrayList<Document> categorySearchData(String field,
			String searchWord) {
		ArrayList<Document> returnList = null;
		try {
			String queryStr = andAnalyze(searchWord, field, englishAnalyzer);

			Query query = new QueryParser(Version.LUCENE_36, field,
					englishAnalyzer).parse(queryStr);
			TopDocs hits = searcher.search(query, searcher.maxDoc());
			returnList = dumpHits(searcher, hits, field);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}

	private ArrayList<Document> dumpHits(IndexSearcher searcher, TopDocs hits,
			String fieldName) throws IOException {
		ArrayList<Document> docList = null;
		if (hits.totalHits == 0) {
			return null;
		}
		docList = new ArrayList<Document>();
		for (ScoreDoc match : hits.scoreDocs) {
			Document doc = searcher.doc(match.doc);
			docList.add(doc);
		}
		return docList;
	}

	public String andAnalyze(String string, String field, Analyzer analyzer)
			throws IOException {
		StringBuffer buffer = new StringBuffer();

		string = checkWord(string);
		
		TokenStream stream = analyzer.tokenStream(field, new StringReader(
				string));
		CharTermAttribute term = stream.addAttribute(CharTermAttribute.class);
		buffer.append("(");
		while (stream.incrementToken()) { // C
			
			buffer.append("+");
			buffer.append(term.toString()).append("* ");
		}
		buffer.append(")");

		return buffer.toString();
	}

	public String orAnalyze(String string, String field, Analyzer analyzer)
			throws IOException {
		StringBuffer buffer = new StringBuffer();

		string = checkWord(string);
		
		TokenStream stream = analyzer.tokenStream(field, new StringReader(
				string));
		CharTermAttribute term = stream.addAttribute(CharTermAttribute.class);
		buffer.append("(");
		while (stream.incrementToken()) { // C
			buffer.append(term.toString()).append("* ");
		}
		buffer.append(")");

		return buffer.toString();
	}
	
	public SearchDTO getSearchDTO() {
		return searchDTO;
	}

	public void setSearchDTO(SearchDTO searchDTO) {
		this.searchDTO = searchDTO;
	}

	

	
	public ArrayList<SearchResultDTO> getSearchData() throws IOException{
		
		TopDocs hits = htmlSearchData();
		
		ArrayList<Document> list = dumpHits(searcher, hits, textField);
		
		ArrayList<SearchResultDTO> returnList = new ArrayList<SearchResultDTO>();
		if(list != null){
			for(Document doc : list){
				SearchResultDTO resultDTO = new SearchResultDTO();
				
				resultDTO.setTitle(doc.get(categoryTitleField));
				
				try {
					String highlight = getHighlightHTML(doc.get(textField), textField, searchDTO.getAndWordSearch() ,searchDTO.getOrWordSearch(),
							searchDTO.getExactWordSearch(), searchDTO.getNotWordSearch());
					
					highlight = substringHighlight(highlight);
					
					resultDTO.setHtmlText(highlight);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				resultDTO.setBreadcrumbs(doc.get(breadcrumbField));
				
				
				String[] categoryIds = doc.get(categoryTreeField).split(".");
				
				StringBuffer buffer = new StringBuffer();
				
				buffer.append(rootCategoryTreeName).append("/");
				
				for(String categoryId : categoryIds){
					
				}
				
				resultDTO.setCategoryTree(buffer.toString());
				
				returnList.add(resultDTO);
			}
		}
		return returnList;
	}

	private String substringHighlight(String highlight) {
		if(highlight.indexOf(highlightTag) >= 0){
			if(highlight.indexOf(highlightTag) == 0){
				if(highlight.length() >= highlight.indexOf(highlightTag) + 200){
					highlight = highlight.substring(highlight.indexOf(highlightTag), 200) + "...";
				}else{
					highlight = highlight.substring(highlight.indexOf(highlightTag), highlight.length());
				}
			}else{
				if(highlight.length() >= highlight.indexOf(highlightTag) + 200){
					highlight = "..." + highlight.substring(highlight.indexOf(highlightTag), highlight.indexOf(highlightTag) + 200) + "...";
				}else{
					highlight = "..." + highlight.substring(highlight.indexOf(highlightTag), highlight.length());
				}
			}
		}
		return highlight;
	}
	
	public String checkWord(String query){
		
		String[] querys = query.split(" ");
		
		StringBuffer buffer = new StringBuffer();
		
		for(String word : querys){
			if(word.endsWith("y")){
				buffer.append(word.substring(0, word.length()-1)).append(" ");
			}else{
				buffer.append(word).append(" ");
			}
		}
		
		
		
		return buffer.toString().trim();
	}
	
	public ArrayList<String> compareStopWord(){
		ArrayList<String> stopList = new ArrayList<String>();
		
		if(searchDTO.getAndWordSearch() != null && !searchDTO.getAndWordSearch().equals("")){
			stopList = checkWord(searchDTO.getAndWordSearch(), stopList);
		}
		
		if(searchDTO.getOrWordSearch() != null && !searchDTO.getOrWordSearch().equals("")){
			stopList = checkWord(searchDTO.getOrWordSearch(), stopList);
		}
		
		if(searchDTO.getExactWordSearch() != null && !searchDTO.getExactWordSearch().equals("")){
			stopList = checkWord(searchDTO.getExactWordSearch(), stopList);
		}
		
		if(searchDTO.getNotWordSearch() != null && !searchDTO.getNotWordSearch().equals("")){
			stopList = checkWord(searchDTO.getNotWordSearch(), stopList);
		}
		
		return stopList;
	}
	
	public ArrayList<String> checkWord(String query, ArrayList<String> stopList){
		String[] queryArr = query.split("\\ ");
		CharArraySet temp = (CharArraySet) StopAnalyzer.ENGLISH_STOP_WORDS_SET;
		
		for(String str : queryArr){
			
			if(temp.contains(str.toLowerCase())){
				if(!stopList.contains(str)){
					stopList.add(str);
				}
			}
		}
		
		
		return stopList;
	}
	

}
