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

        EGG, CONTBOLD12, MENU_BAR, SLOT_BUTTON_PRESSED, SLOT_BUTTON_HOVERED, SLOT_BUTTON_UNPRESSED, OPTIONS_BUTTON_UNPRESSED, OPTIONS_BUTTON_PRESSED,

    }

    public String fileName;
    public int cellWidth;

    public int cellHeight;
    public int numRows;
    public int numCols;

    public ImageInfo(String fileName, int cellWidth, int cellHeight, int numRows, int numCols){
        this.fileName = fileName;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.numRows = numRows;
        this.numCols = numCols;
    }
}
