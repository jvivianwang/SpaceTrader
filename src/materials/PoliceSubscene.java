package materials;

import component.Broom;
import component.Player;
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

public class PoliceSubscene extends SubScene {
    private static final String BACKGROUND_IMAGE = "materials/image/banditBackground.jpg";
    private static final String FONT_PATH = "src/materials/font/Cochin W01 Roman.ttf";

    private Item[] demandItems;
    private YellowButton btnForfeit;
    private YellowButton btnFlee;
    private YellowButton btnFight;
    private YellowButton btnConfirmResult;

    private Label result;
    private Label conversation;

    private ImageView[] demandItemsView;

    private boolean isHidden;

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

        setBackgroundImage(root2);
        createButton(root2);

        setLayoutX(0);
        setLayoutY(-900);
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
        btnConfirmResult = new YellowButton("Confirm Result");
        btnConfirmResult.setLayoutX(600);
        btnConfirmResult.setLayoutY(700);
        btnConfirmResult.setDisable(true);
        root.getChildren().addAll(btnForfeit, btnFlee, btnFight, btnConfirmResult);
    }

    /**
     * Initalizes text for label
     * @param root Root to add conversation and result to
     */
    private void displayText(AnchorPane root) {
        conversation = displayLabel("", "", 700, 100);
        result = displayLabel("", "", 700, 400);
        root.getChildren().addAll(conversation, result);
    }

    /**
     * I don't know what this does yet...comments incoming
     * @param name
     * @param info
     * @param x
     * @param y
     * @return
     */
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

    /**
     * Controls forfeit operations
     * Player loses items and continued to desired destination
     */
    private void forfeit() {
        btnForfeit.setOnMouseClicked(e -> {
            broom.resetInventory();
            result.setText("You lose the items in your inventory");
            disableButtons();
            moveSubScene();
            RegionMapPage.getInstance();
        });
    }

    /**
     * Controls flee operations.
     * Flee chance depends on pilot skill
     */
    private void flee() {
        btnFlee.setOnMouseClicked(e -> {
            int pilotSkill = skills[0];
            double escapeChance = 1 - 0.09 * (pilotSkill);
            boolean escape = escapeChance > 2; //possibly change this moving forward
            fuelLoss(5);
            if (escape) {
                reduceBroomHealth(5);
                loseCredits(5);
                broom.resetInventory();
                disableButtons();
                result.setText("You got what you deserved. You leave with 5 less fuels, "
                        + "credits and health");
            }
            moveSubScene();
            RegionMapPage.getInstance();
        });
    }

    /**
     * Controls fight operation
     * Computes escape chance based on fightskill
     */
    private void fight() {
        btnFight.setOnMouseClicked(e -> {
            int fightSkill = skills[1];
            double escapeChance = 1 - 0.09 * (fightSkill);
            boolean escape = escapeChance > 2; //possibly change this moving forward
            String statement = escape ? "You fought the police and won" : "You fought the police"
                    + "and lost";
            result.setText(statement);
            disableButtons();
            moveSubScene();
            RegionMapPage.getInstance();
        });
    }


    /**
     * Confirms result of the players actions.
     */
    private void confirmResult() {
        btnConfirmResult.setOnMouseClicked(e -> {
            moveSubScene();
            RegionMapPage.getInstance();
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
     * Disable buttons at the end of a decision
     */
    private void disableButtons() {
        btnFlee.setDisable(true);
        btnFight.setDisable(true);
        btnForfeit.setDisable(true);
    }

    /**
     * Reduces fuel amount when the player flees or loses a fight
     * Only reduces the fuel amount if the amount left in the tank is greater than amount you
     * passed in
     * @param amount amount of fuel to lose
     */
    private void fuelLoss(int amount) {
        if (broom.getFuelCapacity() >= amount) {
            broom.setFuelCapacity(broom.getFuelCapacity() - amount);
        }
    }

    /**
     * When police attack the broom, this method will reduce the broom health by a specified amount
     * Only works if the current broom health is greater than the amount passed in
     *
     * @param amount amount of health to lose
     */
    private void reduceBroomHealth(int amount) {
        if (broom.getHealth() >= amount) {
            broom.setHealth(broom.getHealth() - amount);
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
