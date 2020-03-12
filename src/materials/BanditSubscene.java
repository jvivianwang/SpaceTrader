package materials;

import component.Broom;
import component.Equipment;
import component.Player;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class BanditSubscene extends SubScene {
    private static final String BACKGROUND_IMAGE = "materials/image/banditBackground.jpg";
    private static final String FONT_PATH = "src/materials/font/Cochin W01 Roman.ttf";
    private boolean isHidden;
    private YellowButton btnPay;
    private YellowButton btnFlee;
    private YellowButton btnFight;
    private YellowButton btnExit;
    private YellowButton btnConfirmResult;
    private int demandCredits;
    private Label banditDemand;
    private Label result;


    public BanditSubscene() {
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
    public void pay() {
        btnPay.setOnMouseClicked(e -> {
            if (Player.getInstance().getCredits() < demandCredits) {

            } else {
                Player.getInstance().setCredits(Player.getInstance().getCredits() - demandCredits);
                result.setText(String.format("Successful payment! You current credit balance is: %d", Player.getInstance().getCredits()));
                btnConfirmResult.setDisable(false);
                btnPay.setDisable(true);
                btnFlee.setDisable(true);;
                btnFight.setDisable(true);;
            }

        });
    }
    public void fight() {
        btnFight.setOnMouseClicked(e -> {
            btnConfirmResult.setDisable(true);
            btnPay.setDisable(true);
            btnFlee.setDisable(true);;
            btnFight.setDisable(false);;
        });
    }
    public void flee() {
        btnFlee.setOnMouseClicked(e -> {
            btnConfirmResult.setDisable(true);
            btnPay.setDisable(true);
            btnFlee.setDisable(false);;
            btnFight.setDisable(true);;
        });
    }

    public YellowButton getBtnExit() {
        return btnExit;
    }

    private void createButton(AnchorPane root) {
        btnExit = new YellowButton("Exit");
        btnExit.setLayoutX(800);
        btnExit.setLayoutY(700);
        btnExit.setDisable(true);
        btnPay = new YellowButton("Pay");
        btnPay.setLayoutX(400);
        btnPay.setLayoutY(700);
        btnPay.setDisable(true);
        btnFight = new YellowButton("Fight");
        btnFight.setLayoutX(600);
        btnFight.setLayoutY(700);
        btnFight.setDisable(true);
        btnFlee = new YellowButton("Flee");
        btnFlee.setLayoutX(600);
        btnFlee.setLayoutY(700);
        btnFlee.setDisable(true);
        btnConfirmResult = new YellowButton("Confirm Result");
        btnConfirmResult.setLayoutX(600);
        btnConfirmResult.setLayoutY(700);
        btnConfirmResult.setDisable(true);
        root.getChildren().addAll(btnExit, btnPay, btnFight, btnFlee, btnConfirmResult);
    }

}
