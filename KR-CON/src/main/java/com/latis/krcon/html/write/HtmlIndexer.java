package com.latis.krcon.html.write;


import java.io.File;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.TieredMergePolicy;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import com.latis.krcon.html.dto.HtmlDTO;
import com.latis.krcon.html.parser.HtmlWithTikaParser;



public class HtmlIndexer {

	
	private Directory dir = null;
	
	@Autowired
	private WhitespaceAnalyzer whitespaceAnalyer;
	
	@Autowired
	private StandardAnalyzer standardAynalyzer;
	
	private IndexWriter writer;
	
	@Autowired
	private HtmlWithTikaParser htmlParser;
	
	
	private String path;
	
	public HtmlIndexer() {
		// TODO Auto-generated constructor stub
	}
	
	
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
	
	public void init() throws IOException, InterruptedException{
//		String path = "D:/dev/HtmlIndex";
		dir = FSDirectory.open(new File(path));
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_36,
				standardAynalyzer);
		
		
		iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		TieredMergePolicy tmp = new TieredMergePolicy();
		Integer maxMergeAtOnce = 300;
		Integer segmentsPerTier = 300;
		if(maxMergeAtOnce != null){
			tmp.setMaxMergeAtOnce(maxMergeAtOnce);
		}
		if(segmentsPerTier != null){
			tmp.setSegmentsPerTier(segmentsPerTier);
		}				
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
	
	
	public void insertDocument() throws CorruptIndexException, IOException, SAXException, TikaException{
		htmlParser.setPath(path);
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


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}
	

	
	
	
	
}
