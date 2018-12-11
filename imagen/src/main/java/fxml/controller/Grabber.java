package fxml.controller;

import static akka.pattern.Patterns.ask;
import static scala.concurrent.Await.result;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

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

public class Grabber {

	public Grabber() {
		// Webcam.shutdown();
		Webcam webcam = Webcam.getDefault();
		try {

			final ActorSystem system = ActorSystem.create("hello-from-akka", ConfigFactory.defaultApplication());
			webcam.setViewSize(new Dimension(640, 480));
			
			// webcam.open();
			final ActorRef ref = system.actorOf(Props.create(WebcamActor.class, webcam));

//			final File file = new File("img_");

			BufferedImage colorImage;
			BufferedImage grayImage;
			for (int i = 0; i < 30; i++) {
				final Duration timeout = FiniteDuration.create("10s");
				colorImage = (BufferedImage) result(ask(ref, GetImageMsg.OBJECT, timeout.toMillis()),
						timeout);
				ImageIO.write(colorImage, "JPG", new File("img_" + i + ".jpg"));
				TimeUnit.MILLISECONDS.sleep(500);

//				grayImage = new BufferedImage(colorImage.getWidth(), colorImage.getHeight(),
//						BufferedImage.TYPE_INT_RGB);
				ImageIO.write(transform(colorImage), "JPG", new File("bw" + i + ".jpg"));
				// g = grayImage.createGraphics();
				// g.drawImage(colorImage, 0, 0, null);

			}

//			JOptionPane.showMessageDialog(null, "Image has been saved in file: " + file);

			system.terminate();
		} catch (Exception e) {

		} finally {
			webcam.close();

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
	
	public BufferedImage transform(BufferedImage image) {
		return GRAY.filter(image, null);
	}
	
//	public static void grabb() {
	public static void main(String[] args) {
		
		Grabber g = new Grabber();
	}

}