package com.image;

import static akka.pattern.Patterns.ask;
import static scala.concurrent.Await.result;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

public class Application {

	private BufferedImage master;
	private BufferedImage grayScale;
	private BufferedImage blackWhite;

	static enum GetImageMsg {
		OBJECT;
	}
	
	public Application() {
		
		Webcam.shutdown();
		Webcam webcam = Webcam.getDefault();
		try {

			final ActorSystem system = ActorSystem.create("hello-from-akka", ConfigFactory.defaultApplication());
			webcam.setViewSize(new Dimension(640, 480));
			// webcam.open();
			final ActorRef ref = system.actorOf(Props.create(WebcamActor.class, webcam));

			final File file = new File("img_");
			final Duration timeout = FiniteDuration.create("10s");
			final BufferedImage image = (BufferedImage) result(ask(ref, GetImageMsg.OBJECT, timeout.toMillis()),
					timeout);

			for (int i = 0; i < 10; i++) {
				ImageIO.write(image, "JPG", new File("img_" + i + ".jpg"));
				TimeUnit.SECONDS.sleep(1);
			}

			System.out.println("back and white");
			master = ImageIO.read(new File("C:\\Users\\emiliano.veloso\\datos\\clases\\image\\imagen\\img_0.jpg"));
			//grayScale = ImageIO.read(new File("C:/Users/shane/Dropbox/pictures/439px-Join!_It's_your_duty!.jpg"));
			ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
			op.filter(master, master);

			blackWhite = new BufferedImage(master.getWidth(), master.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
			Graphics2D g2d = blackWhite.createGraphics();
			g2d.drawImage(master, 0, 0, null);
			g2d.dispose();

			JOptionPane.showMessageDialog(null, "Image has been saved in file: " + file);

			system.terminate();
		} catch (Exception e) {

		} finally {
			webcam.close();

		}
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

	public static void main(String[] args) throws Exception {
	 Application app = new Application();
	}
}