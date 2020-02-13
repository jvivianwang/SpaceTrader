package materials;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

//Commented out unused imports for now
//import javafx.geometry.Insets;
//import javafx.scene.effect.DropShadow;

public class InfoLabel extends Label {

    private boolean hasSkillPoints;

    private static BackgroundImage backgroundImageReleased;
    private static BackgroundImage backgroundImagePressed;
    private static BackgroundImage backgroundImageFulled;

    public static final String FONT_PATH = "src/materials/font/SEASRN__.ttf";

    public static final String BACKGROUND_IMAGE_RELEASED = "materials/image/textBackground.png";
    public static final String BACKGROUND_IMAGE_PRESSED = "materials/image"
            + "/textBackgroundPressed.png";
    public static final String BACKGROUND_IMAGE_FULLED = "materials/image"
            + "/textBackgroundFulled.png";

    public InfoLabel(String text) {
        setPrefWidth(150);
        setPrefHeight(150);
        setText(text);
        setWrapText(true);
        setLabelFont();
        setAlignment(Pos.CENTER);

        hasSkillPoints = true;
        initializeButtonListeners();

        backgroundImageReleased = new BackgroundImage(new Image(BACKGROUND_IMAGE_RELEASED, 150,
                150, false, true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        backgroundImagePressed = new BackgroundImage(new Image(BACKGROUND_IMAGE_PRESSED, 150,
                150, false, true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        backgroundImageFulled = new BackgroundImage(new Image(BACKGROUND_IMAGE_FULLED, 150,
                150, false, true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImageReleased));
    }

    public void setHasSkillPoints(boolean hasSkillPoints) {
        this.hasSkillPoints = hasSkillPoints;
    }

    private void setLabelFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 23));
        }
    }

    private void initializeButtonListeners() {
        setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                setLayoutY(getLayoutY() + 10);
                if (hasSkillPoints) {
                    setBackground(new Background(backgroundImagePressed));
                } else {
                    setBackground(new Background(backgroundImageFulled));
                }
            }
        });
        setOnMouseReleased(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                setLayoutY(getLayoutY() - 10);
                setBackground(new Background(backgroundImageReleased));
            }
        });
    }
}
