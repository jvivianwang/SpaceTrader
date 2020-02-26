package component;

public class Broom {
    private String name;
    private int cargoCapacity;
    private int fuelCapacity;
    private int[] itemInventory;
    private int health;

    public Broom(){
        health = 0;
        //itemInventory.length == cargoCapacity;
        // TODO: setting the length of itemInventory here manually, need to redefine, same for fuelCapacity
        itemInventory = new int[10];
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

    public void setItemInventory(int[] itemInventory){
        this.itemInventory = itemInventory;
    }

    public int[] getItemInventory(){
        return itemInventory;
    }

    public void setHealth(){
        this.health = health;
    }

    public int getHealth(){
        return health;
    }
}
