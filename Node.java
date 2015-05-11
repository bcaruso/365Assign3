/*
*
*	
*	CSC 365 - Assignment 3 - WebGRAPH
*
*	A NODE IN A GRAPH CONTAINS A STRING URL and A LIST OF EDGES.
*
*	Brandon Caruso
*
*
*
*/

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.lang.Comparable;

public class Node implements Serializable,Comparable<Node> {

	private String title;
	
	private boolean visited = false;
	
	private double distance;
	private int priorityIndex;
	private Node previous;
	
	private Set<Edge> edges = new HashSet<Edge>();
	
	public Node (String title){
		this.title = title;
	}

	public void addEdge(Node n, double sim){
		Edge e = new Edge(n,sim);
		if(!edges.contains(e)){
			edges.add(e);		
		}
	}

	public String getTitle(){
		return title;
	}
	
	public boolean visited(){
		return visited;
	}
	
	public void visit(){
		visited = true;
	}
	
	public void unvisit(){
		visited = false;
	}

	public Node getPreviousNode(){
		return previous;
	}
	
	public void setPreviousNode(Node n){
		previous = n;
	}
	
	public int getPriorityIndex(){
		return priorityIndex;
	}
	
	public void setPriorityIndex(int i){
		priorityIndex = i;
	}
	
	public double getDistance(){
		return distance;
	}
	
	public void setDistance(double d){
		distance = d;
	}
	
	public Set<Edge> getEdges(){
		return edges;
	}


	@Override
	public int compareTo(Node n2){
		return this.title.compareTo(n2.getTitle());
	}
	
	@Override
	public String toString(){
		return "\nTitle: "+title+" \nEdges: "+ edges;
	}
	
}