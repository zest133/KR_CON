package com.latis.krcon.html.category.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.document.Document;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.latis.krcon.html.sort.HtmlSort;
import com.latis.krcon.query.BuildQuery;

public class CategorySearch {
	private static final Logger logger = LoggerFactory.getLogger(CategorySearch.class);
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
		// }
		// }

		// dir = FSDirectory.open(new File(dirPath));
		// reader = IndexReader.open(dir);
		// searcher = new IndexSearcher(reader);

		if (reader != null) {

			IndexReader oldReader = searcher.getIndexReader();
			if (!oldReader.isCurrent()) {
				IndexReader newIndexReader = IndexReader.openIfChanged(oldReader);
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
			logger.error(e.getMessage());
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
			logger.error(e.getMessage());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return returnList;
	}

	private ArrayList<Document> getDocumentList(IndexSearcher searcher,
			TopDocs hits, String fieldName) throws IOException {
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

	@SuppressWarnings("unchecked")
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
			logger.error(e.getMessage());
		}

	}
	
	

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
}
