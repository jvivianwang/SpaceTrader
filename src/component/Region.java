package component;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;

import java.util.Random;

public class Region extends Label {

    private static BackgroundImage regionBackground;

    private String regionName;
    private int techLevel;
    private String description;

    private boolean isDiscovered;


    //Random generate coord while creating new region.
    public Region(String regionName, int techLevel, String description, int regionSpot) {
        Random random = new Random();
        this.regionName = regionName;
        this.techLevel = techLevel;
        this.description = description;
        isDiscovered = false;

        setLayoutX(260 * (regionSpot / 2) + random.nextInt(110));
        setLayoutY(450 * (regionSpot % 2) + random.nextInt(300));
        setPrefWidth(150);
        setPrefHeight(150);

        //image name matches region name.
        setRegionBackgroundToYellow();
    }

    public void setRegionBackgroundToYellow() {
        Image image = new Image("materials/image/N" + regionName + ".png",
                150, 150, false, true);
        regionBackground = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, null);
        setBackground(new Background(regionBackground));
    }

    public void setRegionBackgroundToBlue() {
        Image image = new Image("materials/image/N" + regionName + "Blue.png",
                150, 150, false,
                true);
        regionBackground = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                null);
        setBackground(new Background(regionBackground));
    }


    public int getTechLevel() {
        return techLevel;
    }
    public String getRegionName() {
        return regionName;
    }
    public String getDescription() {
        return description;
    }
    public boolean isDiscovered() {
        return isDiscovered;
    }
    public void setDiscovered(boolean discovered) {
        isDiscovered = discovered;
    }

    public String getCoordinate() {
        return "( " + (int) this.getLayoutX() + ", " + (int) this.getLayoutY() + ")";
    }
}
