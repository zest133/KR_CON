package com.latis.krcon.synonym.dao;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.latis.krcon.synonym.search.SynonymSearch;

public class SynonymSearchDAOImpl implements SynonymSearchDAO{
	@Autowired
	public SynonymSearch synonymSearch;
	
	@Override
	public String checkSynonymWord(String searchWord) {
		// TODO Auto-generated method stub
		
		String returnVal = "";
		try {
			synonymSearch.init();
			if(searchWord != null && !searchWord.equals("")){
				returnVal = synonymSearch.checkSynonymWord(searchWord);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			synonymSearch.close();
		}
		
		
		return returnVal;
		
	}

}
