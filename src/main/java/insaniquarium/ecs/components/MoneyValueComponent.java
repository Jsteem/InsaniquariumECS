package insaniquarium.ecs.components;

import insaniquarium.ecs.Component;

public class MoneyValueComponent extends Component {
    public int moneyValue;

    public MoneyValueComponent(int level){

        int money = 0;
        switch (level) {


            case 0 -> {
                //silver
                money = 15;
                //SoundManager.getInstance().playSound("POINTS.ogg");
            }
            case 1 -> {
                //gold
                money = 25;
                //SoundManager.getInstance().playSound("POINTS.ogg");
            }
            case 2 -> {
                //star
                money = 40;
                //SoundManager.getInstance().playSound("POINTS.ogg");
            }
            case 3 -> {
                //diamond and perl
                money = 200;
                //SoundManager.getInstance().playSound("diamond.ogg");
            }
            case 4 -> {
                //chest
                money = 2000;
                //SoundManager.getInstance().playSound("TREASURE.ogg");
            }
            case 5 -> {
                //beetle
                money = 30;
                //SoundManager.getInstance().playSound("POINTS.ogg");
            }
        }
        this.moneyValue = money;
    }

}
