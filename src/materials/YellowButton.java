package materials;


import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

//Commented out unused imports for now
//import javafx.scene.input.MouseButton;
//import javafx.scene.input.MouseEvent;
//import javafx.event.EventHandler;


public class YellowButton extends Button {

    private final String fontPath = "src/materials/font/Cochin W01 Roman.ttf";
    private final String buttonPressedStyle = "-fx-background-color: transparent;"
           + " -fx-background-image: url('/materials/image/buttonAfter.jpg');"
            + "  -fx-background-size: 100% 100%;";
    private final String buttonFreeStyle = "-fx-background-color: transparent;"
            + " -fx-background-image: url('/materials/image/buttonBefore.jpg');"
            + " -fx-background-size: 100% 100%;";

    private boolean isReleased;

    private static DimensionsHandler dim = new DimensionsHandler();
    private static final int FONTSIZE = dim.getFontsize();
    private static final int PRESS_OFFSET = dim.getPressOffset();

    public YellowButton(String text) {
        setText(text);
        setButtonFont();
        setPrefWidth(dim.customWidth((150)));
        setPrefHeight(dim.customHeight((50)));
        setStyle(buttonFreeStyle);

        isReleased = true;

        initializeButtonListeners();
    }

    private void setButtonFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(fontPath), FONTSIZE));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", FONTSIZE));
        }
    }

    public void setButtonStyle() {
        if (isReleased) {
            setStyle(buttonPressedStyle);
            setPrefHeight(dim.customHeight((60)));
            setLayoutY(getLayoutY() + PRESS_OFFSET);
            isReleased = false;
        } else {
            setStyle(buttonFreeStyle);
            setPrefHeight(dim.customHeight((50)));
            setLayoutY(getLayoutY() - PRESS_OFFSET);
            isReleased = true;
        }
    }

    private void initializeButtonListeners() {
        setOnMouseEntered(event -> {
            setEffect(new DropShadow());
            setStyle(buttonPressedStyle);
        });
        setOnMouseExited(event -> {
            setEffect(null);
            setStyle(buttonFreeStyle);
        });
    }
}
