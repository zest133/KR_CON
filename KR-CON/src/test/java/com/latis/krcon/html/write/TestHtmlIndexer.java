package com.latis.krcon.html.write;

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

@ContextConfiguration(locations={
"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestHtmlIndexer {

	
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
	
	@Value("${fileindex}")
	private String path;
	
	
	@Before
	public void setup() throws IOException, InterruptedException{
//		String path = "D:/dev/HtmlIndex";
		
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
			Thread.sleep(10);
		}
	}
	
	public void addDocument(HtmlDTO dto){
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
	public void testAddDocument() throws CorruptIndexException, IOException, SAXException, TikaException{
		URL url = this.getClass().getClassLoader().getResource("html/test.json"); // 이부분 수정. 
		String path = url.getPath();
		File file = new File(path);
		
		
		JSONParser parser = new JSONParser();
		try {
			 
			Object obj = parser.parse(new FileReader(path));
	 
			JSONObject jsonObject = (JSONObject) obj;
	 
			
			Iterator<String> keys =  jsonObject.keySet().iterator();
			while(keys.hasNext()){
				String key = keys.next();
				JSONObject valueObj =  (JSONObject) jsonObject.get(key);
				String filepath = valueObj.get("FilePath").toString();
				String CATEGORY_TEXT_ID = valueObj.get("CATEGORY_TEXT_ID").toString();
				String breadcrumb = valueObj.get("Breadcrumb").toString();
				String CATEGORY_TREE = valueObj.get("CATEGORY_TREE").toString();
				String CATEGORY_ID = valueObj.get("CATEGORY_ID").toString();
				String LOCALE_KEY = valueObj.get("LOCALE_KEY").toString();
				String CATEGORY_TITLE = valueObj.get("CATEGORY_TITLE").toString();
				String CATEGORY_DESC = valueObj.get("CATEGORY_DESC").toString();
//				String filepath = valueObj.get("FilePath").toString();
//				String filepath = valueObj.get("FilePath").toString();
				
				
				
				HtmlDTO dto = new HtmlDTO();
				
				dto.setCategoryTextId(Integer.parseInt(CATEGORY_TEXT_ID));
				dto.setCategoryTree(CATEGORY_TREE);
				dto.setBreadcrumb(breadcrumb);
				dto.setCategoryId(Integer.parseInt(CATEGORY_ID));
				dto.setLocaleKey(LOCALE_KEY);
				dto.setCategoryTitle(CATEGORY_TITLE);
				dto.setCategoryDesc(CATEGORY_DESC);
				
				
				url = this.getClass().getClassLoader().getResource("html/"+filepath); // 이부분 수정. 
				ArrayList<String> list = htmlParser.htmlParser(url.getPath());
				
				dto.setText(list.get(0));
				dto.setHtml(list.get(1));
				
				addDocument(dto);
			}
			
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	 
		
//		JSONObject jsonObj = new JSONObject(file);
//		Iterator<String> keys =  jsonObj.keys();
//		while(keys.hasNext()){
//			String key = keys.next();
//		}
		
//		htmlParser.setPath("html/toc.json");
		File[] files = htmlParser.getFileList();
		
		
	}
	
	@After
	public void tearDown() throws CorruptIndexException, IOException{
		writeClose();
	}
	
}
