package materials;

import component.Broom;
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

    private YellowButton btnForfeit;
    private YellowButton btnFlee;
    private YellowButton btnFight;
    private YellowButton btnExit;

    private Label healthResultLabel;
    private Label creditsResultLabel;
    private Label fuelResultLabel;
    private Label PoliceDemandLabel;
    private Label resultLabel;

    private Item[] demandItems;
    private ImageView[] demandItemsView;

    private boolean isHidden;

    private Region targetRegion;

    private Player player = Player.getInstance();
    private Broom broom = Broom.getInstance();
    private int[] skills = player.getSkills();

    /**
     * Creates a new police subscene
     */
    public PoliceSubscene() {
        super(new AnchorPane(), 1600, 900);
        prefWidth(1600);
        prefHeight(900);

        isHidden = true;
        AnchorPane root2 = (AnchorPane) this.getRoot();
        setDemandMarket(root2);
        displayText(root2);
        setBackgroundImage(root2);
        createButton(root2);

        setLayoutX(0);
        setLayoutY(-900);
    }

    private void setDemandMarket(AnchorPane root) {
        demandItemsView = new ImageView[3];
        for (int i = 0; i < demandItemsView.length; i++) {
            //set default images to earthdragon just because the imageview cant be null
            demandItemsView[i] = new ImageView(new Image("materials/image/empty.png",
                    100, 100, false, true));
            demandItemsView[i].setLayoutX(100 + 200 * i);
            demandItemsView[i].setLayoutY(75 );
            root.getChildren().add(demandItemsView[i]);
        }
    }

    /**
     * Before you travel to the selectedRegion, if you encounter police, the police will ask random items in your broom
     * @param regionSelected the region you are about to travel to
     */
    public void generatePoliceInfo(Region regionSelected) {
        this.targetRegion = regionSelected;
        updateCreatureList();
        for (int i = 0; i < demandItems.length; i++) {
            Image image = new Image("materials/image/"
                    + demandItems[i].getName() + ".png",
                    100, 100, false, true);
            // Create the ImageView
            demandItemsView[i].setImage(image);
            demandItemsView[i].setDisable(false);
            //selectFromTrader(creatureListImage[i], i);
        }
        resetScene();
    }

    private void updateCreatureList() {
        Item[] array = new Item[Broom.getInstance().getInventory().size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = Broom.getInstance().getInventory().get(i);
        }
        List<Item> shuffleList = Arrays.asList(array);
        //Shuffle the spots
        Collections.shuffle(shuffleList);
        demandItems = new Item[array.length / 2 + 1];
        //Lose size / 2 + 1 items
        for (int i = 0; i < array.length / 2 + 1; i++) {
            demandItems[i] = array[i];
        }

    }



    /**
     * Every time you travel, the subscene's information needs to be updated(including buttons)
     * Disable exit button and enable every other buttons
     */
    private void resetScene() {
        btnExit.setDisable(true);
        btnForfeit.setDisable(false);
        btnFlee.setDisable(false);
        btnFight.setDisable(false);
        PoliceDemandLabel.setText("Well well well... wanna pass? Give me your illegal items: ");
        //reassign Imageview[] to update the image\
        healthResultLabel.setText("Your broom health: " + Broom.getInstance().getHealth());
        fuelResultLabel.setText("Your broom fuel: " + Broom.getInstance().getFuelCapacity());
        creditsResultLabel.setText("Your broom credits: " + Player.getInstance().getCredits());
        resultLabel.setText("");
    }

    /**
     * Creates the background image for the page
     * @param root Root to set background
     */
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

    /**
     * Creates buttons for player to interact with
     * @param root Root to add buttons to
     */
    private void createButton(AnchorPane root) {
        btnForfeit = new YellowButton("Forfeit");
        btnForfeit.setLayoutX(800);
        btnForfeit.setLayoutY(700);
        btnForfeit.setDisable(true);
        btnFlee = new YellowButton("Flee");
        btnFlee.setLayoutX(400);
        btnFlee.setLayoutY(700);
        btnFlee.setDisable(true);
        btnFight = new YellowButton("Fight");
        btnFight.setLayoutX(600);
        btnFight.setLayoutY(700);
        btnFight.setDisable(true);
        btnExit = new YellowButton("Exit");
        btnExit.setLayoutX(1000);
        btnExit.setLayoutY(700);
        btnExit.setDisable(true);
        forfeit();
        flee();
        fight();
        exit();
        root.getChildren().addAll(btnForfeit, btnFlee, btnFight, btnExit);
    }

    /**
     * Initalizes text for label
     * @param root Root to add conversation and result to
     */
    private void displayText(AnchorPane root) {
        PoliceDemandLabel = displayLabel("", 100, 400);
        resultLabel = displayLabel("", 100, 450);
        healthResultLabel = displayLabel("", 100, 500);
        creditsResultLabel = displayLabel("", 100, 550);
        fuelResultLabel = displayLabel("", 100, 600);
        root.getChildren().addAll(PoliceDemandLabel, resultLabel, healthResultLabel, creditsResultLabel,
                fuelResultLabel);
    }

    /**
     * store a message into a "textbox" with (x,y) which the "textbox" style could be reused
     * @param message the message you wanna show on (x,y)
     * @param x
     * @param y
     * @return A Label variable (text)
     */
    private Label displayLabel(String message, double x, double y) {
        Label temp = new Label(message);
        temp.setFont(new Font(23));
        temp.setAlignment(Pos.CENTER_LEFT);
        temp.setPrefWidth(800);
        temp.setPrefHeight(200);
        temp.setLayoutX(x);
        temp.setLayoutY(y);
        temp.setTextFill(Color.web("#ffffff"));
        return temp;
    }

    /**
     * Controls forfeit operations, after this, player can click exit button to continue to the targetRegion
     */
    private void forfeit() {
        btnForfeit.setOnMouseClicked(e -> {
            broom.getInventory().removeAll(Arrays.asList(demandItems));
            resultLabel.setText("You lose the items in your inventory");
            disableButtons();
        });
    }

    /**
     * Controls flee operations. after this, player can click exit button to continue to the targetRegion
     * Flee chance depends on pilot skill
     */
    private void flee() {
        btnFlee.setOnMouseClicked(e -> {
            Random random = new Random();
            int point = random.nextInt(100);
            if (point < 50 + Player.getInstance().getSkills()[0]) {
                resultLabel.setText("Nice speed! Your flee was successful!!");
                targetRegion = Player.getInstance().getCurrentRegion();
            } else {
                resultLabel.setText("Too slow! Better luck next time!");
                Broom.getInstance().setHealth(Broom.getInstance().getHealth() - 50);
                Player.getInstance().setCredits(Player.getInstance().getCredits() - 500);
                Broom.getInstance().getInventory().removeAll(Arrays.asList(demandItems));
            }
            disableButtons();
            creditsResultLabel.setText("Now your credits are: " + Player.getInstance().getCredits());
            healthResultLabel.setText("Your broom health: " + Broom.getInstance().getHealth());
            fuelResultLabel.setText("Your broom fuel: " + Broom.getInstance().getFuelCapacity());
        });
    }

    /**
     * Controls fight operation, after this, player can click exit button to continue to the targetRegion
     * Computes escape chance based on fightskill
     */
    private void fight() {
        btnFight.setOnMouseClicked(e -> {
            Random random = new Random();
            int point = random.nextInt(100);
            if (point < 50 + Player.getInstance().getSkills()[1] * 2) {
                resultLabel.setText("Woah! You fought valiantly and won!");
            }
//            Pdf did not say what happened when failed to fight off the police
//            else {
//                resultLabel.setText("You lost! Better luck next time!");

//                Broom.getInstance().setHealth(Broom.getInstance().getHealth() - 50);
//            }
            disableButtons();
            creditsResultLabel.setText("Now your credits are: " + Player.getInstance().getCredits());
            healthResultLabel.setText("Your broom health: " + Broom.getInstance().getHealth());
            fuelResultLabel.setText("Your broom fuel: " + Broom.getInstance().getFuelCapacity());
        });
    }


    /**
     * Once a action is taken, the player is able to exit the subscene.
     */
    private void exit() {
        btnExit.setOnMouseClicked(e -> {
            moveSubScene();
            RegionMapPage.getInstance().travelTo(targetRegion);
        });
    }

    /**
     * Transitions subscene off main page
     */
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

    /**
     * Disable buttons and enable exit button at the end of a decision
     */
    private void disableButtons() {
        btnFlee.setDisable(true);
        btnFight.setDisable(true);
        btnForfeit.setDisable(true);
        btnExit.setDisable(false);
    }


}


