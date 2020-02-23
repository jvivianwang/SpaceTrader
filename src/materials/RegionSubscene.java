package materials;

import application.Main;
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

    private String regionNameInfo;
    private String techLevelInfo;
    private String descriptionInfo;
    private String distanceInfo;

    private YellowButton btnTravel;
    private YellowButton btnMarket;

    public RegionSubscene() {
        super(new AnchorPane(), 300, 700);
        prefHeight(700);
        prefWidth(300);

        Image backgroundImage = new Image(BACKGROUND_IMAGE,
                300,
                700,
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
        setLayoutY(100);
    }

    private void displayInfo(AnchorPane root) {
        regionNameInfo = "UNKNOWN";
        techLevelInfo = "UNKNOWN";
        descriptionInfo = "UNKNOWN";
        distanceInfo = "UNKNOWN";

        regionName = displayLabel("Region Name", regionNameInfo, 0, 0);
        techLevel = displayLabel("Tech Level", techLevelInfo, 0, 130);
        description = displayLabel("Description", descriptionInfo, 0, 260);
        distance = displayLabel("Distance", distanceInfo, 0, 390);

        root.getChildren().addAll(regionName, techLevel, description, distance);
    }

    public void setDisplayInfo(Region regionSelect) {
        regionNameInfo = regionSelect.getRegionName();
        distanceInfo = calculateDistance(Main.getPlayer().getCurrentRegion(), regionSelect) + "";
        if (regionSelect.isDiscovered()) {
            techLevelInfo = regionSelect.getTechLevel() + "";
            descriptionInfo = regionSelect.getDescription();
        } else {
            techLevelInfo = "UNKNOWN";
            descriptionInfo = "UNKNOWN";
        }
        regionName.setText("Region Name" + "\n" + regionNameInfo);
        techLevel.setText("Tech Level" + "\n" + techLevelInfo);
        description.setText("Description" + "\n" + descriptionInfo);
        distance.setText("Distance" + "\n" + distanceInfo);
    }

    private void createButton(AnchorPane root) {
        btnTravel = new YellowButton("Travel");
        btnMarket = new YellowButton("Market");
        btnTravel.setLayoutX(100);
        btnTravel.setLayoutY(520);
        btnMarket.setLayoutX(100);
        btnMarket.setLayoutY(620);

        root.getChildren().add(btnTravel);
        root.getChildren().add(btnMarket);
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
        temp.setAlignment(Pos.CENTER);
        temp.setPrefWidth(325);
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
}
