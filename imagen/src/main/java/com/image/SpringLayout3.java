package com.image;

import static akka.pattern.Patterns.ask;
import static scala.concurrent.Await.result;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.util.jh.JHGrayFilter;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

public class SpringLayout3 {
	/**
	 * Create the GUI and show it. For thread safety, this method should be invoked
	 * from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("SpringDemo3");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(800, 600);
		// Set up the content pane.
		Container contentPane = frame.getContentPane();
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		final ActorSystem system = ActorSystem.create("hello-from-akka", ConfigFactory.defaultApplication());

		Webcam webcam = Webcam.getDefault();
		final ActorRef ref = system.actorOf(Props.create(WebcamActor.class, webcam));

		webcam.setViewSize(new Dimension(640, 480));
		final Duration timeout = FiniteDuration.create("10s");
		BufferedImage colorImage;
		ImageIcon imageIcon;
		JLabel label;
		try {
			for (int i = 0; i < 60; i++) {
				
				colorImage = (BufferedImage) result(ask(ref, GetImageMsg.OBJECT, timeout.toMillis()), timeout);
				ImageIO.write(colorImage, "JPG", new File("img_" + i + ".jpg"));
				TimeUnit.MILLISECONDS.sleep(33);
				
				imageIcon = new ImageIcon();
				
				imageIcon.setImage(colorImage);
				label = new JLabel(imageIcon);
				// JTextField textField = new JTextField("Text field", 15);
				contentPane.add(label);
				// contentPane.add(textField);
				// Adjust constraints for the label so it's at (5,5).
				layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, contentPane);
				layout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.NORTH, contentPane);
				
				// Adjust constraints for the text field so it's at
				// (<label's right edge> + 5, 5).
//	        layout.putConstraint(SpringLayout.WEST, textField,
//	                             5,
//	                             SpringLayout.EAST, label);
//	        layout.putConstraint(SpringLayout.NORTH, textField,
//	                             5,
//	                             SpringLayout.NORTH, contentPane);
				
				// Adjust constraints for the content pane: Its right
				// edge should be 5 pixels beyond the text field's right
				// edge, and its bottom edge should be 5 pixels beyond
				// the bottom edge of the tallest component (which we'll
				// assume is textField).
//	        layout.putConstraint(SpringLayout.EAST, contentPane,
//	                             5,
//	                             SpringLayout.EAST, textField);
//	        layout.putConstraint(SpringLayout.SOUTH, contentPane,
//	                             5,
//	                             SpringLayout.SOUTH, textField);
				
				// Display the window.
//	        frame.pack();
				frame.setVisible(true);
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Create and add the components.

	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	static enum GetImageMsg {
		OBJECT;
	}

	static class WebcamActor extends UntypedActor {

		final Webcam webcam;

		public WebcamActor(Webcam webcam) {
			this.webcam = webcam;
		}

		@Override
		public void preStart() throws Exception {
			webcam.setViewSize(WebcamResolution.VGA.getSize());
			webcam.open();
		}

		@Override
		public void postStop() throws Exception {
			webcam.close();
		}

		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof GetImageMsg) {
				sender().tell(getImage(), self());
			} else {
				unhandled(msg);
			}
		}

		public BufferedImage getImage() {
			return webcam.getImage();
		}
	}

	private static final JHGrayFilter GRAY = new JHGrayFilter();

}