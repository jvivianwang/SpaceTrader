package materials;

import component.Broom;
import component.Creature;
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

public class TraderSubscene extends SubScene {
    private static final String BACKGROUND_IMAGE = "materials/image/banditBackground.jpg";
    private static final String FONT_PATH = "src/materials/font/Cochin W01 Roman.ttf";
    private boolean isHidden;
    private YellowButton btnBuy;
    private YellowButton btnIgnore;
    private YellowButton btnRob;
    private YellowButton btnNegotiate;
    private YellowButton btnExit;
    private Label banditDemandLabel;
    private Label resultLabel;

    private Region targetRegion;
    private Item[] itemsDemand;
    private ImageView[] itemsDemandImage;



    public TraderSubscene() {
        super(new AnchorPane(), 1600, 900);
        prefWidth(1600);
        prefHeight(900);

        isHidden = true;


        AnchorPane root2 = (AnchorPane) this.getRoot();

        setBackgroundImage(root2);
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
        btnBuy.setDisable(false);
        btnIgnore.setDisable(false);
        btnRob.setDisable(false);
        btnNegotiate.setDisable(false);
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

    private void buy() {
        btnBuy.setOnMouseClicked(e -> {
            resultLabel.setText("result to be implemented");
            btnExit.setDisable(false);
            btnBuy.setDisable(true);
            btnNegotiate.setDisable(true);
            btnRob.setDisable(true);
            btnIgnore.setDisable(true);
        });
    }
    private void ignore() {
        btnIgnore.setOnMouseClicked(e -> {
            resultLabel.setText("result to be implemented");
            btnExit.setDisable(false);
            btnBuy.setDisable(true);
            btnNegotiate.setDisable(true);
            btnRob.setDisable(true);
            btnIgnore.setDisable(true);
        });
    }
    private void rob() {
        btnRob.setOnMouseClicked(e -> {
            resultLabel.setText("result to be implemented");
            btnExit.setDisable(false);
            btnBuy.setDisable(true);
            btnNegotiate.setDisable(true);
            btnRob.setDisable(true);
            btnIgnore.setDisable(true);
        });
    }
    private void negotiate() {
        btnNegotiate.setOnMouseClicked(e -> {
            resultLabel.setText("result to be implemented");
            btnExit.setDisable(false);
            btnBuy.setDisable(true);
            btnNegotiate.setDisable(true);
            btnRob.setDisable(true);
            btnIgnore.setDisable(true);
        });
    }

    private void exit() {
        btnExit.setOnMouseClicked(e -> {
            moveSubScene();
            RegionMapPage.getInstance().travelTo(targetRegion);
        });
    }

    private void createButton(AnchorPane root) {
        btnBuy = new YellowButton("Buy");
        btnBuy.setLayoutX(400);
        btnBuy.setLayoutY(700);
        btnBuy.setDisable(false);
        btnRob = new YellowButton("Rob");
        btnRob.setLayoutX(600);
        btnRob.setLayoutY(700);
        btnRob.setDisable(false);
        btnNegotiate = new YellowButton("Negotiate");
        btnNegotiate.setLayoutX(800);
        btnNegotiate.setLayoutY(700);
        btnNegotiate.setDisable(false);
        btnIgnore = new YellowButton("Ignore");
        btnIgnore.setLayoutX(1000);
        btnIgnore.setLayoutY(700);
        btnIgnore.setDisable(false);
        btnExit = new YellowButton("Exit");
        btnExit.setLayoutX(1200);
        btnExit.setLayoutY(700);
        btnExit.setDisable(true);
        buy();
        rob();
        ignore();
        negotiate();
        exit();
        root.getChildren().addAll(btnExit, btnBuy, btnRob, btnNegotiate, btnIgnore);
    }