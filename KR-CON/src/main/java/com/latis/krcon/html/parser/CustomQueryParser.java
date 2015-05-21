package com.latis.krcon.html.parser;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.util.Version;

public class CustomQueryParser extends QueryParser {
	public CustomQueryParser(Version matchVersion, String field,
			Analyzer analyzer) {
		super(matchVersion, field, analyzer);
		
	}

//	@Override
//	protected final Query getWildcardQuery(String field, String termStr)
//			throws ParseException {
//		throw new ParseException("Wildcard not allowed");
//	}

	@Override
	protected Query getFuzzyQuery(String field, String term, float minSimilarity)
			throws ParseException {
		throw new ParseException("Fuzzy queries not allowed");
	}

	@Override
	protected Query getFieldQuery(String field, String queryText, int slop)
			throws ParseException {
		Query orig = super.getFieldQuery(field, queryText, slop); 
		
		if (!(orig instanceof PhraseQuery)) { 
			return orig; 
		} 

		
		PhraseQuery pq = (PhraseQuery) orig;
		Term[] terms = pq.getTerms(); 
		SpanTermQuery[] clauses = new SpanTermQuery[terms.length];
		for (int i = 0; i < terms.length; i++) {
			clauses[i] = new SpanTermQuery(terms[i]);
		}

		/**
		 * stop word를 체크해서 slop을 변경 하는것도 고려. 
		 */
		SpanNearQuery query = new SpanNearQuery( // #4
				clauses, slop, true); // #4

		return query;
	}
	

}
