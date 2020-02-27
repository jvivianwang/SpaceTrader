package component;

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

public class Market {
    private static Market single_instance = null;
    private Creature[] shopList;

    private Market() {
        shopList = new Creature[5];
    }

    public Creature[] getShopList() {
        return shopList;
    }

    public void updateShopList() {
        if (Player.getInstance().getCurrentRegion().getTechLevel() == 1) {
            shopList = new Creature[]{
                    new Creature("EarthDragon"),
                    new Creature("AirDragon"),
                    new Creature("FireDragon"),
                    new Creature("WaterDragon"),
                    new Creature("MagicDragon")
            };
        } else if (Player.getInstance().getCurrentRegion().getTechLevel() == 2) {
            shopList = new Creature[]{
                    new Creature("AirDragon"),
                    new Creature("FireDragon"),
                    new Creature("WaterDragon"),
                    new Creature("MagicDragon"),
                    new Creature("EarthFairy")
            };
        } else if (Player.getInstance().getCurrentRegion().getTechLevel() == 3) {
            shopList = new Creature[]{
                    new Creature("FireDragon"),
                    new Creature("WaterDragon"),
                    new Creature("MagicDragon"),
                    new Creature("EarthFairy"),
                    new Creature("FireFairy")
            };
        } else if (Player.getInstance().getCurrentRegion().getTechLevel() == 4) {
            shopList = new Creature[]{
                    new Creature("WaterDragon"),
                    new Creature("MagicDragon"),
                    new Creature("EarthFairy"),
                    new Creature("FireFairy"),
                    new Creature("WaterFairy")
            };
        } else {
            shopList = new Creature[]{
                    new Creature("MagicDragon"),
                    new Creature("EarthFairy"),
                    new Creature("FireFairy"),
                    new Creature("WaterFairy"),
                    new Creature("MagicFairy")
            };
        }
    }

    public static Market getInstance() {
        if (single_instance == null) {
            synchronized (Market.class) {
                if (single_instance == null) {
                    single_instance = new Market();
                }
            }
        }
        return single_instance;
    }
}
