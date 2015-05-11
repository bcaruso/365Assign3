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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayDeque;

public class Graph implements Serializable {
	public Map<String,Node> nodes = new HashMap<String,Node>();
	
	public void addNode(String n){
		if(!nodes.containsKey(n)){
			nodes.put(n,new Node(n));
		}
	}
	
	public void addEdge(Node n, double sim, Node e){
		Node left;
		Node right;
		if(!nodes.containsKey(n.getTitle())){
			nodes.put(n.getTitle(),n);
			left = n;
		}else{
			left = get(n.getTitle());
		}
		
		if(!nodes.containsKey(e.getTitle())){
			nodes.put(e.getTitle(),e);
			right = e;
		}else{
			right = get(e.getTitle());
		}
		
		left.addEdge(right,sim);
	}		
	
	public int connected(){
		int count = 0;
		
		for(Node n : nodes.values()){
			n.unvisit();
		}
		
		HashSet<HashSet<Node>> spanningTrees = new HashSet<HashSet<Node>>();
		
		for(Node n : nodes.values()){
			if(!n.visited()){
				ArrayDeque<Node> stack = new ArrayDeque<Node>();
				stack.push(n);
				HashSet<Node> tree = new HashSet<Node>();
				while(!stack.isEmpty()){
					Node p = stack.pop();
					if(!p.visited()){
						p.visit();
						tree.add(p);
 						for(Edge e : p.getEdges()){
							stack.push(e.getNode());
						}
					}else{
						//Merge
						for(HashSet<Node> otherTree : spanningTrees){
							if(otherTree.contains(p)){
								tree.addAll(otherTree);
								spanningTrees.remove(otherTree);
								break;
							}
						}
					}
				}
				spanningTrees.add(tree);
			}
			
		}

		return spanningTrees.size();
	}
	
	public String shortestPath(Node source, Node target){
		PriorityQueue p = new PriorityQueue();
		Node start = get(source);
		Node end = get(target);
		
		if(start == null){
			return "Start point \""+source.getTitle()+"\": Not in Graph.";
		}
		
		if(end == null){
			return "End point \""+ target.getTitle()+"\": Not in Graph.";
		}
		
		start.setDistance(0);
		start.setPreviousNode(null);
		String response = "";
		
		for(Node n : nodes.values()){
			if(n.compareTo(start) != 0){
				n.setDistance(Double.MAX_VALUE);
				n.setPreviousNode(null);
			}
			p.insert(n);
		}
		while (!p.isEmpty()){
			Node current = p.next();
			if(current.compareTo(end) == 0){
				// Found Target
				Node pre = current;
				while(pre != null){
					response = " ---> |  " + pre.getTitle() +"  |"+ response;
					pre = pre.getPreviousNode();
				}
				
			}else{
				for(Edge e : current.getEdges()){
					double altDist = current.getDistance() + e.getSimilarity();
					if (altDist < e.getNode().getDistance()){
						e.getNode().setPreviousNode(current);
						p.changePriority(e.getNode(),altDist);
					}
				}
			}
		}
		
		if(!(response.contains(start.getTitle()) && response.contains(end.getTitle()))){
			return "No path from \""+ start.getTitle()+"\" to \""+ end.getTitle()+"\".";
		}
		return shortestPathPretty(response);
	}
	
	public  String shortestPathPretty(String response){
			
			String workResponse = new String (response);
			String top  = "";
			
			boolean seen = false;
			
			for(int i = 0; i<workResponse.length(); i++){
				if(workResponse.charAt(i)=='|'){
					top += "+";
					if(seen){
						seen = false;
					}else{
						seen = true;
					}
				}else{
					if(seen){
						top += "-";
					}else{
						top += " ";
					}
				}
			}
			
			return top+"\n"+response+"\n"+top;	
		}
		
	public Node get(Node n){
		return nodes.get(n.getTitle());
	}
	
	public Node get(String n){
		return nodes.get(n);
	}
	
	public int numberOfNodes(){
		return nodes.size();
	}
	
	public Collection<Node> getNodes(){
		return nodes.values();
	}
	
	public int numberOfEdges(){
		int numEdges = 0;
		for(Node n : nodes.values()){
			numEdges += n.getEdges().size();
		}
		return numEdges;
	}
	
	@Override
	public String toString(){
		String graph = "";
		for(Node n : nodes.values()){
			graph += n.toString();
		}
		return graph;
	}
	
	
}