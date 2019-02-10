package com.webCrawl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.UnsupportedMimeTypeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class WebCrawlerServiceTest {
	
	@Autowired
	private WebCrawlerService crawler;
	
		@Test
		public void getChildPageUrlsTest() throws MalformedURLException {
			Set<URL> crawlUrl = new HashSet<URL>();
			crawlUrl.add(new URL("https://www.pru.co.uk/pensions-retirement/approaching-retirement/"));
			//https://www.pru.co.uk/pensions-retirement/approaching-retirement/
			try {
				crawler.getChildPageUrls(crawlUrl);
			} catch (UnsupportedMimeTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

}
