package fxml.controller;

import static akka.pattern.Patterns.ask;
import static scala.concurrent.Await.result;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.util.jh.JHGrayFilter;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

public class Grabber extends JFrame {

	public Grabber() {

		setSize(new Dimension(1000, 600));
		setVisible(true);
		JButton botonGrabar = new JButton("Record");

		JLabel l = new JLabel();
		l.setText("hola");
		l.setBackground(Color.BLACK);

		JPanel panel = new JPanel();
		panel.setSize(new Dimension(640, 480));
//		ButtonListener listener = new ButtonListener();
//		botonGrabar.addActionListener(listener);
		getContentPane().setLayout(new GridLayout());
		
		
		
		final JPanel jPanelCamera = new JPanel();
		//jPanelCamera.setLayout(null);

		jPanelCamera.setSize(new Dimension(640, 480));
		final JPanel jPanelCamera2 = new JPanel();

		Webcam webcam = Webcam.getDefault();
//		webcam.setViewSize(WebcamResolution.VGA.getSize());
		final ActorSystem system = ActorSystem.create("hello-from-akka", ConfigFactory.defaultApplication());
//		webcam.setViewSize(new Dimension(640, 480));

		final ActorRef ref = system.actorOf(Props.create(WebcamActor.class, webcam));

		WebcamPanel webcamPanel = new WebcamPanel(webcam);
		webcamPanel.setFPSDisplayed(true);
		webcamPanel.setDisplayDebugInfo(true);
		webcamPanel.setImageSizeDisplayed(true);
		webcamPanel.setMirrored(true);

		jPanelCamera.add(webcamPanel);
//		jPanelCamera2.add(new JLabel("east"));

		System.out.println("Camera OK");
		getContentPane().add(jPanelCamera, SpringLayout.NORTH);
		getContentPane().add(jPanelCamera2, SpringLayout.EAST);
//		getContentPane().add(new JLabel("west"), SpringLayout.WEST);
		panel.add(l);
		panel.add(botonGrabar);
		getContentPane().add(panel, SpringLayout.SOUTH);
		show();

		try {

			BufferedImage colorImage;
			BufferedImage grayImage;
			for (int i = 0; i < 20; i++) {
				final Duration timeout = FiniteDuration.create("10s");
				colorImage = (BufferedImage) result(ask(ref, GetImageMsg.OBJECT, timeout.toMillis()), timeout);
				ImageIO.write(colorImage, "jpg", new File("img_" + i + ".jpg"));
				TimeUnit.MILLISECONDS.sleep(500);

//				jPanelCamera2.removeAll();
				if (jPanelCamera2.getComponents().length == 0) {

					jPanelCamera2.add(new JLabel(new ImageIcon(colorImage)));
//					jPanelCamera2.setSize(320, 240);
				}
				File bwfile = new File("bw" + i + ".jpg");
				// grayImage = new BufferedImage(colorImage.getWidth(), colorImage.getHeight(),
				// BufferedImage.TYPE_INT_RGB);
				ImageIO.write(transform(colorImage), "jpg", bwfile);
				// g = grayImage.createGraphics();
				// g.drawImage(colorImage, 0, 0, null);

//				byte[] bytes = WebcamUtils.getImageBytes(webcam, "jpg");
//				for(int j = 0; j < bytes.length; j++) {
//					System.out.println(bytes[j]);
//				}

//				File fi = new File("bw5.jpg");
				byte[] fileContent = Files.readAllBytes(bwfile.toPath());
				for (int k = 0; k < fileContent.length; k++) {

					System.out.println("length: " + fileContent.length);
					System.out.print(fileContent[k] + ". ");
				}
//				System.out.println("-----");
			}

			// JOptionPane.showMessageDialog(null, "Image has been saved in file: " + file);

		} catch (Exception e) {
			System.out.println(e);

		} finally {
//			webcam.close();

		}
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
		public void onReceive(Object arg0) throws Exception {
			// TODO Auto-generated method stub
			
		}
	}

	private static final JHGrayFilter GRAY = new JHGrayFilter();

	public BufferedImage transform(BufferedImage image) {
		return GRAY.filter(image, null);
	}

	// public static void grabb() {
	public static void main(String[] args) {

		Grabber g = new Grabber();
	}

}
