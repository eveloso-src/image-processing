package com.image;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

public class Panel extends JFrame {
	
	public Panel() {
		JButton boton = new JButton("Grabar");
		JButton boton2 = new JButton("Grabar2");
//		ImageIcon imageIcon = new ImageIcon(image);
		JLabel label = new JLabel("test");
		label.setSize(new Dimension(400, 499));
		SpringLayout slay = new SpringLayout();
		slay.putConstraint(SpringLayout.NORTH, boton, 50	, SpringLayout.NORTH, boton2);
		slay.putConstraint(SpringLayout.NORTH, label, 50	, SpringLayout.NORTH, boton2);
		getContentPane().setLayout(slay);
		getContentPane().add(boton );
		getContentPane().add(boton2);
		getContentPane().add(label);
		setSize(800, 600);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Panel();
		// TODO Auto-generated method stub

	}

}
