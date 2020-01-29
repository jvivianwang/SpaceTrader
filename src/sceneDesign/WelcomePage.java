package sceneDesign;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class WelcomePage {

    private static final int HEIGHT = 900;
    private static final int WIDTH = 1600;
    private AnchorPane mainPane;
    private Scene mainScene;

    //This two should not be outside of the createCanvas().
    //Line 33 should be Canvas canvas = new Canvas(WIDTH, HEIGHT);
    //Line 38 should start with Image play = ...
    public Canvas canvas;
    public Image play;

    public WelcomePage() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        createCanvas();
    }

    public Scene getMainScene() {
        return mainScene;
    }

    private void createCanvas() {
        canvas = new Canvas( WIDTH, HEIGHT );
        mainPane.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        play = new Image("materials/playButton.png", HEIGHT / 3, HEIGHT / 3, false, false);
        Image magic = new Image("materials/sparkle.gif", WIDTH / 8, WIDTH / 8, true, false);
        //space dimensions: 1600 x 900 px
        Image space = new Image("materials/space.jpg", WIDTH, HEIGHT, true, true);
        Image title = new Image("materials/WizardsOfTheCaribbeanTitle.png", WIDTH / 2, WIDTH / 8, true,false);

        canvas.setOnMouseMoved(e -> {
            double x = 1400 - e.getX();
            double y = 700 - e.getY();

            gc.drawImage(space, 0, 0);
            gc.drawImage(play, 600, 250);
            gc.drawImage(magic, x, y);
            gc.drawImage(title, 0,0);
        });


    }
}
