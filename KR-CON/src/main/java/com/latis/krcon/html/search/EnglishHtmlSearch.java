package com.latis.krcon.html.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.latis.krcon.html.filter.HtmlFilter;
import com.latis.krcon.html.search.dto.SearchDTO;
import com.latis.krcon.html.search.dto.SearchResultDTO;
import com.latis.krcon.html.search.highlight.HtmlHighlight;
import com.latis.krcon.html.sort.HtmlSort;
import com.latis.krcon.query.BuildQuery;

public class EnglishHtmlSearch {
	private static final Logger logger = LoggerFactory.getLogger(EnglishHtmlSearch.class);
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
	// @Autowired
	// private StandardAnalyzer standardAnalyzer;

	@Autowired
	private HtmlHighlight htmlHighlight;

	@Autowired
	private BuildQuery buildQuery;

	private SearchDTO searchDTO;

	@Value("${searchResultSize}")
	private int searchResultSize;

	private int totalHits = 0;
	
	@Value("${localeField}")
	private String localeField;
	
	@Autowired
	private HtmlFilter htmlFilter;
	
	public EnglishHtmlSearch() {
		// TODO Auto-generated constructor stub
	}

	public void init() throws IOException {

		if(reader != null){
			
			IndexReader oldReader = searcher.getIndexReader();
			if (!oldReader.isCurrent()) {
				IndexReader newIndexReader = IndexReader.openIfChanged(oldReader);
				oldReader.close();
				searcher.close();
				IndexSearcher searcher2 = new IndexSearcher(newIndexReader);
				searcher = searcher2;
//			searcher2.search();
			}
		}else{
			
			dir = FSDirectory.open(new File(dirPath));
			reader = IndexReader.open(dir);
			searcher = new IndexSearcher(reader);
		}

	}

	public void close() {
		try {
			if (searcher != null)
				searcher.close();
//			if (reader != null)
//				reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
	}

	public TopDocs htmlSearchData() {
		TopDocs hits = null;
		try {
			Query query = buildQuery.totalSearchBuildQuery(englishAnalyzer,
					textField, searchDTO.getAndWordSearch(),
					searchDTO.getOrWordSearch(),
					searchDTO.getExactWordSearch(),
					searchDTO.getNotWordSearch());
			Filter filter = applyChainedFilter(searchDTO.getBreadcrumb(),
					searchDTO.getCategoryTitle(), searchDTO.getLocale());

			HtmlSort htmlSort = new HtmlSort();
			htmlSort.addSortList(new SortField(searchDTO.getSortFileName(),
					SortField.STRING)); // 1 번.

			Sort sort = htmlSort.getSort();

			hits = callQuerySearch(query, filter, sort, searchDTO.getPageNum());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return hits;
	}

	private TopDocs callQuerySearch(Query query, Filter filter, Sort sort,
			int pageNum) throws IOException {

		TopDocs hits;
		int totalPageCount = 0;
		if (pageNum == 0) {
			totalPageCount = searcher.maxDoc();
		} else {
			totalPageCount = (pageNum + 1) * searchResultSize;
			if (totalPageCount > searchDTO.getTotalCount()) {
				totalPageCount = searchDTO.getTotalCount();
			}
		}

		if (filter != null && sort != null) {
			hits = searcher.search(query, filter, totalPageCount, sort);
		} else if (filter != null && sort == null) {
			hits = searcher.search(query, filter, totalPageCount);
		} else if (sort != null && filter == null) {
			hits = searcher.search(query, null, totalPageCount, sort);
		} else {
			hits = searcher.search(query, totalPageCount);
		}
		return hits;
	}

	public String convertToText(String text) {
		if (text != null) {
			text = text.replaceAll("\\s\\s", "");
			return text.replaceAll("\\n", "");
		}
		return null;
	}

	public Filter applyChainedFilter(String breadcrumb, String categoryTitle,
			String locale) throws Exception {
//		HtmlFilter htmlFilter = new HtmlFilter(breadcrumb, categoryTitle,
//				locale);
		
		htmlFilter.setFilter(breadcrumb, categoryTitle, locale);

		return htmlFilter.getFilter();
	}

	private ArrayList<SearchResultDTO> getDocumentList(IndexSearcher searcher,
			TopDocs hits, String fieldName) throws IOException {

		ArrayList<SearchResultDTO> returnList = null;
		if (hits.totalHits == 0) {
			return null;
		}
		returnList = new ArrayList<SearchResultDTO>();

		int totalPageCount = (searchDTO.getPageNum() + 1) * searchResultSize;

		if (hits.totalHits < totalPageCount) {
			totalPageCount = hits.totalHits;
		}

		ScoreDoc[] scoreDocs = hits.scoreDocs;
		int compareValue = 0;
		if (totalPageCount < searchResultSize) {
			compareValue = 0;
		} else {
			if(totalPageCount == hits.totalHits){
				compareValue = totalPageCount - (totalPageCount - searchDTO.getPageNum() * searchResultSize);
			}else{
				compareValue = totalPageCount - searchResultSize;
			}
		}

		for (int i = compareValue; i < totalPageCount; i++) {
			Document doc = searcher.doc(scoreDocs[i].doc);
			SearchResultDTO resultDTO = new SearchResultDTO();

			resultDTO.setTitle(doc.get(categoryTitleField));

			try {
//				String highlight = htmlHighlight.getHighlightHTML(
//						englishAnalyzer, doc.get(textField), textField,
//						searchDTO.getAndWordSearch(),
//						searchDTO.getOrWordSearch(),
//						searchDTO.getExactWordSearch(),
//						searchDTO.getNotWordSearch());
//
//				highlight = htmlHighlight.substringHighlight(highlight);
				
				String highlight = htmlHighlight.getHighlightHTML(
						englishAnalyzer, doc.get(textField), textField,
						searchDTO.getAndWordSearch(),
						searchDTO.getOrWordSearch(),
						searchDTO.getExactWordSearch(),
						searchDTO.getNotWordSearch());
				

				highlight = htmlHighlight.substringHighlight(highlight);

				highlight = htmlHighlight.getHighlightHTML(
						englishAnalyzer, highlight, textField,
						searchDTO.getAndWordSearch(),
						searchDTO.getOrWordSearch(),
						searchDTO.getExactWordSearch(),
						searchDTO.getNotWordSearch());

				resultDTO.setHtmlText(highlight);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}

			resultDTO.setBreadcrumbs(doc.get(breadcrumbField));

			StringBuffer buffer = new StringBuffer();

			buffer.append(rootCategoryTreeName);

			
			
			String categoryTreeId = doc.get(categoryTreeField);
			if(!categoryTreeId.equals(rootCategoryTreeName)){
				String solasId = doc.get(categoryTreeField).substring(rootCategoryTreeName.length() + 1);
				String[] ids = solasId.split("\\.");
				
				String subCategory = "";
				
				for (String id : ids) {
					if (subCategory.equals("")) {
						subCategory = subCategory + id;
					} else {
						subCategory = subCategory + "." + id;
					}
					
					buffer.append("/").append(rootCategoryTreeName).append(".")
					.append(subCategory);
					
				}
				
				resultDTO.setCategoryTree(buffer.toString());
			}else{
				resultDTO.setCategoryTree(rootCategoryTreeName);
			}

			returnList.add(resultDTO);
		}
		return returnList;
	}

	public SearchDTO getSearchDTO() {
		return searchDTO;
	}

	public void setSearchDTO(SearchDTO searchDTO) {
		this.searchDTO = searchDTO;
	}

	public ArrayList<SearchResultDTO> getSearchData() throws IOException {
		TopDocs hits = htmlSearchData();
		this.totalHits = hits.totalHits;
		ArrayList<SearchResultDTO> list = getDocumentList(searcher, hits,
				textField);
		return list;

	}

	public ArrayList<String> compareStopWord() {
		ArrayList<String> stopList = new ArrayList<String>();

		if (searchDTO.getAndWordSearch() != null
				&& !searchDTO.getAndWordSearch().equals("")) {
			stopList = checkWord(searchDTO.getAndWordSearch(), stopList);
		}

		if (searchDTO.getOrWordSearch() != null
				&& !searchDTO.getOrWordSearch().equals("")) {
			stopList = checkWord(searchDTO.getOrWordSearch(), stopList);
		}

		if (searchDTO.getExactWordSearch() != null
				&& !searchDTO.getExactWordSearch().equals("")) {
			stopList = checkWord(searchDTO.getExactWordSearch(), stopList);
		}

		if (searchDTO.getNotWordSearch() != null
				&& !searchDTO.getNotWordSearch().equals("")) {
			stopList = checkWord(searchDTO.getNotWordSearch(), stopList);
		}

		if (stopList.size() == 0) {
			stopList = null;
		}

		return stopList;
	}

	public ArrayList<String> checkWord(String query, ArrayList<String> stopList) {
		String[] queryArr = query.split("\\ ");
		CharArraySet temp = (CharArraySet) StopAnalyzer.ENGLISH_STOP_WORDS_SET;

		for (String str : queryArr) {

			if (temp.contains(str.toLowerCase())) {
				if (!stopList.contains(str)) {
					stopList.add(str);
				}
			}
		}

		return stopList;
	}

	public int getTotalHits() {
		return totalHits;
	}

	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}

}
