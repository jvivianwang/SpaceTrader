package materials;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class OlivanderMarketSubscene extends SubScene {

    private static final String BACKGROUND_IMAGE = "materials/image/magicalBackground.png";
    private boolean isHidden;
    private YellowButton btnExit;

    public OlivanderMarketSubscene() {
        super(new AnchorPane(), 1600, 900);
        prefWidth(1600);
        prefHeight(900);

        isHidden = true;

        AnchorPane root2 = (AnchorPane) this.getRoot();
        setBackgroundImage(root2);
        createButton(root2);

        setLayoutX(0);
        setLayoutY(-900);
    }

    private void setBackgroundImage(AnchorPane root) {
        Image backgroundImage = new Image(BACKGROUND_IMAGE,
                1600,
                900,
                false,
                true);
        BackgroundImage image = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                null);
        root.setBackground(new Background(image));
    }

    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.5));
        transition.setNode(this);

        if (isHidden) {
            //setToY(y) is move down y units instead move to y coordinate.
            transition.setToY(900);
            isHidden = false;
        } else {
            transition.setToY(-900);
            isHidden = true;
        }

        transition.play();
    }
    private void createButton(AnchorPane root){
        btnExit = new YellowButton("Exit");
        root.getChildren().add(btnExit);
    }

    public YellowButton getBtnExit() {
        return btnExit;
    }

}
