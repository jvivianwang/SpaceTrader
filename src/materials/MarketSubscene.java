package materials;

import component.Broom;
import component.Creature;
import component.Market;
import component.Player;
import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MarketSubscene extends SubScene {

    private static final String BACKGROUND_IMAGE = "materials/image/magicalBackground.png";
    private static final String FONT_PATH = "src/materials/font/SEASRN__.ttf";

    private boolean isHidden;
    private YellowButton btnExit;
    private YellowButton btnBuy;
    private YellowButton btnSell;

    private ImageView[] shopListImage;
    private ImageView[] inventoryListImage;

    //Index should be from 0 to 4 since only five creature from the list
    private int creatureIndexSelectedFromStore;
    private int creatureIndexSelectedFromBroom;

    private Label credits;
    private Label inventorySize;
    private Label price;

    public MarketSubscene() {
        super(new AnchorPane(), 1600, 900);
        prefWidth(1600);
        prefHeight(900);

        isHidden = true;
        creatureIndexSelectedFromStore = -1;
        creatureIndexSelectedFromBroom = -1;

        AnchorPane root2 = (AnchorPane) this.getRoot();

        setMarket(root2);
        setInventory(root2);
        setPlayerInfo(root2);

        setBackgroundImage(root2);
        createButton(root2);

        setLayoutX(0);
        setLayoutY(-900);
    }

    private void setMarket(AnchorPane root) {
        shopListImage = new ImageView[5];
        for (int i = 0; i < shopListImage.length; i++) {
            //set default images to earthdragon just because the imageview cant be null
            shopListImage[i] = new ImageView(new Image("materials/image/empty.png",
                    100, 100, false, true));
            shopListImage[i].setLayoutX(200);
            shopListImage[i].setLayoutY(50 + 150 * i);
            root.getChildren().add(shopListImage[i]);
        }
    }

    private void setInventory(AnchorPane root) {
        //The boarder
        ImageView[] boarder = new ImageView[10];
        for (int i = 0; i < boarder.length - 1; i++) {
            boarder[i] = new ImageView(new Image("materials/image/emptyBoarder.png",
                    100, 100, false, true));
            boarder[i].setLayoutX(1000 + (i % 3) * 150);
            boarder[i].setLayoutY(100 + (i / 3) * 150);
            root.getChildren().add(boarder[i]);
        }
        boarder[9] = new ImageView(new Image("materials/image/emptyBoarder.png",
                100, 100, false, true));
        boarder[9].setLayoutX(1150);
        boarder[9].setLayoutY(550);
        root.getChildren().add(boarder[9]);

        //The image inside boarder
        inventoryListImage = new ImageView[10];
        for (int i = 0; i < inventoryListImage.length - 1; i++) {
            inventoryListImage[i] = new ImageView(new Image("materials/image/empty.png",
                    100, 100, false, true));
            inventoryListImage[i].setLayoutX(1000 + (i % 3) * 150);
            inventoryListImage[i].setLayoutY(100 + (i / 3) * 150);
            root.getChildren().add(inventoryListImage[i]);
        }
        inventoryListImage[9] = new ImageView(new Image("materials/image/empty.png",
                100, 100, false, true));
        inventoryListImage[9].setLayoutX(1150);
        inventoryListImage[9].setLayoutY(550);
        root.getChildren().add(inventoryListImage[9]);
    }

    private void setPlayerInfo(AnchorPane root) {
        credits = new Label("Your current credits: " + Player.getInstance().getCredits());
        credits.setStyle("-fx-font-weight: bold");
        credits.setStyle("-fx-border-color: SADDLEBROWN ; -fx-background-color: BURLYWOOD; -fx-border-width: 2px");
        try {
            credits.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            credits.setFont(Font.font("Verdana", 23));
        }
        credits.setLayoutX(450);
        credits.setLayoutY(200);

        inventorySize = new Label("# of Creatures in your broom: " +
                Broom.getInstance().getCreatureInventory().size());
        inventorySize.setStyle("-fx-font-weight: bold");
        inventorySize.setStyle("-fx-border-color: SADDLEBROWN ; " +
                "-fx-background-color: BURLYWOOD; -fx-border-width: 2px");
        try {
            inventorySize.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            inventorySize.setFont(Font.font("Verdana", 23));
        }
        inventorySize.setLayoutX(450);
        inventorySize.setLayoutY(350);

        price = new Label("The price of selected creature: ");
        price.setStyle("-fx-font-weight: bold");
        price.setStyle("-fx-border-color: SADDLEBROWN ; -fx-background-color: BURLYWOOD; -fx-border-width: 2px");
        try {
            price.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            price.setFont(Font.font("Verdana", 23));
        }
        price.setLayoutX(450);
        price.setLayoutY(500);

        root.getChildren().addAll(credits, inventorySize, price);
    }

    private void selectFromStore(ImageView creatureImage, int creatureIndexFromList) {
        creatureImage.setOnMouseClicked(e -> {
            creatureIndexSelectedFromStore = creatureIndexFromList;
            creatureIndexSelectedFromBroom = -1;
            updateBuySellBtn();
            updatePlayerInfo();
        });
    }

    private void selectFromInventory(ImageView creatureImage, int creatureIndexFromList) {
        creatureImage.setOnMouseClicked(e -> {
            creatureIndexSelectedFromBroom = creatureIndexFromList;
            creatureIndexSelectedFromStore = -1;
            updateBuySellBtn();
            updatePlayerInfo();
        });
    }

    private void updateBuySellBtn() {
        if (creatureIndexSelectedFromStore == -1) {
            btnBuy.setDisable(true);
        } else {
            btnBuy.setDisable(false);
        }
        if (creatureIndexSelectedFromBroom == -1) {
            btnSell.setDisable(true);
        } else {
            btnSell.setDisable(false);
        }
    }

    public void updateMarket() {
        Market.getInstance().updateShopList();
        for (int i = 0; i < Market.getInstance().getShopList().length; i++) {
            Image image = new Image("materials/image/" + Market.getInstance().getShopList()[i].getName() + ".png",
                    100, 100, false, true);
            // Create the ImageView
            shopListImage[i].setImage(image);
            shopListImage[i].setDisable(false);
            selectFromStore(shopListImage[i], i);
        }
    }

    public void updateInventory() {
        for (int i = 0; i < inventoryListImage.length; i++) {
            if (i < Broom.getInstance().getCreatureInventory().size()) {
                Image image = new Image(
                        "materials/image/" + Broom.getInstance().getCreatureInventory().get(i).getName() + ".png",
                        100, 100, false, true);
                inventoryListImage[i].setImage(image);
                selectFromInventory(inventoryListImage[i], i);
            } else {
                Image image = new Image("materials/image/empty.png",
                        100, 100, false, true);
                inventoryListImage[i].setImage(image);
            }
        }
    }

    public void updatePlayerInfo() {
        credits.setText("Your current credits: " + Player.getInstance().getCredits());
        inventorySize.setText("# of Creatures in your broom: " +
                Broom.getInstance().getCreatureInventory().size());
        if (creatureIndexSelectedFromStore != -1) {
            price.setText("The price of selected creature: " +
                    Market.getInstance().getShopList()[creatureIndexSelectedFromStore].getFinalPrice());
        } else if (creatureIndexSelectedFromBroom != -1) {
            price.setText("The price of selected creature: " +
                    Broom.getInstance().getCreatureInventory().get(creatureIndexSelectedFromBroom).getFinalPrice());
        } else {
            price.setText("The price of selected creature: ");
        }
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

    private void createButton(AnchorPane root){
        btnExit = new YellowButton("Exit");
        btnExit.setLayoutX(1300);
        btnExit.setLayoutY(700);
        btnBuy = new YellowButton("Buy");
        btnBuy.setLayoutX(700);
        btnBuy.setLayoutY(700);
        btnSell = new YellowButton("Sell");
        btnSell.setLayoutX(1000);
        btnSell.setLayoutY(700);
        transactionButtonFunction();
        root.getChildren().addAll(btnExit, btnBuy, btnSell);
    }

    private void transactionButtonFunction() {
        btnBuy.setOnMouseClicked(e -> {
            int price = Market.getInstance().getShopList()[creatureIndexSelectedFromStore].getFinalPrice();
            if (Player.getInstance().getCredits() < price) {
                new Alert(Alert.AlertType.NONE,
                        "Ooops! You don't have enough credits.", ButtonType.OK).show();
            } else if (Broom.getInstance().getCreatureInventory().size() >= Broom.getInstance().getCargoCapacity()){
                new Alert(Alert.AlertType.NONE,
                        "Ooops! You don't enough space to carry the creature.", ButtonType.OK).show();
            } else {
                shopListImage[creatureIndexSelectedFromStore].setDisable(true);
                Image image = new Image("materials/image/soldOut.jpg",
                        100, 100, false, true);
                shopListImage[creatureIndexSelectedFromStore].setImage(image);
                Player.getInstance().setCredits(Player.getInstance().getCredits() - price);
                Broom.getInstance().gainCreature(Market.getInstance().getShopList()[creatureIndexSelectedFromStore]);
                creatureIndexSelectedFromStore = -1;
                updateInventory();
                updatePlayerInfo();
            }
        });

        btnSell.setOnMouseClicked(e -> {
            int price = Broom.getInstance().getCreatureInventory().get(creatureIndexSelectedFromBroom).getFinalPrice();
            Broom.getInstance().removeCreature(creatureIndexSelectedFromBroom);
            creatureIndexSelectedFromBroom = -1;
            updateInventory();
            Player.getInstance().setCredits(Player.getInstance().getCredits() + price);
            updatePlayerInfo();
        });
    }

    public YellowButton getBtnExit() {
        return btnExit;
    }

}
