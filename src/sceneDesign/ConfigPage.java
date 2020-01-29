package sceneDesign;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class ConfigPage {

    private static final int HEIGHT = 900;
    private static final int WIDTH = 1600;
    private AnchorPane mainPane;
    private Scene mainScene;

    public ConfigPage() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
    }

    public Scene getMainScene() {
        return mainScene;
    }
}
