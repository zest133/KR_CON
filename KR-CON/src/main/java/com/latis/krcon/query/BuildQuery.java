package com.latis.krcon.query;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;

import com.latis.krcon.html.parser.CustomQueryParser;

public class BuildQuery {

	@Autowired
	private StandardAnalyzer standardAnalyzer;

	public Query totalSearchBuildQuery(Analyzer analyzer, String fieldName,
			String andSearch, String orSearch, String exact, String non)
			throws IOException, ParseException {
		BooleanQuery booleanQuery = new BooleanQuery();
		if (andSearch != null){
			
			andSearch = this.stringReplace(andSearch);
			if (!andSearch.equals("")) {
				String andQueryStr = andMakeQuery(andSearch, fieldName, analyzer);
				if(!andQueryStr.equals("")){
					
					Query andQuery = getQueryParser(analyzer, fieldName,
							andQueryStr);
					booleanQuery.add(andQuery, BooleanClause.Occur.MUST);
				}
			}
		}

		if (orSearch != null){
			
			orSearch = this.stringReplace(orSearch);
			if (!orSearch.equals("")) {
				String orQueryStr = orMakeQuery(orSearch, fieldName, analyzer);
				if(!orQueryStr.equals("")){
					Query orQuery = getQueryParser(analyzer, fieldName, orQueryStr);
					booleanQuery.add(orQuery, BooleanClause.Occur.MUST);
					
				}
			}
		}

		if (exact != null){
			exact = this.stringReplace(exact);
			if (!exact.equals("")) {
				Query exactQuery = getCustomQueryParser(fieldName, exact);
				booleanQuery.add(exactQuery, BooleanClause.Occur.MUST);
			}
		}

		if (non != null){
			non = this.stringReplace(non);
			if (!non.equals("")) {
				String notAndQueryStr = orMakeQuery(non, fieldName, analyzer);
				if(!notAndQueryStr.equals("")){
					Query notAndQuery = getQueryParser(analyzer, fieldName,
							notAndQueryStr);
					booleanQuery.add(notAndQuery, BooleanClause.Occur.MUST_NOT);
				}
			}
			
		}
		return booleanQuery;

	}



	public Query getCustomQueryParser(String fieldName, String exact)
			throws ParseException {
		CustomQueryParser queryParser = new CustomQueryParser(
				Version.LUCENE_36, fieldName, standardAnalyzer);
		Query exactQuery = queryParser.parse(exact);
		return exactQuery;
	}



	public Query getQueryParser(Analyzer analyzer, String fieldName,
			String andQueryStr) throws ParseException {
		Query andQuery = new QueryParser(Version.LUCENE_36, fieldName,
				analyzer).parse(andQueryStr); // #B
		return andQuery;
	}
	
	

	public String categoryMakeQuery(String searchWord, String field)
			throws IOException {
		StringBuffer buffer = new StringBuffer();

		// TokenStream stream = analyzer.tokenStream(field, new StringReader(
		// searchWord));
		// CharTermAttribute term =
		// stream.addAttribute(CharTermAttribute.class);
		buffer.append("(");
		// while (stream.incrementToken()) { // C
		buffer.append("+");
		buffer.append(field);
		buffer.append(":");
		buffer.append(searchWord);
		// }
		buffer.append(")");

		String output = buffer.toString();


		return buffer.toString();
	}

	public String keywordAnalyzeMakeQuery(String searchWord, String field)
			throws IOException {
		searchWord = this.stringReplace(searchWord);
		StringBuffer buffer = new StringBuffer();

//		buffer.append("(");
		buffer.append("+");
		buffer.append(field);
		buffer.append(":");
		buffer.append("\"").append(searchWord).append("\"");
//		buffer.append(")");

		String output = buffer.toString();


		return buffer.toString();
	}

	public String andMakeQuery(String string, String field, Analyzer analyzer)
			throws IOException {
		StringBuffer buffer = new StringBuffer();

		StringBuffer termBuffer = null;
		string = checkWord(string);

		if (string.length() != 0) {

			TokenStream stream = analyzer.tokenStream(field, new StringReader(
					string));
			CharTermAttribute term = stream
					.addAttribute(CharTermAttribute.class);
			while (stream.incrementToken()) { // C
				if(termBuffer == null){
					termBuffer = new StringBuffer();
				}
				termBuffer.append("+");
				termBuffer.append(term.toString()).append("* ");
			}
			if(termBuffer != null){
				
				buffer.append("(").append(termBuffer.toString()).append(")");
			}
		}

		return buffer.toString();
	}

	public String orMakeQuery(String string, String field, Analyzer analyzer)
			throws IOException {
		StringBuffer buffer = new StringBuffer();
		StringBuffer termBuffer = null;
		string = checkWord(string);

		TokenStream stream = analyzer.tokenStream(field, new StringReader(
				string));
		CharTermAttribute term = stream.addAttribute(CharTermAttribute.class);
		while (stream.incrementToken()) { // C
			if(termBuffer == null){
				termBuffer = new StringBuffer();
			}
			termBuffer.append(term.toString()).append("* ");
		}
		if(termBuffer != null){
			buffer.append("(").append(termBuffer.toString()).append(")");
		}

		return buffer.toString();
	}

	public String checkWord(String query) {
		String[] querys = query.split(" ");

		StringBuffer buffer = new StringBuffer();
		
		for (String word : querys) {
			if (word.endsWith("y")) {
				buffer.append(word.substring(0, word.length() - 1)).append(" ");
			} else {
				buffer.append(word).append(" ");
			}
		}
		return buffer.toString().trim();
	}

	public String stringReplace(String str) {
		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
		str = str.replaceAll(match, "");
		return str;
	}
}
