/*
*
*	
*	CSC 365 - Assignment 3 - WebGRAPH
*
*	THIS IS AN IMPLEMENTATION OF A PRIORITY TO BE USED IN DIJKSTRA'S ALGORITHM
*
*	Brandon Caruso
*
*
*
*/

import java.util.ArrayList;

public class PriorityQueue{

	ArrayList<Node> heap = new ArrayList<Node>();
	
	public PriorityQueue(){
		heap.add(new Node("EMPTY"));
	}
	
	public void insert(Node n){
		heap.add(n);
		n.setPriorityIndex(heap.size()-1);
		siftup(n.getPriorityIndex());
	}
	
	public Node peek(){
		return heap.get(1);
	}
	
	public Node next(){
		Node next = heap.get(1);
		if(heap.size()-1 > 1 ){
			heap.set(1,heap.remove(heap.size()-1));
			siftdown(1);
			return next;
		}else{
			heap.remove(heap.get(1));
			return next;
		}
		
	}
	
	public void siftup(int k){
		while (k >1 && heap.get(k/2).getDistance() > heap.get(k).getDistance()){
			exchange(k,k/2);
			k = k/2;
		}
	}
	
	public void siftdown(int k){
		while (2*k <= heap.size()-1){
			int j = 2*k;
			if(j < heap.size()-1 && heap.get(j).getDistance() > heap.get(j+1).getDistance()){
				j++;
			}
			if (heap.get(k).getDistance() <= heap.get(j).getDistance()) break;
			exchange(k,j);
			k = j;
		}
	}
	
	public void changePriority(Node n, double distance){
		int nLocation = n.getPriorityIndex();
		if(nLocation >= 0){
			Node node = heap.get(nLocation);
			if(node.getDistance() > distance){
				node.setDistance(distance);
				siftup(nLocation);
			}else{
				node.setDistance(distance);
				siftdown(nLocation);
			}
		}
	}
	
	public void exchange(int p1, int p2){
		Node n = heap.get(p1);
		heap.get(p1).setPriorityIndex(p2);
		heap.get(p2).setPriorityIndex(p1);
		heap.set(p1,heap.get(p2));
		heap.set(p2,n);
	}
		
	public boolean isEmpty(){
		return heap.size() == 1;
	}

}