package scene;

import component.Broom;
import component.Player;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import component.Region;

import materials.*;

import java.util.*;

public class RegionMapPage {
    private static RegionMapPage singleInstance = null;

    private static DimensionsHandler dim = new DimensionsHandler();
    private static final int HEIGHT = dim.getHeight();
    private static final int WIDTH = dim.getWidth();
    private static final int FONTSIZE = dim.getFontsize();
    private AnchorPane mainPane;
    private Scene mainScene;

    private ArrayList<Region> regionList;


    //Trick for the animation of subscene
    private RegionSubscene subscene1;
    private RegionSubscene subscene2;
    private RegionSubscene nextSceneToHide;

    private MarketSubscene marketScene;
    private BanditSubscene banditSubscene;
    private TraderSubscene traderSubscene;
    private PoliceSubscene policeSubscene;


    private int currentSceneIndex;

    private Region regionSelected;
    private int fuelCost;

    public static RegionMapPage getInstance() {
        if (singleInstance == null) {
            synchronized (RegionMapPage.class) {
                if (singleInstance == null) {
                    singleInstance = new RegionMapPage();
                }
            }
        }
        return singleInstance;
    }

    private RegionMapPage() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        regionList = new ArrayList<>();
        createSubScenes();

        createBackground();

        createRegion();
    }

    private void createBackground() {
        Image backgroundImage = new Image("materials/image/worldMap.jpg",
                WIDTH,
                HEIGHT,
                false,
                true);
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                null);
        mainPane.setBackground(new Background(background));
    }

    // For region info needs to be refactored
    private void createRegion() {
        // Create 10 spots.
        Integer[] array = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<Integer> shuffleList = Arrays.asList(array);

        //Shuffle the spots
        Collections.shuffle(shuffleList);

        regionList.add(new Region("0", 1, "Paulina’s Peaceful Paradise", shuffleList.get(0)));
        regionList.add(new Region("1", 1, "Jay’s Jovial Jackpot", shuffleList.get(1)));
        regionList.add(new Region("2", 1, "Princeton’s Proud Palace", shuffleList.get(2)));
        regionList.add(new Region("3", 2, "Vivian’s Vivacious Veranda", shuffleList.get(3)));
        regionList.add(new Region("4", 2, "Rylie’s Radiant Resort", shuffleList.get(4)));
        regionList.add(new Region("5", 2, "Aibek’s Astonishing Aquarium ", shuffleList.get(5)));
        regionList.add(new Region("6", 3, "Yukt's Yacht ", shuffleList.get(6)));
        regionList.add(new Region("7", 3, "Diagon Alley", shuffleList.get(7)));
        regionList.add(new Region("8", 4, "Ollivander’s ", shuffleList.get(8)));
        regionList.add(new Region("9", 5, "Hogsmeade", shuffleList.get(9)));

        //Once you create all the region you also spawn player in a random region.
        Player.getInstance().setCurrentRegion(regionList.get(shuffleList.get(0)));
        Player.getInstance().getCurrentRegion().setDiscovered(true);
        Player.getInstance().getCurrentRegion().setRegionBackgroundToBlue();

        for (Region g: regionList) {
            mainPane.getChildren().add(g);
        }
        createMarketScene();
        createNPCSubScene();

        for (Region g: regionList) {
            buttonPressed(g);
        }
        showSubScene(Player.getInstance().getCurrentRegion());
    }

    private void buttonPressed(Region targetRegion) {
        targetRegion.setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                regionSelected = targetRegion;
                showSubScene(targetRegion);
            }
        });

        travelAndMarketButtonFunction(subscene1);
        travelAndMarketButtonFunction(subscene2);
        marketScene.getBtnExit().setOnMouseClicked(e -> {
            marketScene.moveSubScene();
            showSubScene(Player.getInstance().getCurrentRegion());

        });
    }

    private void calculateFuelCost() {
        int distance =  (int) Math.sqrt(Math.pow(regionSelected.getLayoutX()
                - Player.getInstance().getCurrentRegion().getLayoutX(), 2)
                + Math.pow(regionSelected.getLayoutY()
                - Player.getInstance().getCurrentRegion().getLayoutY(), 2));
        fuelCost = (int) ((distance / 100) * (1 - 0.02 * Player.getInstance().getSkills()[0]));
    }

    private void travelAndMarketButtonFunction(RegionSubscene subscene) {
        subscene.getBtnTravel().setOnMouseClicked(event -> {
            calculateFuelCost();
            if (Broom.getInstance().getFuelCapacity() < fuelCost) {
                new Alert(Alert.AlertType.NONE,
                        "Ooops! You don't have enough fuel.", ButtonType.OK).show();
            } else {
                //Use npcEncounterForUser() for game purpose
                //Use npcEncounterForTesting() for debug purpose
                npcEncounterForUser();
            }
        });
        subscene.getBtnMarket().setOnMouseClicked(event -> {
            nextSceneToHide.moveSubScene();
            nextSceneToHide = null;
            marketScene.updatePlayerInfo();
            marketScene.updateInventory();
            marketScene.moveSubScene();
        });
        subscene.getBtnRefuel().setOnMouseClicked(event -> {
            if (Broom.getInstance().getFuelCapacity() >= 1000) {
                Alert a = new Alert(Alert.AlertType.NONE,
                        "Your current fuel is greater than the capacity!", ButtonType.OK);

                a.show();
            } else {
                int cost = 1000 - Broom.getInstance().getFuelCapacity();
                if (Player.getInstance().getCredits() > 0) {
                    System.out.println(Broom.getInstance().getFuelCapacity());
                    if (Player.getInstance().getCredits() > cost) {
                        Player.getInstance().setCredits(Player.getInstance().getCredits() - cost);
                        Broom.getInstance().setFuelCapacity(1000);

                        System.out.println(Broom.getInstance().getFuelCapacity());

                        Alert a = new Alert(Alert.AlertType.NONE,
                                "You've refueled to full capacity!", ButtonType.OK);

                        a.show();
                        showSubScene(Player.getInstance().getCurrentRegion());
                    } else {
                        int fuelCapacity = Broom.getInstance().getFuelCapacity();
                        int creditAmount = Player.getInstance().getCredits();
                        Broom.getInstance().setFuelCapacity(fuelCapacity + creditAmount);
                        Player.getInstance().setCredits(0);
                        Alert a = new Alert(Alert.AlertType.NONE,
                                "You've increased your fuel to"
                                        + fuelCapacity + "!", ButtonType.OK);

                        a.show();
                        showSubScene(Player.getInstance().getCurrentRegion());
                    }
                } else {
                    Alert a = new Alert(Alert.AlertType.NONE,
                            "Your current fuel is greater than the capacity!", ButtonType.OK);

                    a.show();
                    showSubScene(Player.getInstance().getCurrentRegion());
                }
            }
        });
        subscene.getBtnRepair().setOnMouseClicked(event -> {
            if (Broom.getInstance().getHealth() >= 1000) { //max broom health
                Alert alert = new Alert(Alert.AlertType.NONE, "Your broom is already"
                        + "at max health!", ButtonType.OK);
                alert.show();
            } else {
                int level = Player.getInstance().getCurrentRegion().getTechLevel();
                int cost = (int) Math.floor(100 * level
                        * (1 - 0.01 * (Player.getInstance().getSkills()[3] * 3)));
                if (Player.getInstance().getCredits() > cost) {
                    Player.getInstance().setCredits(Player.getInstance().getCredits() - cost);
                    Alert a = new Alert(Alert.AlertType.NONE,
                            "You've healed your ship by 50 hp!", ButtonType.OK);
                    a.show();
                } else {
                    Alert a = new Alert(Alert.AlertType.NONE, "You can't afford"
                        + "this. Broke boiiiiiii!!", ButtonType.OK);
                    a.show();
                }
            }
        });
    }



    /**
     * [M6_General_Encounters 3/13/20]
     * NPC encounters occur during travel, interrupting travel between regions
     * [M6_Bandit_Encounters 3/13/20]
     * Game difficulty increases bandit encounters
     * [M6_Space_Police_Encounter 3/13/20]
     * Game difficulty increases space-police encounters
     */
    private void npcEncounterForUser() {
        Random random = new Random();
        int points = random.nextInt(100);

        if (Player.getInstance().getDifficulty().equalsIgnoreCase("easy")) {
            //Range 0~20 : meet bandit
            if (points < 20) {
                banditSubscene.generateBanditInfo(regionSelected);
                banditSubscene.moveSubScene();
            } else if (points < 40) {
                //Range 20~40 : meet police
                //If the player has no item in inventory then just change police to bandit:)
                if (Broom.getInstance().getInventory().size() == 0) {
                    banditSubscene.generateBanditInfo(regionSelected);
                    banditSubscene.moveSubScene();
                } else {
                    policeSubscene.generatePoliceInfo(regionSelected);
                    policeSubscene.moveSubScene();
                }
            } else {
                //Range 40~100 : meet trader
                traderSubscene.generateTraderInfo(regionSelected);
                traderSubscene.moveSubScene();
            }
        } else if (Player.getInstance().getDifficulty().equalsIgnoreCase("medium")) {
            //Range 0~30 : meet bandit
            if (points < 30) {
                banditSubscene.generateBanditInfo(regionSelected);
                banditSubscene.moveSubScene();
            } else if (points < 60) {
                //Range 30~60 : meet police
                //If the player has no item in inventory then just change police to bandit:)
                if (Broom.getInstance().getInventory().size() == 0) {
                    banditSubscene.generateBanditInfo(regionSelected);
                    banditSubscene.moveSubScene();
                } else {
                    policeSubscene.generatePoliceInfo(regionSelected);
                    policeSubscene.moveSubScene();
                }
            } else {
                //Range 60~100 : meet trader
                traderSubscene.generateTraderInfo(regionSelected);
                traderSubscene.moveSubScene();
            }
        } else {
            //Range 0~40 : meet bandit
            if (points < 40) {
                banditSubscene.generateBanditInfo(regionSelected);
                banditSubscene.moveSubScene();
            } else if (points < 80) {
                //Range 40~80 : meet police
                //If the player has no item in inventory then just change police to bandit:)
                if (Broom.getInstance().getInventory().size() == 0) {
                    banditSubscene.generateBanditInfo(regionSelected);
                    banditSubscene.moveSubScene();
                } else {
                    policeSubscene.generatePoliceInfo(regionSelected);
                    policeSubscene.moveSubScene();
                }
            } else {
                //Range 60~100 : meet trader
                traderSubscene.generateTraderInfo(regionSelected);
                traderSubscene.moveSubScene();
            }
        }
    }

    private void npcEncounterForTesting(BanditSubscene banditSubscene) {
        banditSubscene.generateBanditInfo(regionSelected);
        banditSubscene.moveSubScene();
    }

    private void npcEncounterForTesting(TraderSubscene traderSubscene) {
        traderSubscene.generateTraderInfo(regionSelected);
        traderSubscene.moveSubScene();
    }

    private void npcEncounterForTesting(PoliceSubscene policeSubscene) {
        policeSubscene.generatePoliceInfo(regionSelected);
        policeSubscene.moveSubScene();
    }

    public void travelTo(Region targetRegion) {
        Player.getInstance().getCurrentRegion().setRegionBackgroundToYellow();
        targetRegion.setRegionBackgroundToBlue();
        targetRegion.setDiscovered(true);
        //Cost fuel to travel
        //If the targetRegion is same as the currentRegion,
        // it means the player flee back which still cost fuel
        //So cost fuel anyway
        Broom.getInstance().setFuelCapacity(Broom.getInstance().getFuelCapacity() - fuelCost);
        Player.getInstance().setCurrentRegion(targetRegion);
        //Set marketScene store info based on current region tech level before animation
        marketScene.updateMarket();
        marketScene.updateOlivanderMarket();
        showSubScene(targetRegion);
    }

    //changed to public from private
    public void showSubScene(Region targetRegion) {
        if (nextSceneToHide != null) {
            nextSceneToHide.moveSubScene();
        }

        if (currentSceneIndex % 2 == 0) {
            subscene1.setDisplayInfo(targetRegion);
            if (targetRegion == Player.getInstance().getCurrentRegion()) {
                subscene1.getBtnTravel().setDisable(true);
                subscene1.getBtnRefuel().setDisable(false);
                subscene1.getBtnMarket().setDisable(false);
                subscene1.getBtnRepair().setDisable(false);
            } else {
                subscene1.getBtnTravel().setDisable(false);
                subscene1.getBtnRefuel().setDisable(false);
                subscene1.getBtnRepair().setDisable(false);
            }
            subscene1.moveSubScene();
            nextSceneToHide = subscene1;
        } else {
            subscene2.setDisplayInfo(targetRegion);
            subscene2.moveSubScene();
            if (targetRegion == Player.getInstance().getCurrentRegion()) {
                subscene2.getBtnTravel().setDisable(true);
                subscene2.getBtnRefuel().setDisable(false);
                subscene2.getBtnMarket().setDisable(false);
                subscene1.getBtnRepair().setDisable(false);
            } else {
                subscene2.getBtnTravel().setDisable(false);
                subscene2.getBtnRefuel().setDisable(false);
                subscene2.getBtnMarket().setDisable(true);
                subscene1.getBtnRepair().setDisable(false);
            }
            nextSceneToHide = subscene2;
        }
        currentSceneIndex = (currentSceneIndex + 1) % 2;
    }

    private void createSubScenes() {
        subscene1 = new RegionSubscene();
        subscene2 = new RegionSubscene();
        currentSceneIndex = 0;
        mainPane.getChildren().addAll(subscene1, subscene2);

    }

    private void createMarketScene() {
        marketScene = new MarketSubscene();
        marketScene.updateMarket();
        marketScene.updateOlivanderMarket();
        mainPane.getChildren().add(marketScene);
    }

    private void createNPCSubScene() {
        banditSubscene = new BanditSubscene();
        traderSubscene = new TraderSubscene();
        policeSubscene = new PoliceSubscene();
        mainPane.getChildren().addAll(banditSubscene, traderSubscene, policeSubscene);
    }

    public Scene getMainScene() {
        return mainScene;
    }
    public MarketSubscene getMarketScene() {
        return marketScene;
    }
    public void reset() {
        singleInstance = new RegionMapPage();
    }
}
