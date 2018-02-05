/**
 * 
 */
package hr.tennis.bot.fetcher.service;

import hr.tennis.bot.fetcher.FetchResults;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.christianschenk.simplecache.SimpleCache;

/**
 * @author Dajan
 *
 */
public class WebFetcherService {
	
	private static final SimpleCache<String> cache = new SimpleCache<String>(3600);

	private static final Log log = LogFactory.getLog(FetchResults.class);

	private static long lastFetchTime = 0;
	private final static long SLEEP = 400;  //milliseconds

	public static String fetchHTML(String url) throws IOException{
		try {
			return fetchHTML(new URL(url));
		} catch (MalformedURLException e) {
			//ignorable
			return null;
		}
	}

	public static String fetchHTML(URL url) throws IOException{
		
		String cached = cache.get(url.toString());
		if(cached!=null){
			log.debug(String.format("fetching url %s from cache",url));
			return cached;
		}

		long fetchTime = System.currentTimeMillis();
		long diff = fetchTime - lastFetchTime;

		if(diff < SLEEP) {
			try {
				log.debug(String.format("sleeping %d ms",SLEEP - diff));
				Thread.sleep(SLEEP - diff);
			} catch (InterruptedException e) {
				e.printStackTrace();
				//ignorable
			}
		}
		lastFetchTime = System.currentTimeMillis();
		log.debug(String.format("fetching url %s",url));

		String content = null;
		URLConnection connection =  url.openConnection();
		Scanner scanner = new Scanner(connection.getInputStream());
		scanner.useDelimiter("\\Z");
		content = scanner.next();
		
		if(url.toString().contains("ranking")){
			cache.put(url.toString(),content);
		}
		return content;
	}
}
