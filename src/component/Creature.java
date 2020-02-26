package component;


import application.Main;

public class Creature {
    private int level;
    private int basePrice;
    private String name;
    private Player player = Main.getPlayer();
    //contains base price
    //contains creature level
    //evolve creatures

    public Creature(String name, int level, int basePrice) {
        this.name = name;
        this.level = level;
        this.basePrice = basePrice;
    }

    private int calculatePrice() {
        return basePrice * player.getCurrentRegion().getTechLevel() / (player.getSkills()[2]);
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