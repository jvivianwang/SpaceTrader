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

    private Label resultLabel;
    private Label PoliceDemandLabel;

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

        displayText(root2);
        setBackgroundImage(root2);
        createButton(root2);

        setLayoutX(0);
        setLayoutY(-900);
    }

    /**
     * Before you travel to the selectedRegion, if you encounter police, the police will ask random items in your broom
     * @param regionSelected the region you are about to travel to
     */
    public void generatePoliceInfo(Region regionSelected) {
        this.targetRegion = regionSelected;
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
        resetScene();
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
        //reassign Imageview[] to update the image
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
        PoliceDemandLabel = displayLabel("", 700, 100);
        resultLabel = displayLabel("", 700, 400);
        root.getChildren().addAll(PoliceDemandLabel, resultLabel);
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
            int pilotSkill = skills[0];
            int points = random.nextInt(100);
            boolean escape = (points < 50 + pilotSkill * 2); //possibly change this moving forward
            targetRegion = player.getCurrentRegion(); // player goes back to the previous region
            if (escape) {
                reduceBroomHealth(5);
                loseCredits(5);
                broom.resetInventory();
                disableButtons();
                resultLabel.setText("You got what you deserved. You leave with 5 less fuels, "
                        + "credits and health");
            }
        });
    }

    /**
     * Controls fight operation, after this, player can click exit button to continue to the targetRegion
     * Computes escape chance based on fightskill
     */
    private void fight() {
        btnFight.setOnMouseClicked(e -> {
            int fightSkill = skills[1];
            Random random = new Random();
            int points = random.nextInt(100);
            boolean escape = (points < 50 + fightSkill * 2);
            String statement = escape ? "You fought the police and won" : "You fought the police"
                    + "and lost";
            resultLabel.setText(statement);
            disableButtons();
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

//    /**
//     * Reduces fuel amount when the player flees or loses a fight
//     * Only reduces the fuel amount if the amount left in the tank is greater than amount you
//     * passed in
//     * @param amount amount of fuel to lose
//     */
//    private void fuelLoss(int amount) {
//        if (broom.getFuelCapacity() >= amount) {
//            broom.setFuelCapacity(broom.getFuelCapacity() - amount);
//        }
//    }
    // This should be in travelTo() method in RegionMapPage.java

    /**
     * When police attack the broom, this method will reduce the broom health by a specified amount
     * Only works if the current broom health is greater than the amount passed in
     *
     * @param amount amount of health to lose
     */
    private void reduceBroomHealth(int amount) {
        if (broom.getHealth() >= amount) {
            broom.setHealth(broom.getHealth() - amount);
        } else {
            //Game over();
        }
    }

    /**
     * When police attack you, you will be fine iff the amount passed in is greater than the
     * amount of credits you currently have
     *
     * @param amount amount of credits to lose
     */
    private void loseCredits(int amount) {
        if (player.getCredits() >= amount) {
            player.setCredits(player.getCredits() - amount);
        }
    }

}

