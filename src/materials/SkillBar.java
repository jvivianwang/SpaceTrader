package materials;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BackgroundImage;

import javax.swing.*;

public class SkillBar extends Label {



    private static DimensionsHandler dim = new DimensionsHandler();

    private BackgroundImage skillBar;

    private SkillImage skillImage;

    public SkillBar(SkillImage skillImage) {
        this.skillImage = skillImage;
        setPrefWidth(dim.customWidth((100)));
        setPrefHeight(dim.customHeight((10)));
        setText("0");
        setWrapText(true);
        setAlignment(Pos.CENTER);
        String fontsize = dim.customHeight(30) + "px; ";
        setStyle("-fx-font-size: " + fontsize
                + "-fx-background-color: transparent; "
                + "-fx-background-image: url('/" + skillImage.getUrl() + "');"
                + " -fx-background-size: 100% 100%;");
    }
}
