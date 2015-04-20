package com.latis.krcon.html.write;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
	
	/**
	 * 
	 * #open mode (create,append,create_or_append)
	OPEN_MODE	=	create_or_append
	#OPEN_MODE	=	create
	
	#merge setting
	MERGE_POLICY	=	true
	MAX_MERGE_AT_ONCE	=	300
	SEGMENTS_PER_TIER	=	100
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	
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
//			System.out.println("lock");
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
		htmlParser.setPath("html/");
		File[] files = htmlParser.getFileList();
		//    
		//   
		HashMap<String, String[]> map = new HashMap<String, String[]>();
		//full path, level seq, total index, parent path, category, lang  
		//KRCON>KR-CON (English)>SOLAS 1974 ***>SOLAS 2015 Consolidated Edition>
		//filename SOLAS 1974 Convention Articles.
		//KRCON>KR-CON (English)>SOLAS 1974 ***>SOLAS 2015 Consolidated Edition>
		//filename article1.
		///KRCON>KR-CON (English)>SOLAS 1974 ***>
		map.put("CACBHJFH.htm",new String[]{"solars/chapter1/partA/item1","0","5","solars/chapter1/partA"});
		map.put("BABBHHBB.htm",new String[]{"solars/chapter1","0","1","solars","chapter1", "en"});
		map.put("CACDHBHA.htm",new String[]{"solars/chapter1/partA/item1","1","6","solars/chapter1/partA"});
		map.put("CACDIAHF.htm",new String[]{"solars/chapter1/partA/item1","2","7","solars/chapter1/partA"});
		map.put("CACEFCHJ.htm",new String[]{"solars/chapter1/partA/item1","3","8","solars/chapter1/partA"});
		map.put("CACFAFBG.htm",new String[]{"solars/chapter1/partA/item1","4","9","solars/chapter1/partA"});
		
		map.put("BABBADDG.htm",new String[]{"solars","0","0","null","solars", "en"} );
		map.put("CACGJDIG.htm",new String[]{"solars/chapter2/partA/item2","2","12","solars/chapter2/partA"});
		map.put("CACHBFHB.htm",new String[]{"solars/chapter2/partA/item2","3","13","solars/chapter2/partA"});
		map.put("CACHDGFD.htm",new String[]{"solars/chapter2/partA/item2","4","14","solars/chapter2/partA"});
		map.put("BABBIBJC.htm",new String[]{"solars/chapter2","1","2","solars", "chapter2","en"});
		map.put("BABCHFBC.htm",new String[]{"solars/chapter1/partA","0","3","solars/chapter1,} );
		map.put("BABCIGEH.htm",new String[]{"solars/chapter2/partA","0","4","solars/chapter2"});
		
		
		map.put("CACFBJHD.htm",new String[]{"solars/chapter2/partA/item2","0","10","solars/chapter2/partA"});
		map.put("CACFEGFG.htm",new String[]{"solars/chapter2/partA/item2","1","11","solars/chapter2/partA"});

		
		
		for(File file : files){
			ArrayList<String> list =  htmlParser.htmlParser(file.getPath());
			Iterator<String> key =  map.keySet().iterator();
			while(key.hasNext()){
				String keyVal = key.next();
				if(file.getName().equals(keyVal)){
					String[] dataArr = map.get(keyVal);
					HtmlDTO dto = new HtmlDTO();
					
					dto.setFilename(file.getName());
					dto.setPath(dataArr[0]);
					dto.setLevelSequence(Integer.parseInt(dataArr[1]));
					dto.setIndex(Integer.parseInt(dataArr[2]));
					dto.setTitle(list.get(0));
					dto.setText(list.get(1));
					dto.setHtml(list.get(2));
					addDocument(dto);
					break;
				}
				
			}
			
			
		}
		writer.commit();
		
	}
	
	@After
	public void tearDown() throws CorruptIndexException, IOException{
		writeClose();
	}
	
	
	
	
	
	
}
