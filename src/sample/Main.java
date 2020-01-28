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


//Last updated: 1/27/20
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

        Image earth = new Image("earth.png", 150, 150, false, false);
        Image spaceShip = new Image("spaceship.png", 100, 100, false, false);
        Image space = new Image("space.png");

        canvas.setOnMouseMoved(e -> {
            double x = 512 - e.getX();
            double y = 512 - e.getY();
            gc.drawImage(space, 0, 0);
            gc.drawImage(earth, 196, 196);
            gc.drawImage(spaceShip, x, y);
        });

        //Switch to player config.
        canvas.setOnMouseClicked(e -> {
            double x = e.getX();
            double y = e.getY();
            if ((x < canvas.getWidth() / 2 + earth.getWidth() / 2 && x > canvas.getWidth() / 2 - earth.getWidth() / 2) && (y < canvas.getHeight() / 2 + earth.getHeight()  / 2 && y > canvas.getHeight()  / 2 - earth.getHeight()  / 2)) {
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
