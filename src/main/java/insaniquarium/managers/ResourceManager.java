package insaniquarium.managers;

import insaniquarium.utility.GameImage;
import insaniquarium.utility.ImageInfo;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ResourceManager {
    private static ResourceManager instance;

    private HashMap<ImageInfo.IMAGE_NAME, GameImage> images = new HashMap<>();

    private HashMap<ImageInfo.IMAGE_NAME, ImageInfo> nameToInfo = new HashMap<>();

    private ResourceManager(){
        loadImageInfo();


    }

    public static ResourceManager getInstance(){
        if(instance == null){
            instance = new ResourceManager();
        }
        return instance;
    }

    public GameImage getImage(ImageInfo.IMAGE_NAME imageName){
        ImageInfo info = nameToInfo.get(imageName);
        GameImage image = images.get(imageName);
        if(image == null){
            if(info.imageName != null){
                image = images.get(info.imageName);
                GameImage imageClone = new GameImage(image, info);
                if(info.replaceColors != null){
                    images.put(imageName, alterGameImageColors(imageClone, info.replaceColors, info.replaceColor));
                }
                else{
                    images.put(imageName, alterGameImageIntensity(
                            imageClone, info.transparency, info.greenFactor, info.blueFactor, info.redFactor));
                }
            }
            else{
                image = new GameImage(info);
                images.put(imageName, image);
            }
        }
        return image;
    }

    public GameImage alterGameImageColors(GameImage gameImage, int[] colorsFrom, int colorTo){

        int width = (int) gameImage.getImage().getWidth();
        int height = (int) gameImage.getImage().getHeight();

        BufferedImage original = new BufferedImage(width, height, Transparency.TRANSLUCENT);
        SwingFXUtils.fromFXImage(gameImage.getImage(), original);
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

        gameImage.setImage(SwingFXUtils.toFXImage(combined, null));
        return gameImage;
    }
    public GameImage alterGameImageIntensity(
            GameImage gameImage,
            float transparency,
            double greenFactor,
            double blueFactor,
            double redFactor){


        int width = (int) gameImage.getImage().getWidth();
        int height = (int) gameImage.getImage().getHeight();

        BufferedImage original = new BufferedImage(width, height, Transparency.TRANSLUCENT);
        SwingFXUtils.fromFXImage(gameImage.getImage(), original);
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
        gameImage.setImage(SwingFXUtils.toFXImage(combined, null));
        return gameImage;
    }
    public void loadImageInfo(){
        nameToInfo.put(ImageInfo.IMAGE_NAME.SMALL_SWIM, new ImageInfo("smallswim.gif", 80,80,5,10));
        nameToInfo.put(ImageInfo.IMAGE_NAME.SMALL_EAT, new ImageInfo("smalleat.gif", 80,80,5,10));
        nameToInfo.put(ImageInfo.IMAGE_NAME.SMALL_TURN, new ImageInfo("smallturn.gif", 80,80,5,10));
        nameToInfo.put(ImageInfo.IMAGE_NAME.SMALL_DIE, new ImageInfo("smalldie.gif", 80,80,5,10));
        nameToInfo.put(ImageInfo.IMAGE_NAME.HUNGRY_SWIM, new ImageInfo("hungryswim.gif", 80,80,5,10));
        nameToInfo.put(ImageInfo.IMAGE_NAME.HUNGRY_EAT, new ImageInfo("hungryeat.gif", 80,80,5,10));
        nameToInfo.put(ImageInfo.IMAGE_NAME.HUNGRY_TURN, new ImageInfo("hungryturn.gif", 80,80,5,10));

        nameToInfo.put(ImageInfo.IMAGE_NAME.BACKGROUND_TANK0, new ImageInfo("aquarium1.jpg", -1,-1,-1,-1));
        nameToInfo.put(ImageInfo.IMAGE_NAME.BACKGROUND_TANK1, new ImageInfo("aquarium2.jpg", -1,-1,-1,-1));
        nameToInfo.put(ImageInfo.IMAGE_NAME.BACKGROUND_TANK2, new ImageInfo("aquarium3.jpg", -1,-1,-1,-1));
        nameToInfo.put(ImageInfo.IMAGE_NAME.BACKGROUND_TANK3, new ImageInfo("aquarium4.jpg", -1,-1,-1,-1));
        nameToInfo.put(ImageInfo.IMAGE_NAME.BACKGROUND_TANK4, new ImageInfo("aquarium5.jpg", -1,-1,-1,-1));
        nameToInfo.put(ImageInfo.IMAGE_NAME.BACKGROUND_TANK5, new ImageInfo("aquarium6.jpg", -1,-1,-1,-1));

        nameToInfo.put(ImageInfo.IMAGE_NAME.FOOD, new ImageInfo("food.gif", 40,40,5,10));
        nameToInfo.put(ImageInfo.IMAGE_NAME.MONEY, new ImageInfo("money.gif", 72,72,6,10));


        nameToInfo.put(ImageInfo.IMAGE_NAME.SYLVESTER, new ImageInfo("sylv.gif", 160,160,2,10));


        nameToInfo.put(ImageInfo.IMAGE_NAME.CONTBOLD12, new ImageInfo("ContinuumBold12outline.gif", -1,-1,-1,-1));



        nameToInfo.put(ImageInfo.IMAGE_NAME.MENU_BAR, new ImageInfo("menubar.gif", -1,-1,-1,-1));
        nameToInfo.put(ImageInfo.IMAGE_NAME.SLOT_BUTTON_PRESSED, new ImageInfo("mbuttond.gif", 0,0,-1,-1));
        nameToInfo.put(ImageInfo.IMAGE_NAME.SLOT_BUTTON_HOVERED, new ImageInfo("mbuttono.gif", 0,0,-1,-1));
        nameToInfo.put(ImageInfo.IMAGE_NAME.SLOT_BUTTON_UNPRESSED, new ImageInfo("mbuttonu.gif", 0,0,-1,-1));

        nameToInfo.put(ImageInfo.IMAGE_NAME.OPTIONS_BUTTON_UNPRESSED, new ImageInfo("OPTIONSBUTTON.gif", 0,0,-1,-1));
        nameToInfo.put(ImageInfo.IMAGE_NAME.OPTIONS_BUTTON_PRESSED, new ImageInfo("OPTIONSBUTTONd.gif", 0,0,-1,-1));


        nameToInfo.put(ImageInfo.IMAGE_NAME.WEAPONS, new ImageInfo("LazerGunz.gif", 46,39,1,10));
        nameToInfo.put(ImageInfo.IMAGE_NAME.EGG, new ImageInfo("EggPieces.gif", 46,39,1,3));

        nameToInfo.put(ImageInfo.IMAGE_NAME.STAR_CATCHER, new ImageInfo("starcatcher.gif", 80, 80, 3, 10));
        nameToInfo.put(ImageInfo.IMAGE_NAME.BEETLE_MUNCHER, new ImageInfo("gekko.gif", 80, 80, 7, 10));
        nameToInfo.put(ImageInfo.IMAGE_NAME.GUPPY_CRUNCHER, new ImageInfo("grubgrubber.gif", 80, 80, 5, 10));
        nameToInfo.put(ImageInfo.IMAGE_NAME.BREEDER, new ImageInfo("breeder.gif", 80, 80, 9, 10));
        nameToInfo.put(ImageInfo.IMAGE_NAME.BREEDER_HUNGRY, new ImageInfo("hungrybreeder.gif", 80, 80, 12, 10));
        nameToInfo.put(ImageInfo.IMAGE_NAME.ULTRAVORE, new ImageInfo("ultravore.gif", 160, 160, 4, 10));

        nameToInfo.put(ImageInfo.IMAGE_NAME.ULTRAVORE_HUNGRY,
                new ImageInfo(ImageInfo.IMAGE_NAME.ULTRAVORE, 160, 160, 4, 10,
                1f, 1, 1, 1,  new int[]{0xFFC7E1EB, 0xFF7D96A2}, 0xFFD2CC2B));


        nameToInfo.put(ImageInfo.IMAGE_NAME.SMALL_SWIM_STAR,
                new ImageInfo(ImageInfo.IMAGE_NAME.SMALL_SWIM, 80,80,5,10,
                        0.7f, 1.2, 1, 1,  null, 0));
        nameToInfo.put(ImageInfo.IMAGE_NAME.SMALL_EAT_STAR,
                new ImageInfo(ImageInfo.IMAGE_NAME.SMALL_EAT, 80,80,5,10,
                        0.7f, 1.2, 1, 1,  null, 0));
        nameToInfo.put(ImageInfo.IMAGE_NAME.SMALL_TURN_STAR,
                new ImageInfo(ImageInfo.IMAGE_NAME.SMALL_TURN, 80,80,5,10,
                        0.7f, 1.2, 1, 1,  null, 0));
        nameToInfo.put(ImageInfo.IMAGE_NAME.SMALL_DIE_STAR,
                new ImageInfo(ImageInfo.IMAGE_NAME.SMALL_DIE, 80,80,5,10,
                        0.7f, 1.2, 1, 1,  null, 0));
        nameToInfo.put(ImageInfo.IMAGE_NAME.HUNGRY_SWIM_STAR,
                new ImageInfo(ImageInfo.IMAGE_NAME.HUNGRY_SWIM, 80,80,5,10,
                        0.7f, 1.2, 1, 1,  null, 0));
        nameToInfo.put(ImageInfo.IMAGE_NAME.HUNGRY_EAT_STAR,
                new ImageInfo(ImageInfo.IMAGE_NAME.HUNGRY_EAT, 80,80,5,10,
                        0.7f, 1.2, 1, 1,  null, 0));
        nameToInfo.put(ImageInfo.IMAGE_NAME.HUNGRY_TURN_STAR,
                new ImageInfo(ImageInfo.IMAGE_NAME.HUNGRY_TURN, 80,80,5,10,
                        0.7f, 1.2, 1, 1,  null, 0));

    }
}
