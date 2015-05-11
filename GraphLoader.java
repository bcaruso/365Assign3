/*
*
*	
*	CSC 365 - Assignment 3 - WebGRAPH
*
*	READS FROM FILE OR LOADS IN GRAPH.
*
*	Brandon Caruso
*
*
*
*/

import java.io.*;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import java.net.*;


public class GraphLoader {

	private static String[] skipLinkPiecesWikipedia = new String[]{"#",".tif", ".tiff", ".gif",".jpeg", ".jpg", ".png", ".pdf"};
	private static ArrayDeque<String> needToInsert = new ArrayDeque<String>();
	private static int pageCount = 0;

	private static Graph g = null;
	public static Graph getGraph(){
		 File f = new File("graph.ser");
		 if(f.exists()){
			try{
			  ObjectInputStream input = new ObjectInputStream(new FileInputStream(f));
			  g = (Graph) input.readObject();
			  System.out.println("<<<READ>>>>");
			  input.close();
			} catch(ClassNotFoundException ex){
				ex.printStackTrace();
			} catch(IOException ex){
				ex.printStackTrace();
			}
		 }else{
		 	g = new Graph();
		 	// Establish Root Page and Root Histogram
		 	needToInsert.addLast("http://en.wikipedia.org/wiki/Typography");
		 	while (!needToInsert.isEmpty() && pageCount < 500){
		 		pageCount++;
		 		insertPage(needToInsert.pop());
		 	}			
		 	
		 	System.out.println("Page Count: "+pageCount);
		 	
			try {
			  ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(f));
			  output.writeObject(g);
			  output.close(); 
			  System.out.println("<<<SAVED>>>>");
			} catch(IOException ex){
				ex.printStackTrace();
			}
		 }
		 return g;
	}
	
	private static void insertPage(String url){
	
			try{
				
				Document doc = Jsoup.connect(url).get();
				Elements i = doc.select("h1#firstHeading");
				String nodeTitle = i.text();
				Elements content = doc.select("div#bodyContent.mw-body-content");
				Elements links = content.select("p a[href]");
				Histogram root = URLHandler.createHistogram(new URL(url));
				g.addNode(nodeTitle);
				Node current = g.get(nodeTitle);
				int contribution = 0;
				for(int j = 0; j< links.size() && contribution < 10; j++){ 
						Histogram temp = URLHandler.createHistogram(new URL(links.get(j).attr("abs:href")));
						Document neighbordoc = Jsoup.connect(links.get(j).attr("abs:href")).get();
						Elements title = neighbordoc.select("h1#firstHeading");
						String neighborNodeTitle = title.text();
						if(!wikipediaBadLinkFilter(links.get(j).attr("abs:href"))){
							Node neighbor = new Node(neighborNodeTitle);
							if(g.get(neighbor) == null){
								needToInsert.addLast(links.get(j).attr("abs:href"));
							}						
							double sim = HistogramSimilarity.similarMetric(root,temp);
							g.addEdge(current, 1/sim ,neighbor);
							System.out.println("Added: "+current.getTitle() +" -- "+1/sim+" --> "+neighbor.getTitle());
							contribution++;
						}
					}
				
			} catch(IOException ex){
				ex.printStackTrace();
			} catch(URISyntaxException ex){
				ex.printStackTrace();
			}
	
			
	}
	
	private static boolean wikipediaBadLinkFilter(String link){
			if(link.indexOf("wikipedia.org") >=0){
				if(link.indexOf("en.wikipedia.org") < 0){
				System.out.println("PAGE IN DIFFERENT LANGUAGE - SKIPPED: "+link);
				return true;
				}
				
			}
			String lookingForColon = link.replaceFirst("http://","");
			if(lookingForColon.indexOf(":") >=0){
				System.out.println("COLON SKIPPED: "+link);
				return true;	
			}
			
			if(link.indexOf("Category:") >=0){
				
				System.out.println("Category SKIPPED: "+link);
				return true;	
			}
			
			if(link.indexOf("http://en.wikipedia.org/wiki/") <0){
				
				System.out.println("NOT WIKI PROPER - SKIPPED: "+link);
				return true;	
			}
			
			for(int i = 0; i<skipLinkPiecesWikipedia.length; i++){
				if(link.indexOf(skipLinkPiecesWikipedia[i])>=0){
					System.out.println("SKIPPED: "+link);
					return true;
				}
			}
			
			return false;
		}
	
}
