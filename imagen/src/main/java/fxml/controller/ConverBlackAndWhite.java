package fxml.controller;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ConverBlackAndWhite {

    public static void main(String... args) {

        try {

            File input = new File("/tmp/java-duke.png");
            BufferedImage image = ImageIO.read(input);

            BufferedImage result = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_BYTE_BINARY);

            Graphics2D graphic = result.createGraphics();
            graphic.drawImage(image, 0, 0, Color.WHITE, null);
            graphic.dispose();

            File output = new File("/tmp/java-duke-black-white.png");
            ImageIO.write(result, "png", output);

        }  catch (IOException e) {
            e.printStackTrace();
        }

    }

}