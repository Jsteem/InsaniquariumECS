package insaniquarium.ecs.components;

import insaniquarium.managers.FontManager;
import insaniquarium.utility.GameFont;
import insaniquarium.utility.ImageInfo;
import javafx.scene.paint.Color;


import java.util.ArrayList;
import java.util.Map;

public class TextComponent extends Component {
    public GameFont gameFont;

    public void buildText(String text) {
        this.charArray = text.toCharArray();
        this.width = calculateWidth();

        double dx = x;
        if(position == POSITION.CENTER){
            dx -= width/2;
        }
        else if(position == POSITION.LEFT){
            dx -= width;
        }
        generateOffsetsX(dx);
    }

    public enum POSITION{
        LEFT,
        RIGHT,
        CENTER
    }
    public String text;
    public double x;
    public double y;
    public float percentage;
    public Color color;
    public int width;
    public Map<Character, int[]> characterToData;
    public char[] charArray;

    public ArrayList<Double> offsetsX;
    public ImageInfo.IMAGE_NAME fontImage;

    public POSITION position;

    public TextComponent(ImageInfo.IMAGE_NAME fontName, POSITION position, String text, double x, double y, float percentage, Color color){
        this.fontImage = fontName;
        this.gameFont = FontManager.getInstance().getFont(fontName);
        this.characterToData =  gameFont.getCharacterToData();
        this.x = x;
        this.y = y;
        this.percentage = percentage;
        this.color = color;
        this.position = position;
        buildText(text);
    }

    private int calculateWidth(){
        int width = 0;
        if(characterToData != null){
            for (int i = 0; i < charArray.length; i++) {
                char c = charArray[i];
                if(characterToData.containsKey(c)) {
                    width += characterToData.get(c)[4];
                }
                else{
                    width += 4;
                }
            }
        }

        return width;
    }
    private void generateOffsetsX(double dx){
        offsetsX = new ArrayList<>();
        for (int i = 0; i < charArray.length; i++) {
            offsetsX.add(dx);
            char c = charArray[i];
            if(characterToData.containsKey(c)){
                int[] data = characterToData.get(c);
                dx += data[4] * percentage;
            }
            else{
                dx += 4;
            }
        }
    }
}
