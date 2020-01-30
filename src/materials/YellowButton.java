package materials;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class YellowButton extends Button {

    private final String FONT_PATH = "src/materials/font/FFF_Tusj.ttf";
    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/materials/image/buttonAfter.jpg');  -fx-background-size: 100% 100%;";
    private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/materials/image/buttonBefore.jpg'); -fx-background-size: 100% 100%;";

    private boolean isReleased;

    public YellowButton(String text) {
        setText(text);
        setButtonFont();
        setPrefWidth(150);
        setPrefHeight(50);
        setStyle(BUTTON_FREE_STYLE);

        isReleased = true;

        initializeButtonListeners();
    }

    private void setButtonFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 23));
        }
    }

    public void setButtonStyle() {
        if (isReleased) {
            setStyle(BUTTON_PRESSED_STYLE);
            setPrefHeight(60);
            setLayoutY(getLayoutY() + 10);
            isReleased = false;
        } else {
            setStyle(BUTTON_FREE_STYLE);
            setPrefHeight(50);
            setLayoutY(getLayoutY() - 10);
            isReleased = true;
        }
    }

    private void initializeButtonListeners() {
        setOnMouseEntered(event -> {
            setEffect(new DropShadow());
            setStyle(BUTTON_PRESSED_STYLE);
        });
        setOnMouseExited(event -> {
            setEffect(null);
            setStyle(BUTTON_FREE_STYLE);
        });
    }
}
