package com.image;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamUtils;
import com.github.sarxos.webcam.util.ImageUtils;

public class Base {
	
	public static void main(String[] args) {
		Webcam webcam = Webcam.getDefault();
		webcam.setViewSize(new Dimension(640, 480));
		WebcamUtils.capture(webcam, "test1", ImageUtils.FORMAT_JPG);
		byte[] bytes = WebcamUtils.getImageBytes(webcam, "jpg");
		System.out.println("Bytes length: " + bytes.length);
//
//		// save image in JPG format and return as byte buffer
//		ByteBuffer buffer = WebcamUtils.getImageByteBuffer(webcam, "jpg");
//		System.out.println("Buffer length: " + buffer.capacity());
//		
//
	}

}
