package insaniquarium.utility;

import insaniquarium.Main;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class GameImage {
    private Image image;
    private static String path = "../images/";


    public GameImage(GameImage image){
        this.image = image.image;

    }
    public GameImage(String fileName) {

        String path = this.path + fileName;
        String pathMask = this.path + "_" + fileName;


        InputStream imageFile = Main.class.getResourceAsStream(path);
        InputStream maskFile = Main.class.getResourceAsStream(pathMask);
        if (maskFile == null) {
            //try other path
            String[] tokens = fileName.split("\\.(?=[^\\.]+$)");
            pathMask = this.path + tokens[0] + "_." + tokens[1];
            maskFile = Main.class.getResourceAsStream(pathMask);
        }

        if (maskFile == null) {
            File file = new File(Main.class.getResource(path).getFile());
            try {
                this.image = SwingFXUtils.toFXImage(ImageIO.read(file), null);

            } catch (Exception e) {

            }
        } else {
            try {
                BufferedImage imageOriginal = ImageIO.read(imageFile);
                BufferedImage mask = ImageIO.read(maskFile);
                BufferedImage combined = new BufferedImage(mask.getWidth(), mask.getHeight(), Transparency.TRANSLUCENT);


                for (int x = 0; x < combined.getWidth(); x++) {
                    for (int y = 0; y < combined.getHeight(); y++) {
                        //note: the alpha channel is stored in the white pixels of the mask
                        //since an integer is 32 bits long, pushing 0xFF << 24 (from the white pixel)
                        //into the most significant byte means full opacity (pixel visible)
                        //while pushing 0x00 (from the black pixel) means transparency
                        int masked = (imageOriginal.getRGB(x, y) & 0x00FFFFFF) | ((mask.getRGB(x, y) & 0xFFFFFFFF) << 24);
                        combined.setRGB(x, y, masked);
                    }
                }

                this.image = SwingFXUtils.toFXImage(combined, null);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void alterGameImageColors(int[] colorsFrom, int colorTo){
        if(image == null){
            return;
        }
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        BufferedImage original = new BufferedImage(width, height, Transparency.TRANSLUCENT);
        SwingFXUtils.fromFXImage(image, original);
        BufferedImage combined = new BufferedImage(width, height, Transparency.TRANSLUCENT);

        for (int x = 0; x < original.getWidth(); x++) {
            for (int y = 0; y < original.getHeight(); y++) {
                int originalRGB = original.getRGB(x, y);
                for(int colorFrom : colorsFrom){
                    if (originalRGB == colorFrom) {
                        originalRGB = colorTo;
                    }
                }
                combined.setRGB(x, y, originalRGB);

            }
        }


        image = SwingFXUtils.toFXImage(combined, null);
    }
    public void alterGameImageIntensity(float transparency, double greenFactor, double blueFactor, double redFactor){
        if (image == null) {
            return;
        }

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        BufferedImage original = new BufferedImage(width, height, Transparency.TRANSLUCENT);
        SwingFXUtils.fromFXImage(image, original);
        BufferedImage combined = new BufferedImage(width, height, Transparency.TRANSLUCENT);

        for (int x = 0; x < original.getWidth(); x++) {
            for (int y = 0; y < original.getHeight(); y++) {
                int originalRGB = original.getRGB(x, y);

                // Extract RGB channels from the original image
                int originalRed = (originalRGB >> 16) & 0xFF;
                int originalGreen = (originalRGB >> 8) & 0xFF;
                int originalBlue = originalRGB & 0xFF;

                int newGreen = (int) (originalGreen * greenFactor);
                int newBlue = (int) (originalBlue * blueFactor);
                int newRed = (int) (originalRed * redFactor);
                // Ensure the channel values stay within the valid range (0-255)
                newGreen = Math.min(newGreen, 255);
                newBlue = Math.min(newBlue, 255);
                newRed = Math.min(newRed, 255);

                // Extract the alpha channel from the mask image
                int maskAlpha = (originalRGB >> 24) & 0xFF;

                // Calculate the new alpha value based on the transparency
                int newAlpha = (int) (transparency * maskAlpha);

                // Combine the original RGB channels with the new alpha value
                int masked = (newAlpha << 24) | (newRed << 16) | (newGreen << 8) | newBlue;

                combined.setRGB(x, y, masked);
            }
        }
        image = SwingFXUtils.toFXImage(combined, null);
    }

    public javafx.scene.image.Image getImage() {
        return this.image;
    }


}