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
            image = new GameImage(info.fileName);
            if(info.replaceColors != null){
                image.alterGameImageColors(info.replaceColors, info.replaceColor);
            }
            else if(info.transparency != 0 ||
                    info.greenFactor != 0 ||
                    info.blueFactor != 0 ||
                    info.redFactor != 0){
                image.alterGameImageIntensity(info.transparency, info.greenFactor, info.blueFactor, info.redFactor);
            }
            images.put(imageName, image);
        }
        return image;

    }
    public ImageInfo getImageInfo(ImageInfo.IMAGE_NAME name) {
        return nameToInfo.get(name);
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
        nameToInfo.put(ImageInfo.IMAGE_NAME.PEARL, new ImageInfo("pearl.gif", 72,72,1,1));

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
                new ImageInfo(nameToInfo.get(ImageInfo.IMAGE_NAME.ULTRAVORE), 1f, 1, 1, 1,  new int[]{0xFFC7E1EB, 0xFF7D96A2}, 0xFFD2CC2B));


        nameToInfo.put(ImageInfo.IMAGE_NAME.SMALL_SWIM_STAR,
                new ImageInfo(nameToInfo.get(ImageInfo.IMAGE_NAME.SMALL_SWIM), 0.7f, 1.4, 1, 1,  null, 0));
        nameToInfo.put(ImageInfo.IMAGE_NAME.SMALL_EAT_STAR,
                new ImageInfo(nameToInfo.get(ImageInfo.IMAGE_NAME.SMALL_EAT), 0.7f, 1.4, 1, 1,  null, 0));
        nameToInfo.put(ImageInfo.IMAGE_NAME.SMALL_TURN_STAR,
                new ImageInfo(nameToInfo.get(ImageInfo.IMAGE_NAME.SMALL_TURN), 0.7f, 1.4, 1, 1,  null, 0));
        nameToInfo.put(ImageInfo.IMAGE_NAME.SMALL_DIE_STAR,
                new ImageInfo(nameToInfo.get(ImageInfo.IMAGE_NAME.SMALL_DIE), 0.7f, 1.4, 1, 1,  null, 0));
        nameToInfo.put(ImageInfo.IMAGE_NAME.HUNGRY_SWIM_STAR,
                new ImageInfo(nameToInfo.get(ImageInfo.IMAGE_NAME.HUNGRY_SWIM), 0.7f, 1.4, 1, 1,  null, 0));
        nameToInfo.put(ImageInfo.IMAGE_NAME.HUNGRY_EAT_STAR,
                new ImageInfo(nameToInfo.get(ImageInfo.IMAGE_NAME.HUNGRY_EAT), 0.7f, 1.4, 1, 1,  null, 0));
        nameToInfo.put(ImageInfo.IMAGE_NAME.HUNGRY_TURN_STAR,
                new ImageInfo(nameToInfo.get(ImageInfo.IMAGE_NAME.HUNGRY_TURN), 0.7f, 1.4, 1, 1,  null, 0));

        nameToInfo.put(ImageInfo.IMAGE_NAME.STINKY, new ImageInfo("stinky.gif", 80, 80, 3, 10));
        nameToInfo.put(ImageInfo.IMAGE_NAME.NIKO, new ImageInfo("niko.gif", 80, 80, 3, 10));
        nameToInfo.put(ImageInfo.IMAGE_NAME.ZORF, new ImageInfo("zorf.gif", 80, 80, 3, 10));
        nameToInfo.put(ImageInfo.IMAGE_NAME.PREGO, new ImageInfo("prego.gif", 80, 80, 4, 10));
        nameToInfo.put(ImageInfo.IMAGE_NAME.VERT, new ImageInfo("vert.gif", 80, 80, 2, 10));
        nameToInfo.put(ImageInfo.IMAGE_NAME.CLYDE, new ImageInfo("clyde.gif", 80, 80, 1, 10));
        nameToInfo.put(ImageInfo.IMAGE_NAME.MERYL, new ImageInfo("meryl.gif", 80, 80, 3, 10)); //todo: eye blinking?




    }


}
