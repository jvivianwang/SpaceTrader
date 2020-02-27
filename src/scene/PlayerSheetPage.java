package scene;

import application.Main;
import component.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import materials.YellowButton;

//Commented out unused import for now
//import javafx.scene.text.Text;


public class PlayerSheetPage {

    private static final int HEIGHT = 900;
    private static final int WIDTH = 1600;
    private AnchorPane mainPane;
    private Scene mainScene;

    private YellowButton btnNextPage;



    public PlayerSheetPage() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        createBackground();
        createInfo();
        createButton();
    }

    private void createBackground() {
        Image backgroundImage = new Image("materials/image/playerSheetBackground.jpg",
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

    private void createInfo() {
        Label name = displayInfo("Player Name: " + Player.getInstance().getName(),
                100,
                100);
        Label credits = displayInfo("Player Credits: " + Player.getInstance().getCredits(),
                100,
                150);
        Label skillPointsLeft = displayInfo("Player Skills: " + Player.getInstance().getSkillPoints(),
                100,
                200);
        Label pilot = displayInfo("Pilot Skill Level: " + Player.getInstance().getSkills()[0],
                100,
                250);
        Label fighter = displayInfo("Fighter Skill Level: " + Player.getInstance().getSkills()[1],
                100,
                300);
        Label merchant = displayInfo("Merchant Skill Level: " + Player.getInstance().getSkills()[2],
                100,
                350);
        Label engineer = displayInfo("Engineer Skill Level: " + Player.getInstance().getSkills()[3],
                100,
                400);

        mainPane.getChildren().addAll(name,
                credits,
                skillPointsLeft,
                pilot,
                fighter,
                merchant,
                engineer);
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

    private void createButton() {
        btnNextPage  = new YellowButton("BEGIN GAME");
        btnNextPage.setLayoutX(750);
        btnNextPage.setLayoutY(600);
        btnNextPage.setPrefWidth(200);
        mainPane.getChildren().add(btnNextPage);
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public YellowButton getBtnNextPage() {
        return btnNextPage;
    }
}
