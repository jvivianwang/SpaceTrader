package scene;

import component.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import materials.DimensionsHandler;
import materials.YellowButton;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

//Commented out unused import for now
//import javafx.scene.text.Text;

public class PlayerSheetPage {

    private static DimensionsHandler dim = new DimensionsHandler();
    private static final int HEIGHT = dim.getHeight();
    private static final int WIDTH = dim.getWidth();
    private static final int FONTSIZE = dim.getFontsize();

    private final String fontPath = "src/materials/font/Cochin W01 Roman.ttf";
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
                dim.customWidth(680),
                dim.customHeight(40));
        Label credits = displayInfo("Player Credits: " + Player.getInstance().getCredits(),
                dim.customWidth(280),
                dim.customHeight(170));
        Label skillPointsLeft = displayInfo("Player Skills: "
                        + Player.getInstance().getSkillPoints(),
                dim.customWidth(680),
                dim.customHeight(170));
        Label pilot = displayInfo("Pilot Skill Level: " + Player.getInstance().getSkills()[0],
                dim.customWidth(1080),
                dim.customHeight(170));
        Label fighter = displayInfo("Fighter Skill Level: " + Player.getInstance().getSkills()[1],
                dim.customWidth(280),
                dim.customHeight(320));
        Label merchant = displayInfo("Merchant Skill Level: " + Player.getInstance().getSkills()[2],
                dim.customWidth(1080),
                dim.customHeight(320));
        Label engineer = displayInfo("Engineer Skill Level: " + Player.getInstance().getSkills()[3],
                dim.customWidth(680),
                dim.customHeight(320));
        Image image = new Image("materials/image/broom.png");
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX(dim.customWidth((730)));
        imageView.setLayoutY(dim.customHeight((470)));
        imageView.setFitHeight(dim.customHeight((250)));
        imageView.setFitWidth(dim.customWidth((250)));

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
        temp.setPrefWidth(dim.customWidth((325)));
        temp.setPrefHeight(dim.customHeight((100)));
        temp.setLayoutX(x);
        temp.setLayoutY(y);
        temp.setStyle("-fx-font-weight: bold");
        temp.setStyle("-fx-border-color: SADDLEBROWN ;"
                + "-fx-background-color: BURLYWOOD; -fx-border-width: 2px");
        return temp;
    }

    private void createButton() {
        btnNextPage  = new YellowButton("BEGIN GAME");
        btnNextPage.setLayoutX(dim.customWidth((750)));
        btnNextPage.setLayoutY(dim.customHeight((750)));
        btnNextPage.setPrefWidth(dim.customWidth((200)));
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
            myLabel.setFont(Font.loadFont(new FileInputStream(fontPath), dim.customHeight(30)));
        } catch (FileNotFoundException e) {
            myLabel.setFont(Font.font("Verdana", FONTSIZE));
        }
    }


}
