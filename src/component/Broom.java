package component;

import materials.Item;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Broom {
    private static Broom singleInstance = null;

    private String name;
    private int cargoCapacity;
    private int fuelCapacity;


    private ArrayList<Item> inventory;


    private int health;
    private boolean unicorn;


    private Broom() {
        health = 1000;
        fuelCapacity = 1000;
        //itemInventory.length == cargoCapacity;
        cargoCapacity = 9;
        inventory = new ArrayList<>(cargoCapacity);
        unicorn = false;
    }

    public static Broom getInstance() {
        if (singleInstance == null) {
            synchronized (Broom.class) {
                if (singleInstance == null) {
                    singleInstance = new Broom();
                }
            }
        }
        return singleInstance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setCargoCapacity(int cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }

    public int getCargoCapacity() {
        return cargoCapacity;
    }

    public void setFuelCapacity(int fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public int getFuelCapacity() {
        return this.fuelCapacity;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public String getHealthString() {
        DecimalFormat df = new DecimalFormat("#");
        return df.format(Broom.getInstance().getHealth());
    }

    public void repair() {
        health += 50;
    }

    public void gainCreature(Creature creature) {
        for (int i = 0; i < inventory.size(); i++) {
            //If we have a same level creature in our inventory we evolve it
            if (inventory.get(i) instanceof Creature) {
                if (inventory.get(i).getLevel() == creature.getLevel()) {
                    inventory.remove(i);
                    inventory.add(new Creature(creature.getLevel() + 1));
                    return;
                }
            }
        }
        //we dont have same creature, we add to our inventory
        inventory.add(new Creature(creature.getLevel()));
    }

    public void gainEquipment(Equipment equipment) {
        inventory.add(new Equipment(equipment.getIndex(), equipment.getLevel()));
    }

    public void remove(int index) {
        inventory.remove(index);
    }

    //resets broom inventory by creating an empty array list
    public void resetInventory() {
        inventory = new ArrayList<>(cargoCapacity);
    }

    public void setUnicorn(boolean unicorn) {
        this.unicorn = unicorn;
    }
    public boolean getUnicorn() {
        return unicorn;
    }
    public void reset() {
        singleInstance = new Broom();
    }
}

