package com.latis.krcon.html.search;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.ChainedFilter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mvel2.sh.command.basic.Set;
import org.springframework.beans.factory.annotation.Autowired;
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
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestEnglishHtmlSearch {

	private String dirPath = "D:/dev/HtmlIndex";
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
		// String andWordSearch = "Eco Driver Pack";
		String orWordSearch = "Wireless network setup";
		String exactWordSearch = "\"Eco Driver Pack\"";
		String notWordSearch = "Wireless network setup";

		try {
			totalSearch(null, null, exactWordSearch, null,"D:\\dev\\git\\KR-CON\\KR-CON\\target\\test-classes\\html\\BABBADDG.htm", null);
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void totalSearch(String andSearch, String orSearch, String exact,
			String non, String fileNameFilter, String categoryFilter)
			throws Exception {

		BooleanQuery bq = new BooleanQuery();

		if (andSearch != null && andSearch != "") {
			String andQueryStr = andAnalyze(andSearch, "text", englishAnalyzer);
			Query andQuery = new QueryParser(Version.LUCENE_36, "text",
					englishAnalyzer).parse(andQueryStr); // #B
			bq.add(andQuery, BooleanClause.Occur.MUST);
		}

		if (orSearch != null && orSearch != "") {
			String orQueryStr = orAnalyze(orSearch, "text", englishAnalyzer);

			Query orQuery = new QueryParser(Version.LUCENE_36, "text",
					englishAnalyzer).parse(orQueryStr);
			bq.add(orQuery, BooleanClause.Occur.MUST);

		}

		if (exact != null && exact != "") {
			// Set set = (Set) standardAnalyzer.STOP_WORDS_SET;
			CustomQueryParser queryParser = new CustomQueryParser(
					Version.LUCENE_36, "text", standardAnalyzer);
			Query exactQuery = queryParser.parse(exact);
			bq.add(exactQuery, BooleanClause.Occur.MUST);

		}

		if (non != null && non != "") {
			String notAndQueryStr = orAnalyze(non, "text", englishAnalyzer);
			Query notAndQuery = new QueryParser(Version.LUCENE_36, "text",
					englishAnalyzer).parse(notAndQueryStr);
			bq.add(notAndQuery, BooleanClause.Occur.MUST_NOT);
		}

		
		HtmlFilter htmlFilter = new HtmlFilter(fileNameFilter, categoryFilter);
		ChainedFilter chain =  htmlFilter.getFilter();
		TopDocs hits = null;
		if(chain != null){
			hits = searcher.search(bq,chain, searcher.maxDoc());
		}else{
			hits = searcher.search(bq, searcher.maxDoc());
		}
		
		dumpHits(searcher, hits, "text");
		// assertEquals("or", 3, hits.totalHits);

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
			buffer.append(field);
			buffer.append(":");
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
			buffer.append(field);
			buffer.append(":");
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
