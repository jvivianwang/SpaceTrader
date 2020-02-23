package materials;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class MarketSubscene extends SubScene {

    private static final String BACKGROUND_IMAGE = "materials/image/regionInfoBackground.png";
    private boolean isHidden;
    private YellowButton btnExit;

    public MarketSubscene() {
        super(new AnchorPane(), 1200, 700);
        prefWidth(1200);
        prefHeight(700);

        isHidden = true;

        setLayoutX(200);
        setLayoutY(-800);

        AnchorPane root = (AnchorPane) this.getRoot();

        createButton(root);
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
