package com.latis.krcon.category.write.fileread;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.latis.krcon.category.dto.CategoryDTO;

public class CSVFileReader {

	private String filePath = null;
	
	public CSVFileReader(){
		
	}
	
	
	public ArrayList<CategoryDTO> readFile(){
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		ArrayList<CategoryDTO> list = new ArrayList<CategoryDTO>();
		try {
	 
			br = new BufferedReader(new FileReader(filePath));
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
		
		return list;
	 
	}


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
