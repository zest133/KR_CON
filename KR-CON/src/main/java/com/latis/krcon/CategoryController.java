package com.latis.krcon;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.latis.krcon.category.dto.CategoryDTO;

@Controller
public class CategoryController {
	@RequestMapping(value = "/root_category.do", method = RequestMethod.POST)
	public ArrayList<CategoryDTO> getRootCategory() {

		ArrayList<CategoryDTO> returnList = new ArrayList<CategoryDTO>();
		
		/*
		 * root 로 찾는다
		 */
		
		CategoryDTO categoryDTO = new CategoryDTO();
		
		categoryDTO.setCategory("root");
		categoryDTO.setFileName("pt01.htm");
		categoryDTO.setFullPath("/Basic/");
		categoryDTO.setIndex(0);
		categoryDTO.setSequence(0);
		categoryDTO.setTitle("Basic");
		
		returnList.add(categoryDTO);

		return returnList;
	}

	@RequestMapping(value = "/sub_category.do", method = RequestMethod.POST)
	public ArrayList<CategoryDTO> getSubCategory(@PathVariable String parent) {
		
		ArrayList<CategoryDTO> returnList = new ArrayList<CategoryDTO>();
		
		/*
		 * parent 로 찾는다
		 */

		return returnList;
	}
}
