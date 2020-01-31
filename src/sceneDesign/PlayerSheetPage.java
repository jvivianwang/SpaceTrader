package sceneDesign;

import application.Main;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class PlayerSheetPage {

    private static final int HEIGHT = 900;
    private static final int WIDTH = 1600;
    private AnchorPane mainPane;
    private Scene mainScene;

    public PlayerSheetPage() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        createBackground();
        createInfo();
    }

    private void createBackground() {
        Image backgroundImage = new Image("materials/image/playerSheetBackground.jpg", WIDTH, HEIGHT, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }
    public Label displayInfo(String name, double x, double y) {
        Label temp = new Label(name);
        temp.setFont(new Font(23));
        temp.setAlignment(Pos.CENTER);
        temp.setPrefWidth(325);
        temp.setPrefHeight(100);
        temp.setLayoutX(x);
        temp.setLayoutY(y);
        return temp;
    }

    private void createInfo() {
        Label name = displayInfo("Player Name: " + Main.getPlayer().getName(), 100, 100);
        Label credits = displayInfo("Player Credits: " + Main.getPlayer().getCredits(), 100, 150);
        Label skillPointsLeft = displayInfo("Player Skills: " + Main.getPlayer().getSkillPoints(), 100, 200);
        Label pilot = displayInfo("Pilot Skill Level: " + Main.getPlayer().getSkills()[0], 100, 250);
        Label fighter = displayInfo("Fighter Skill Level: " + Main.getPlayer().getSkills()[1], 100, 300);
        Label merchant = displayInfo("Merchant Skill Level: " + Main.getPlayer().getSkills()[2], 100, 350);
        Label engineer = displayInfo("Engineer Skill Level: " + Main.getPlayer().getSkills()[3], 100, 400);

        mainPane.getChildren().addAll(name, credits, skillPointsLeft, pilot, fighter, merchant, engineer);
    }

    public Scene getMainScene() {
        return mainScene;
    }
}
