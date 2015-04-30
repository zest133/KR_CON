package com.latis.krcon.html.search.highlight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharReader;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.charfilter.HTMLStripCharFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.NullFragmenter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.latis.krcon.query.BuildQuery;

public class HtmlHighlight {

	@Value("${highlightStartTag}")
	private String highlightStartTag;
	
	@Value("${highlightEndTag}")
	private String highlightEndTag;
	
	
	@Autowired
	private StandardAnalyzer standardAnalyzer;
	
	@Autowired
	private BuildQuery buildQuery;
	
	public HtmlHighlight() {
		// TODO Auto-generated constructor stub
	}
	
	
	public String highlightHTML(Analyzer analyzer, String htmlText, Query query,String field) {

		QueryScorer scorer = new QueryScorer(query, field);

		SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter(
				this.highlightStartTag, this.highlightEndTag);

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
	
	public String getHighlightHTML(Analyzer analyzer, String text, String field, String andWordSearch,
			String orWordSearch, String exact, String non)
			throws CorruptIndexException, IOException, ParseException {
		String result = null;
			//
		StringBuffer buffer = new StringBuffer();
		buffer.append(andWordSearch).append(" ").append(orWordSearch).append(" ").append(exact).append(" ").append(non);
		Query query = buildQuery.totalSearchBuildQuery(analyzer, field, buffer.toString(), null,
				null, null);
		result = highlightHTML(analyzer, text, query,
				field);
		return result;
	}
	
	
	public String getHighlightHTML(Analyzer analyzer, String text, String field, String queryStr)
			throws CorruptIndexException, IOException, ParseException {
		String result = null;
		Query query = buildQuery.totalSearchBuildQuery(analyzer, field, queryStr, null,
				null, null);
		result = highlightHTML(analyzer, text, query,
				field);
		return result;
	}
	
	
	public String substringHighlight(String highlight) {
		int offset = highlight.indexOf(highlightStartTag);
		if(offset > -1){
			
			int substringLength = 200;
			
			if(offset > 0){
				if(highlight.length() >= offset + substringLength){
					highlight = "..." + highlight.substring(offset, offset + substringLength) + "...";
				}else{
					highlight = "..." + highlight.substring(offset, highlight.length());
				}
			}else{
				if(highlight.length() >= substringLength){
					highlight = highlight.substring(offset, substringLength) + "...";
				}else{
					highlight = highlight.substring(offset, highlight.length());
				}
			}
		}
		return highlight;
	}
	
	
	
}
