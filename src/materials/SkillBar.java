package materials;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BackgroundImage;

public class SkillBar extends Label {

    private BackgroundImage skillBar;

    private SkillImage skillImage;

    public SkillBar(SkillImage skillImage) {
        this.skillImage = skillImage;
        setPrefWidth(100);
        setPrefHeight(0);
        setText("0");
        setWrapText(true);
        setAlignment(Pos.CENTER);
        setStyle("-fx-font-size: 30px; "
                + "-fx-background-color: transparent; "
                + "-fx-background-image: url('/" + skillImage.getUrl() + "');"
                + " -fx-background-size: 100% 100%;");
    }
}
