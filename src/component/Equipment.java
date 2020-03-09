package component;


import materials.Item;

public class Equipment implements Item {
    private int basePrice;
    private String name;
    private int index;
    private int level;

    //index 0 : Invisibility Cloak
    //index 1 : Magic Wand
    //index 2 : Magic Hat
    //index 3: Pilot Goggle


    public Equipment(int index, int level) {
        this.index = index;
        this.level = level;
        setPriceAndName(index);
    }

    private void setPriceAndName(int index) {
        if (index == 0) {
            setName("InvisibilityCloak");
            setBasePrice(400);
        } else if (index == 1) {
            setName("MagicWand");
            setBasePrice(500);
        } else if (index == 2) {
            setName("MagicHat");
            setBasePrice(600);
        } else {
            setName("PilotGoggles");
            setBasePrice(700);
        }
    }

    public int getFinalPrice() {
        return (int) Math.floor(basePrice * Player.getInstance().getCurrentRegion().getTechLevel()
                * (1 - 0.01 * (Player.getInstance().getSkills()[2] * 3)));
    }

    public int getLevel() {
        return level;
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

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "Equitment{"
                + ", name='" + name + '\''
                + '}';
    }

    public int calculateSkills() {
        return this.level;
    }
}


