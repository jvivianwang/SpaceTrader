package materials;

import application.Main;
import component.Player;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import component.Region;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class RegionSubscene extends SubScene {

    private static final String BACKGROUND_IMAGE = "materials/image/regionInfoBackground.png";

    private boolean isHidden;

    private Label regionName;
    private Label techLevel;
    private Label description;
    private Label distance;
    private Label coordinate;

    private String regionNameInfo;
    private String techLevelInfo;
    private String descriptionInfo;
    private String distanceInfo;
    private String coordinateInfo;

    private YellowButton btnTravel;
    private YellowButton btnMarket;
    private YellowButton btnOlivanderMarket;

    public RegionSubscene() {
        super(new AnchorPane(), 300, 900);
        prefHeight(900);
        prefWidth(300);

        Image backgroundImage = new Image(BACKGROUND_IMAGE,
                300,
                900,
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

        setLayoutX(1900);
        setLayoutY(0);
    }

    private void displayInfo(AnchorPane root) {
        regionNameInfo = "UNKNOWN";
        techLevelInfo = "UNKNOWN";
        descriptionInfo = "UNKNOWN";
        distanceInfo = "UNKNOWN";
        coordinateInfo = "UNKNOWN";

        regionName = displayLabel("Region Name", regionNameInfo, 20, 100);
        techLevel = displayLabel("Tech Level", techLevelInfo, 20, 200);
        description = displayLabel("Description", descriptionInfo, 20, 300);
        distance = displayLabel("Distance", distanceInfo, 20, 400);
        coordinate = displayLabel("Coordinate", coordinateInfo, 20, 500);

        root.getChildren().addAll(regionName, techLevel, description, distance, coordinate);
    }

    public void setDisplayInfo(Region regionSelect) {
        regionNameInfo = regionSelect.getRegionName();
        coordinateInfo = regionSelect.getCoordinate();
        distanceInfo = calculateDistance(Player.getInstance().getCurrentRegion(), regionSelect) + "";
        if (regionSelect.isDiscovered()) {
            techLevelInfo = regionSelect.getTechLevel() + "";
            descriptionInfo = regionSelect.getDescription();
        } else {
            techLevelInfo = "UNKNOWN";
            descriptionInfo = "UNKNOWN";
        }
        regionName.setText("Region Name: " + "\n" + regionNameInfo);
        techLevel.setText("Tech Level: " + "\n" + techLevelInfo);
        description.setText("Description: " + "\n" + descriptionInfo);
        distance.setText("Distance: " + "\n" + distanceInfo);
        coordinate.setText("Coordinate: " + "\n" + coordinateInfo);
    }

    private void createButton(AnchorPane root) {
        btnTravel = new YellowButton("Travel");
        btnMarket = new YellowButton("Market");
        btnOlivanderMarket = new YellowButton("Olivander Market");
        btnTravel.setLayoutX(80);
        btnTravel.setLayoutY(600);
        btnMarket.setLayoutX(80);
        btnMarket.setLayoutY(700);
        btnOlivanderMarket.setPrefWidth(250);
        btnOlivanderMarket.setLayoutX(30);
        btnOlivanderMarket.setLayoutY(800);

        root.getChildren().add(btnTravel);
        root.getChildren().add(btnMarket);
        root.getChildren().add(btnOlivanderMarket);
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
            transition.setToX(-600);
            isHidden = false;
        } else {
            transition.setToX(600);
            isHidden = true;
        }

        transition.play();
    }

    public Label displayLabel(String name, String info, double x, double y) {
        Label temp = new Label(name + "\n" + info);
        temp.setFont(new Font(23));
        temp.setAlignment(Pos.CENTER_LEFT);
        temp.setPrefWidth(280);
        temp.setPrefHeight(100);
        temp.setLayoutX(x);
        temp.setLayoutY(y);
        return temp;
    }

    public YellowButton getBtnTravel() {
        return btnTravel;
    }
    public YellowButton getBtnMarket() {
        return btnMarket;
    }
    public YellowButton getBtnOlivanderMarket() {
        return btnOlivanderMarket;
    }
}
