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
import org.apache.lucene.analysis.ReusableAnalyzerBase.TokenStreamComponents;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.util.Version;

public class CustomXmlAnalyzer2 extends ReusableAnalyzerBase{

	 private final Version version;

	    public CustomXmlAnalyzer2(final Version version) {
	        super();
	        this.version = version;
	    }

		@Override
		protected TokenStreamComponents createComponents(String fieldName,
				Reader reader) {
			// TODO Auto-generated method stub
			final Tokenizer source = new WhitespaceTokenizer(version, reader);
			TokenStream filter = new LowerCaseFilter(version, source);
			List<String> words = new ArrayList<String>();
			Set<Object> stopWords = StopFilter.makeStopSet(version, words, true);
			filter = new StopFilter(version, filter, stopWords);
	        return new TokenStreamComponents(source, filter);
		}


}
