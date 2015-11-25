package com.latis.krcon;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.CharTokenizer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.LowerCaseTokenizer;
import org.apache.lucene.analysis.ReusableAnalyzerBase;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.ReusableAnalyzerBase.TokenStreamComponents;
import org.apache.lucene.analysis.WhitespaceTokenizer;
import org.apache.lucene.analysis.en.EnglishMinimalStemFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class EngSimpleAnalyzer extends ReusableAnalyzerBase {

	private final Version version;

	public EngSimpleAnalyzer(final Version version) {
		super();
		this.version = version;
	}

	@Override
	protected TokenStreamComponents createComponents(final String fieldName,
			final Reader reader) {
		
		
		LowerCaseTokenizer tokenStream = new LowerCaseTokenizer(version,
				reader);
		EnglishMinimalStemFilter filter = new EnglishMinimalStemFilter(tokenStream);
		
//		CharTermAttribute term = filter
//				.addAttribute(CharTermAttribute.class);
//		try {
//			while(tokenStream.incrementToken()){
//				System.out.println(term.toString());
//				break;
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		return new TokenStreamComponents(tokenStream, filter);
//		return result;
	}
	
	
	

}
