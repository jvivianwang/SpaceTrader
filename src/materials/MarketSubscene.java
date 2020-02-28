package materials;

import component.*;
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
import materials.YellowButton;

import javax.xml.stream.FactoryConfigurationError;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MarketSubscene extends SubScene {

    private static final String BACKGROUND_IMAGE = "materials/image/magicalBackground.png";
    private static final String FONT_PATH = "src/materials/font/SEASRN__.ttf";

    private boolean isHidden;
    private boolean equipmenSlotSelected;
    private YellowButton btnExit;
    private YellowButton btnBuy;
    private YellowButton btnSell;
    private YellowButton btnEquip;
    private YellowButton btnUnequip;

    private ImageView[] shopListImage;
    private ImageView[] inventoryListImage;
    private ImageView[] shopEquipmentListImage;
    private ImageView equipmentImage;

    //Index should be from 0 to 4 since only five creature from the list
    private int creatureIndexSelectedFromStore;
    private int indexSelectedFromBroom;
    private int equipmentIndexSelectedFromStore;

    private Label credits;
    private Label inventorySize;
    private Label price;
    private Label pilotLabel;
    private Label fighterLabel;
    private Label merchantLabel;
    private Label engineerLabel;

    public MarketSubscene() {
        super(new AnchorPane(), 1600, 900);
        prefWidth(1600);
        prefHeight(900);

        isHidden = true;
        equipmenSlotSelected = false;
        creatureIndexSelectedFromStore = -1;
        indexSelectedFromBroom = -1;
        equipmentIndexSelectedFromStore = -1;

        AnchorPane root2 = (AnchorPane) this.getRoot();

        setMarket(root2);
        setOlivanderMarket(root2);
        setInventory(root2);
        setEquipSlot(root2);
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

    private void setOlivanderMarket(AnchorPane root) {
        shopEquipmentListImage = new ImageView[2];
        for (int i = 0; i < shopEquipmentListImage.length; i++) {
            //set default images to earthdragon just because the imageview cant be null
            shopEquipmentListImage[i] = new ImageView(new Image("materials/image/empty.png",
                    100, 100, false, true));
            shopEquipmentListImage[i].setLayoutX(450 + 200 * i);
            shopEquipmentListImage[i].setLayoutY(550);
            root.getChildren().add(shopEquipmentListImage[i]);
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

    private void setEquipSlot(AnchorPane root){
        Image equipSlot = new Image("materials/image/emptyBoarder.png", 150, 150,
                false, true);
        ImageView equipSlotView = new ImageView(equipSlot);
        equipSlotView.setLayoutX(1125);
        equipSlotView.setLayoutY(450);

        equipmentImage = new ImageView(new Image("materials/image/empty.png", 150, 150,
                false, true));
        equipmentImage.setLayoutX(1125);
        equipmentImage.setLayoutY(450);
        equipmentImage.setOnMouseClicked(e -> {
            if(Player.getInstance().getEquippmentItem() != null){
                equipmenSlotSelected = true;
                indexSelectedFromBroom = -1;
                creatureIndexSelectedFromStore = -1;
                equipmentIndexSelectedFromStore = -1;
                updateEquipUnequipBtn();
                updateBuySellBtn();
            }
        });
        root.getChildren().addAll(equipSlotView, equipmentImage);

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
        credits.setLayoutY(50);

        inventorySize = new Label("# of Creatures in your broom: " +
                Broom.getInstance().getInventory().size());
        inventorySize.setStyle("-fx-font-weight: bold");
        inventorySize.setStyle("-fx-border-color: SADDLEBROWN ; " +
                "-fx-background-color: BURLYWOOD; -fx-border-width: 2px");
        try {
            inventorySize.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            inventorySize.setFont(Font.font("Verdana", 23));
        }
        inventorySize.setLayoutX(450);
        inventorySize.setLayoutY(100);

        price = new Label("The price of selected creature: ");
        price.setStyle("-fx-font-weight: bold");
        price.setStyle("-fx-border-color: SADDLEBROWN ; -fx-background-color: BURLYWOOD; -fx-border-width: 2px");
        try {
            price.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            price.setFont(Font.font("Verdana", 23));
        }
        price.setLayoutX(450);
        price.setLayoutY(150);

//        InfoLabel pilotLabel = createInfoLabel("Pilot", 0);
//        InfoLabel fighterLabel = createInfoLabel("Fighter", 1);
//        InfoLabel merchantLabel = createInfoLabel("Merchant", 2);
//        InfoLabel engineerLabel = createInfoLabel("Engineer", 3);

        pilotLabel = new Label("Pilot skills: " + Player.getInstance().getSkills()[0]);
        pilotLabel.setStyle("-fx-font-weight: bold");
        pilotLabel.setStyle("-fx-border-color: SADDLEBROWN ; -fx-background-color: BURLYWOOD; -fx-border-width: 2px");
        try {
            pilotLabel.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            pilotLabel.setFont(Font.font("Verdana", 23));
        }
        pilotLabel.setLayoutX(450);
        pilotLabel.setLayoutY(250);

        fighterLabel = new Label("Fighter skills: " + Player.getInstance().getSkills()[1]);
        fighterLabel.setStyle("-fx-font-weight: bold");
        fighterLabel.setStyle("-fx-border-color: SADDLEBROWN ; -fx-background-color: BURLYWOOD; -fx-border-width: 2px");
        try {
            fighterLabel.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            fighterLabel.setFont(Font.font("Verdana", 23));
        }
        fighterLabel.setLayoutX(450);
        fighterLabel.setLayoutY(300);

        merchantLabel = new Label("Merchant skills: " + Player.getInstance().getSkills()[2]);
        merchantLabel.setStyle("-fx-font-weight: bold");
        merchantLabel.setStyle("-fx-border-color: SADDLEBROWN ; -fx-background-color: BURLYWOOD; -fx-border-width: 2px");
        try {
            merchantLabel.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            merchantLabel.setFont(Font.font("Verdana", 23));
        }
        merchantLabel.setLayoutX(450);
        merchantLabel.setLayoutY(350);

        engineerLabel = new Label("Engineer skills: " + Player.getInstance().getSkills()[3]);
        engineerLabel.setStyle("-fx-font-weight: bold");
        engineerLabel.setStyle("-fx-border-color: SADDLEBROWN ; -fx-background-color: BURLYWOOD; -fx-border-width: 2px");
        try {
            engineerLabel.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            engineerLabel.setFont(Font.font("Verdana", 23));
        }
        engineerLabel.setLayoutX(450);
        engineerLabel.setLayoutY(400);

        root.getChildren().addAll(
                credits, inventorySize, price, pilotLabel, fighterLabel, merchantLabel, engineerLabel);
    }

    private void selectFromStore(ImageView creatureImage, int creatureIndexFromList) {
        creatureImage.setOnMouseClicked(e -> {
            creatureIndexSelectedFromStore = creatureIndexFromList;
            indexSelectedFromBroom = -1;
            equipmentIndexSelectedFromStore = -1;
            equipmenSlotSelected = false;
            updateBuySellBtn();
            updateEquipUnequipBtn();
            updatePlayerInfo();
        });
    }

    private void selectFromEquipmentStore(ImageView equipmentImage, int equipmentIndexFromList) {
        equipmentImage.setOnMouseClicked(e -> {
            equipmentIndexSelectedFromStore = equipmentIndexFromList;
            indexSelectedFromBroom = -1;
            creatureIndexSelectedFromStore = -1;
            equipmenSlotSelected = false;
            updateBuySellBtn();
            updateEquipUnequipBtn();
            updatePlayerInfo();
        });
    }

    private void selectFromInventory(ImageView image, int indexFromList) {
        image.setOnMouseClicked(e -> {
            indexSelectedFromBroom = indexFromList;
            creatureIndexSelectedFromStore = -1;
            equipmentIndexSelectedFromStore = -1;
            equipmenSlotSelected = false;
            updateBuySellBtn();
            updateEquipUnequipBtn();
            updatePlayerInfo();
        });
    }

    private void updateBuySellBtn() {
        if (creatureIndexSelectedFromStore != -1 || equipmentIndexSelectedFromStore != -1) {
            btnBuy.setDisable(false);
        } else {
            btnBuy.setDisable(true);
        }
        if (indexSelectedFromBroom != -1) {
            btnSell.setDisable(false);
        } else {
            btnSell.setDisable(true);
        }
    }

    private void updateEquipUnequipBtn() {
        if (indexSelectedFromBroom != -1) {
            if (Broom.getInstance().getInventory().get(indexSelectedFromBroom) instanceof Equipment) {
                btnEquip.setDisable(false);
            } else {
                btnEquip.setDisable(true);
            }
        } else {
            btnEquip.setDisable(true);
        }
        if (! equipmenSlotSelected || Broom.getInstance().getInventory().size() >=
                Broom.getInstance().getCargoCapacity()) {
            btnUnequip.setDisable(true);
        } else {
            btnUnequip.setDisable(false);
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
            if (i < Broom.getInstance().getInventory().size()) {
                Image image;
                if (Broom.getInstance().getInventory().get(i) instanceof Creature) {
                    image = new Image(
                            "materials/image/" +
                                    ((Creature) Broom.getInstance().getInventory().get(i)).getName() + ".png",
                            100, 100, false, true);
                } else {
                    image = new Image(
                            "materials/image/" +
                                    ((Equipment) Broom.getInstance().getInventory().get(i)).getName() + ".png",
                            100, 100, false, true);
                }
                inventoryListImage[i].setImage(image);
                selectFromInventory(inventoryListImage[i], i);
            } else {
                Image image = new Image("materials/image/empty.png",
                        100, 100, false, true);
                inventoryListImage[i].setImage(image);
            }
        }
    }

    public void updateOlivanderMarket() {
        Market.getInstance().updateEquipmentList();
        for (int i = 0; i < Market.getInstance().getEquipmentList().length; i++) {
            Image image = new Image(
                    "materials/image/" + Market.getInstance().getEquipmentList()[i].getName() + ".png",
                    100, 100, false, true);
            // Create the ImageView
            shopEquipmentListImage[i].setImage(image);
            shopEquipmentListImage[i].setDisable(false);
            selectFromEquipmentStore(shopEquipmentListImage[i], i);
        }
    }

    public void updateEquipSlot() {
        Image image;
        if (Player.getInstance().getEquippmentItem() != null) {
            image = new Image(
                    "materials/image/" + Player.getInstance().getEquippmentItem().getName() + ".png",
                    150, 150, false, true);
        } else {
            image = new Image(
                    "materials/image/empty.png",
                    150, 150, false, true);
        }
        equipmentImage.setImage(image);
    }

    public void updatePlayerInfo() {
        credits.setText("Your current credits: " + Player.getInstance().getCredits());

        inventorySize.setText("# of item in your broom: " +
                Broom.getInstance().getInventory().size());

        pilotLabel.setText("Pilot skills: " + Player.getInstance().getSkills()[0]);
        fighterLabel.setText("Fighter skills: " + Player.getInstance().getSkills()[1]);
        merchantLabel.setText("Merchant skills: " + Player.getInstance().getSkills()[2]);
        engineerLabel.setText("Engineer skills: " + Player.getInstance().getSkills()[3]);

        if (creatureIndexSelectedFromStore != -1) {
            price.setText("The price of selected creature: " +
                    Market.getInstance().getShopList()[creatureIndexSelectedFromStore].getFinalPrice());
        } else if (equipmentIndexSelectedFromStore != -1) {
            price.setText("The price of selected equipment: " +
                    Market.getInstance().getEquipmentList()[equipmentIndexSelectedFromStore].getFinalPrice());
        } else if (indexSelectedFromBroom != -1) {
            if (Broom.getInstance().getInventory().get(indexSelectedFromBroom) instanceof Creature) {
                price.setText("The price of selected creature: " +
                        ((Creature) Broom.getInstance().getInventory().get(indexSelectedFromBroom)).getFinalPrice());
            } else {
                price.setText("The price of selected equipment: " +
                        ((Equipment) Broom.getInstance().getInventory().get(indexSelectedFromBroom)).getFinalPrice());
            }
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
        btnExit.setLayoutX(800);
        btnExit.setLayoutY(700);
        btnBuy = new YellowButton("Buy");
        btnBuy.setLayoutX(400);
        btnBuy.setLayoutY(700);
        btnBuy.setDisable(true);
        btnSell = new YellowButton("Sell");
        btnSell.setLayoutX(600);
        btnSell.setLayoutY(700);
        btnSell.setDisable(true);
        btnEquip = new YellowButton("Equip");
        btnEquip.setLayoutX(1000);
        btnEquip.setLayoutY(700);
        btnEquip.setDisable(true);
        btnUnequip = new YellowButton("Unequip");
        btnUnequip.setLayoutX(1200);
        btnUnequip.setLayoutY(700);
        btnUnequip.setDisable(true);

        transactionButtonFunction();
        equipButtonFunction();
        root.getChildren().addAll(btnExit, btnBuy, btnSell,btnEquip, btnUnequip);
    }

    private void transactionButtonFunction() {
        btnBuy.setOnMouseClicked(e -> {
            int price;
            //creature selected
            if (creatureIndexSelectedFromStore != -1) {
                price = Market.getInstance().getShopList()[creatureIndexSelectedFromStore].getFinalPrice();
            } else {
                //equipment selected
                price = Market.getInstance().getEquipmentList()[equipmentIndexSelectedFromStore].getFinalPrice();
            }
            if (Player.getInstance().getCredits() < price) {
                new Alert(Alert.AlertType.NONE,
                        "Ooops! You don't have enough credits.", ButtonType.OK).show();
            } else if (Broom.getInstance().getInventory().size() >= Broom.getInstance().getCargoCapacity()){
                new Alert(Alert.AlertType.NONE,
                        "Ooops! You don't enough space to carry the item.", ButtonType.OK).show();
            } else {
                Player.getInstance().setCredits(Player.getInstance().getCredits() - price);
                Image image = new Image("materials/image/soldOut.jpg",
                        100, 100, false, true);
                if (creatureIndexSelectedFromStore != -1) {
                    shopListImage[creatureIndexSelectedFromStore].setDisable(true);
                    shopListImage[creatureIndexSelectedFromStore].setImage(image);
                    Broom.getInstance().gainCreature(
                            Market.getInstance().getShopList()[creatureIndexSelectedFromStore]);
                } else {
                    shopEquipmentListImage[equipmentIndexSelectedFromStore].setDisable(true);
                    shopEquipmentListImage[equipmentIndexSelectedFromStore].setImage(image);
                    Broom.getInstance().gainEquipment(
                            Market.getInstance().getEquipmentList()[equipmentIndexSelectedFromStore]);
                }
                creatureIndexSelectedFromStore = -1;
                equipmentIndexSelectedFromStore = -1;
                updateInventory();
                updatePlayerInfo();
                updateBuySellBtn();
            }
        });

        btnSell.setOnMouseClicked(e -> {
            int price;
            if (Broom.getInstance().getInventory().get(indexSelectedFromBroom) instanceof Creature) {
                price = ((Creature) Broom.getInstance().getInventory().get(indexSelectedFromBroom)).getFinalPrice();
            } else {
                price = ((Equipment) Broom.getInstance().getInventory().get(indexSelectedFromBroom)).getFinalPrice();
            }
            Broom.getInstance().remove(indexSelectedFromBroom);
            indexSelectedFromBroom = -1;
            updateInventory();
            Player.getInstance().setCredits(Player.getInstance().getCredits() + price);
            updatePlayerInfo();
            updateBuySellBtn();
        });
    }

    public void equipButtonFunction(){
        btnEquip.setOnMouseClicked(e -> {
            Player.getInstance().setEquippmentItem(
                    (Equipment) Broom.getInstance().getInventory().get(indexSelectedFromBroom));
            indexSelectedFromBroom = -1;
            updateInventory();
            updateEquipSlot();
            updatePlayerInfo();
        });
        btnUnequip.setOnMouseClicked(e -> {
            Player.getInstance().setEquippmentItem(null);
            equipmenSlotSelected = false;
            updateInventory();
            updateEquipSlot();
            updatePlayerInfo();
        });
    }

    public YellowButton getBtnExit() {
        return btnExit;
    }

}
