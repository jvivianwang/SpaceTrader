package scene;

import application.Main;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import component.Region;
import materials.ConfigSkillSubscene;
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

    private RegionSubscene subscene1;
    private RegionSubscene subscene2;
    private RegionSubscene nextSceneToHide;
    private int currentSceneIndex;

    public RegionMapPage() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        regionList = new ArrayList<>();

        createBackground();
        createRegion();
        createSubScenes();
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
        Main.getPlayer().setCurrentRegion(regionList.get(shuffleList.get(0)));
        Main.getPlayer().getCurrentRegion().setDiscovered(true);

        for (Region g: regionList) {
            buttonPressed(g);
            mainPane.getChildren().add(g);
        }
    }

    private void buttonPressed(Region region) {
        region.setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                showSubScene(region);
            }
        });
    }

    private void showSubScene(Region region) {
        if (nextSceneToHide != null) {
            nextSceneToHide.moveSubScene();
        }

        if (currentSceneIndex % 2 == 0) {
            subscene1.setDisplayInfo(Main.getPlayer().getCurrentRegion(), region);
            subscene1.moveSubScene();
            nextSceneToHide = subscene1;
        } else {
            subscene2.setDisplayInfo(Main.getPlayer().getCurrentRegion(), region);
            subscene2.moveSubScene();
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

    public Scene getMainScene() {
        return mainScene;
    }
}
