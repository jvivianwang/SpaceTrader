package materials;

import component.Creature;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import javax.swing.text.html.ImageView;

public class TraderSubscene extends SubScene {
    private static final String BACKGROUND_IMAGE = "materials/image/banditBackground.jpg";
    private static final String FONT_PATH = "src/materials/font/Cochin W01 Roman.ttf";

    private boolean isHidden;

    private Creature[] creatureList;
    private YellowButton btnBuy;
    private YellowButton btnIgnore;
    private YellowButton btnRob;
    private YellowButton btnNegotiate;
    private YellowButton btnConfirmResult;

    private Label result;

    private int creatureSelectedFromList;
    private ImageView[] creatureListImage;



    public TraderSubscene() {
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
        btnBuy = new YellowButton("Buy");
        btnBuy.setLayoutX(800);
        btnBuy.setLayoutY(700);
        btnBuy.setDisable(true);
        btnIgnore = new YellowButton("Ignore");
        btnIgnore.setLayoutX(400);
        btnIgnore.setLayoutY(700);
        btnIgnore.setDisable(true);
        btnRob = new YellowButton("Rob");
        btnRob.setLayoutX(600);
        btnRob.setLayoutY(700);
        btnRob.setDisable(true);
        btnNegotiate = new YellowButton("Negotiate");
        btnNegotiate.setLayoutX(600);
        btnNegotiate.setLayoutY(700);
        btnNegotiate.setDisable(true);
        btnConfirmResult = new YellowButton("Confirm Result");
        btnConfirmResult.setLayoutX(600);
        btnConfirmResult.setLayoutY(700);
        btnConfirmResult.setDisable(true);
        root.getChildren().addAll(btnBuy, btnIgnore, btnRob, btnNegotiate, btnConfirmResult);
    }

    private void buy() {

    }
    private void ignore() {

    }
    private void rob() {

    }
    private void negotiate() {

    }
    private void confirmResult() {

    }
    private int creatureListImageClicked() {
        return 0;
    }
}
