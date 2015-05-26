package com.latis.krcon;

import java.io.Reader;
import java.util.Set;

import org.apache.lucene.analysis.CharTokenizer;
import org.apache.lucene.analysis.LetterTokenizer;
import org.apache.lucene.util.Version;

import com.google.common.collect.ImmutableSet;

public class XMLTokenizer2 extends LetterTokenizer{

	final static Set<Character> chars = ImmutableSet.of('/', '>');
	
	public XMLTokenizer2(Version matchVersion, Reader input) {
		super(matchVersion, input);
	}
	
	@Override
	protected boolean isTokenChar(int ascii) {
		System.out.println(ascii);
		char c = (char) ascii;
		return !(Character.isWhitespace(c) || chars.contains(c));
	}

	
	@Override
	protected int normalize(int c) {
		// TODO Auto-generated method stub
		return super.normalize(c);
	}
	

}
