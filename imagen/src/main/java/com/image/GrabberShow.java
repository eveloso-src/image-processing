package com.image;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.VideoInputFrameGrabber;

public class GrabberShow implements Runnable {
    //final int INTERVAL=1000;///you may use interval
    IplImage image;
    CanvasFrame canvas = new CanvasFrame("Web Cam");
    public GrabberShow() {
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    }
  
    public void run() {
        FrameGrabber grabber = new VideoInputFrameGrabber(0); 
        int i=0;
//        try {
//            grabber.start();
//            IplImage img;
//            while (true) {
//                img = grabber.grab();
//                if (img != null) {
//                    cvFlip(img, img, 1);// l-r = 90_degrees_steps_anti_clockwise
//                    cvSaveImage((i++)+"-capture.jpg", img);
                    // show image on window
//                    canvas.showImage(img);
//                }
                 //Thread.sleep(INTERVAL);
//            }
//        } catch (Exception e) {
//        }
    }
}