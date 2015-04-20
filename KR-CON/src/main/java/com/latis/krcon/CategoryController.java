package com.latis.krcon;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.latis.krcon.category.dto.CategoryDTO;

@Controller
public class CategoryController {

	/*
	 * dynatree JSON 구조
	 * 
	 * [ { "title": "Folder 2", "isFolder": true, "key": "folder2", "children":
	 * [ {"title": "Sub-item 2.1"} ] }, {"title": "Item 5"} ]
	 */

	@RequestMapping(value = "/root_category.do")
	public @ResponseBody String getRootCategory() throws JSONException {
//		ArrayList<JSONObject> returnList = new ArrayList<JSONObject>();
		
		JSONArray returnList = new JSONArray();

		JSONObject jsonObject = new JSONObject();

		JSONArray array = new JSONArray();


		jsonObject.put("title", "Folder 1");
		jsonObject.put("isFolder", "true");
		jsonObject.put("key", "folder1");

		array.put(new JSONObject("{\"title\": \"Sub 1\"}"));

		jsonObject.put("children", array);

		returnList.put(jsonObject);

		/*
		 * root 로 찾는다
		 */

		return returnList.toString();
	}

	@RequestMapping(value = "/sub_category.do")
	public ArrayList<JSONObject> getSubCategory(@PathVariable String parent) {

		ArrayList<JSONObject> returnList = new ArrayList<JSONObject>();

		/*
		 * parent 로 찾는다
		 */

		return returnList;
	}
}
