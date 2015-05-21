package com.latis.krcon.xml.write;

import java.io.File;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharTokenizer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TieredMergePolicy;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.search.spans.Spans;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.latis.krcon.html.parser.CustomQueryParser;


@ContextConfiguration(locations={
"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SpanQueryTests {
	private IndexSearcher searcher;
	private IndexReader reader;
	private Analyzer analyzer;

	
	@Value("${fileindex2}")
	private String path;
	
	
	@Autowired
	private TieredMergePolicy tmp;
	
	private Directory dir;
	
	
	private String temp = "<doc>text sample text <x>test</x> words lipsum words words <x>text</x> some other text </doc><foobar>a</foobar> some more text flop</doc>";
	@Before
	public void setUp() throws Exception {
//		Directory dir = new RAMDirectory();
//		analyzer = new CustomSimpleAnalyzer(Version.LUCENE_36);
		temp  = ltgtReplaceAll(temp);
		analyzer = new CustomXmlAnalyzer2(Version.LUCENE_36);
		
		dir = FSDirectory.open(new File(path));
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_36,analyzer);
		
		
		iwc.setOpenMode(OpenMode.CREATE);
		iwc.setMergePolicy(tmp);
		
		
//		lockChecker();
		IndexWriter writer = new IndexWriter(dir, iwc);
		
		
		
		
//		IndexWriter writer = new IndexWriter(dir, analyzer,
//				IndexWriter.MaxFieldLength.UNLIMITED);
		
		ImmutableList<String> docs = ImmutableList.of(temp);
		int id = 0;
		for (String content : docs) {
			Document doc = new Document();
			doc.add(new Field("id", String.valueOf(id++), Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			doc.add(new Field("content", content, Field.Store.YES,
					Field.Index.ANALYZED,TermVector.WITH_POSITIONS_OFFSETS));
			writer.addDocument(doc);
			id++;
		}
		writer.close();

		
		
		reader = IndexReader.open(dir);
		searcher = new IndexSearcher(reader);
//		searcher = new IndexSearcher(dir);
//		reader = searcher.getIndexReader();
	}

	@After
	public void tearDown() throws Exception {
		searcher.close();
	}

	@Test
	public void testTermNearQuery() throws Exception {
		
		
//		CustomQueryParser queryParser = new CustomQueryParser(
//				Version.LUCENE_36, fieldName, standardAnalyzer);
//		Query exactQuery = queryParser.parse(exact);
		
		
		CustomQueryParser queryParser = new CustomQueryParser(
				Version.LUCENE_36, "content", analyzer);
		Query exactQuery = queryParser.parse("\"<doc> text sample\"");
		TopDocs hits = searcher.search(exactQuery,  searcher.maxDoc());
//		
		
		
		
		SpanTermQuery tq1 = new SpanTermQuery(new Term("content", "<doc>"));
		SpanTermQuery tq2 = new SpanTermQuery(new Term("content", "text"));
		SpanTermQuery tq3 = new SpanTermQuery(new Term("content", "sample"));
		SpanNearQuery snq1 = new SpanNearQuery(new SpanQuery[] { tq1,tq2, tq3 }, 1, false);
		dumpSpans(snq1);
//		SpanNearQuery snq2 = new SpanNearQuery(new SpanQuery[] { tq2, tq3 }, 2,
//				false);
//		dumpSpans(snq2);
	}

	private void dumpSpans(SpanQuery query) throws IOException {
		Spans spans = query.getSpans(reader);
		System.out.println(query + ":");
		int numSpans = 0;

		TopDocs hits = searcher.search(query, 10);
		float[] scores = new float[2];
		for (ScoreDoc sd : hits.scoreDocs) {
			scores[sd.doc] = sd.score;
		}

		while (spans.next()) { // A
			numSpans++;

			int id = spans.doc();
			Document doc = reader.document(id); // B

			String temp = doc.get("content");
			TokenStream stream = analyzer.tokenStream("content", // C
					new StringReader(doc.get("content"))); // C
			CharTermAttribute term = stream
					.addAttribute(CharTermAttribute.class);

			StringBuilder buffer = new StringBuilder();
			buffer.append("   ");
			int i = 0;
			while (stream.incrementToken()) { // D
				if (i == spans.start()) { // E
					buffer.append("<"); // E
				} // E
				buffer.append(term.toString()); // E
				if (i + 1 == spans.end()) { // E
					buffer.append(">"); // E
				} // E
				buffer.append(" ");
				i++;
			}
			buffer.append("(").append(scores[id]).append(") ");
			System.out.println(buffer);
		}

		if (numSpans == 0) {
			System.out.println("   No spans");
		}
		System.out.println();
	}
	
	
	public String ltgtReplaceAll(String str){
		str = str.replaceAll("\\<", " <");
		str = str.replaceAll("\\>", "> ");
		return str;
		
	}
}