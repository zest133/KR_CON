package com.latis.krcon.logger;

import java.io.FileNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class LoggerTest {
	
	
	private Logger logger = LoggerFactory.getLogger(LoggerTest.class);
	
	@Test
	public void log(){
		try {
			Log4jConfigurer.initLogging("classpath:log4j.properties");
			
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		
		
		String logText = "LOG";
		int logNum = 1;
		
		logger.info("String {}", logText);
		logger.info("int {}", logNum);
		
		logger.error("Error {}", "error");
		
		
	}
}
