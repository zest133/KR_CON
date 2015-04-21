package com.latis.krcon.html.sort;

import java.util.ArrayList;

import org.apache.lucene.search.ChainedFilter;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

public class HtmlSort {

	private ArrayList<SortField> sortList = null;
	

	public HtmlSort(){
		sortList = new ArrayList<SortField>();
	}
	
	
	public ArrayList<SortField> getSortList() {
		return sortList;
	}

	public void addSortList(SortField sort) {
		this.sortList.add(sort);
	}
	
	public void setSortList(ArrayList<SortField> sortList) {
		this.sortList = sortList;
	}
	
	public Sort getSort(){
		/**
		 * new Sort(new SortField[] {
//					SortField.FIELD_SCORE,
//					new SortField("category", SortField.STRING) })
		 */
		if(sortList.size() >0 ){
			SortField[] sortFieldArr = sortList.toArray(new SortField[sortList.size()]);
			return new Sort(sortFieldArr);
		}
		return null;
	}

}
