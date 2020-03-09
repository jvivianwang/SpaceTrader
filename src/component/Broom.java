package component;

import materials.Item;

import java.util.ArrayList;

public class Broom {
    private static Broom single_instance = null;

    private String name;
    private int cargoCapacity;
    private int fuelCapacity;
    private ArrayList<Item> inventory;
    private int health;

    private Broom() {
        health = 0;
        //itemInventory.length == cargoCapacity;
        cargoCapacity = 9;
        inventory = new ArrayList<>(9);
    }

    public static Broom getInstance() {
        if (single_instance == null) {
            synchronized (Broom.class) {
                if (single_instance == null) {
                    single_instance = new Broom();
                }
            }
        }
        return single_instance;
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

    public void setHealth() {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void gainCreature(Creature creature) {
        for (int i = 0; i < inventory.size(); i++) {
            //If we have a same level creature in our inventory we evolve it
            if (inventory.get(i) instanceof Creature) {
                if (((Creature) inventory.get(i)).getLevel() == creature.getLevel()) {
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
}
