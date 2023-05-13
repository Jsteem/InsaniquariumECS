package insaniquarium.utility;

import java.util.Map;

public abstract class GameFont {

    Map<Character, int[]> characterToData;

    public Map<Character, int[]> getCharacterToData(){
        return this.characterToData;
    }
}
