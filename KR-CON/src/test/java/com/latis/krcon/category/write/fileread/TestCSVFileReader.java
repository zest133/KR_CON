package com.latis.krcon.category.write.fileread;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.latis.krcon.category.dto.CategoryDTO;


@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/root-context.xml", 
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestCSVFileReader {

	private String filePath = null;
	
	
	
	@Test
	public void readFile(){
//		 this.getClass().getre
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("csv/TocData.csv");
//		Resource resource = new ClassPathResource("/cvs/TocData.csv");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
//		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		ArrayList<CategoryDTO> list = new ArrayList<CategoryDTO>();
		try {
	 
//			br = new BufferedReader(new FileReader(filePath));
			int count = 1;
			while ((line = br.readLine()) != null) {
	 
			        // use comma as separator
				String[] data = line.split(cvsSplitBy);
				CategoryDTO dto = new CategoryDTO();
				dto.setTitle(data[0]);
				dto.setFileName(data[1]);
				dto.setCategory(data[2]);
				dto.setFullPath(data[3]);
				dto.setSequence(Integer.parseInt(data[4]));
				dto.setIndex(count);
				count++;
				list.add(dto);
	 
			}
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
//		return list;
	 
	}


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
