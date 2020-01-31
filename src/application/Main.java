package application;

import component.Player;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import materials.ConfigSkillSubscene;
import sceneDesign.ConfigPage;
import sceneDesign.PlayerSheetPage;
import sceneDesign.WelcomePage;

public class Main extends Application {

    public static Player player;

    private WelcomePage wp;
    private ConfigPage cp;
    private PlayerSheetPage psp;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        stage.setTitle("Space Trader");
        player = new Player();
        //All scenes initialized.
        wp = new WelcomePage();
        cp = new ConfigPage();

        //set first scene to WelcomePage.
        stage.setScene(wp.getMainScene());

        //Switch to player config.

           //--------------------- TO BE IMPROVED -------------------//
          //---------- NEED TO BE INSIDE WelcomePage.java ----------//
         //---- canvas, play variable should not be accessible ----//
        //----- READ COMMENTS AT LINE 18 IN WelcomePage.java -----//
        wp.canvas.setOnMouseClicked(e -> {
            double x = e.getX();
            double y = e.getY();
            if ((x < wp.canvas.getWidth() / 2 + wp.play.getWidth() / 2 && x > wp.canvas.getWidth() / 2 - wp.play.getWidth() / 2) &&
                    (y < wp.canvas.getHeight() / 2 + wp.play.getHeight()  / 2 && y > wp.canvas.getHeight()  / 2 - wp.play.getHeight()  / 2)) {
                stage.setScene(cp.getMainScene());
            }
        });

        sceneSwitchToPSP(cp.easySubScene);
        sceneSwitchToPSP(cp.medSubScene);
        sceneSwitchToPSP(cp.hardSubScene);

        stage.show();
    }

    private void sceneSwitchToPSP(ConfigSkillSubscene subscene) {
        subscene.btnConfirm.setOnMouseClicked(e -> {
            if (subscene.nameValue.getText().trim().isEmpty()) {
                subscene.nameValue.setStyle("-fx-border-color: red");
            } else {
                player = new Player();
                player.setCredits( subscene.getCredits());
                player.setSkillPoints(subscene.getSkillPoints());
                player.setSkills(subscene.getSkills());
                player.setName(subscene.nameValue.getText());
                System.out.println("Created");
                psp = new PlayerSheetPage();
                stage.setScene(psp.getMainScene());
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
