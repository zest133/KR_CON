package com.latis.krcon.category.write;

import java.io.File;
import java.io.IOException;

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
import org.springframework.beans.factory.annotation.Autowired;

import com.latis.krcon.category.dto.CategoryDTO;



public class CategoryIndexer {

	
	private Directory dir = null;
	
	@Autowired
	private WhitespaceAnalyzer whitespaceAnalyer;
	private IndexWriter writer;
	
	
	/**
	 * 
	 * #open mode (create,append,create_or_append)
	OPEN_MODE	=	create_or_append
	#OPEN_MODE	=	create
	
	#merge setting
	MERGE_POLICY	=	true
	MAX_MERGE_AT_ONCE	=	300
	SEGMENTS_PER_TIER	=	100
	 */
	
	
	public CategoryIndexer(String indexPath) throws IOException, InterruptedException{
		dir = FSDirectory.open(new File(indexPath));
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_36,
				whitespaceAnalyer);
		
		
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

	
	
	
	
	public void makeIndex(){
		
	}



	public void lockChecker() throws IOException, InterruptedException {
		while(dir.fileExists(IndexWriter.WRITE_LOCK_NAME)){
//			dir.clearLock(name);
//			System.out.println("lock");
			Thread.sleep(10);
		}
	}
	
	public void addDocument(CategoryDTO dto){
		try {
			writer.addDocument(convetDocument(dto));
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
	
	public Document convetDocument(CategoryDTO dto) {
		// TODO Auto-generated method stub
		
		
		Document doc = new Document();
//		NumericField createdAt = new NumericField("createdAt",Field.Store.YES,true);
//		createdAt.setLongValue(this.getCreatedAt());		
//		doc.add(createdAt);		
	
		
		NumericField index = new NumericField("index",Field.Store.YES,true);
		index.setIntValue(dto.getIndex());		
		doc.add(index);		
		
	
		
		
		doc.add(new Field("title",dto.getTitle(),Field.Store.YES,Field.Index.ANALYZED));
		doc.add(new Field("filename", dto.getFileName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("category",dto.getCategory(),Field.Store.YES,Field.Index.NOT_ANALYZED));
		doc.add(new Field("fullPath",dto.getFullPath(),Field.Store.YES,Field.Index.NOT_ANALYZED));
		
		NumericField sequence = new NumericField("sequence",Field.Store.YES,true);
		index.setIntValue(dto.getIndex());		
		doc.add(sequence);
//		doc.add(new Field("sequence",this.getId(),Field.Store.YES,Field.Index.NOT_ANALYZED));
		return doc;
		
		
	}
	
	
	
	
	
}
