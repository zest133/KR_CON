package com.latis.krcon;

import java.io.Reader;
import java.util.Set;

import org.apache.lucene.analysis.CharTokenizer;
import org.apache.lucene.util.Version;

import com.google.common.collect.ImmutableSet;

public class XMLTokenizer extends CharTokenizer{

	final static Set<Character> chars = ImmutableSet.of('/', '>');
	
	public XMLTokenizer(Version matchVersion, Reader input) {
		super(matchVersion, input);
	}
	
	@Override
	protected boolean isTokenChar(int ascii) {
		System.out.println(ascii);
		char c = (char) ascii;
		return !(Character.isWhitespace(c) || chars.contains(c));
	}

	

}
