package materials;

import component.Broom;
import component.Equipment;
import component.Player;
import component.Region;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import scene.RegionMapPage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class PoliceSubscene extends SubScene {
    private static final String BACKGROUND_IMAGE = "materials/image/banditBackground.jpg";
    private static final String FONT_PATH = "src/materials/font/Cochin W01 Roman.ttf";
    private boolean isHidden;
    private YellowButton btnFF;
    private YellowButton btnFlee;
    private YellowButton btnFight;
    private YellowButton btnExit;
    private Label banditDemandLabel;
    private Label resultLabel;

    private Region targetRegion;
    private Item[] itemsDemand;
    private ImageView[] itemsDemandImage;

    public PoliceSubscene() {
        super(new AnchorPane(), 1600, 900);
        prefWidth(1600);
        prefHeight(900);

        isHidden = true;

        AnchorPane root2 = (AnchorPane) this.getRoot();
        setBackgroundImage(root2);
        displayText(root2);
        createButton(root2);

        setLayoutX(0);
        setLayoutY(-900);
    }

    public void generatePoliceInfo(Region regionSelected) {
        this.targetRegion = regionSelected;
        Item[] array = new Item[Broom.getInstance().getInventory().size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = Broom.getInstance().getInventory().get(i);
        }
        List<Item> shuffleList = Arrays.asList(array);
        //Shuffle the spots
        Collections.shuffle(shuffleList);

        itemsDemand = new Item[array.length / 2 + 1];
        //Lose size / 2 + 1 items
        for (int i = 0; i < array.length / 2 + 1; i++) {
            itemsDemand[i] = array[i];
        }
        resetScene();
    }

    private void resetScene() {
        btnExit.setDisable(true);
        btnFF.setDisable(false);
        btnFlee.setDisable(false);
        btnFight.setDisable(false);
        banditDemandLabel.setText("Well well well... wanna pass? Give me your illegal items: ");
        //reassign Imageview[] to update the image
        resultLabel.setText("");
    }

    private void setBackgroundImage(AnchorPane root) {
        Image backgroundImage = new Image(BACKGROUND_IMAGE,
                1600,
                900,
                false,
                true);
        BackgroundImage image = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                null);
        root.setBackground(new Background(image));
    }

    private void displayText(AnchorPane root) {
        banditDemandLabel = displayLabel("", "", 700, 100);
        resultLabel = displayLabel("", "", 700, 400);
        //create Imageview[] to show the image and add to root.
        root.getChildren().addAll(banditDemandLabel, resultLabel);
    }

    private Label displayLabel(String name, String info, double x, double y) {
        Label temp = new Label(name + "\n" + info);
        temp.setFont(new Font(23));
        temp.setAlignment(Pos.CENTER_LEFT);
        temp.setPrefWidth(800);
        temp.setPrefHeight(200);
        temp.setLayoutX(x);
        temp.setLayoutY(y);
        temp.setTextFill(Color.web("#ffffff"));
        return temp;
    }

    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.5));
        transition.setNode(this);
        if (isHidden) {
            //setToY(y) is move down y units instead move to y coordinate.
            transition.setToY(900);
            isHidden = false;
        } else {
            transition.setToY(-900);
            isHidden = true;
        }
        transition.play();
    }

    private void forFeit() {
        btnFF.setOnMouseClicked(e -> {
            resultLabel.setText("forfeit result to be implemented");
            btnExit.setDisable(false);
            btnFF.setDisable(true);
            btnFlee.setDisable(true);
            btnFight.setDisable(true);
        });
    }
    private void fight() {
        btnFight.setOnMouseClicked(e -> {
            resultLabel.setText("Fight result to be implemented");
            btnExit.setDisable(false);
            btnFF.setDisable(true);
            btnFlee.setDisable(true);
            btnFight.setDisable(true);
        });
    }
    private void flee() {
        btnFlee.setOnMouseClicked(e -> {
            resultLabel.setText("Flee result to be implemented");
            btnExit.setDisable(false);
            btnFF.setDisable(true);
            btnFlee.setDisable(true);
            btnFight.setDisable(true);
        });
    }
    private void exit() {
        btnExit.setOnMouseClicked(e -> {
            moveSubScene();
            RegionMapPage.getInstance().travelTo(targetRegion);
        });
    }

    private void createButton(AnchorPane root) {
        btnFF = new YellowButton("forFeit");
        btnFF.setLayoutX(400);
        btnFF.setLayoutY(700);
        btnFF.setDisable(false);
        btnFight = new YellowButton("Fight");
        btnFight.setLayoutX(600);
        btnFight.setLayoutY(700);
        btnFight.setDisable(false);
        btnFlee = new YellowButton("Flee");
        btnFlee.setLayoutX(800);
        btnFlee.setLayoutY(700);
        btnFlee.setDisable(false);
        btnExit = new YellowButton("Exit");
        btnExit.setLayoutX(1000);
        btnExit.setLayoutY(700);
        btnExit.setDisable(true);
        forFeit();
        fight();
        flee();
        exit();
        root.getChildren().addAll(btnExit, btnFF, btnFight, btnFlee);
    }

}
