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

    public Creature(int level) {
        this.level = level;
        setLevelandName(level);
    }

    private void setLevelandName(int level) {
        if (level == 0) {
            setName("EarthDragon");
            setBasePrice(50);
        } else if (level == 1) {
            setName("AirDragon");
            setBasePrice(150);
        } else if (level == 2) {
            setName("FireDragon");
            setBasePrice(350);
        } else if (level == 3) {
            setName("WaterDragon");
            setBasePrice(750);
        } else if (level == 4) {
            setName("MagicDragon");
            setBasePrice(1550);
        } else if (level == 5) {
            setName("EarthFairy");
            setBasePrice(3150);
        } else if (level == 6) {
            setName("FireFairy");
            setBasePrice(6350);
        } else if (level == 7) {
            setName("WaterFairy");
            setBasePrice(12750);
        } else if (level == 8) {
            setName("MagicFairy");
            setBasePrice(20550);
        } else {
            setName("Unicorn");
            setBasePrice(9999999);
        }
    }

    public int getFinalPrice() {
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