package com.webCrawl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.webCrawl")
public class WebCrawlerService {
	
	private final Set<URL> links;
	private final long startTime;
	private static final Logger LOG = LoggerFactory.getLogger(WebCrawlerService.class);

	public WebCrawlerService(final URL inputDomain) {
		
		this.links = new HashSet<URL>();
		this.startTime = System.currentTimeMillis();
		try {
		getChildPageUrls(prepareUrls(inputDomain));
		}catch(UnsupportedMimeTypeException e) {
			LOG.info("Skipped invalid url");
		}
		
	}

	private static Set<URL> prepareUrls(final URL startURL) {
		final Set<URL> crawlUrl = new HashSet<URL>();
		crawlUrl.add(startURL);
		return crawlUrl;
	}

	public Set<URL> getChildPageUrls(final Set<URL> URLS) throws UnsupportedMimeTypeException {
		URLS.removeAll(this.links);
		if (!URLS.isEmpty()) {
			final Set<URL> newURLS = new HashSet<URL>();
			try {
				this.links.addAll(URLS);
				for (final URL url : URLS) {
					if(checkValidChildUrl(url.toString()) ) {
						LOG.info("time = " + (System.currentTimeMillis() - this.startTime) + " connect to : " + url);
						final Document document = Jsoup.connect(url.toString()).get();
					final Elements linksOnPage = document.select("a[href]");
					for (final Element page : linksOnPage) {
						final String urlText = page.attr("abs:href");
						if(checkValidChildUrl(urlText)) {
						final URL discoveredURL = new URL(urlText);
						newURLS.add(discoveredURL);
							}
						}
					}
				}
			} catch (Exception e) {
				LOG.info("ONE url found invalid while crawling");
			}
			getChildPageUrls(newURLS);
		}
		return links;
	}

	public void writeToTextFile() throws IOException {
		final File tmpFile = File.createTempFile("crawlResults", ".out");
		LOG.info("Creating a txt file with all results in path {}",tmpFile.getAbsolutePath());
		try (final FileWriter writer = new FileWriter(tmpFile)) {
			for (final URL url : this.links) {
				writer.write(url + "\n");
			}
		}
	}

	public enum excludeCrawling {
		 youtube,
		 twitter,
		 facebook,
		 instagram,
		 pinterest,
		 nature,
		 messenger,
		 investis,		 
		 mandg,
		 jackson,
		 library,
		 eastspring,
		 fundslibrary,
		 mandgprudential,
		 linkedin,
		 unbiased,
		 google,
		 ageuk,
		 samaritans,
		 moneyadviceservice,
		 linkassetservices,
		 taylorandfrancis,
		 mot,
		 linkprocessagent,
		 eoswetenschap,
		 ncbi,
		 wikimedia,
		 wikipedia,
		 widerimage,
		 agency,
		 citicprufunds,
		 scientificamerican,
		 pixabay,
		 pensionsadvisoryservice,
		 refinitiv,
		 thomsonreuters,
		 greenhousesports,
		 seattletimes,
		 nytimes,
		 cnbc,
		 getsafeonline,
		 apprenticeshipsinscotland,
		 reuters,
		 yahoo,
		 funds,
		 thepensionsregulator
	 }


	public boolean checkValidChildUrl(String childUrl) {
		try {
			
			String patter = "^(http|https)://.*$";
			if (!"".equals(childUrl) && childUrl.matches(patter) && !childUrl.toString().contains(".pdf")) {
				URI uri = new URL(childUrl).toURI();
				for (excludeCrawling links : excludeCrawling.values()) {

					if (uri.toString().contains(links.toString())) {
						return false;
					}
				}
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;

	}
	
}
