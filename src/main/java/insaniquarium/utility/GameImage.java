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
    private ImageInfo imageInfo;

    public GameImage(GameImage image, ImageInfo imageInfo){
        this.image = image.image;
        this.imageInfo = imageInfo;
    }
    public GameImage(ImageInfo imageInfo) {
        this.imageInfo = imageInfo;
        String path = this.path + imageInfo.fileName;
        String pathMask = this.path + "_" + imageInfo.fileName;


        InputStream imageFile = Main.class.getResourceAsStream(path);
        InputStream maskFile = Main.class.getResourceAsStream(pathMask);
        if (maskFile == null) {
            //try other path
            String[] tokens = imageInfo.fileName.split("\\.(?=[^\\.]+$)");
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

    public javafx.scene.image.Image getImage() {
        return this.image;
    }

    public ImageInfo getImageInfo() {
        return this.imageInfo;
    }

    public void setImage(WritableImage fxImage) {

        this.image = fxImage;
    }
}