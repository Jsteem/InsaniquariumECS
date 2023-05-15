package insaniquarium.game.data;

public class PlayerData {
    private String name;
    private int unlockedTankNumber = 0;
    private int unlockedLevelNumber = 3;

    public PlayerData(String name){
        this.name = name;
    }
    public void increaseLevel(){
        if(++unlockedLevelNumber>4){
            unlockedLevelNumber = 0;
            unlockedTankNumber++;
        }
    }
    public int getUnlockedTankNumber(){
        return this.unlockedTankNumber;
    }
    public int getUnlockedLevelNumber(){
        return this.unlockedLevelNumber;
    }
}
