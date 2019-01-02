package fxml.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
 
import javax.imageio.ImageIO;
import javax.xml.ws.http.HTTPException;
public class ImageToBinary {
    public static void main(String[] args) {
         
         
        try {
            // read "any" type of image (in this case a png file)
            BufferedImage image = ImageIO.read(
            		new File("C:\\Users\\emiliano.veloso\\datos\\clases\\image\\imagen\\bytes_.jpg"));
//            		new URL("http://www.freeonlinepicture.org/wp-content/uploads/2011/03/bgsingle_red_rose.jpg"));
            // write it to byte array in-memory (jpg format)
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", b);
 
            // do whatever with the array...
            byte[] jpgByteArray = b.toByteArray();
 
                    //store image in binary file called ImageInBinary.bat
            File file = new File("C:\\Users\\emiliano.veloso\\datos\\clases\\image\\imagen\\imgs\\ImageInBinary.bmp");
             
             
            FileOutputStream ostream = new FileOutputStream(file);
            
            
             
            System.out.println("length of byte : "+jpgByteArray.length);
         // convert it to a String with 0s and 1s        
            StringBuilder sb = new StringBuilder();
            for (byte by : jpgByteArray){
                System.out.println(Integer.toBinaryString(by));
                ostream.write(Integer.toBinaryString(by).getBytes());
            }
             
                     
            InputStream in = new ByteArrayInputStream(jpgByteArray);
            BufferedImage img = ImageIO.read(in);
             
            ImageIO.write(img, "jpg", 
                     new File("C:\\Users\\emiliano.veloso\\datos\\clases\\image\\imagen\\imgs\\mypic_new.jpg"));
             
 
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         
          
 
    }   
}