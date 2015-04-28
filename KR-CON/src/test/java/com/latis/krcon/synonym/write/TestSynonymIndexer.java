package com.latis.krcon.synonym.write;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.TieredMergePolicy;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.tika.exception.TikaException;
import org.codehaus.jackson.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;

import com.latis.krcon.html.dto.HtmlDTO;
import com.latis.krcon.html.parser.HtmlWithTikaParser;
import com.latis.krcon.synonym.dto.SynonymDTO;

@ContextConfiguration(locations={
"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSynonymIndexer {

	
private Directory dir = null;
	
//	@Autowired
//	private WhitespaceAnalyzer whitespaceAnalyer;
	
	@Autowired
	private StandardAnalyzer standardAynalyzer;
	
	private IndexWriter writer;
	
	@Autowired
	private HtmlWithTikaParser htmlParser;
	
	@Autowired
	private TieredMergePolicy tmp;
	
	@Value("${synonymindex}")
	private String path;
	
	private String data;
	
	@Before
	public void setup() throws IOException, InterruptedException{
//		String path = "D:/dev/HtmlIndex";
		data = "{"+
		"		   \"ILO\": [                                              "+
		"	       \"International Labour Organisation\",                  "+
		"	       \"InternationalLabourOrganisation\",                    "+
		"	       \"International LabourOrganisation\",                   "+
		"	       \"InternationalLabour Organisation\",                   "+
		"	       \"InternationalLabour\",                                "+
		"	       \"International Labour\"                                "+
		"	   ],                                                          "+
		"	   \"IEC\": [                                                  "+
		"	       \"International Electrotechnical Commission\",          "+
		"	       \"InternationalElectrotechnicalCommission\",            "+
		"	       \"International ElectrotechnicalCommission\",           "+
		"	       \"InternationalElectrotechnical Commission\",           "+
		"	       \"InternationalElectrotechnical\",                      "+
		"	       \"International Electrotechnical\"                      "+
		"	   ],                                                          "+
		"	   \"ITU\": [                                                  "+
		"	       \"International Telecommunication Union\",              "+
		"	       \"InternationalTelecommunicationUnion\",                "+
		"	       \"International TelecommunicationUnion\",               "+
		"	       \"InternationalTelecommunication Union\",               "+
		"	       \"InternationalTelecommunication\",                     "+
		"	       \"International Telecommunication\"                     "+
		"	   ],                                                          "+
		"	                                                               "+
		"	   \"Lifeboat\": [                                             "+
		"	       \"life boat\",                                          "+
		"	       \"lifeboats\",                                          "+
		"	       \"life boats\"                                          "+
		"	   ],                                                          "+
		"	                                                               "+
		"	   \"passenger ship\":[                                        "+
		"	   	\"passengership\",                                         "+
		"	   	\"passengers ship\" ,                                      "+
		"	   	\"passengerships\",                                        "+
		"	   	\"passenger ships\",                                       "+
		"	   ]                                                           "+
		"	}                                                              ";
		dir = FSDirectory.open(new File(path));
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_36,
				standardAynalyzer);
		
		
		iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		iwc.setMergePolicy(tmp);
		
		
		lockChecker();
		writer = new IndexWriter(dir, iwc);
		
		
	}
	public void lockChecker() throws IOException, InterruptedException {
		while(dir.fileExists(IndexWriter.WRITE_LOCK_NAME)){
//			dir.clearLock(name);
//			System.out.println("lock");
			Thread.sleep(10);
		}
	}
	
	public void addDocument(SynonymDTO dto){
		try {
			
			writer.addDocument(dto.convetDocument());
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void writeClose() throws CorruptIndexException, IOException{
		if(writer != null){
			writer.close();
		}
	}
	
	
	@Test
	public void testAddDocument() throws CorruptIndexException, IOException, SAXException, TikaException, ParseException{
		
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(data);
 
		JSONObject jsonObject = (JSONObject) obj;
 
		
		Iterator<String> keys =  jsonObject.keySet().iterator();
		while(keys.hasNext()){
			String key = keys.next();
			JSONArray jsonArr = (JSONArray) jsonObject.get(key);
			SynonymDTO dto = new SynonymDTO();
			for(int i = 0; i < jsonArr.size(); i++){
				
				dto.addSynList(jsonArr.get(i).toString());
			}
			dto.setWord(key);
			addDocument(dto);
		}
			
	 
		
//		JSONObject jsonObj = new JSONObject(file);
//		Iterator<String> keys =  jsonObj.keys();
//		while(keys.hasNext()){
//			String key = keys.next();
//			System.out.println(key);
//		}
		
//		htmlParser.setPath("html/toc.json");
//		File[] files = htmlParser.getFileList();
		
		
	}
	
	@After
	public void tearDown() throws CorruptIndexException, IOException{
		writeClose();
	}
	
}
