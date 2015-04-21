package com.latis.krcon;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class CategoryController {

	/*
	 * dynatree JSON 구조
	 * 
	 * [ { "title": "Folder 2", "isFolder": true, "key": "folder2", "children":
	 * [ {"title": "Sub-item 2.1"} ] }, {"title": "Item 5"} ]
	 */

	@RequestMapping(value = "/root_category.do")
	public @ResponseBody String getRootCategory() {
//		ArrayList<JSONObject> returnList = new ArrayList<JSONObject>();
		
		JSONArray returnList = new JSONArray();

		JSONObject jsonObject = new JSONObject();

		JSONArray array = new JSONArray();

		jsonObject.put("title", "Folder 1");
		jsonObject.put("other", "other");
		jsonObject.put("more", "more");
		jsonObject.put("attr", "attr");
		jsonObject.put("key", "folder1");
		
		jsonObject.put("isFolder", "true");
		jsonObject.put("isLazy", "true");
		/*
		 * root 로 찾는다
		 */
		
		returnList.add(jsonObject);

		return returnList.toString();
	}

	@RequestMapping(value = "/sub_category.do")
	public @ResponseBody String getSubCategory(@RequestParam String key) {
//		System.out.println(key);
		
		JSONArray returnList = new JSONArray();

		JSONObject jsonObject = new JSONObject();

		JSONArray array = new JSONArray();


		jsonObject.put("title", "Folder 1");
		jsonObject.put("isFolder", "true");
		jsonObject.put("key", "folder1");
		jsonObject.put("isLazy", "true");
		jsonObject.put("other", "other");
		jsonObject.put("more", "more");
		jsonObject.put("attr", "attr");




		/*
		 * parent 로 찾는다
		 */
		returnList.add(jsonObject);

		return returnList.toString();
	}
}
