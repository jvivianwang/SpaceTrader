package scene;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import materials.DimensionsHandler;

public class WelcomePage {

    private static DimensionsHandler dim = new DimensionsHandler();
    private static final int HEIGHT = dim.getHeight();
    private static final int WIDTH = dim.getWidth();
    private static final int FONTSIZE = dim.getFontsize();

    private AnchorPane mainPane;
    private Scene mainScene;

    //This two should not be outside of the createCanvas().
    //Line 33 should be Canvas canvas = new Canvas(WIDTH, HEIGHT);
    //Line 38 should start with Image play = ...
    private Canvas canvas;
    private Image play;

    public WelcomePage() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        createCanvas();
    }

    public Scene getMainScene() {
        return mainScene;
    }

    private void createCanvas() {
        canvas = new Canvas(WIDTH, HEIGHT);
        mainPane.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        play = new Image("materials/image/playButton.png",
                HEIGHT / 3,
                HEIGHT / 3,
                false,
                false);
        Image magic = new Image("materials/image/sparkle.gif",
                WIDTH / 8,
                WIDTH / 8,
                true,
                false);
        //background dimensions: 1600 x 900 px
        Image background = new Image("materials/image/testBackground.jpg",
                WIDTH, HEIGHT,
                true,
                true);
        Image title = new Image("materials/image/WizardsOfTheCaribbeanTitle.png",
                WIDTH / 2,
                WIDTH / 8,
                true,
                false);
        int buttonDimX = dim.customWidth(600);
        int buttonDimY = dim.customHeight(250);
        gc.drawImage(background, 0, 0);
        gc.drawImage(play, buttonDimX, buttonDimY);
        gc.drawImage(title, 0, 0);

        canvas.setOnMouseMoved(e -> {
            double x = dim.customWidth(1400) - e.getX();
            double y = dim.customHeight(700) - e.getY();

            gc.drawImage(background, 0, 0);
            gc.drawImage(play, buttonDimX, buttonDimY);
            gc.drawImage(title, 0, 0);
            gc.drawImage(magic, x, y);
        });
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Image getPlay() {
        return play;
    }

}
