/*
*	URLHandler CLASS - CSC 365 - Assignment 1
*
*	Author: Brandon Caruso
*   Handles creating Histograms from URLs.
*/

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import java.net.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Scanner;

public class URLHandler {
	
		/** 
		*	stopWords	
		*	This array is searched prior to inserting a word into the Histogram
		*/	
	
		private static String[] stopWords = new String[]{"a","able","about","across","after","all",
		"almost","also","am","among","an","and","any","are","as","at","be","because","been","but","by","can","cannot","could",
		"dear","did","do","does","either","else","ever","every","for","from","get","got","had","has","have","he","her","hers",
		"him","his","how","however","i","if","in","into","is","it","its","just","least","let","like","likely","may","me","might",
		"most","must","my","neither","no","nor","not","of","off","often","on","only","or","other","our","own","rather","said",
		"say","says","she","should","since","so","some","than","that","the","their","them","then","there","these","they",
		"this","tis","to","too","twas","us","wants","was","we","were","what","when","where","which","while","who","whom",
		"why","will","with","would","yet","you","your"};
	
		/* STOP WORDS ARRAY DONE */	
	
		public static Histogram createHistogram(URL url) throws IOException, URISyntaxException{
			
			Histogram h = new Histogram(url,128);
			
			Document doc = Jsoup.parse(url.openStream(),null,url.toURI().toString());
			
			String dirtyDoc = doc.text();
			//Remove Punctuation
			String cleanDoc = dirtyDoc.replaceAll("[^a-zA-Z ]","");
			//System.out.println(cleanDoc);
			// Create Histogram
			
			Scanner s = new Scanner(cleanDoc);
			
			while(s.hasNext()){
				String str = s.next();
				if(!isStopWord(str))
					h.add(str);
			}	
			
			s.close();
			
			return h;
			
		}
		
		private static boolean isStopWord(String key){
			return Arrays.binarySearch(stopWords,key.toLowerCase()) >= 0 ;
		}
		
}