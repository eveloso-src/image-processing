package com.image;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Demo extends JFrame {

	public Demo() {

		setVisible(true);
		setSize(800, 600);
		// JPanel gui = new JPanel(new BorderLayout(2, 2));
		// JPanel images = new JPanel(new GridLayout(0, 2, 2, 2));
		// gui.add(images, BorderLayout.CENTER);
		//
		// final JLabel scaled = new JLabel("hola");
		// final JLabel scaled2 = new JLabel("chau");
		//
		// add(gui);
		// add(scaled);
		// add(scaled2);

		JPanel panel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		panel.setLayout(layout);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(new JLabel("Button1"), gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		panel.add(new JButton("Button 2"), gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		panel.add(new JButton("Button 3"), gbc);

		add(panel);

	}

	public static void main(String[] args) {
		new Demo();
	}
}
