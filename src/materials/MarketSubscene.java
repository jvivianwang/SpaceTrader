package materials;

import component.Market;
import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class MarketSubscene extends SubScene {

    private static final String BACKGROUND_IMAGE = "materials/image/magicalBackground.png";
    private boolean isHidden;
    private YellowButton btnExit;

    private ImageView[] shopListImage;

    public MarketSubscene() {
        super(new AnchorPane(), 1600, 900);
        prefWidth(1600);
        prefHeight(900);

        isHidden = true;

        AnchorPane root2 = (AnchorPane) this.getRoot();
        setMarket(root2);
        setBackgroundImage(root2);
        createButton(root2);

        setLayoutX(0);
        setLayoutY(-900);
    }

    private void setMarket(AnchorPane root) {
        shopListImage = new ImageView[5];
        for (int i = 0; i < shopListImage.length; i++) {
            //set default images to earthdragon just because the imageview cant be null
            shopListImage[i] = new ImageView(new Image("materials/image/EarthDragon.png",
                    100, 100, false, true));
            shopListImage[i].setLayoutX(200);
            shopListImage[i].setLayoutY(50 + 150 * i);
            root.getChildren().add(shopListImage[i]);
        }
    }

    public void updateMarket() {
        Market.getInstance().updateShopList();
        for (int i = 0; i <Market.getInstance().getShopList().length; i++) {
            Image image = new Image("materials/image/" + Market.getInstance().getShopList()[i].getName() + ".png",
                    100, 100, false, true);
            // Create the ImageView
            shopListImage[i].setImage(image);
        }
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
