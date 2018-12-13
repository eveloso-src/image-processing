package com.image;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Demo3 extends JFrame {

	public Demo3() {

		setVisible(true);
		setSize(800, 600);
//	    JPanel gui = new JPanel(new BorderLayout(2, 2));
//        JPanel images = new JPanel(new GridLayout(0, 2, 2, 2));
//        gui.add(images, BorderLayout.CENTER);
//
//        final JLabel scaled = new JLabel("hola");
//        final JLabel scaled2 = new JLabel("chau");
//
//        add(gui);
//        add(scaled);
//        add(scaled2);

		JButton jb1 = new JButton("NORTH");    
		JButton jb2 = new JButton("SOUTH");
		JButton jb3 = new JButton("WEST");     
		JButton jb4 = new JButton("EAST");
		JButton jb5 = new JButton("CENTER");       
		// Define the panel to hold the buttons
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(jb1, BorderLayout.NORTH);
		panel.add(jb2, BorderLayout.SOUTH);
		panel.add(jb3, BorderLayout.WEST);
		panel.add(jb4, BorderLayout.EAST);
		panel.add(jb5, BorderLayout.CENTER);
		add(panel);

	
	}

	public static void main(String[] args) {
		new Demo3();
	}
}
