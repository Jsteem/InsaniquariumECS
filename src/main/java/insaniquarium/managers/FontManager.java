package insaniquarium.managers;

import insaniquarium.managers.drawrequest.Font;
import insaniquarium.utility.ContinuumBold12Outline;
import insaniquarium.utility.GameFont;
import insaniquarium.utility.ImageInfo;

import java.util.HashMap;

public class FontManager {
    private static FontManager instance;
    ContinuumBold12Outline font;

    HashMap<ImageInfo.IMAGE_NAME, GameFont> fontNameToFont = new HashMap<>();

    private FontManager(){
        fontNameToFont.put(ImageInfo.IMAGE_NAME.CONTBOLD12, new ContinuumBold12Outline());

    }

    public static FontManager getInstance(){
        if (instance == null) {
            instance = new FontManager();
        }
        return instance;
    }

    public GameFont getFont(ImageInfo.IMAGE_NAME fontName){
        return fontNameToFont.get(fontName);
    }
}



