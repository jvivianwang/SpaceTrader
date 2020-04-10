package application;


import component.Broom;
import component.Market;
import component.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import materials.ConfigSkillSubscene;
import materials.MarketSubscene;
import scene.ConfigPage;
import scene.PlayerSheetPage;
import scene.RegionMapPage;
import scene.WelcomePage;

import java.io.File;


//Commented out imports we haven't used yet
//import javafx.embed.swing.JFXPanel;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.image.Image;

public class Main extends Application {

    private WelcomePage wp;
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) {
        music();
        stage = primaryStage;
        stage.setTitle("Space Trader");
        //All scenes initialized.
        wp = new WelcomePage();
        ConfigPage.getInstance();


        //set first scene to WelcomePage.
        stage.setScene(wp.getMainScene());

        //Switch to player config.
        //music();
        sceneSwitchToCP();

        //Switch to player sheet Page.
//        sceneSwitchToPSP(ConfigPage.getInstance().getEasySubScene());
//        sceneSwitchToPSP(ConfigPage.getInstance().getMedSubScene());
//        sceneSwitchToPSP(ConfigPage.getInstance().getHardSubScene());

        //Switch to Region Map Page.
        //Call sceneSwitchToRMP() at line 93.

        stage.show();
    }

    private void sceneSwitchToCP() {
        wp.getCanvas().setOnMouseClicked(e -> {
            double x = e.getX();
            double y = e.getY();
            double wCanvas = wp.getCanvas().getWidth();
            double hCanvas = wp.getCanvas().getHeight();
            double wPlay = wp.getPlay().getWidth();
            double hPlay = wp.getPlay().getHeight();
            if ((x < wCanvas / 2 + wPlay / 2 && x > wCanvas / 2 - wPlay / 2)
                    && (y < hCanvas / 2 + hPlay  / 2 && y > hCanvas  / 2 - hPlay  / 2)) {
                stage.setScene(ConfigPage.getInstance().getMainScene());
            }
        });
    }


    private void sceneSwitchToPSP(ConfigSkillSubscene subscene) {

        subscene.getBtnConfirm().setOnMouseClicked(e -> {
            if (subscene.getNameValue().getText().trim().isEmpty()
                    && !subscene.getBroomCheckBox().isSelected()) {
                //subscene.nameValue.setStyle("-fx-border-color: red");
                Alert a = new Alert(Alert.AlertType.NONE,
                        "Please enter your name and select your broom", ButtonType.OK);

                a.show();

            } else if (subscene.getNameValue().getText().trim().isEmpty()) {
                //subscene.nameValue.setStyle("-fx-border-color: red");
                Alert a = new Alert(Alert.AlertType.NONE,
                        "Please enter your name", ButtonType.OK);

                a.show();

            } else if (!subscene.getBroomCheckBox().isSelected()) {
                //subscene.nameValue.setStyle("-fx-border-color: red");
                Alert a = new Alert(Alert.AlertType.NONE,
                        "Please select your broom", ButtonType.OK);

                a.show();

            } else {
                Player.getInstance();
                Player.getInstance().setDifficulty(subscene.getDifficulty());
                Player.getInstance().setCredits(subscene.getCredits());
                Player.getInstance().setSkillPoints(subscene.getSkillPoints());
                Player.getInstance().setSkills(subscene.getSkills());
                Player.getInstance().setName(subscene.getNameValue().getText());
                //create broom
                Broom.getInstance();
                if (Player.getInstance().getDifficulty().equalsIgnoreCase("easy")) {
                    Broom.getInstance().setHealth(2000);
                } else if (Player.getInstance().getDifficulty().equalsIgnoreCase("medium")) {
                    Broom.getInstance().setHealth(1000);
                } else if (Player.getInstance().getDifficulty().equalsIgnoreCase("hard")) {
                    Broom.getInstance().setHealth(500);
                }
                PlayerSheetPage.getInstance();
                stage.setScene(PlayerSheetPage.getInstance().getMainScene());
            }
        });
    }


    public static void setScene(Scene newScene){stage.setScene(newScene);}

    public void music() {
        File file = new File("src/materials/monsterMusic.mp3");
        javafx.scene.media.Media music = new javafx.scene.media.Media(file.toURI().toString());
        javafx.scene.media.MediaPlayer mp = new javafx.scene.media.MediaPlayer(music);
        mp.play();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
