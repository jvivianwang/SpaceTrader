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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Market {
    private static Market single_instance = null;
    private Creature[] shopList;
    private Creature[] tradeList;
    private Equipment[] equipmentList;

    private Market() {
        shopList = new Creature[5];
        equipmentList = new Equipment[2];
    }

    public Creature[] getShopList() {
        return shopList;
    }
    public Creature[] getTradeList() {
        return tradeList;
    }

    public Equipment[] getEquipmentList() {
        return equipmentList;
    }

    public void updateShopList() {
        if (Player.getInstance().getCurrentRegion().getTechLevel() == 1) {
            shopList = new Creature[] {new Creature(0),
                new Creature(1),
                new Creature(2),
                new Creature(3),
                new Creature(4) };
        } else if (Player.getInstance().getCurrentRegion().getTechLevel() == 2) {
            shopList = new Creature[] {
                new Creature(1),
                new Creature(2),
                new Creature(3),
                new Creature(4),
                new Creature(5)
            };
        } else if (Player.getInstance().getCurrentRegion().getTechLevel() == 3) {
            shopList = new Creature[] {
                new Creature(2),
                new Creature(3),
                new Creature(4),
                new Creature(5),
                new Creature(6)
            };
        } else if (Player.getInstance().getCurrentRegion().getTechLevel() == 4) {
            shopList = new Creature[] {
                new Creature(3),
                new Creature(4),
                new Creature(5),
                new Creature(6),
                new Creature(7)
            };
        } else {
            shopList = new Creature[] {
                new Creature(4),
                new Creature(5),
                new Creature(6),
                new Creature(7),
                new Creature(8)
            };
        }
    }

    public void updateEquipmentList() {
        Integer[] array = new Integer[]{0, 1, 2, 3};
        List<Integer> shuffleList = Arrays.asList(array);
        Collections.shuffle(shuffleList);
        equipmentList = new Equipment[] {
            new Equipment(shuffleList.get(0), Player.getInstance().
                    getCurrentRegion().getTechLevel()),
            new Equipment(shuffleList.get(1), Player.getInstance().
                     getCurrentRegion().getTechLevel())
        };
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
