package com.latis.krcon.html.search;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.latis.krcon.html.parser.CustomQueryParser;

@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestBuildTree {

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

	@Test
	public void getRootTree() throws IOException, ParseException {
		String rootKey = "0000.00e0.1530";
		
		BooleanQuery textBooleanQuery = new BooleanQuery();

		CustomQueryParser queryParser = new CustomQueryParser(
				Version.LUCENE_36, "categoryTree", englishAnalyzer);
		Query exactQuery = queryParser.parse(rootKey);
		textBooleanQuery.add(exactQuery, BooleanClause.Occur.MUST);

		TopDocs hits = null;

		hits = searcher.search(textBooleanQuery, searcher.maxDoc());

		for (ScoreDoc scoreDoc : hits.scoreDocs) {

			Document doc = searcher.doc(scoreDoc.doc);

			// System.out.println(doc.get("categoryTitle"));

		}

	}

	@Test
	public void getSubTree() throws ParseException, IOException {
		
		String key = "0000.00e0.1530.????";
		
		
		BooleanQuery textBooleanQuery = new BooleanQuery();

		CustomQueryParser queryParser = new CustomQueryParser(
				Version.LUCENE_36, "categoryTree", englishAnalyzer);
		Query exactQuery = queryParser.parse(key);
		textBooleanQuery.add(exactQuery, BooleanClause.Occur.MUST);

		TopDocs hits = null;

		hits = searcher.search(textBooleanQuery, searcher.maxDoc());

		for (ScoreDoc scoreDoc : hits.scoreDocs) {

			Document doc = searcher.doc(scoreDoc.doc);

			System.out.println(doc.get("categoryTitle"));

		}
	}

}
