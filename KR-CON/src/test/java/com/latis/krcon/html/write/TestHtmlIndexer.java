package com.latis.krcon.html.write;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
		String path = "D:/dev/HtmlIndex";
		dir = FSDirectory.open(new File(path));
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_36,
				standardAynalyzer);
		
		
		iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
//		TieredMergePolicy tmp = new TieredMergePolicy();
//		Integer maxMergeAtOnce = 300;
//		Integer segmentsPerTier = 300;
//		if(maxMergeAtOnce != null){
//			tmp.setMaxMergeAtOnce(maxMergeAtOnce);
//		}
//		if(segmentsPerTier != null){
//			tmp.setSegmentsPerTier(segmentsPerTier);
//		}				
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
			
			
			
			
			
//			Document doc = convetDocument(dto);
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
	
//	public Document convetDocument(CategoryDTO dto) {
//		// TODO Auto-generated method stub
//		
//		
//		Document doc = new Document();
////		NumericField createdAt = new NumericField("createdAt",Field.Store.YES,true);
////		createdAt.setLongValue(this.getCreatedAt());		
////		doc.add(createdAt);		
//	
//		
//		NumericField index = new NumericField("index",Field.Store.YES,true);
//		index.setIntValue(dto.getIndex());		
//		doc.add(index);		
//		
//	
//		
//		
//		doc.add(new Field("title",dto.getTitle(),Field.Store.YES,Field.Index.ANALYZED));
//		doc.add(new Field("filename", dto.getFileName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
//		doc.add(new Field("category",dto.getCategory(),Field.Store.YES,Field.Index.NOT_ANALYZED));
//		doc.add(new Field("fullPath",dto.getFullPath(),Field.Store.YES,Field.Index.NOT_ANALYZED));
//		
//		NumericField sequence = new NumericField("sequence",Field.Store.YES,true);
//		sequence.setIntValue(dto.getIndex());		
//		doc.add(sequence);
////		doc.add(new Field("sequence",this.getId(),Field.Store.YES,Field.Index.NOT_ANALYZED));
//		return doc;
//		
//		
//	}
	
	@Test
	public void testAddDocument() throws CorruptIndexException, IOException, SAXException, TikaException{
		htmlParser.setPath("html/");
		File[] files = htmlParser.getFileList();
		for(File file : files){
			ArrayList<String> list =  htmlParser.htmlParser(file.getPath());
			HtmlDTO dto = new HtmlDTO();
			dto.setFilename(file.getPath());
			dto.setTitle(list.get(0));
			dto.setText(list.get(1));
			
			addDocument(dto);
		}
		writer.commit();
		
	}
	
	@After
	public void tearDown() throws CorruptIndexException, IOException{
		writeClose();
	}
	
	
	
	
}
