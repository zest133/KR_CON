package com.latis.krcon;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.util.Version;

public class XMLAnalyzer extends Analyzer{

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		// TODO Auto-generated method stub
		return new XMLTokenizer(Version.LUCENE_36,reader);
	}
	
	@Override
	public TokenStream reusableTokenStream(String fieldName,
			Reader reader) throws IOException {
		Tokenizer tokenizer = (Tokenizer) getPreviousTokenStream();
		if (tokenizer == null) {
			tokenizer = new XMLTokenizer(Version.LUCENE_36, reader);
			setPreviousTokenStream(tokenizer);
		} else
			tokenizer.reset(reader);
		return tokenizer;
	}


}
