/*
*	HISTOGRAM SIMILARITY CLASS - CSC 365 - Assignment 1
*
*	Author: Brandon Caruso
*	This class contains static methods that performs Cosine Similarity Calculation for a
*	Query URL and ArrayList of Histograms.
*
*/

import java.util.Iterator;
import java.io.IOException;
import java.util.ArrayList;
import java.net.URISyntaxException;
import java.util.function.Consumer;
import java.util.function.BiFunction;
import java.net.*;

public class HistogramSimilarity {

		private static double vectorLength(Histogram h){
			
			// Super Cool Functional Approach!		
			BiFunction<Double,Entry,Double> sumOfSquares = (Double r, Entry e) -> r + Math.pow(e.getCount(),2);
			return Math.sqrt(h.reduce(0.0,sumOfSquares));
			
		}	
		
		private static double vectorScalar(Histogram h1, Histogram h2){
			
			Histogram smallestHistogram;
			Histogram largestHistogram;
			//System.out.println(h1.getURL().toString() + " : " + h1.getNumberOfEntries() );
			//System.out.println(h2.getURL().toString() + " : " + h2.getNumberOfEntries() );
			//Only go through the smallest Histogram.
			if(h1.getNumberOfEntries() > h2.getNumberOfEntries()){
				smallestHistogram = h2;
				largestHistogram = h1;
			}else{
				smallestHistogram = h1;
				largestHistogram = h2;
			}
			
			// Super Cool Functional Approach!
			BiFunction<Double,Entry,Double> scalarProduct = (Double r, Entry e) -> r + (largestHistogram.getCount(e.getKey()) * e.getCount());
			return 	smallestHistogram.reduce(0.0,scalarProduct);
			
		}
		
		public static double similarMetric(Histogram h1, Histogram h2){
			
			// This is the result just before taking the arccos to find the angle 
			// between two vectors. Don't want the angle just this ratio.
			return vectorScalar(h1,h2)/(vectorLength(h1) * vectorLength(h2));
			
		}
		
		public static Histogram findMostSimilar(String query, ArrayList<Histogram> ref) throws IOException, URISyntaxException{
			
			Iterator<Histogram> it = ref.iterator();
			URL url = new URL(query);
			Histogram queryHistogram = URLHandler.createHistogram(url);
			Histogram mostSimilar = null;
			double maxSimilarity = 0.0;
			
			while (it.hasNext()){
				Histogram refHistogram = it.next();
				double simMet = similarMetric(queryHistogram,refHistogram);
				System.out.println(refHistogram.getURL());
				System.out.println("Current Max: "+ maxSimilarity);
				System.out.println("Similarity Result: " + simMet);
				if( maxSimilarity < simMet){
					maxSimilarity = simMet;
					mostSimilar = refHistogram;
				}
			}
			
			return mostSimilar;
		} 
}