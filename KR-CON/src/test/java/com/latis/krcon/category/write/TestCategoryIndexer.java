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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;



import com.latis.krcon.category.dto.CategoryDTO;


@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestCategoryIndexer {

	
	private Directory dir = null;
	
	@Autowired
	private WhitespaceAnalyzer whitespaceAnalyer;
	
	@Autowired
	private StandardAnalyzer standardAynalyzer;
	
	private IndexWriter writer;
	
	
	private CategoryDTO dto = null;
	private CategoryDTO dto1 = null;
	private CategoryDTO dto2 = null;
	
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
		String path = "C:/categoryindex";
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
		
		dto = new CategoryDTO();
		dto.setCategory("root");
		dto.setFileName("pt01.htm");
		dto.setFullPath("/Basic/");
		dto.setIndex(0);
		dto.setSequence(0);
		dto.setTitle("Basic");
		
		
		dto2 = new CategoryDTO();
		dto2.setCategory("Basic");
		dto2.setFileName("pt01.html");
		dto2.setFullPath("/Basic/Introduction");
		dto2.setIndex(1);
		dto2.setSequence(0);
		dto2.setTitle("Introduction");
		
//		dto1 = mock(CategoryDTO.class);
//		when(dto1.getCategory()).thenReturn("Basic");
//		when(dto1.getFileName()).thenReturn("introduction.htm");
//		when(dto1.getFullPath()).thenReturn("Basic");
//		when(dto1.getIndex()).thenReturn(1);
//		when(dto1.getSequence()).thenReturn(0);
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
	public void testAddDocument() throws CorruptIndexException, IOException{
		addDocument(dto);
		addDocument(dto2);
		writeClose();
	}
	
	
	
	
}
