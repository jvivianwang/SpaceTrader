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

    private final String fontPath = "src/materials/font/FFF_Tusj.ttf";
    private final String buttonPressedStyle = "-fx-background-color: transparent;"
           + " -fx-background-image: url('/materials/image/buttonAfter.jpg');"
            + "  -fx-background-size: 100% 100%;";
    private final String buttonFreeStyle = "-fx-background-color: transparent;"
            + " -fx-background-image: url('/materials/image/buttonBefore.jpg');"
            + " -fx-background-size: 100% 100%;";

    private boolean isReleased;

    public YellowButton(String text) {
        setText(text);
        setButtonFont();
        setPrefWidth(150);
        setPrefHeight(50);
        setStyle(buttonFreeStyle);

        isReleased = true;

        initializeButtonListeners();
    }

    private void setButtonFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(fontPath), 23));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 23));
        }
    }

    public void setButtonStyle() {
        if (isReleased) {
            setStyle(buttonPressedStyle);
            setPrefHeight(60);
            setLayoutY(getLayoutY() + 10);
            isReleased = false;
        } else {
            setStyle(buttonFreeStyle);
            setPrefHeight(50);
            setLayoutY(getLayoutY() - 10);
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
