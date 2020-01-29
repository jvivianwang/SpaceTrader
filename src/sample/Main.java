package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Space Trader");

        //The welcome page.
        Group root = new Group();
        Scene welcomeScene = new Scene(root);
        primaryStage.setScene(welcomeScene);

        //Add canvas to the scene so we have the graphic.
        Canvas canvas = new Canvas( 512, 512 );
        root.getChildren().add(canvas);

        //Create graphic in the canvas.
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image play = new Image("playButton.png", 150, 150, false, false);
        Image magic = new Image("sparkle.gif", 100, 100, true, false);
        //space dimensions: 1150 x 864 px
        Image space = new Image("space.png", 1150, 864, true, true);
        Image title = new Image("WizardsOfTheCaribbeanTitle.png", 450, 95, true,false);

        canvas.setOnMouseMoved(e -> {
            double x = 512 - e.getX();
            double y = 512 - e.getY();

            gc.drawImage(space, 0, 0);
            gc.drawImage(play, 196, 196);
            gc.drawImage(magic, x, y);
            gc.drawImage(title, 0,0);
        });

        //Switch to player config.
        canvas.setOnMouseClicked(e -> {
            double x = e.getX();
            double y = e.getY();
            if ((x < canvas.getWidth() / 2 + play.getWidth() / 2 && x > canvas.getWidth() / 2 - play.getWidth() / 2) &&
                    (y < canvas.getHeight() / 2 + play.getHeight()  / 2 && y > canvas.getHeight()  / 2 - play.getHeight()  / 2)) {
                System.out.println("Start");
                //primaryStage.setScene(configScene);
            }
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
