package com.latis.krcon.html.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharReader;
import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.charfilter.HTMLStripCharFilter;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.kr.KoreanAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.ChainedFilter;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.NullFragmenter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.vectorhighlight.BaseFragmentsBuilder;
import org.apache.lucene.search.vectorhighlight.FastVectorHighlighter;
import org.apache.lucene.search.vectorhighlight.FieldQuery;
import org.apache.lucene.search.vectorhighlight.FragListBuilder;
import org.apache.lucene.search.vectorhighlight.FragmentsBuilder;
import org.apache.lucene.search.vectorhighlight.ScoreOrderFragmentsBuilder;
import org.apache.lucene.search.vectorhighlight.SimpleFragListBuilder;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mvel2.sh.command.basic.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.latis.krcon.html.filter.HtmlFilter;
import com.latis.krcon.html.parser.CustomQueryParser;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;

@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestEnglishHtmlSearch {

//	private String dirPath = "D:/dev/HtmlIndex";
	@Value("${fileindex}")
	private String dirPath;
	
	private IndexSearcher searcher;
	private Directory dir;
	private IndexReader reader;

	@Autowired
	private EnglishAnalyzer englishAnalyzer;
	@Autowired
	private StandardAnalyzer standardAnalyzer;

	@Before
	public void setup() throws IOException {
		dir = FSDirectory.open(new File(dirPath));
		reader = IndexReader.open(dir);
		searcher = new IndexSearcher(reader);
	}

	@After
	public void tearDown() throws IOException {
		searcher.close();
		reader.close();
	}

	public String getDirPath() {
		return dirPath;
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}

	@Test
	public void testHtmlSearchData() {
		// Query allCategoryQuery = new MatchAllDocsQuery();
//		String andWordSearch = "Advance Driver Pack";
		String orWordSearch = "Wireless network setup";
		String exactWordSearch = "\"Eco Driver Pack\"";
		String notWordSearch = "Wireless network setup";

		try {
			Query[] query = totalSearch(null, null, exactWordSearch, null);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Query[] totalSearch(String andSearch, String orSearch, String exact, String non) throws IOException, ParseException{

		BooleanQuery textBooleanQuery = new BooleanQuery();
		BooleanQuery htmlBooleanQuery = new BooleanQuery();

		Query[] returnQuery = new Query[2];
		
		if (andSearch != null && andSearch != "") {
			String andQueryStr = andAnalyze(andSearch, "text", englishAnalyzer);
			Query andQuery = new QueryParser(Version.LUCENE_36, "text",
					englishAnalyzer).parse(andQueryStr); // #B
			
			textBooleanQuery.add(andQuery, BooleanClause.Occur.MUST);
			
			andQuery = new QueryParser(Version.LUCENE_36, "html",
					englishAnalyzer).parse(andQueryStr);
			htmlBooleanQuery.add(andQuery, BooleanClause.Occur.MUST);
		}

		if (orSearch != null && orSearch != "") {
			String orQueryStr = orAnalyze(orSearch, "text", englishAnalyzer);

			Query orQuery = new QueryParser(Version.LUCENE_36, "text",
					englishAnalyzer).parse(orQueryStr);
			textBooleanQuery.add(orQuery, BooleanClause.Occur.MUST);
			
			
			orQuery = new QueryParser(Version.LUCENE_36, "html",
					englishAnalyzer).parse(orQueryStr);
			htmlBooleanQuery.add(orQuery, BooleanClause.Occur.MUST);

		}

		if (exact != null && exact != "") {
			// Set set = (Set) standardAnalyzer.STOP_WORDS_SET;
			CustomQueryParser queryParser = new CustomQueryParser(
					Version.LUCENE_36, "text", standardAnalyzer);
			Query exactQuery = queryParser.parse(exact);
			textBooleanQuery.add(exactQuery, BooleanClause.Occur.MUST);
			
			queryParser = new CustomQueryParser(
					Version.LUCENE_36, "html", standardAnalyzer);
			exactQuery = queryParser.parse(exact);
			htmlBooleanQuery.add(exactQuery, BooleanClause.Occur.MUST);
		}

		if (non != null && non != "") {
			String notAndQueryStr = orAnalyze(non, "text", englishAnalyzer);
			Query notAndQuery = new QueryParser(Version.LUCENE_36, "text",
					englishAnalyzer).parse(notAndQueryStr);
			textBooleanQuery.add(notAndQuery, BooleanClause.Occur.MUST_NOT);
			
			notAndQuery = new QueryParser(Version.LUCENE_36, "html",
					englishAnalyzer).parse(notAndQueryStr);
			
			htmlBooleanQuery.add(notAndQuery, BooleanClause.Occur.MUST_NOT);
		}

//		applyFilter();
//		HtmlFilter htmlFilter = new HtmlFilter(fileNameFilter, categoryFilter);
//		ChainedFilter chain =  htmlFilter.getFilter();
//		TopDocs hits = null;
//		if(chain != null){
//			hits = searcher.search(textBooleanQuery,chain, searcher.maxDoc());
//		}else{
//			hits = searcher.search(textBooleanQuery, searcher.maxDoc());
//		}
//		
//		for (ScoreDoc scoreDoc : hits.scoreDocs) {
//			
//			Document doc = searcher.doc(scoreDoc.doc);
//			String result = highlightHTML(englishAnalyzer, doc.get("html"), htmlBooleanQuery, "html");
//			System.out.println(result);
//		}
		returnQuery[0] = textBooleanQuery;
		returnQuery[1] = htmlBooleanQuery;
		return returnQuery;
		
		
	}
	
	public Filter applyChainedFilter(String breadcrumb, String categoryTitle, String locale){
		return null;
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
				"f",
				new HTMLStripCharFilter(CharReader.get(strReader
						.markSupported() ? strReader : new BufferedReader(
						strReader))));

		try {

			String highlightedText = highlighter.getBestFragment(ts, htmlText);

			if (highlightedText != null) {

				return highlightedText;

			}

		} catch (Exception e) {

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
			System.out.println("No hits");
			return null;
		}
		docList = new ArrayList<Document>();
		for (ScoreDoc match : hits.scoreDocs) {
			Document doc = searcher.doc(match.doc);
			System.out.println(match.score + ":" + doc.get(fieldName));
			docList.add(doc);
		}
		return docList;
	}

	public String andAnalyze(String string, String field, Analyzer analyzer)
			throws IOException {
		StringBuffer buffer = new StringBuffer();

		TokenStream stream = analyzer.tokenStream(field, new StringReader(
				string));
		CharTermAttribute term = stream.addAttribute(CharTermAttribute.class);
		// PositionIncrementAttribute posIncr = stream
		// .addAttribute(PositionIncrementAttribute.class);
		buffer.append("(");
		while (stream.incrementToken()) { // C
			buffer.append("+");
//			buffer.append(field);
//			buffer.append(":");
			buffer.append(term.toString()).append("* ");
		}
		buffer.append(")");

		String output = buffer.toString();

		System.out.println(output);

		return buffer.toString();
	}

	public String orAnalyze(String string, String field, Analyzer analyzer)
			throws IOException {
		StringBuffer buffer = new StringBuffer();

		TokenStream stream = analyzer.tokenStream(field, new StringReader(
				string));
		CharTermAttribute term = stream.addAttribute(CharTermAttribute.class);
		// PositionIncrementAttribute posIncr = stream
		// .addAttribute(PositionIncrementAttribute.class);
		buffer.append("(");
		while (stream.incrementToken()) { // C
			// buffer.append("");
//			buffer.append(field);
//			buffer.append(":");
			buffer.append(term.toString()).append("* ");
		}
		buffer.append(")");

		String output = buffer.toString();

		System.out.println(output);

		return buffer.toString();
	}

	public void displayTokens(TokenStream stream) throws IOException {

		CharTermAttribute term = stream.addAttribute(CharTermAttribute.class);
		while (stream.incrementToken()) {

			System.out.print("[" + term.toString() + "] "); // B
		}
		System.out.println();
	}

	public void displayTokens(Analyzer analyzer, String text)
			throws IOException {
		displayTokens(analyzer.tokenStream("contents", new StringReader(text))); // A
	}
	
	

}
