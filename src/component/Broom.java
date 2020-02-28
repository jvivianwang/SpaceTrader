package component;

import java.util.ArrayList;

public class Broom {
    private static Broom single_instance = null;

    private String name;
    private int cargoCapacity;
    private int fuelCapacity;
    private ArrayList<Creature> creatureInventory;
    private int health;

    private Broom(){
        health = 0;
        //itemInventory.length == cargoCapacity;
        cargoCapacity = 10;
        creatureInventory = new ArrayList<>(10);
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

    public String getName(){
        return this.name;
    }

    public void setCargoCapacity(int cargoCapacity){
        this.cargoCapacity = cargoCapacity;
    }

    public int getCargoCapacity(){
        return cargoCapacity;
    }

    public void setFuelCapacity(int fuelCapacity){
        this.fuelCapacity = fuelCapacity;
    }

    public int getFuelCapacity(){
        return this.fuelCapacity;
    }

    public ArrayList<Creature> getCreatureInventory(){
        return creatureInventory;
    }

    public void setHealth(){
        this.health = health;
    }

    public int getHealth(){
        return health;
    }

    public void gainCreature(Creature creature) {
        for (int i = 0; i < creatureInventory.size(); i++) {
            //If we have a same level creature in our inventory we evolve it
            if (creatureInventory.get(i).getLevel() == creature.getLevel()) {
                creatureInventory.remove(i);
                creatureInventory.add(new Creature(creature.getLevel() + 1));
                return;
            }
        }
        //we dont have same creature, we add to our inventory
        creatureInventory.add(new Creature(creature.getLevel()));
    }

    public void removeCreature(int creatureIndex) {
        creatureInventory.remove(creatureIndex);
    }
}
