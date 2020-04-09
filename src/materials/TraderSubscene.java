package materials;
import component.Broom;
import component.Creature;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import scene.RegionMapPage;

import java.util.Random;


public class TraderSubscene extends SubScene {

    private static DimensionsHandler dim = new DimensionsHandler();
    private static final int HEIGHT = dim.getHeight();
    private static final int WIDTH = dim.getWidth();
    private static final int FONTSIZE = dim.getFontsize();

    private static final String BACKGROUND_IMAGE = "materials/image/banditBackground2.jpg";

    private boolean isHidden;

    private Creature[] creatureList;
    private YellowButton btnBuy;
    private YellowButton btnExitOrIgnore;
    private YellowButton btnRob;
    private YellowButton btnNegotiate;

    private Label creditLabel;
    private Label priceLabel;
    private Label resultLabel;

    private int creatureSelectedFromList;
    private int creatureOnSaleLeft;
    private ImageView[] creatureListImage;
    private ImageView[] inventoryListImage;

    private Region targetRegion;


    public TraderSubscene() {
        super(new AnchorPane(), WIDTH, HEIGHT);
        prefWidth(WIDTH);
        prefHeight(HEIGHT);

        isHidden = true;

        creatureSelectedFromList = -1;
        creatureList = new Creature[3];

        AnchorPane root2 = (AnchorPane) this.getRoot();
        setTraderMarket(root2);
        setBackgroundImage(root2);
        setInventory(root2);
        displayText(root2);

        createButton(root2);

        setLayoutX(0);
        setLayoutY(-HEIGHT);
    }

    /**
     * Draw the background of the current subscene
     * @param root the root where we draw the background
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
     * set shopList images to empty.png in the current root
     * @param root the root we update the demandList Image
     */
    private void setTraderMarket(AnchorPane root) {
        creatureListImage = new ImageView[3];
        for (int i = 0; i < creatureListImage.length; i++) {
            //set default images to earthdragon just because the imageview cant be null
            creatureListImage[i] = new ImageView(new Image("materials/image/empty.png",
                    dim.customWidth(100), dim.customHeight(100), false, true));
            creatureListImage[i].setLayoutX(dim.customWidth(100 + 200 * i));
            creatureListImage[i].setLayoutY(dim.customHeight((75)));
            root.getChildren().add(creatureListImage[i]);
        }
    }

    /**
     * Update the boarder and inventoryImage in the current root
     * @param root the root we try to access
     */
    private void setInventory(AnchorPane root) {
        //The border
        ImageView[] border = new ImageView[9];
        for (int i = 0; i < border.length; i++) {
            border[i] = new ImageView(new Image("materials/image/emptyBoarder.png",
                    dim.customWidth(100), dim.customHeight(100), false, true));
            border[i].setLayoutX(dim.customWidth(1000 + (i % 3) * 150));
            border[i].setLayoutY(dim.customHeight(50 + (i / 3) * 150));
            root.getChildren().add(border[i]);
        }

        //The image inside border
        inventoryListImage = new ImageView[9];
        for (int i = 0; i < inventoryListImage.length; i++) {
            inventoryListImage[i] = new ImageView(new Image("materials/image/empty.png",
                    dim.customWidth(100), dim.customHeight(100), false, true));
            inventoryListImage[i].setLayoutX(dim.customWidth(1000 + (i % 3) * 150));
            inventoryListImage[i].setLayoutY(dim.customHeight(50 + (i / 3) * 150));
            root.getChildren().add(inventoryListImage[i]);
        }
    }

    /**
     * Every time we click the image we store the index of the image
     * so that we can easily access it later
     * @param creatureImage The image shown in the store
     * @param creatureIndexFromList The index of the image
     */
    private void selectFromTrader(ImageView creatureImage, int creatureIndexFromList) {
        creatureImage.setOnMouseClicked(e -> {
            creatureSelectedFromList = creatureIndexFromList;
            priceLabel.setText("The item price: "
                    + creatureList[creatureIndexFromList].getFinalPrice());
            btnBuy.setDisable(false);
        });
    }

    /**
     * Create buttons in the given root and assign event function to the buttons
     * @param root the root where we create buttons in
     */
    private void createButton(AnchorPane root) {
        btnRob = new YellowButton("Rob");
        btnRob.setLayoutX(dim.customWidth((200)));
        btnRob.setLayoutY(dim.customHeight((700)));
        btnRob.setDisable(true);
        btnBuy = new YellowButton("Buy");
        btnBuy.setLayoutX(dim.customWidth((400)));
        btnBuy.setLayoutY(dim.customHeight((700)));
        btnBuy.setDisable(true);
        btnNegotiate = new YellowButton("Negotiate");
        btnNegotiate.setLayoutX(dim.customWidth((800)));
        btnNegotiate.setLayoutY(dim.customHeight((700)));
        btnNegotiate.setDisable(true);
        btnExitOrIgnore = new YellowButton("Exit or Ignore");
        btnExitOrIgnore.setLayoutX(dim.customWidth((1200)));
        btnExitOrIgnore.setLayoutY(dim.customHeight((700)));
        btnExitOrIgnore.setPrefWidth(dim.customWidth((300)));

        updateInventory();
        buy();
        rob();
        exitOrIgnore();
        negotiate();
        root.getChildren().addAll(btnRob, btnBuy, btnNegotiate, btnExitOrIgnore);
    }

    /**
     * Move the subscene up and down with its own
     * algorithm which determines moving up or moving down
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
     * Update/refresh the shopList
     */
    public void updateCreatureList() {
        if (Player.getInstance().getCurrentRegion().getTechLevel() == 1) {
            creatureList =
            new Creature[]{
                new Creature(0),
                new Creature(6),
                new Creature(3)
            };
        } else if (Player.getInstance().getCurrentRegion().getTechLevel() == 2
                || Player.getInstance().getCurrentRegion().getTechLevel() == 3) {
            creatureList = new Creature[] {
                new Creature(1),
                new Creature(2),
                new Creature(7)
            };
        } else if (Player.getInstance().getCurrentRegion().getTechLevel() == 4
                || Player.getInstance().getCurrentRegion().getTechLevel() == 5) {
            creatureList = new Creature[] {
                new Creature(2),
                new Creature(4),
                new Creature(5)
            };
        } else {
            creatureList = new Creature[] {
                new Creature(1),
                new Creature(3),
                new Creature(6),
            };
        }
    }


    /**
     * [M6_Trader_Encounter 3/13/20]
     * The trader will ask the player if they would like to buy a few of a certain item
     * @param targetRegion the region player is about to travel
     */
    public void generateTraderInfo(Region targetRegion) {
        this.targetRegion = targetRegion;
        creatureSelectedFromList = -1;
        creatureList = new Creature[3];
        creatureOnSaleLeft = 3;
        updateCreatureList();
        updateInventory();
        for (int i = 0; i < creatureList.length; i++) {
            Image image = new Image("materials/image/"
                    + creatureList[i].getName() + ".png",
                    dim.customWidth(100), dim.customHeight(100), false, true);
            // Create the ImageView
            creatureListImage[i].setImage(image);
            creatureListImage[i].setDisable(false);
            selectFromTrader(creatureListImage[i], i);
        }
        btnBuy.setDisable(true);
        btnNegotiate.setDisable(false);
        btnRob.setDisable(false);
        creditLabel.setText("Your current credits left: " + Player.getInstance().getCredits());
        priceLabel.setText("The item price: ");
        resultLabel.setText("");
    }

    /**
     * Initialize text for label
     * @param root Root to add conversation and result to
     */
    private void displayText(AnchorPane root) {
        creditLabel = displayLabel("", dim.customWidth(200), dim.customHeight(300));
        priceLabel = displayLabel("", dim.customWidth(200), dim.customHeight(400));
        resultLabel = displayLabel("", dim.customWidth(200), dim.customHeight(500));
        root.getChildren().addAll(creditLabel, priceLabel, resultLabel);
    }

    /**
     * Update/refresh the inventory
     */
    public void updateInventory() {
        for (int i = 0; i < inventoryListImage.length; i++) {
            if (i < Broom.getInstance().getInventory().size()) {
                Image image = new Image(
                        "materials/image/"
                                + Broom.getInstance().getInventory().get(i).getName() + ".png",
                        dim.customWidth(100), dim.customHeight(100), false, true);
                inventoryListImage[i].setImage(image);
            } else {
                Image image = new Image("materials/image/empty.png",
                        dim.customWidth(100), dim.customHeight(100), false, true);
                inventoryListImage[i].setImage(image);
            }
        }
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
     * [M6_Buy_Trader_Option 3/13/20]
     * After buying the items, the player continues travelling to the target destination.
     */
    private void buy() {
        btnBuy.setOnMouseClicked(e -> {
            //buy the item
            int price = creatureList[creatureSelectedFromList].getFinalPrice();
            if (Player.getInstance().getCredits() < price) {
                new Alert(Alert.AlertType.NONE,
                        "Ooops! You don't have enough credits.", ButtonType.OK).show();
            } else if (Broom.getInstance().getInventory().size()
                    >= Broom.getInstance().getCargoCapacity()) {
                new Alert(Alert.AlertType.NONE,
                        "Ooops! You don't enough space to carry the item.", ButtonType.OK).show();
            } else {

                Player.getInstance().setCredits(Player.getInstance().getCredits() - price);
                Image image = new Image("materials/image/soldOut.jpg",
                        dim.customWidth(100), dim.customHeight(100), false, true);
                if (creatureSelectedFromList != -1) {
                    creatureListImage[creatureSelectedFromList].setDisable(true);
                    creatureListImage[creatureSelectedFromList].setImage(image);
                    creatureOnSaleLeft--;
                    Broom.getInstance().gainCreature(
                            creatureList[creatureSelectedFromList]);
                }
                creatureSelectedFromList = -1;
                //updatePlayerInfo();
                btnBuy.setDisable(true);
                btnExitOrIgnore.setDisable(false);
                if (creatureOnSaleLeft > 0) {
                    btnRob.setDisable(false);
                } else {
                    btnRob.setDisable(true);
                    btnNegotiate.setDisable(true);
                }
                updateInventory();
                creditLabel.setText("Your current credits left: "
                        + Player.getInstance().getCredits());
                priceLabel.setText("The item price: ");
                resultLabel.setText("You bought a creature");
            }
        });
    }

    /**
     * [M6_Rob_Trader_Option 3/13/20]
     *  Fighter skill affects likelihood of success of robbing trader
     * [M6_Rob_Trader_Option 3/13/20]
     *  Successful robbing: Some of trader’s items added to player inventory, cargo space updated,
     *  and player continues to intended destination
     * [M6_Rob_Trader_Option 3/13/20]
     *  Unsuccessful robbing: Ship health is lowered and player continues to intended destination
     */
    private void rob() {
        btnRob.setOnMouseClicked(e -> {
            int random = new Random().nextInt(100);
            //Unsuccessful robbing
            if (Player.getInstance().getDifficulty().equalsIgnoreCase("easy")) {
                if (random > 50 + Player.getInstance().getSkills()[1] * 2) {
                    resultLabel.setText("Get outta here now!!!");
                    if (Broom.getInstance().getHealth() <= 50) {
                        new Alert(Alert.AlertType.NONE,
                                "Broom Destroyed Game Over.", ButtonType.OK).show();
                        Platform.exit();
                        System.exit(0);
                    } else {
                        Broom.getInstance().setHealth(Broom.getInstance().getHealth() - 50);
                    }
                }

            } else if (Player.getInstance().getDifficulty().equalsIgnoreCase("medium")) {
                if (random > 30 + Player.getInstance().getSkills()[1] * 2) {
                    resultLabel.setText("Get outta here now!!!");
                    if (Broom.getInstance().getHealth() <= 100) {
                        new Alert(Alert.AlertType.NONE,
                                "Broom Destroyed Game Over.", ButtonType.OK).show();
                        Platform.exit();
                        System.exit(0);
                    } else {
                        Broom.getInstance().setHealth(Broom.getInstance().getHealth() - 100);
                    }
                }

            } else {
                if (random > 10 + Player.getInstance().getSkills()[2] * 2) {
                    resultLabel.setText("Get outta here now!!!");
                    if (Broom.getInstance().getHealth() <= 150) {
                        new Alert(Alert.AlertType.NONE,
                                "Broom Destroyed Game Over.", ButtonType.OK).show();
                        Platform.exit();
                        System.exit(0);
                    } else {
                        Broom.getInstance().setHealth(Broom.getInstance().getHealth() - 150);
                    }
                }
            }
            //Successful robbing
            if (Broom.getInstance().getInventory().size()
                    >= Broom.getInstance().getCargoCapacity()) {
                new Alert(Alert.AlertType.NONE,
                        "Ooops! You don't enough space to carry the item.", ButtonType.OK).show();
            } else {
                Image image = new Image("materials/image/soldOut.jpg",
                        dim.customWidth(100), dim.customHeight(100), false, true);
                creatureListImage[0].setDisable(true);
                creatureListImage[0].setImage(image);
                //Trader’s item added to player inventory
                Broom.getInstance().gainCreature(creatureList[0]);
                creatureSelectedFromList = -1;
                updateInventory();
                resultLabel.setText("Please don't hurt me! I'll give you anything you want!!!");
            }
            priceLabel.setText("The item price: ");
            btnBuy.setDisable(true);
            btnExitOrIgnore.setDisable(false);
            btnRob.setDisable(true);
            btnNegotiate.setDisable(true);
            for (ImageView g: creatureListImage) {
                g.setDisable(true);
            }
        });

    }

    /**
     * [M6_Negotiate_Trader_Option 3/13/20]
     *  Merchant skill affects likelihood of success of negotiating with trader
     * [M6_Negotiate_Trader_Option 3/13/20]
     *  Successful negotiation: the trader's price is significantly reduced
     * [M6_Negotiate_Trader_Option 3/13/20]
     *  Unsuccessful negotiation: trader's price should increase
     * [M6_Negotiate_Trader_Option 3/13/20]
     *   Can only negotiate once per encounter
     */
    private void negotiate() {
        btnNegotiate.setOnMouseClicked(e -> {
            Random random = new Random();
            int point = random.nextInt(100);
            if (Player.getInstance().getDifficulty().equalsIgnoreCase("easy")) {
                //Successful negotiation
                if (point < 50 + Player.getInstance().getSkills()[2] * 2) {
                    resultLabel.setText("Damn it.. Ok Now all items 50% off..");
                    for (Creature g: creatureList) {
                        g.setBasePrice(g.getBasePrice() / 2);
                    }
                } else {
                    //Unsuccessful negotiation
                    resultLabel.setText("Yo!! Now 2 X prices. You buy it or you leave now");
                    for (Creature g: creatureList) {
                        g.setBasePrice(g.getBasePrice() * 2);
                    }
                }
            } else if (Player.getInstance().getDifficulty().equalsIgnoreCase("medium")) {
                //Successful negotiation
                if (point < 30 + Player.getInstance().getSkills()[2] * 2) {
                    resultLabel.setText("Damn it.. Ok Now all items 50% off..");
                    for (Creature g: creatureList) {
                        g.setBasePrice(g.getBasePrice() / 2);
                    }
                } else {
                    //Unsuccessful negotiation
                    resultLabel.setText("Yo!! Now 2 X prices. You buy it or you leave now");
                    for (Creature g: creatureList) {
                        g.setBasePrice(g.getBasePrice() * 2);
                    }
                }
            } else {
                //Successful negotiation
                if (point < 10 + Player.getInstance().getSkills()[2] * 2) {
                    resultLabel.setText("Damn it.. Ok Now all items 50% off..");
                    for (Creature g: creatureList) {
                        g.setBasePrice(g.getBasePrice() / 2);
                    }
                } else {
                    //Unsuccessful negotiation
                    resultLabel.setText("Yo!! Now 2 X prices. You buy it or you leave now");
                    for (Creature g: creatureList) {
                        g.setBasePrice(g.getBasePrice() * 2);
                    }
                }
            }
            if (creatureSelectedFromList != -1) {
                priceLabel.setText("The item price: "
                        + creatureList[creatureSelectedFromList].getFinalPrice());
            } else {
                priceLabel.setText("The item price: ");
            }
            btnBuy.setDisable(true);
            btnExitOrIgnore.setDisable(false);
            btnRob.setDisable(false);
            //Can only negotiate once per encounter
            btnNegotiate.setDisable(true);
        });
    }

    /**
     * [M6_Ignore_Trader_Option 3/13/20]
     *  Player continues to intended destination
     */
    private void exitOrIgnore() {
        btnExitOrIgnore.setOnMouseClicked(e -> {
            moveSubScene();
            RegionMapPage.getInstance().travelTo(targetRegion);
        });

    }




}
