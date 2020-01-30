package materials;

import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class ConfigSkillSubscene extends SubScene {

    private final static String FONT_PATH = "src/materials/font/FFF_Tusj.ttf";
    private final static String BACKGROUND_IMAGE = "materials/image/configSubsceneBG.png";

    private boolean isHidden;

    public ConfigSkillSubscene() {
        super(new AnchorPane(), 600, 400);
        prefHeight(600);
        prefWidth(400);

        Image backgroundImage = new Image(BACKGROUND_IMAGE, 600, 400, false, true);
        BackgroundImage image = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();

        root2.setBackground(new Background(image));

        isHidden = true;

        setLayoutX(500);
        setLayoutY(-500);
    }

    //The skill scene will move down once you select level.
    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.5));
        transition.setNode(this);

        if(isHidden) {
            //setToY(y) is move down y units instead move to y coordinate.
            transition.setToY(650);
            isHidden = false;
        } else {
            transition.setToY(-650);
            isHidden = true;
        }

        transition.play();
    }
}
