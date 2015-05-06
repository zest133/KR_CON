package com.latis.krcon.html.category.search;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javassist.compiler.Parser;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.latis.krcon.html.sort.HtmlSort;
import com.latis.krcon.query.BuildQuery;

public class CategorySearch {
	private IndexSearcher searcher;
	private Directory dir;
	private IndexReader reader;

	@Autowired
	private KeywordAnalyzer analyzer;

	private String searchWord;

	@Autowired
	private HtmlSort htmlSort;

	@Value("${categoryTreeField}")
	private String categoryTreeField;

	@Value("${anonymousData}")
	private String anonymousData;

	@Value("${fileindex}")
	private String dirPath;

	@Autowired
	private BuildQuery buildQuery;

	public CategorySearch() {

	}

	public void init() throws IOException {

		// if(searcher != null){
		// IndexReader oldReader = searcher.getIndexReader();
		// if(!oldReader.isCurrent()) {
		// System.out.println("dddd");
		// }
		// }

		// dir = FSDirectory.open(new File(dirPath));
		// reader = IndexReader.open(dir);
		// searcher = new IndexSearcher(reader);

		if (reader != null) {

			IndexReader oldReader = searcher.getIndexReader();
			if (!oldReader.isCurrent()) {
				IndexReader newIndexReader = IndexReader
						.openIfChanged(oldReader);
				oldReader.close();
				searcher.close();
				IndexSearcher searcher2 = new IndexSearcher(newIndexReader);
				searcher = searcher2;
				// searcher2.search();
			}
		} else {

			dir = FSDirectory.open(new File(dirPath));
			reader = IndexReader.open(dir);
			searcher = new IndexSearcher(reader);
		}
	}

//	public void close() {
//		try {
//			if (reader != null)
//				reader.close();
//			if (searcher != null)
//				searcher.close();
//
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

	public ArrayList<Document> search() {
		return categorySearchData(categoryTreeField, searchWord);
	}

	public ArrayList<Document> currentSearch(String currentCategoryTree) {
		return categorySearchData(categoryTreeField, currentCategoryTree);
	}

	public ArrayList<Document> categoryAllSearchData() {
		Query allCategoryQuery = new MatchAllDocsQuery();
		htmlSort.addSortList(new SortField(categoryTreeField, SortField.STRING));

		ArrayList<Document> list = null;
		try {
			TopDocs hits = searcher.search(allCategoryQuery, searcher.maxDoc(),
					htmlSort.getSort());
			list = getDocumentList(searcher, hits, categoryTreeField);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<Document> categorySubTreeSearchData() {
		String searchWord = this.searchWord + anonymousData;
		return categorySearchData(categoryTreeField, searchWord);
	}

	public ArrayList<Document> categorySearchData(String field,
			String searchWord) {
		ArrayList<Document> returnList = null;
		try {
			String queryStr = buildQuery.categoryMakeQuery(searchWord, field);
			htmlSort.addSortList(new SortField(categoryTreeField,
					SortField.STRING));
			Query query = new QueryParser(Version.LUCENE_36, field, analyzer)
					.parse(queryStr);
			TopDocs hits = searcher.search(query, searcher.maxDoc(),
					htmlSort.getSort());
			returnList = getDocumentList(searcher, hits, field);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}

	private ArrayList<Document> getDocumentList(IndexSearcher searcher,
			TopDocs hits, String fieldName) throws IOException {
		ArrayList<Document> docList = null;
		if (hits.totalHits == 0) {
			System.out.println("No hits");
			return null;
		}
		docList = new ArrayList<Document>();
		for (ScoreDoc match : hits.scoreDocs) {
			Document doc = searcher.doc(match.doc);
			docList.add(doc);
		}
		return docList;
	}

	public void checkSubCategory(Document document, JSONObject jsonObject) {
		try {
			String queryStr = buildQuery.categoryMakeQuery(
					document.get(categoryTreeField) + anonymousData,
					categoryTreeField);

			Query query = new QueryParser(Version.LUCENE_36, categoryTreeField,
					analyzer).parse(queryStr);

			TopDocs hits = searcher.search(query, searcher.maxDoc());

			if (hits.totalHits == 0) {
			} else {
				jsonObject.put("isFolder", "true");
				jsonObject.put("isLazy", "true");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
}
