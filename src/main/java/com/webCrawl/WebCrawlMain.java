package com.webCrawl;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan({"com.webCrawl"})
public class WebCrawlMain extends SpringBootServletInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(WebCrawlMain.class);	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebCrawlMain.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(WebCrawlMain.class, args);
		try {
			if(args != null) {
			long startTime = System.currentTimeMillis();	
			LOG.info("Start Time {}", System.currentTimeMillis());	
			WebCrawlerService crawler = new WebCrawlerService(new URL("https://www.prudential.co.uk/"));
			crawler.writeToTextFile();
			
			LOG.info("Time Taken for the process " + (System.currentTimeMillis() - startTime)/1000 +" Seconds");
			}else {
				LOG.info("Please provide the Domain to get the site map Ex :{}","https://www.prudential.co.uk/");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
