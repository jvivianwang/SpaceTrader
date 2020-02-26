package scene;

import application.Main;
import component.Player;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import component.Region;
import materials.MarketSubscene;
import materials.RegionSubscene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RegionMapPage {

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

    private int currentSceneIndex;

    private Region regionSelected;
    private Player player;

    public RegionMapPage() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        regionList = new ArrayList<>();
        player = Main.getPlayer();
        createSubScenes();

        createBackground();

        createRegion();
    }

    private void createBackground() {
        Image backgroundImage = new Image("materials/image/configBackground.png",
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

        regionList.add(new Region("0", 1, "CS1332 is so great", shuffleList.get(0)));
        regionList.add(new Region("1", 1, "CS2340 is so great", shuffleList.get(1)));
        regionList.add(new Region("2", 1, "CS2110 is so great", shuffleList.get(2)));
        regionList.add(new Region("3", 2, "MATH3012 is so great", shuffleList.get(3)));
        regionList.add(new Region("4", 2, "CS1100 is so great", shuffleList.get(4)));
        regionList.add(new Region("5", 2, "Jiawei is so great", shuffleList.get(5)));
        regionList.add(new Region("6", 3, "Rylie is so great", shuffleList.get(6)));
        regionList.add(new Region("7", 3, "Vivian is so great", shuffleList.get(7)));
        regionList.add(new Region("8", 4, "Paulina is so great", shuffleList.get(8)));
        regionList.add(new Region("9", 5, "Princeton is so great", shuffleList.get(9)));

        //Once you create all the region you also spawn player in a random region.
        player.setCurrentRegion(regionList.get(shuffleList.get(0)));
        player.getCurrentRegion().setDiscovered(true);
        player.getCurrentRegion().setRegionBackgroundToBlue();


        for (Region g: regionList) {
            mainPane.getChildren().add(g);
        }
        createMarketScene();
        for (Region g: regionList) {
            buttonPressed(g);
        }
        showSubScene(player.getCurrentRegion());
    }

    private void buttonPressed(Region targetRegion) {
        targetRegion.setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                regionSelected = targetRegion;
                showSubScene(targetRegion);
            }
        });
        subscene1.getBtnTravel().setOnMouseClicked(event -> {
            player.getCurrentRegion().setRegionBackgroundToYellow();
            regionSelected.setRegionBackgroundToBlue();
            regionSelected.setDiscovered(true);
            player.setCurrentRegion(regionSelected);
            showSubScene(regionSelected);
        });
        subscene2.getBtnTravel().setOnMouseClicked(event -> {
            player.getCurrentRegion().setRegionBackgroundToYellow();
            regionSelected.setRegionBackgroundToBlue();
            regionSelected.setDiscovered(true);
            player.setCurrentRegion(regionSelected);
            showSubScene(regionSelected);
        });

        subscene1.getBtnMarket().setOnMouseClicked(event -> {
            nextSceneToHide.moveSubScene();
            nextSceneToHide = null;
            //Set marketScene store info based on current region tech level before animation
            marketScene.moveSubScene();
        });

        subscene2.getBtnMarket().setOnMouseClicked(event -> {
            nextSceneToHide.moveSubScene();
            nextSceneToHide = null;
            //Set marketScene store info based on current region tech level before animation
            marketScene.moveSubScene();
        });
        marketScene.getBtnExit().setOnMouseClicked(e -> {
          marketScene.moveSubScene();
          showSubScene(player.getCurrentRegion());

        });
    }




    private void showSubScene(Region targetRegion) {
        if (nextSceneToHide != null) {
            nextSceneToHide.moveSubScene();
        }

        if (currentSceneIndex % 2 == 0) {
            subscene1.setDisplayInfo(targetRegion);
            if (targetRegion == player.getCurrentRegion()) {
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
            if (targetRegion == player.getCurrentRegion()) {
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

    private  void createMarketScene(){
        marketScene = new MarketSubscene();
        mainPane.getChildren().add(marketScene);
    }

    public Scene getMainScene() {
        return mainScene;
    }
}
