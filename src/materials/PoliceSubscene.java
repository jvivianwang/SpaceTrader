package materials;

import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import scene.RegionMapPage;

public class PoliceSubscene extends SubScene {
    private static final String BACKGROUND_IMAGE = "materials/image/banditBackground.jpg";
    private static final String FONT_PATH = "src/materials/font/Cochin W01 Roman.ttf";

    private Item[] demandItems;
    private YellowButton btnForfeit;
    private YellowButton btnFlee;
    private YellowButton btnFight;
    private YellowButton btnConfirmResult;

    private Label result;
    private Label conversation;

    private ImageView[] demandItemsView;

    private boolean isHidden;


    public PoliceSubscene() {
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

    private void createButton(AnchorPane root) {
        btnForfeit = new YellowButton("Forfeit");
        btnForfeit.setLayoutX(800);
        btnForfeit.setLayoutY(700);
        btnForfeit.setDisable(true);
        btnFlee = new YellowButton("Flee");
        btnFlee.setLayoutX(400);
        btnFlee.setLayoutY(700);
        btnFlee.setDisable(true);
        btnFight = new YellowButton("Fight");
        btnFight.setLayoutX(600);
        btnFight.setLayoutY(700);
        btnFight.setDisable(true);
        btnConfirmResult = new YellowButton("Confirm Result");
        btnConfirmResult.setLayoutX(600);
        btnConfirmResult.setLayoutY(700);
        btnConfirmResult.setDisable(true);
        root.getChildren().addAll(btnForfeit, btnFlee, btnFight, btnConfirmResult);
    }
    private void confirmResult() {
        btnConfirmResult.setOnMouseClicked(e -> {
//            moveSubscene();
//            RegionMapPage.getInstance()
        });
    }

}
