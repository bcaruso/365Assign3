/*
*	
*	CSC 365 - Assignment 3 - WebGRAPH
*
*	A EDGE IN A GRAPH CONTAINS A NODE and WEIGHT (based on Similarity Metric^^).
*
*	^^ Using Histograms to represent pages and comparing the two Histograms.
*
*	Brandon Caruso
*
*/

import java.io.Serializable;
import java.lang.Comparable;

public class Edge implements Serializable, Comparable<Edge>{

	// This is the Node connected to.
	private Node node;
	
	private double similarity;
	
	public Edge ( Node node , double sim ){
		this.node = node;	
		this.similarity = sim;
	}
	
	public Node getNode(){
		return node;
	}
	
	public double getSimilarity(){
		return similarity;
	}
	
	@Override
	public int compareTo(Edge e2){
		return this.node.compareTo(e2.getNode());
	}
	
	@Override
	public String toString(){
		return "Node: "+node.getTitle()+" Similarity: "+ similarity;
	}
	
}