package component;


import application.Main;

public class Creature {
    private int level;
    private int basePrice;
    private String name;
    //contains base price
    //contains creature level
    //evolve creatures
    //    EarthDragon is level 0
    //    AirDragon is level 1
    //    FireDragon is level 2
    //    WaterDragon is level 3
    //    MagicDragon is level 4
    //    EarthFairy is level 5
    //    FireFairy is level 6
    //    WaterFairy is level 7
    //    MagicFairy is level 8
    //    Unicorn is level 9

    public Creature(String name) {
        this.name = name;
        setLevelandPrice(name);
    }

    private void setLevelandPrice(String name) {
        if (name.equalsIgnoreCase("EarthDragon")) {
            setLevel(0);
            setBasePrice(100);
        } else if (name.equalsIgnoreCase("AirDragon")) {
            setLevel(1);
            setBasePrice(300);
        } else if (name.equalsIgnoreCase("FireDragon")) {
            setLevel(2);
            setBasePrice(700);
        } else if (name.equalsIgnoreCase("WaterDragon")) {
            setLevel(3);
            setBasePrice(1500);
        } else if (name.equalsIgnoreCase("MagicDragon")) {
            setLevel(4);
            setBasePrice(3100);
        } else if (name.equalsIgnoreCase("EarthFairy")) {
            setLevel(5);
            setBasePrice(6300);
        } else if (name.equalsIgnoreCase("FireFairy")) {
            setLevel(6);
            setBasePrice(12700);
        } else if (name.equalsIgnoreCase("WaterFairy")) {
            setLevel(7);
            setBasePrice(25500);
        } else if (name.equalsIgnoreCase("MagicFairy")) {
            setLevel(8);
            setBasePrice(51100);
        } else {
            setLevel(9);
            setBasePrice(9999999);
        }
    }

    private int calculatePrice() {
        return (int) Math.floor(basePrice * Player.getInstance().getCurrentRegion().getTechLevel() *
                (1 - 0.01 * (Player.getInstance().getSkills()[2] * 3)));
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Creature{" +
                "level=" + level +
                ", name='" + name + '\'' +
                '}';
    }
}