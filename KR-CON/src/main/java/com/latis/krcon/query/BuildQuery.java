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
	
	
	public Query totalSearchBuildQuery(Analyzer analyzer, String fieldName, String andSearch, 
			String orSearch, String exact, String non) throws IOException, ParseException{
		BooleanQuery booleanQuery = new BooleanQuery();
		if (andSearch != null && andSearch != "") {
			String andQueryStr = andMakeQuery(andSearch, fieldName, analyzer);
			Query andQuery = new QueryParser(Version.LUCENE_36, fieldName,
					analyzer).parse(andQueryStr); // #B
			booleanQuery.add(andQuery, BooleanClause.Occur.MUST);
		}

		if (orSearch != null && orSearch != "") {
			String orQueryStr = orMakeQuery(orSearch, fieldName, analyzer);

			Query orQuery = new QueryParser(Version.LUCENE_36, fieldName,
					analyzer).parse(orQueryStr);
			booleanQuery.add(orQuery, BooleanClause.Occur.MUST);
		}

		if (exact != null && exact != "") {
			CustomQueryParser queryParser = new CustomQueryParser(
					Version.LUCENE_36,fieldName, standardAnalyzer);
			Query exactQuery = queryParser.parse(exact);
			booleanQuery.add(exactQuery, BooleanClause.Occur.MUST);
			
		}

		if (non != null && non != "") {
			String notAndQueryStr = orMakeQuery(non, fieldName, analyzer);
			Query notAndQuery = new QueryParser(Version.LUCENE_36, fieldName,
					analyzer).parse(notAndQueryStr);
			booleanQuery.add(notAndQuery, BooleanClause.Occur.MUST_NOT);
		}
		return booleanQuery;
		
		
	}
	
	public String categoryMakeQuery(String searchWord, String field)
			throws IOException {
		StringBuffer buffer = new StringBuffer();

//		TokenStream stream = analyzer.tokenStream(field, new StringReader(
//				searchWord));
//		CharTermAttribute term = stream.addAttribute(CharTermAttribute.class);
		buffer.append("(");
//		while (stream.incrementToken()) { // C
			buffer.append("+");
			buffer.append(field);
			buffer.append(":");
			buffer.append(searchWord);
//		}
		buffer.append(")");

		String output = buffer.toString();

		System.out.println(output);

		return buffer.toString();
	}
	
	public String keywordAnalyzeMakeQuery(String searchWord, String field)
			throws IOException {
		StringBuffer buffer = new StringBuffer();

		buffer.append("(");
		buffer.append("+");
		buffer.append(field);
		buffer.append(":");
		buffer.append("\"").append(searchWord).append("\"");
		buffer.append(")");

		String output = buffer.toString();

		System.out.println(output);

		return buffer.toString();
	}
	
	public String andMakeQuery(String string, String field, Analyzer analyzer)
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

	public String orMakeQuery(String string, String field, Analyzer analyzer)
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
}
