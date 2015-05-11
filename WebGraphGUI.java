/*
*	WebGraphGUI CLASS - CSC 365 - Assignment 3
*
*	Author: Brandon Caruso
*   Sets up GUI and Handles events.
*/

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.Color;
import java.net.URISyntaxException;
import java.net.*;
import java.io.IOException;
import java.util.*;

public class WebGraphGUI {

	// GUI Components
		
	private JFrame frame;
	private JPanel content;
	private JLabel spanLabel;
	private JLabel sIn;
	private JTextField start;
	private JLabel eIn;
	private JTextField end;
	private  JButton submit;
	private  JLabel pathHeader;
	private  JLabel nodesHeader;
	private  JTextArea nodesArea;
	private JScrollPane nodesScroll;
	private  JTextArea pathArea;
	private JScrollPane pathScroll;

	public void createGUI(){
	
		// Create Main Window
		frame = new JFrame("WebGraph");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		content = new JPanel();
		content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
		content.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		frame.add(content);
		// Create and Add components
		
		nodesHeader = new JLabel("Wikipedia Pages:");
		nodesHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
		content.add(nodesHeader);
		
		nodesArea = new JTextArea("");
		nodesArea.setAlignmentX(Component.LEFT_ALIGNMENT);
		nodesArea.setRows(25);
		nodesArea.setEditable(false);
		ArrayList<Node> nodes = new ArrayList<Node>(WebGraph.g.getNodes());
		Collections.sort(nodes);
		for(Node n : nodes){
			nodesArea.setText(nodesArea.getText() +n.getTitle()+"\n");
		}
		nodesScroll = new JScrollPane (nodesArea, 
  		 JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		nodesScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
		content.add(nodesScroll);
		
		sIn= new JLabel("Enter start:");
		sIn.setAlignmentX(Component.LEFT_ALIGNMENT);
		content.add(sIn);
		
		start= new JTextField();
		start.setColumns(35);
		start.setAlignmentX(Component.LEFT_ALIGNMENT);
		start.setMaximumSize(start.getPreferredSize());
		content.add(start);
		
		eIn= new JLabel("Enter end:");
		eIn.setAlignmentX(Component.LEFT_ALIGNMENT);
		content.add(eIn);
		
		end= new JTextField();
		end.setColumns(35);
		end.setAlignmentX(Component.LEFT_ALIGNMENT);
		end.setMaximumSize(end.getPreferredSize());
		content.add(end);
		
		submit= new JButton("Submit");
		submit.setAlignmentX(Component.LEFT_ALIGNMENT);
		submit.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				submitClicked(e);
			}
		});
		
		content.add(submit);
		
		pathHeader = new JLabel("Path Found:");
		pathHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
		content.add(pathHeader);
		
		pathArea = new JTextArea("");
		pathArea.setAlignmentX(Component.LEFT_ALIGNMENT);
		pathArea.setRows(3);
		pathArea.setEditable(false);
		pathArea.setFont(new Font("monospaced", Font.PLAIN, 12));
		pathScroll = new JScrollPane (pathArea, 
  		 JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pathScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
		content.add(pathScroll);
		
		spanLabel = new JLabel("Spanning Trees: "+WebGraph.g.connected());
		spanLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		content.add(spanLabel);
		
		//Display Window
		frame.pack();
		frame.setVisible(true);
	}
	
	// Action Methods
		
	public void submitClicked(ActionEvent e){
		String path = WebGraph.g.shortestPath(new Node(start.getText()), new Node(end.getText()));
		pathArea.setText(path);
	}
	
}