package application;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sceneDesign.ConfigPage;
import sceneDesign.PlayerSheetPage;
import sceneDesign.WelcomePage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Space Trader");

        //All scenes initialized.
        WelcomePage wp = new WelcomePage();
        ConfigPage cp = new ConfigPage();
        PlayerSheetPage psp = new PlayerSheetPage();

        //set first scene to WelcomePage.
        primaryStage.setScene(wp.getMainScene());

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
                primaryStage.setScene(cp.getMainScene());
            }
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
