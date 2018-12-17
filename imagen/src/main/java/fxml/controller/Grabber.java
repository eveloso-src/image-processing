package fxml.controller;

import static akka.pattern.Patterns.ask;
import static scala.concurrent.Await.result;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.WebcamUtils;
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
<<<<<<< HEAD
		
		
		//setSize(new Dimension(640, 480));
=======

		setSize(new Dimension(1000, 600));
>>>>>>> 0836fac1cbb25703bf7010d400d1c412bfc09e31
		setVisible(true);
		JButton botonGrabar = new JButton("Record");

<<<<<<< HEAD
//		JLabel l = new JLabel();
//		l.setBounds(50, 100, 250, 20);
//		l.setText("hola");
//		l.setBackground(Color.BLACK);
=======
		JLabel l = new JLabel();
		l.setText("hola");
		l.setBackground(Color.BLACK);
>>>>>>> 0836fac1cbb25703bf7010d400d1c412bfc09e31

		JPanel panel = new JPanel();
		panel.setSize(new Dimension(640, 480));
		ButtonListener listener = new ButtonListener();
		botonGrabar.addActionListener(listener);
<<<<<<< HEAD
		getContentPane().setLayout(new GridLayout());
		
		
		
		final JPanel jPanelCamera = new JPanel();
		//jPanelCamera.setLayout(null);

		jPanelCamera.setSize(new Dimension(640, 480));
//		jTabbedPane1.addTab("Camera", jPanelCamera);
=======

		final JPanel jPanelCamera = new JPanel();
		final JPanel jPanelCamera2 = new JPanel();
>>>>>>> 0836fac1cbb25703bf7010d400d1c412bfc09e31

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
<<<<<<< HEAD
		add(jPanelCamera,  BorderLayout.CENTER);
		add(new JLabel(), BorderLayout.EAST);
		add(new JLabel(), BorderLayout.WEST);

//		add(new JLabel(), BorderLayout.CENTER);

		add(botonGrabar, BorderLayout.SOUTH);
//		add(panel);
//		add(l);
//		frame.add(canvas);
=======
		getContentPane().add(jPanelCamera, SpringLayout.NORTH);
		getContentPane().add(jPanelCamera2, SpringLayout.EAST);
//		getContentPane().add(new JLabel("west"), SpringLayout.WEST);
		panel.add(l);
		panel.add(botonGrabar);
		getContentPane().add(panel, SpringLayout.SOUTH);
>>>>>>> 0836fac1cbb25703bf7010d400d1c412bfc09e31
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

<<<<<<< HEAD
			//system.terminate();
=======
//			system.terminate();
>>>>>>> 0836fac1cbb25703bf7010d400d1c412bfc09e31
		} catch (Exception e) {
			System.out.println(e);

		} finally {
<<<<<<< HEAD
			//webcam.close();
=======
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
>>>>>>> 0836fac1cbb25703bf7010d400d1c412bfc09e31

		}
	}
<<<<<<< HEAD
	
=======

	private static final JHGrayFilter GRAY = new JHGrayFilter();

	public BufferedImage transform(BufferedImage image) {
		return GRAY.filter(image, null);
	}

	// public static void grabb() {
	public static void main(String[] args) {

		Grabber g = new Grabber();
	}

>>>>>>> 0836fac1cbb25703bf7010d400d1c412bfc09e31
}
