package materials;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BackgroundImage;

public class SkillBar extends Label {

    private BackgroundImage skillBar;

    private Skill skill;

    public SkillBar(Skill skill) {
        this.skill = skill;
        setPrefWidth(100);
        setPrefHeight(0);
        setText("0");
        setWrapText(true);
        setAlignment(Pos.CENTER);
        setStyle("-fx-font-size: 30px; "
                + "-fx-background-color: transparent; "
                + "-fx-background-image: url('/" + skill.getUrl() + "');"
                + " -fx-background-size: 100% 100%;");
    }
}
