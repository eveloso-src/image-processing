package fxml.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.github.sarxos.webcam.WebcamUtils;

public class ImageParser {

	public static void main(String[] args) {
		File file = new File("img_6.jpg");
		byte[] fileContent;
		try {
			fileContent = Files.readAllBytes(file.toPath());
			for (int k=0; k < fileContent.length; k ++) {
				
				System.out.print(fileContent[k]  + " ");
			}
			System.out.println(fileContent.length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
