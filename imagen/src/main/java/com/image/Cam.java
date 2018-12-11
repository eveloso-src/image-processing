package com.image;

//import au.edu.jcu.v4l4j.CaptureCallback;
//
//import au.edu.jcu.v4l4j.RGBFrameGrabber;
//import au.edu.jcu.v4l4j.VideoDevice;
//import au.edu.jcu.v4l4j.VideoFrame;
//import au.edu.jcu.v4l4j.exceptions.V4L4JException;
//import com.github.sarxos.v4l4j.V4L4J;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Cam 
//implements CaptureCallback
{
//    static {
//        V4L4J.init();
//    }

    private CountDownLatch latch = new CountDownLatch(1);
    private String fileName;
    private String fileFormat;
    private File image;

    public Cam(String fileFormat, int width, int height) throws Exception {
        this.fileFormat = fileFormat.toUpperCase();
//        VideoDevice device = new VideoDevice("/dev/video0");
//        RGBFrameGrabber grabber = device.getRGBFrameGrabber(width, height, 0, 0);
//        grabber.setCaptureCallback(this);
//        grabber.startCapture();
//
//        latch.await(10, TimeUnit.SECONDS);
//
//        grabber.stopCapture();
//        device.releaseFrameGrabber();
//        device.releaseControlList();
//        device.release();
    }

//    @Override
    public void nextFrame(
//    		VideoFrame frame
    		) {
//        BufferedImage bufferedImage = frame.getBufferedImage();
//
//        BufferedImage image_to_save2 = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
//        image_to_save2.getGraphics().drawImage(bufferedImage, 0, 0, null);
//        bufferedImage = image_to_save2;
//
//        image = new File("cam/" + getFileName());
//        try {
//            ImageIO.write(bufferedImage, fileFormat, image);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            frame.recycle();
//            latch.countDown();
//        }
    }

//    @Override
//    public void exceptionReceived(V4L4JException e) {
//        e.printStackTrace();
//    }

    private String getFileName() {
        if (fileName == null || fileName.isEmpty())
            fileName = System.currentTimeMillis() + "." + fileFormat.toLowerCase();

        return fileName;
    }

    public File getImage() {
        return image;
    }
}