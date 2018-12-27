package fxml.controller;

import org.bytedeco.javacpp.opencv_core.CvType;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.opencv.core.Core;

public class JavCam {
	public static void main(String[] args) {
		// Create canvas frame for displaying webcam.
		CanvasFrame canvas = new CanvasFrame("Webcam");
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat matrix = new Mat(5, 5, CvType.CV_8UC1, new Scalar(0));
		
		// Set Canvas frame to close on exit
		canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

		// Declare FrameGrabber to import output from webcam
		FrameGrabber grabber = new OpenCVFrameGrabber("");

		try {

			// Start grabber to capture video
			grabber.start();

			// Declare img as IplImage
//			IplImage img;
			Frame img;

			while (true) {

				// inser grabed video fram to IplImage img
				img = grabber.grab();

				// Set canvas size as per dimentions of video frame.
				canvas.setCanvasSize(grabber.getImageWidth(), grabber.getImageHeight());

				if (img != null) {
					// Flip image horizontally
//					cvFlip(img, img, 1);
					// Show video frame in canvas
					canvas.showImage(img);
				}
			}
		} catch (Exception e) {
		}
	}
}