package insaniquarium.utility;

public class ImageInfo {



    public enum IMAGE_NAME{
        SMALL_SWIM,
        SMALL_TURN,
        SMALL_EAT,
        SMALL_DIE,
        HUNGRY_SWIM,
        HUNGRY_TURN,
        HUNGRY_EAT,
        BACKGROUND_TANK1,
        BACKGROUND_TANK2,
        BACKGROUND_TANK3,
        BACKGROUND_TANK4,
        FOOD,
        MONEY,
        WEAPONS,
        SYLVESTER,
        EGG,
        CONTBOLD12,
        MENU_BAR,
        SLOT_BUTTON_PRESSED,
        SLOT_BUTTON_HOVERED,
        SLOT_BUTTON_UNPRESSED,
        OPTIONS_BUTTON_UNPRESSED,
        OPTIONS_BUTTON_PRESSED,
        STAR_CATCHER,
        BEETLE_MUNCHER,
        GUPPY_CRUNCHER,
        BREEDER,
        BREEDER_HUNGRY,
        ULTRAVORE,
        ULTRAVORE_HUNGRY,
        BACKGROUND_TANK0,
        BACKGROUND_TANK5, SMALL_SWIM_STAR, SMALL_EAT_STAR, SMALL_TURN_STAR, SMALL_DIE_STAR, HUNGRY_SWIM_STAR, HUNGRY_EAT_STAR, HUNGRY_TURN_STAR, PEARL,

    }

    public String fileName;
    public int cellWidth;
    public int cellHeight;
    public int numRows;
    public int numCols;
    public float transparency;
    public double greenFactor;
    public double redFactor;
    public double blueFactor;
    public int[] replaceColors;
    public int replaceColor;

    public ImageInfo(String fileName, int cellWidth, int cellHeight, int numRows, int numCols){
        this.fileName = fileName;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public ImageInfo(ImageInfo imageInfo, float transparency, double greenFactor, double redFactor, double blueFactor,
                     int[] replaceColors, int replaceColor){
        this.fileName = imageInfo.fileName;
        this.cellWidth = imageInfo.cellWidth;
        this.cellHeight = imageInfo.cellHeight;
        this.numRows = imageInfo.numRows;
        this.numCols = imageInfo.numCols;
        this.transparency = transparency;
        this.greenFactor = greenFactor;
        this.redFactor = redFactor;
        this.blueFactor = blueFactor;
        this.replaceColor = replaceColor;
        this.replaceColors = replaceColors;
    }
}

