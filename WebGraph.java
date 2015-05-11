/*
*	WebGraph CLASS - CSC 365 - Assignment 3
*
*	Author: Brandon Caruso
*   Runs the show!
*/


import javax.swing.*;


public class WebGraph {

	static Graph g ;
	
	public static void main(String[] args){
			
			g = GraphLoader.getGraph();
			
			
			WebGraphGUI gui = new WebGraphGUI();
			
			// Establish Event Dispatch Thread and Create GUI
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					gui.createGUI();
				}
			});
			
		}
}