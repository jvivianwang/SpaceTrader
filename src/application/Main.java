package application;

import component.Player;
import component.Region;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import materials.ConfigSkillSubscene;
import scene.ConfigPage;
import scene.PlayerSheetPage;
import scene.RegionMapPage;
import scene.WelcomePage;

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

    private static Player player;

    private WelcomePage wp;
    private ConfigPage cp;
    private PlayerSheetPage psp;
    private RegionMapPage rmp;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("Space Trader");
        player = new Player();
        //All scenes initialized.
        wp = new WelcomePage();
        cp = new ConfigPage();

        //set first scene to WelcomePage.
        stage.setScene(wp.getMainScene());

        //Switch to player config.
        sceneSwitchToCP();

        //Switch to player sheet Page.
        sceneSwitchToPSP(cp.getEasySubScene());
        sceneSwitchToPSP(cp.getMedSubScene());
        sceneSwitchToPSP(cp.getHardSubScene());

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
                stage.setScene(cp.getMainScene());
            }
        });
    }

    private void sceneSwitchToPSP(ConfigSkillSubscene subscene) {

        subscene.getBtnConfirm().setOnMouseClicked(e -> {
            if (subscene.getNameValue().getText().trim().isEmpty()) {
                //subscene.nameValue.setStyle("-fx-border-color: red");
                Alert a = new Alert(Alert.AlertType.NONE,
                        "Please enter your name", ButtonType.OK);

                a.show();

            } else {
                player = new Player();
                player.setCredits(subscene.getCredits());
                player.setSkillPoints(subscene.getSkillPoints());
                player.setSkills(subscene.getSkills());
                player.setName(subscene.getNameValue().getText());
                psp = new PlayerSheetPage();
                stage.setScene(psp.getMainScene());
                sceneSwitchToRMP();
            }
        });
    }

    private void sceneSwitchToRMP() {
        psp.getBtnNextPage().setOnMouseClicked(e -> {
                rmp = new RegionMapPage();
                stage.setScene(rmp.getMainScene());
        });
    }

    public static Player getPlayer() {
        return player;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
