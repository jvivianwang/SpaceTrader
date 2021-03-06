package materials;

import component.Broom;
import component.Player;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import component.Region;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class RegionSubscene extends SubScene {

    private static DimensionsHandler dim = new DimensionsHandler();

    private static final int HEIGHT = dim.getHeight();

    private static final int WIDTH = dim.getWidth();

    private static final int FONTSIZE = dim.getFontsize();

    private static final int SUBSCENE_WIDTH = dim.customWidth(300);


    private static final String BACKGROUND_IMAGE = "materials/image/configSubsceneBG.png";

    private boolean isHidden;

    private Label regionName;
    private Label techLevel;
    private Label description;
    private Label distance;
    private Label coordinate;
    private Label fuelRemain;
    private Label shipHealth;

    private String regionNameInfo;
    private String techLevelInfo;
    private String descriptionInfo;
    private String distanceInfo;
    private String coordinateInfo;
    private String fuelRemainInfo;
    private String shipHealthInfo;

    private YellowButton btnTravel;
    private YellowButton btnRefuel;
    private YellowButton btnMarket;
    private YellowButton btnRepair;

    public RegionSubscene() {
        super(new AnchorPane(), SUBSCENE_WIDTH, HEIGHT);
        prefHeight(HEIGHT);
        prefWidth(SUBSCENE_WIDTH);

        Image backgroundImage = new Image(BACKGROUND_IMAGE,
                SUBSCENE_WIDTH,
                HEIGHT,
                false,
                true);
        BackgroundImage image = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                null);

        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(image));
        displayInfo(root2);

        createButton(root2);

        isHidden = true;

        setLayoutX(WIDTH + SUBSCENE_WIDTH);
        setLayoutY(0);
    }

    private void displayInfo(AnchorPane root) {
        regionNameInfo = "UNKNOWN";
        techLevelInfo = "UNKNOWN";
        descriptionInfo = "UNKNOWN";
        distanceInfo = "UNKNOWN";
        coordinateInfo = "UNKNOWN";
        fuelRemainInfo = "UNKNOWN";
        shipHealthInfo = "UNKNOWN";

        regionName = displayLabel("Region Name", regionNameInfo,
                dim.customWidth(20), dim.customHeight(50));
        techLevel = displayLabel("Tech Level", techLevelInfo,
                dim.customWidth(20), dim.customHeight(100));
        description = displayLabel("Description", descriptionInfo,
                dim.customWidth(20), dim.customHeight(150));
        distance = displayLabel("Distance", distanceInfo,
                dim.customWidth(20), dim.customHeight(200));
        coordinate = displayLabel("Coordinate", coordinateInfo,
                dim.customWidth(20), dim.customHeight(250));
        fuelRemain = displayLabel("Fuel Remain", fuelRemainInfo,
                dim.customWidth(20), dim.customHeight(300));
        shipHealth = displayLabel("Broom Health", shipHealthInfo,
                dim.customWidth(20), dim.customHeight(350));

        root.getChildren().addAll(regionName, techLevel,
                description, distance, coordinate, fuelRemain, shipHealth);
    }

    public void setDisplayInfo(Region regionSelect) {
        regionNameInfo = regionSelect.getRegionName();
        coordinateInfo = regionSelect.getCoordinate();
        distanceInfo = calculateDistance(Player.getInstance().getCurrentRegion(), regionSelect)
                + "";
        fuelRemainInfo = Broom.getInstance().getFuelCapacity() + " /1000";
        shipHealthInfo = Broom.getInstance().getHealthString();
        if (regionSelect.isDiscovered()) {
            techLevelInfo = regionSelect.getTechLevel() + "";
            descriptionInfo = regionSelect.getDescription();
        } else {
            techLevelInfo = "UNKNOWN";
            descriptionInfo = "UNKNOWN";
        }
        regionName.setText("Region Name: " + "\n" + regionNameInfo);
        regionName.setTextFill(Color.web("#ffffff"));
        techLevel.setText("Tech Level: " + "\n" + techLevelInfo);
        techLevel.setTextFill(Color.web("#ffffff"));
        description.setText("Description: " + "\n" + descriptionInfo);
        description.setTextFill(Color.web("#ffffff"));
        distance.setText("Distance: " + "\n" + distanceInfo);
        distance.setTextFill(Color.web("#ffffff"));
        coordinate.setText("Coordinate: " + "\n" + coordinateInfo);
        coordinate.setTextFill(Color.web("#ffffff"));
        fuelRemain.setText("Fuel Remain: " + "\n" + fuelRemainInfo);
        fuelRemain.setTextFill(Color.web("#ffffff"));
        shipHealth.setText("Broom Health: " + '\n' + shipHealthInfo);
        shipHealth.setTextFill(Color.web("#ffffff"));
    }

    private void createButton(AnchorPane root) {
        btnTravel = new YellowButton("Travel");
        btnRefuel = new YellowButton("Refuel");
        btnMarket = new YellowButton("Market");
        btnRepair = new YellowButton("Repair");
        btnTravel.setLayoutX(dim.customWidth(80));
        btnTravel.setLayoutY(dim.customHeight(500));
        btnRefuel.setLayoutX(dim.customWidth(80));
        btnRefuel.setLayoutY(dim.customHeight(560));
        btnMarket.setLayoutX(dim.customWidth(80));
        btnMarket.setLayoutY(dim.customHeight(620));
        btnRepair.setLayoutX(dim.customWidth(80));
        btnRepair.setLayoutY(dim.customHeight(680));

        root.getChildren().add(btnTravel);
        root.getChildren().add(btnRefuel);
        root.getChildren().add(btnMarket);
        root.getChildren().add(btnRepair);
    }

    private int calculateDistance(Region r1, Region r2) {
        return (int) Math.sqrt(Math.pow(r2.getLayoutX() - r1.getLayoutX(),
                2) + Math.pow(r2.getLayoutY() - r1.getLayoutY(), 2));
    }

    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.5));
        transition.setNode(this);

        if (isHidden) {
            //setToY(y) is move down y units instead move to y coordinate.
            transition.setToX(-dim.customWidth(600));
            isHidden = false;
        } else {
            transition.setToX(dim.customWidth(600));
            isHidden = true;
        }

        transition.play();
    }

    public Label displayLabel(String name, String info, double x, double y) {
        Label temp = new Label(name + "\n" + info);
        temp.setFont(new Font(FONTSIZE));
        temp.setAlignment(Pos.CENTER_LEFT);
        temp.setPrefWidth(dim.customWidth(280));
        temp.setPrefHeight(dim.customHeight(100));
        temp.setLayoutX(x);
        temp.setLayoutY(y);
        return temp;
    }

    public YellowButton getBtnTravel() {
        return btnTravel;
    }
    public YellowButton getBtnRefuel() {
        return btnRefuel;
    }
    public YellowButton getBtnMarket() {
        return btnMarket;
    }
    public YellowButton getBtnRepair() {
        return btnRepair;
    }

}
