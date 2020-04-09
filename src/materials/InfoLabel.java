package materials;


import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class InfoLabel extends Label {

    private static DimensionsHandler dim = new DimensionsHandler();
    private static final int HEIGHT = dim.getHeight();
    private static final int WIDTH = dim.getWidth();
    private static final int FONTSIZE = dim.getFontsize();

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
        setPrefWidth(dim.customWidth(150));
        setPrefHeight(dim.customHeight(150));
        setText(text);
        setWrapText(true);
        setLabelFont();
        setAlignment(Pos.CENTER);

        hasSkillPoints = true;
        initializeButtonListeners();

        backgroundImageReleased = new BackgroundImage(new Image(BACKGROUND_IMAGE_RELEASED,
                dim.customWidth(150),
                dim.customHeight(150),
                false, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                null);
        backgroundImagePressed = new BackgroundImage(new Image(BACKGROUND_IMAGE_PRESSED,
                dim.customWidth(150),
                dim.customHeight(150),
                false, true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                null);
        backgroundImageFulled = new BackgroundImage(new Image(BACKGROUND_IMAGE_FULLED,
                dim.customWidth(150),
                dim.customHeight(150),
                false, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                null);
        setBackground(new Background(backgroundImageReleased));
    }

    public void setHasSkillPoints(boolean hasSkillPoints) {
        this.hasSkillPoints = hasSkillPoints;
    }

    private void setLabelFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), FONTSIZE));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", FONTSIZE));
        }
    }

    private void initializeButtonListeners() {
        setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                setLayoutY(getLayoutY() + dim.customWidth(10));
                if (hasSkillPoints) {
                    setBackground(new Background(backgroundImagePressed));
                } else {
                    setBackground(new Background(backgroundImageFulled));
                }
            }
        });
        setOnMouseReleased(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                setLayoutY(getLayoutY() - dim.customWidth(10));
                setBackground(new Background(backgroundImageReleased));
            }
        });
    }
}
