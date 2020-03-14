package materials;

import component.Broom;
import component.Player;
import component.Region;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
    private boolean isHidden;
    private YellowButton btnPay;
    private YellowButton btnFlee;
    private YellowButton btnFight;
    private YellowButton btnExit;
    private YellowButton btnContinueNoPay;
    private int demandCredits;
    private Label banditDemandLabel;
    private Label broomHealth;
    private Label broomFuel;
    private Label resultLabel;
    private Label creditLabel;

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

    /**
     * [M6_Bandit_Encounter 3/13/20]
     * Bandit should demand a number of credits every time player meets bandit
     * @param regionSelected the region the player is about to travel to
     */
    public void generateBanditInfo(Region regionSelected) {
        this.targetRegion = regionSelected;
        Random random = new Random();
        int baseDemand = random.nextInt(100);
        if (Player.getInstance().getDifficulty().equalsIgnoreCase("easy")) {
            demandCredits = baseDemand * 10;
        } else if (Player.getInstance().getDifficulty().equalsIgnoreCase("medium")) {
            demandCredits = baseDemand * 50;
        } else {
            demandCredits = baseDemand * 100;
        }
        resetScene();
    }

    /**
     * Refresh/update the banditSubscene
     */
    private void resetScene() {
        btnExit.setDisable(true);
        btnPay.setDisable(false);
        btnContinueNoPay.setDisable(true);
        btnFlee.setDisable(false);
        btnFight.setDisable(false);
        banditDemandLabel.setText("Well well well... wanna pass? Give me your money: " +  demandCredits);
        broomHealth.setText("Your broom health: " + Broom.getInstance().getHealth());
        broomFuel.setText("Your broom fuel: " + Broom.getInstance().getFuelCapacity());
        resultLabel.setText("");
        creditLabel.setText("Your current credits left: " + Player.getInstance().getCredits());
    }

    /**
     * Draw the background of the current subscene
     * @param root the root where we draw the background
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
     * Create the text box (Label) in the current subscene
     * @param root the root where we display the text (text box)
     */
    private void displayText(AnchorPane root) {
        banditDemandLabel = displayLabel("", 400, 100);
        creditLabel = displayLabel("", 400,200);
        broomHealth = displayLabel("", 400, 300);
        broomFuel = displayLabel("", 400, 400);
        resultLabel = displayLabel("", 400, 500);

        root.getChildren().addAll(banditDemandLabel, creditLabel, broomFuel, broomHealth, resultLabel);
    }

    /**
     * display the text in the text box and shows the text box at a coordinate (x, y)
     * @param message the text we put in the text box
     * @param x the x location of top left corner of the text box
     * @param y the y location of top left corner of the text box
     * @return the text box (Label)
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
     * Move the subscene up and down with its own algorithm which determines moving up or moving down
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
     * [M6_Pay_Bandit_Option 3/13/20]
     *  When paying bandit, player should lose the specified number of credits and continue to intended destination
     */
    private void pay() {
        btnPay.setOnMouseClicked(e -> {
            //Not enough money
            if (Player.getInstance().getCredits() < demandCredits) {
                resultLabel.setText("You do not have enough credits to pay.");
                btnPay.setDisable(true);
                //Enable continue without pay button
                btnContinueNoPay.setDisable(false);
            } else {
                //Paid
                Player.getInstance().setCredits(Player.getInstance().getCredits() - demandCredits);
                resultLabel.setText(String.format("Successful payment! You current credit balance is: %d",
                        Player.getInstance().getCredits()));
                //Enable exit button
                disableButtons();
            }
            banditDemandLabel.setText("Now your credits are: " + Player.getInstance().getCredits());
            broomHealth.setText("Your broom health: " + Broom.getInstance().getHealth());
            broomFuel.setText("Your broom fuel: " + Broom.getInstance().getFuelCapacity());
        });
    }

    /**
     * [M6_Pay_Bandit_Option 3/12/20]
     *  If player can’t afford Bandit demands but chooses not to flee or fight, then player loses entire inventory,
     *  cargo space updated, then continues to intended destination
     * [M6_Pay_Bandit_Option 3/12/20]
     *  If player can’t afford the demand and has no items (and chooses not to flee or fight),
     *  their ship health is lowered then they continue to intended destination
     */
    private void continueNoPay() {
        btnContinueNoPay.setOnMouseClicked(e -> {
            //Player has no items
            if (Broom.getInstance().getInventory().size() <= 0) {
                resultLabel.setText("You do not have any items we can take. Your ship get damaged");
                //Health below zero Game Over
                if (Broom.getInstance().getHealth() <= 50) {
                    new Alert(Alert.AlertType.NONE,
                            "Broom Destroyed Game Over.", ButtonType.OK).show();
                    Platform.exit();
                    System.exit(0);
                } else {
                    //lose health
                    Broom.getInstance().setHealth(Broom.getInstance().getHealth() - 50);
                }
            } else {
                //Player has items
                resultLabel.setText("We take all your items");
                //lose entire cargo
                Broom.getInstance().resetInventory();
            }
            banditDemandLabel.setText("Now your credits are: " + Player.getInstance().getCredits());
            broomHealth.setText("Your broom health: " + Broom.getInstance().getHealth());
            broomFuel.setText("Your broom fuel: " + Broom.getInstance().getFuelCapacity());
            //Enable Exit button
            disableButtons();
        });
    }

    /**
     * [M6_Fight_Bandit_Option 3/13/20]
     *  Fighter skill affects likelihood of success of fighting bandit
     * [M6_Fight_Bandit_Option 3/13/20]
     *  Successful fighting: Player granted additional credits and continues traveling to intended destination
     * [M6_Fight_Bandit_Option 3/13/20]
     *  Unsuccessful fighting: Player should lose all credits and ship’s health should be lowered
     */
    private void fight() {
        btnFight.setOnMouseClicked(e -> {
            Random random = new Random();
            int point = random.nextInt(100);
            //Successful fighting
            if (point < 50 + Player.getInstance().getSkills()[1] * 2) {
                resultLabel.setText("Woah! You fought valiantly and won!");
                Player.getInstance().setCredits(Player.getInstance().getCredits() + 500);
            } else {
                //Unsuccessful fighting
                resultLabel.setText("You lost! Better luck next time!");
                //lose all credits
                Player.getInstance().setCredits(0);
                //Health below zero Game Over
                if (Broom.getInstance().getHealth() <= 50) {
                    new Alert(Alert.AlertType.NONE,
                            "Broom Destroyed Game Over.", ButtonType.OK).show();
                    Platform.exit();
                    System.exit(0);
                } else {
                    //lose health
                    Broom.getInstance().setHealth(Broom.getInstance().getHealth() - 50);
                }
            }
            //Enable Exit button
            disableButtons();
            banditDemandLabel.setText("Now your credits are: " + Player.getInstance().getCredits());
            broomHealth.setText("Your broom health: " + Broom.getInstance().getHealth());
            broomFuel.setText("Your broom fuel: " + Broom.getInstance().getFuelCapacity());
        });
    }

    /**
     * [M6_Flee_Bandit_Option 3/13/20]
     *  Pilot skill affects likelihood of success of fleeing bandit
     * [M6_Flee_Bandit_Option 3/13/20]
     *  Successful fleeing: Player should be returned to original region.
     *  Player will maintain all credits and items, but lose fuel
     * [M6_Flee_Bandit_Option 3/13/20]
     *  Unsuccessful fleeing: Player should lose all credits and ship health should be lowered
     *
     *  |*** fuelCost is in the RegionMapPage ***|
     */
    private void flee() {
        btnFlee.setOnMouseClicked(e -> {
            Random random = new Random();
            int point = random.nextInt(100);
            if (Player.getInstance().getDifficulty().equalsIgnoreCase("easy")) {
                //Successful fleeing
                if (point < 50 + Player.getInstance().getSkills()[0]) {
                    resultLabel.setText("Nice speed! Your flee was successful!!");
                    targetRegion = Player.getInstance().getCurrentRegion();
                } else {
                    //Unsuccessful fleeing
                    resultLabel.setText("Too slow! Better luck next time!");
                    //Health below zero Game Over
                    if (Broom.getInstance().getHealth() <= 50) {
                        new Alert(Alert.AlertType.NONE,
                                "Broom Destroyed Game Over.", ButtonType.OK).show();
                        Platform.exit();
                        System.exit(0);
                    } else {
                        //lose health
                        Broom.getInstance().setHealth(Broom.getInstance().getHealth() - 50);
                    }
                    //lose credits
                    Player.getInstance().setCredits(0);
                }
            } else if (Player.getInstance().getDifficulty().equalsIgnoreCase("medium")) {
                //Successful fleeing
                if (point < 30 + Player.getInstance().getSkills()[0]) {
                    resultLabel.setText("Nice speed! Your flee was successful!!");
                    targetRegion = Player.getInstance().getCurrentRegion();
                } else {
                    //Unsuccessful fleeing
                    resultLabel.setText("Too slow! Better luck next time!");
                    //Health below zero Game Over
                    if (Broom.getInstance().getHealth() <= 100) {
                        new Alert(Alert.AlertType.NONE,
                                "Broom Destroyed Game Over.", ButtonType.OK).show();
                        Platform.exit();
                        System.exit(0);
                    } else {
                        //lose health
                        Broom.getInstance().setHealth(Broom.getInstance().getHealth() - 100);
                    }
                    //lose credits
                    Player.getInstance().setCredits(0);
                }
            } else {
                //Successful fleeing
                if (point < 10 + Player.getInstance().getSkills()[0]) {
                    resultLabel.setText("Nice speed! Your flee was successful!!");
                    targetRegion = Player.getInstance().getCurrentRegion();
                } else {
                    //Unsuccessful fleeing
                    resultLabel.setText("Too slow! Better luck next time!");
                    //Health below zero Game Over
                    if (Broom.getInstance().getHealth() <= 150) {
                        new Alert(Alert.AlertType.NONE,
                                "Broom Destroyed Game Over.", ButtonType.OK).show();
                        Platform.exit();
                        System.exit(0);
                    } else {
                        //lose health
                        Broom.getInstance().setHealth(Broom.getInstance().getHealth() - 150);
                    }
                    //lose credits
                    Player.getInstance().setCredits(0);
                }
            }
            //Enable Exit button
            disableButtons();
            banditDemandLabel.setText("Now your credits are: " + Player.getInstance().getCredits());
            broomHealth.setText("Your broom health: " + Broom.getInstance().getHealth());
            broomFuel.setText("Your broom fuel: " + Broom.getInstance().getFuelCapacity());
        });
    }

    /**
     * exit the current subscene
     */
    private void exit() {
        btnExit.setOnMouseClicked(e -> {
            moveSubScene();
            RegionMapPage.getInstance().travelTo(targetRegion);
        });
    }

    /**
     * Create buttons in the given root and assign event function to the buttons
     * @param root the root where we create buttons in
     */
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
        btnContinueNoPay = new YellowButton("ConsistNoPay");
        btnContinueNoPay.setPrefWidth(250);
        btnContinueNoPay.setLayoutX(1000);
        btnContinueNoPay.setLayoutY(700);
        btnContinueNoPay.setDisable(true);
        btnExit = new YellowButton("Exit");
        btnExit.setLayoutX(1300);
        btnExit.setLayoutY(700);
        btnExit.setDisable(true);
        pay();
        fight();
        flee();
        exit();
        continueNoPay();
        root.getChildren().addAll(btnExit, btnPay, btnContinueNoPay, btnFight, btnFlee);
    }

    /**
     * Once action is taken and we enable the exit button and disable other buttons
     */
    private void disableButtons() {
        btnFlee.setDisable(true);
        btnFight.setDisable(true);
        btnPay.setDisable(true);
        btnContinueNoPay.setDisable(true);
        btnExit.setDisable(false);
    }
}
