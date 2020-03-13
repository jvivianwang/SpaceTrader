package materials;
import component.Broom;
import component.Creature;
import component.Market;
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
import scene.RegionMapPage;

import java.util.Random;


public class TraderSubscene extends SubScene {
    private static final String BACKGROUND_IMAGE = "materials/image/banditBackground.jpg";
    private static final String FONT_PATH = "src/materials/font/Cochin W01 Roman.ttf";

    private boolean isHidden;

    private Creature[] creatureList;
    private YellowButton btnBuy;
    private YellowButton btnExitOrIgnore;
    private YellowButton btnRob;
    private YellowButton btnNegotiate;
    private Label result;

    private int creatureSelectedFromList;
    private ImageView[] creatureListImage;
    private ImageView[] inventoryListImage;

    private Region targetRegion;


    public TraderSubscene() {
        super(new AnchorPane(), 1600, 900);
        prefWidth(1600);
        prefHeight(900);

        isHidden = true;

        creatureSelectedFromList = -1;
        creatureList = new Creature[3];

        AnchorPane root2 = (AnchorPane) this.getRoot();
        setTraderMarket(root2);
        setBackgroundImage(root2);
        setInventory(root2);

        createButton(root2);

        setLayoutX(0);
        setLayoutY(-900);
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


    private void setTraderMarket(AnchorPane root) {
        creatureListImage = new ImageView[3];
        for (int i = 0; i < creatureListImage.length; i++) {
            //set default images to earthdragon just because the imageview cant be null
            creatureListImage[i] = new ImageView(new Image("materials/image/empty.png",
                    100, 100, false, true));
            creatureListImage[i].setLayoutX(100 + 200 * i);
            creatureListImage[i].setLayoutY(75 );
            root.getChildren().add(creatureListImage[i]);
        }
    }

    private void setInventory(AnchorPane root) {
        //The boarder
        ImageView[] boarder = new ImageView[9];
        for (int i = 0; i < boarder.length; i++) {
            boarder[i] = new ImageView(new Image("materials/image/emptyBoarder.png",
                    100, 100, false, true));
            boarder[i].setLayoutX(1000 + (i % 3) * 150);
            boarder[i].setLayoutY(50 + (i / 3) * 150);
            root.getChildren().add(boarder[i]);
        }

        //The image inside boarder
        inventoryListImage = new ImageView[9];
        for (int i = 0; i < inventoryListImage.length; i++) {
            inventoryListImage[i] = new ImageView(new Image("materials/image/empty.png",
                    100, 100, false, true));
            inventoryListImage[i].setLayoutX(1000 + (i % 3) * 150);
            inventoryListImage[i].setLayoutY(50 + (i / 3) * 150);
            root.getChildren().add(inventoryListImage[i]);
        }
    }

    private void selectFromTrader(ImageView creatureImage, int creatureIndexFromList) {
        creatureImage.setOnMouseClicked(e -> {
            creatureSelectedFromList = creatureIndexFromList;
//            updateBuySellBtn();
            updateRobBuyNegotiateBtn();
//            updatePlayerInfo();
        });
    }


    private void createButton(AnchorPane root) {
        btnRob = new YellowButton("Rob");
        btnRob.setLayoutX(200);
        btnRob.setLayoutY(700);
        btnRob.setDisable(true);
        btnBuy = new YellowButton("Buy");
        btnBuy.setLayoutX(400);
        btnBuy.setLayoutY(700);
        btnBuy.setDisable(true);
        btnNegotiate = new YellowButton("Negotiate");
        btnNegotiate.setLayoutX(800);
        btnNegotiate.setLayoutY(700);
        btnNegotiate.setDisable(true);
        btnExitOrIgnore = new YellowButton("Exit or Ignore");
        btnExitOrIgnore.setLayoutX(1200);
        btnExitOrIgnore.setLayoutY(700);

        updateInventory();
        buy();
        rob();
        exitOrIgnore();
        negotiate();
        root.getChildren().addAll(btnRob, btnBuy, btnNegotiate, btnExitOrIgnore);
    }


    private void updateRobBuyNegotiateBtn() {
        if (creatureSelectedFromList != -1 ) {
            btnBuy.setDisable(false);
            btnNegotiate.setDisable(false);
            btnRob.setDisable(false);
            btnExitOrIgnore.setDisable(true);
        } else {
            btnBuy.setDisable(true);
            btnNegotiate.setDisable(true);
            btnRob.setDisable(true);
            btnExitOrIgnore.setDisable(false);
        }

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

    public void updateCreatureList(){
        if (Player.getInstance().getCurrentRegion().getTechLevel() == 1) {
            creatureList = new Creature[]{
                    new Creature(0),
                    new Creature(6),
                    new Creature(3)
            };
        } else if (Player.getInstance().getCurrentRegion().getTechLevel() == 2 ||
                Player.getInstance().getCurrentRegion().getTechLevel() == 3) {
            creatureList = new Creature[] {
                    new Creature(1),
                    new Creature(2),
                    new Creature(7)
            };
        } else if (Player.getInstance().getCurrentRegion().getTechLevel() == 4 ||
                Player.getInstance().getCurrentRegion().getTechLevel() == 5) {
            creatureList = new Creature[] {
                    new Creature(2),
                    new Creature(4),
                    new Creature(5)
            };
        }else {
            creatureList = new Creature[] {
                    new Creature(1),
                    new Creature(3),
                    new Creature(6),
            };
        }
    }


    public void generateTraderInfo(Region targetRegion) {
        this.targetRegion = targetRegion;
        creatureSelectedFromList = -1;
        creatureList = new Creature[3];
        updateCreatureList();
        updateInventory();
        for (int i = 0; i < creatureList.length; i++) {
            Image image = new Image("materials/image/"
                    + creatureList[i].getName() + ".png",
                    100, 100, false, true);
            // Create the ImageView
            creatureListImage[i].setImage(image);
            creatureListImage[i].setDisable(false);
            selectFromTrader(creatureListImage[i], i);
        }
        btnBuy.setDisable(true);
        btnNegotiate.setDisable(false);
        btnRob.setDisable(false);
    }

    public void updateInventory() {
        for (int i = 0; i < inventoryListImage.length; i++) {
            if (i < Broom.getInstance().getInventory().size()) {
                Image image = new Image(
                        "materials/image/"
                                + Broom.getInstance().getInventory().get(i).getName() + ".png",
                        100, 100, false, true);
                inventoryListImage[i].setImage(image);
            } else {
                Image image = new Image("materials/image/empty.png",
                        100, 100, false, true);
                inventoryListImage[i].setImage(image);
            }
        }
    }

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

    private void buy() {
            btnBuy.setOnMouseClicked(e -> {
                //buy the item
                int price = 0;
                if (creatureSelectedFromList != -1) {
                    price = creatureList[creatureSelectedFromList].getFinalPrice();
                }
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
                            100, 100, false, true);
                    if (creatureSelectedFromList != -1) {
                        creatureListImage[creatureSelectedFromList].setDisable(true);
                        creatureListImage[creatureSelectedFromList].setImage(image);
                        Broom.getInstance().gainCreature(
                                creatureList[creatureSelectedFromList]);
                    }
                    creatureSelectedFromList = -1;
                    //updatePlayerInfo();
                    updateRobBuyNegotiateBtn();
                    updateInventory();
                }
            });
    }
    private void rob() {
        btnRob.setOnMouseClicked(e -> {
            int skillPoints = 0;
            int price = 0;
            int random = new Random().nextInt(100);
            if (creatureSelectedFromList != -1) {
                //skillPoints = Player.getInstance().getSkillPoints() * Player.getInstance().getDifficulty();
            }
//            if (Player.getInstance().getSkillPoints() < MAX_FIGHTER_SKILL_POINTS) {
//                new Alert(Alert.AlertType.NONE,
//                        "Ooops! You don't have enough fighter skill points.", ButtonType.OK).show();
//            } else
                if (Broom.getInstance().getInventory().size()
                    >= Broom.getInstance().getCargoCapacity()) {
                new Alert(Alert.AlertType.NONE,
                        "Ooops! You don't enough space to carry the item.", ButtonType.OK).show();
            } else {

                new Alert(Alert.AlertType.NONE,
                        "You just successfully robbed " + creatureList
                                [creatureSelectedFromList].getName() , ButtonType.OK).show();
                Image image = new Image("materials/image/soldOut.jpg",
                        100, 100, false, true);
                if (creatureSelectedFromList != -1) {
                    creatureListImage[creatureSelectedFromList].setDisable(true);
                    creatureListImage[creatureSelectedFromList].setImage(image);
                    Broom.getInstance().gainCreature(
                            creatureList[creatureSelectedFromList]);
                }
                creatureSelectedFromList = -1;
                //updatePlayerInfo();
                updateRobBuyNegotiateBtn();
            }

        });

    }
    private void negotiate() {

    }
    private void exitOrIgnore() {
        btnExitOrIgnore.setOnMouseClicked(e -> {
            moveSubScene();
            RegionMapPage.getInstance().travelTo(targetRegion);
        });

    }
    private int creatureListImageClicked() {
        return 0;

    }




}
