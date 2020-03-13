package scene;

import component.Player;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import component.Region;
import materials.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RegionMapPage {
    private static RegionMapPage single_instance = null;

    private static final int HEIGHT = 900;
    private static final int WIDTH = 1600;
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

    public static RegionMapPage getInstance() {
        if (single_instance == null) {
            synchronized (RegionMapPage.class) {
                if (single_instance == null) {
                    single_instance = new RegionMapPage();
                }
            }
        }
        return single_instance;
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

    private void travelAndMarketButtonFunction(RegionSubscene subscene) {
        subscene.getBtnTravel().setOnMouseClicked(event -> {
            banditSubscene.generateBanditInfo(regionSelected);
            banditSubscene.moveSubScene();
            //change to police subScene to test out police
            //Change to NPC interaction
            //Before implement random NPC interaction in order to work on your own NPC page you shall do like these:
            //banditSubscene = new BanditSubscene();
            //banditSubscene.moveSubScene();
        });
        subscene.getBtnMarket().setOnMouseClicked(event -> {
            nextSceneToHide.moveSubScene();
            nextSceneToHide = null;
            marketScene.moveSubScene();
        });
    }

    public void travelTo(Region targetRegion) {
        Player.getInstance().getCurrentRegion().setRegionBackgroundToYellow();
        targetRegion.setRegionBackgroundToBlue();
        targetRegion.setDiscovered(true);
        Player.getInstance().setCurrentRegion(targetRegion);
        //Set marketScene store info based on current region tech level before animation
        marketScene.updateMarket();
        marketScene.updateOlivanderMarket();
        showSubScene(targetRegion);
    }


    private void showSubScene(Region targetRegion) {
        if (nextSceneToHide != null) {
            nextSceneToHide.moveSubScene();
        }

        if (currentSceneIndex % 2 == 0) {
            subscene1.setDisplayInfo(targetRegion);
            if (targetRegion == Player.getInstance().getCurrentRegion()) {
                subscene1.getBtnTravel().setDisable(true);
                subscene1.getBtnMarket().setDisable(false);
            } else {
                subscene1.getBtnTravel().setDisable(false);
                subscene1.getBtnMarket().setDisable(true);
            }
            subscene1.moveSubScene();
            nextSceneToHide = subscene1;
        } else {
            subscene2.setDisplayInfo(targetRegion);
            subscene2.moveSubScene();
            if (targetRegion == Player.getInstance().getCurrentRegion()) {
                subscene2.getBtnTravel().setDisable(true);
                subscene2.getBtnMarket().setDisable(false);
            } else {
                subscene2.getBtnTravel().setDisable(false);
                subscene2.getBtnMarket().setDisable(true);
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
}
