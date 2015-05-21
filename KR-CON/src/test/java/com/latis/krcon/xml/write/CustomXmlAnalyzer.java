package com.latis.krcon.xml.write;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.KeywordTokenizer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.ReusableAnalyzerBase;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.WhitespaceTokenizer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.util.Version;

public class CustomXmlAnalyzer extends Analyzer{

	 private final Version version;

	    public CustomXmlAnalyzer(final Version version) {
	        super();
	        this.version = version;
	    }

		@Override
		public TokenStream tokenStream(String fieldName, Reader reader) {
			// TODO Auto-generated method stub
			Tokenizer tokenizer = (Tokenizer) getPreviousTokenStream();
			TokenStream filter = null;
			if (tokenizer == null) {
				tokenizer = new WhitespaceTokenizer(version, reader);
				filter = new LowerCaseFilter(version, tokenizer);
				List<String> words = new ArrayList<String>();
				Set<Object> stopWords = StopFilter.makeStopSet(version, words, true);
				filter = new StopFilter(version, filter, stopWords);
//				filter = new LowerCaseFilter(Version.LUCENE_36, filter);
				setPreviousTokenStream(tokenizer);
				return filter;
			} else {
				
				try {
					tokenizer.reset(reader);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return tokenizer;
			}
		}


}
