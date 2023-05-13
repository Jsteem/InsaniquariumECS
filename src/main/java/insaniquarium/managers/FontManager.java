package insaniquarium.managers;

import insaniquarium.utility.ContinuumBold12Outline;

public class FontManager {
    private static FontManager instance;
    ContinuumBold12Outline font;

    private FontManager(){
        font = new ContinuumBold12Outline();
    }

    public static FontManager getInstance(){
        if (instance == null) {
            instance = new FontManager();
        }
        return instance;
    }

    public ContinuumBold12Outline getFont(){
        return font;
    }
}



