package com.latis.krcon.category.write;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.TieredMergePolicy;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;

import com.latis.krcon.category.dto.CategoryDTO;
import com.latis.krcon.category.write.fileread.CSVFileReader;



public class CategoryIndexer {

	
	private Directory dir = null;
	
	@Autowired
	private StandardAnalyzer standardAnalyer;
	private IndexWriter writer;
	
	
//	@Autowired
//	private CSVFileReader csvFileReader;
	
	private String indexPath = null;
	
	public CategoryIndexer(){
		
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
		dir = FSDirectory.open(new File(indexPath));
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_36,
				standardAnalyer);
		
		
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
	
	private void lockChecker() throws IOException, InterruptedException {
		while(dir.fileExists(IndexWriter.WRITE_LOCK_NAME)){
			Thread.sleep(10);
		}
	}
	
	public void addDocument(ArrayList<CategoryDTO> dtoList){
		try {
			for(CategoryDTO dto : dtoList){
				writer.addDocument(dto.convetDocument());
			}
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
	
	

	public String getIndexPath() {
		return indexPath;
	}

	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}
	
	
	
	
	
}
