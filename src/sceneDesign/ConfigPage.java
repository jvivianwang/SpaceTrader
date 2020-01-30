package sceneDesign;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import materials.ConfigSkillSubscene;
import materials.YellowButton;

public class ConfigPage {

    private static final int HEIGHT = 900;
    private static final int WIDTH = 1600;
    private AnchorPane mainPane;
    private Scene mainScene;

    private ConfigSkillSubscene easySubScene;
    private ConfigSkillSubscene medSubScene;
    private ConfigSkillSubscene hardSubScene;

    private ConfigSkillSubscene nextSceneToHide;
    private YellowButton nextButtonToRelease;

    public ConfigPage() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        createSubScenes();

        createYellowButtons();
        createBackground();
    }

    private void difficultyButtonPressed(YellowButton subScene) {
        if(nextButtonToRelease != null) {
            nextButtonToRelease.setButtonStyle();
        }
        subScene.setButtonStyle();
        nextButtonToRelease = subScene;
    }

    private void showSubScene(ConfigSkillSubscene subScene) {
        if(nextSceneToHide != null) {
            nextSceneToHide.moveSubScene();
        }
        subScene.moveSubScene();
        nextSceneToHide = subScene;
    }

    private void createSubScenes() {
        easySubScene = new ConfigSkillSubscene();
        medSubScene = new ConfigSkillSubscene();
        hardSubScene = new ConfigSkillSubscene();
        mainPane.getChildren().addAll(easySubScene, medSubScene, hardSubScene);
    }

    public Scene getMainScene() {
        return mainScene;
    }

    private void createYellowButtons() {
        YellowButton btnEasy = new YellowButton("Easy");
        btnEasy.setLayoutX(500);
        btnEasy.setLayoutY(600);
        YellowButton btnMed = new YellowButton("Medium");
        btnMed.setLayoutX(700);
        btnMed.setLayoutY(600);
        YellowButton btnHard = new YellowButton("Hard");
        btnHard.setLayoutX(900);
        btnHard.setLayoutY(600);

        btnEasy.setOnMousePressed(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                difficultyButtonPressed(btnEasy);
                showSubScene(easySubScene);
                //player.setCredits(1000);
                //player.setSkillPoints(16);
            }
        });
        btnMed.setOnMousePressed(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                difficultyButtonPressed(btnMed);
                showSubScene(medSubScene);
                //player.setCredits(500);
                //player.setSkillPoints(12);
            }
        });
        btnHard.setOnMousePressed(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                difficultyButtonPressed(btnHard);
                showSubScene(hardSubScene);
                //player.setCredits(100);
                //player.setSkillPoints(8);
            }
        });

        mainPane.getChildren().addAll(btnEasy, btnMed, btnHard);
    }

    private void createBackground() {
        Image backgroundImage = new Image("materials/image/configBackground.png", WIDTH, HEIGHT, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }
}
