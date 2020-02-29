package scene;

import component.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import materials.YellowButton;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

//Commented out unused import for now
//import javafx.scene.text.Text;



public class PlayerSheetPage {

    private final String fontPath = "src/materials/font/Cochin W01 Roman.ttf";
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
                680,
                40);
        Label credits = displayInfo("Player Credits: " + Player.getInstance().getCredits(),
                280,
                170);
        Label skillPointsLeft = displayInfo("Player Skills: "
                        + Player.getInstance().getSkillPoints(),
                680,
                170);
        Label pilot = displayInfo("Pilot Skill Level: " + Player.getInstance().getSkills()[0],
                1080,
                170);
        Label fighter = displayInfo("Fighter Skill Level: " + Player.getInstance().getSkills()[1],
                280,
                320);
        Label merchant = displayInfo("Merchant Skill Level: " + Player.getInstance().getSkills()[2],
                1080,
                320);
        Label engineer = displayInfo("Engineer Skill Level: " + Player.getInstance().getSkills()[3],
                680,
                320);
        Image image = new Image("materials/image/broom.png");
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX(730);
        imageView.setLayoutY(470);
        imageView.setFitHeight(250);
        imageView.setFitWidth(250);

        mainPane.getChildren().addAll(name,
                credits,
                skillPointsLeft,
                pilot,
                fighter,
                merchant,
                engineer,
                imageView);
    }

    public Label displayInfo(String name, double x, double y) {
        Label temp = new Label(name);
        setLabelFont(temp);
        temp.setAlignment(Pos.CENTER);
        temp.setPrefWidth(325);
        temp.setPrefHeight(100);
        temp.setLayoutX(x);
        temp.setLayoutY(y);
        temp.setStyle("-fx-font-weight: bold");
        temp.setStyle("-fx-border-color: SADDLEBROWN ;"
                + "-fx-background-color: BURLYWOOD; -fx-border-width: 2px");
        return temp;
    }

    private void createButton() {
        btnNextPage  = new YellowButton("BEGIN GAME");
        btnNextPage.setLayoutX(750);
        btnNextPage.setLayoutY(750);
        btnNextPage.setPrefWidth(200);
        mainPane.getChildren().add(btnNextPage);
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public YellowButton getBtnNextPage() {
        return btnNextPage;
    }
    private void setLabelFont(Label myLabel) {
        try {
            myLabel.setFont(Font.loadFont(new FileInputStream(fontPath), 30));
        } catch (FileNotFoundException e) {
            myLabel.setFont(Font.font("Verdana", 23));
        }
    }
}
