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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import scene.RegionMapPage;

import java.util.Random;


public class BanditSubscene extends SubScene {
    private static final String BACKGROUND_IMAGE = "materials/image/banditBackground.jpg";
    private static final String FONT_PATH = "src/materials/font/Cochin W01 Roman.ttf";
    private boolean isHidden;
    private YellowButton btnPay;
    private YellowButton btnFlee;
    private YellowButton btnFight;
    private YellowButton btnExit;
    private int demandCredits;
    private Label banditDemandLabel;
    private Label resultLabel;

    private Region targetRegion;

    public BanditSubscene() {
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

    public void generateBanditInfo(Region regionSelected) {
        this.targetRegion = regionSelected;
        Random random = new Random();
        int baseDemand = random.nextInt(100);
        if (Player.getInstance().getDifficulty().equalsIgnoreCase("easy")) {
            demandCredits = baseDemand;
        } else if (Player.getInstance().getDifficulty().equalsIgnoreCase("medium")) {
            demandCredits = baseDemand * 10;
        } else {
            demandCredits = baseDemand * 100;
        }
        resetScene();
    }

    private void resetScene() {
        btnExit.setDisable(true);
        btnPay.setDisable(false);
        btnFlee.setDisable(false);
        btnFight.setDisable(false);
        banditDemandLabel.setText("Well well well... wanna pass? Give me your money: " +  demandCredits);
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

    private void pay() {
        btnPay.setOnMouseClicked(e -> {
            if (Player.getInstance().getCredits() < demandCredits) {

            } else {
                Player.getInstance().setCredits(Player.getInstance().getCredits() - demandCredits);
                resultLabel.setText(String.format("Successful payment! You current credit balance is: %d", Player.getInstance().getCredits()));
                btnExit.setDisable(false);
                btnPay.setDisable(true);
                btnFlee.setDisable(true);
                btnFight.setDisable(true);
            }

        });
    }
    private void fight() {
        btnFight.setOnMouseClicked(e -> {
            resultLabel.setText("Fight result to be implemented");
            btnExit.setDisable(false);
            btnPay.setDisable(true);
            btnFlee.setDisable(true);
            btnFight.setDisable(true);
        });
    }
    private void flee() {
        btnFlee.setOnMouseClicked(e -> {
            resultLabel.setText("Flee result to be implemented");
            btnExit.setDisable(false);
            btnPay.setDisable(true);
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
        btnPay = new YellowButton("Pay");
        btnPay.setLayoutX(400);
        btnPay.setLayoutY(700);
        btnPay.setDisable(false);
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
        pay();
        fight();
        flee();
        exit();
        root.getChildren().addAll(btnExit, btnPay, btnFight, btnFlee);
    }

}
