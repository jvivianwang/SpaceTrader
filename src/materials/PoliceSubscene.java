package materials;

import component.Broom;
import component.Player;
import component.Region;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import scene.PlayerSheetPage;
import scene.RegionMapPage;

import java.util.*;

public class PoliceSubscene extends SubScene {

    private static DimensionsHandler dim = new DimensionsHandler();
    private static final int HEIGHT = dim.getHeight();
    private static final int WIDTH = dim.getWidth();
    private static final int FONTSIZE = dim.getFontsize();

    private static final String BACKGROUND_IMAGE = "materials/image/banditBackground2.jpg";
    private static final String FONT_PATH = "src/materials/font/Cochin W01 Roman.ttf";

    private YellowButton btnForfeit;
    private YellowButton btnFlee;
    private YellowButton btnFight;
    private YellowButton btnExit;

    private Label healthResultLabel;
    private Label creditsResultLabel;
    private Label fuelResultLabel;
    private Label policeDemandLabel;
    private Label resultLabel;

    private Item[] demandItems;
    private ImageView[] demandItemsView;
    private boolean isHidden;
    private Region targetRegion;

    /**
     * Creates a new police subscene
     */
    public PoliceSubscene() {
        super(new AnchorPane(), WIDTH, HEIGHT);
        prefWidth(WIDTH);
        prefHeight(HEIGHT);

        isHidden = true;
        AnchorPane root2 = (AnchorPane) this.getRoot();
        setDemandMarket(root2);
        displayText(root2);
        setBackgroundImage(root2);
        createButton(root2);

        setLayoutX(dim.customWidth((0)));
        setLayoutY(-HEIGHT);
    }

    /**
     * set list images to empty.png in the current root
     * @param root the root we update the demandList Image
     */
    private void setDemandMarket(AnchorPane root) {
        demandItemsView = new ImageView[3];
        for (int i = 0; i < demandItemsView.length; i++) {
            //set default images to earthdragon just because the imageview cant be null
            demandItemsView[i] = new ImageView(new Image("materials/image/empty.png",
                    dim.customWidth(100), dim.customHeight(100), false, true));
            demandItemsView[i].setLayoutX(dim.customWidth(100 + 200 * i));
            demandItemsView[i].setLayoutY(dim.customWidth((200)));
            root.getChildren().add(demandItemsView[i]);
        }
    }

    /**
     * Before you travel to the selectedRegion, if you encounter police,
     * the police will ask random items in your broom
     * @param regionSelected the region you are about to travel to
     */
    public void generatePoliceInfo(Region regionSelected) {
        this.targetRegion = regionSelected;
        updateCreatureList();
        for (int i = 0; i < demandItems.length; i++) {
            Image image = new Image("materials/image/"
                    + demandItems[i].getName() + ".png",
                    dim.customWidth(100), dim.customHeight(100), false, true);
            // Create the ImageView
            demandItemsView[i].setImage(image);
            demandItemsView[i].setDisable(false);
            //selectFromTrader(creatureListImage[i], i);
        }
        resetScene();
    }

    /**
     * [Space_Police_Encounter 3/13/20]
     *  SP demand item(s) for forfeiture
     */
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
        System.arraycopy(array, 0, demandItems, 0, array.length / 2 + 1);
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
        policeDemandLabel.setText("Well well well... wanna pass? Give me your illegal items");
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
                WIDTH,
                HEIGHT,
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
        btnForfeit.setLayoutX(dim.customWidth((800)));
        btnForfeit.setLayoutY(dim.customWidth((700)));
        btnForfeit.setDisable(false);
        btnFlee = new YellowButton("Flee");
        btnFlee.setLayoutX(dim.customWidth((400)));
        btnFlee.setLayoutY(dim.customWidth((700)));
        btnFlee.setDisable(false);
        btnFight = new YellowButton("Fight");
        btnFight.setLayoutX(dim.customWidth((600)));
        btnFight.setLayoutY(dim.customWidth((700)));
        btnFight.setDisable(false);
        btnExit = new YellowButton("Exit");
        btnExit.setLayoutX(dim.customWidth((1000)));
        btnExit.setLayoutY(dim.customWidth((700)));
        btnExit.setDisable(true);
        forfeit();
        flee();
        fight();
        exit();
        root.getChildren().addAll(btnForfeit, btnFlee, btnFight, btnExit);
    }

    /**
     * Initialize text for label
     * @param root Root to add conversation and result to
     */
    private void displayText(AnchorPane root) {
        policeDemandLabel = displayLabel("", dim.customWidth(100), dim.customHeight(100));
        resultLabel = displayLabel("", dim.customWidth(100), dim.customHeight(450));
        healthResultLabel = displayLabel("", dim.customWidth(100), dim.customHeight(500));
        creditsResultLabel = displayLabel("", dim.customWidth(100), dim.customHeight(550));
        fuelResultLabel = displayLabel("", dim.customWidth(100), dim.customHeight(600));
        root.getChildren().addAll(policeDemandLabel, resultLabel,
                healthResultLabel, creditsResultLabel,
                fuelResultLabel);
    }

    /**
     * store a message into a "textbox" with (x,y) which the "textbox" style could be reused
     * @param message the message you wanna show on (x,y)
     * @param x the x coordinate
     * @param y the y coordinate
     * @return A Label variable (text)
     */
    private Label displayLabel(String message, double x, double y) {
        Label temp = new Label(message);
        temp.setFont(new Font(FONTSIZE));
        temp.setAlignment(Pos.CENTER_LEFT);
        temp.setPrefWidth(dim.customWidth((800)));
        temp.setPrefHeight(dim.customHeight((200)));
        temp.setLayoutX(x);
        temp.setLayoutY(y);
        temp.setTextFill(Color.web("#ffffff"));
        return temp;
    }

    /**
     * [M6_Forfeit_To_SP_Option 3/13/20]
     * Specified item(s) removed from player's inventory,
     * cargo space updated, and player continues to intended destination
     */
    private void forfeit() {
        btnForfeit.setOnMouseClicked(e -> {
            //lose items
            Broom.getInstance().getInventory().removeAll(Arrays.asList(demandItems));
            resultLabel.setText("You lose the items in your inventory");
            //Enable exit button
            disableButtons();
        });
    }

    /**
     * [M6_Flee_SP_Option 3/13/20]
     *  Pilot skill affects likelihood of success of fleeing
     * [M6_Flee_SP_Option 3/13/20]
     *  Successful fleeing: Player should return to original region and lose fuel
     * [M6_Flee_SP_Option 3/13/20]
     *  Unsuccessful fleeing: Player should have specified items confiscated from inventory,
     *  cargo space updated, lose additional credits as a fine, and have ship health lowered.
     *  Return to the original region.
     */
    private void flee() {
        btnFlee.setOnMouseClicked(e -> {
            Random random = new Random();
            int point = random.nextInt(100);
            if (point < 50 + Player.getInstance().getSkills()[0]) {
                resultLabel.setText("Nice speed! Your flee was successful!!");
            } else {
                resultLabel.setText("Too slow! Better luck next time!");
                //Health below zero Game Over
                Broom.getInstance().setHealth(Broom.getInstance().getHealth() - 50);
                //Health below zero Game Over
                if (Broom.getInstance().getHealth() <= 0) {
                    Alert alert = new Alert(Alert.AlertType.NONE,
                            "Better luck next time! You died.", ButtonType.OK);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        PlayerSheetPage.getInstance().reset();
                        application.Main.setScene(PlayerSheetPage.getInstance().getMainScene());
                    }
                }
                //Lose credits
                if (Player.getInstance().getCredits() < 500) {
                    Player.getInstance().setCredits(0);
                } else {
                    Player.getInstance().setCredits(Player.getInstance().getCredits() - 500);
                }
                //Lose items
                Broom.getInstance().getInventory().removeAll(Arrays.asList(demandItems));
            }
            //Return to the original region
            targetRegion = Player.getInstance().getCurrentRegion();
            //Enable exit button
            disableButtons();
            creditsResultLabel.setText("Now your credits are: "
                    + Player.getInstance().getCredits());
            healthResultLabel.setText("Your broom health: "
                    + Broom.getInstance().getHealth());
            fuelResultLabel.setText("Your broom fuel: "
                    + Broom.getInstance().getFuelCapacity());
        });
    }

    /**
     * [M6_Fight_SP_Option 3/13/20]
     *  Fighter skill affects likelihood of success of fighting space-police
     * [M6_Fight_SP_Option 3/13/20]
     *  Successful fighting: Player should keep all inventory
     *  items and continue to intended destination
     * ---------------------------------------------------------------------
     * Assumption: Unsuccessful fighting: Player lose all credits,
     * and have ship health lowered.
     */
    private void fight() {
        btnFight.setOnMouseClicked(e -> {
            Random random = new Random();
            int point = random.nextInt(100);
            //Successful fighting
            if (point < 50 + Player.getInstance().getSkills()[1] * 2) {
                resultLabel.setText("Woah! You fought valiantly and won!");
            } else {
                //Unsuccessful fighting
                resultLabel.setText("You lost! Better luck next time!");
                //Lose all credits
                Player.getInstance().setCredits(0);
                Broom.getInstance().setHealth(Broom.getInstance().getHealth() - 50);
                //Health below zero Game Over
                if (Broom.getInstance().getHealth() <= 0) {
                    Alert alert = new Alert(Alert.AlertType.NONE,
                            "Better luck next time! You died.", ButtonType.OK);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        PlayerSheetPage.getInstance().reset();
                        application.Main.setScene(PlayerSheetPage.getInstance().getMainScene());
                    }
                }
            }
            //Enable exit button
            disableButtons();
            creditsResultLabel.setText("Now your credits are: "
                    + Player.getInstance().getCredits());
            healthResultLabel.setText("Your broom health: "
                    + Broom.getInstance().getHealth());
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
            transition.setToY(HEIGHT);
            isHidden = false;
        } else {
            transition.setToY(-HEIGHT);
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


