package materials;

import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class ConfigSkillSubscene extends SubScene {

    private final static String FONT_PATH = "src/materials/font/FFF_Tusj.ttf";
    private final static String BACKGROUND_IMAGE = "materials/image/configSubsceneBG.png";

    private final static String PILOT_BAR = "materials/image/YellowSkillBar.png";
    private final static String FIGHTER_BAR = "materials/image/BlackSkillBar.png";
    private final static String MERCHANT_BAR = "materials/image/PurpleSkillBar.png";
    private final static String ENGINEER_BAR = "materials/image/RedSkillBar.png";

    private boolean isHidden;
    private int[] skills;
    private int skillPoints;
    private int credits;

    public ConfigSkillSubscene(String difficulty) {
        super(new AnchorPane(), 1200, 600);
        prefHeight(1200);
        prefWidth(600);

        Image backgroundImage = new Image(BACKGROUND_IMAGE, 1200, 600, false, true);
        BackgroundImage image = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();

        root2.setBackground(new Background(image));

        isHidden = true;

        difficultyMode(difficulty);
        //Create layout for skill bar and buttons.
        createSkillAllocate();

        setLayoutX(200);
        setLayoutY(-700);
    }

    private void difficultyMode(String difficulty) {
        if(difficulty.equals("Easy")) {
            credits = 1000;
            skillPoints = 16;
        } else if(difficulty.equals("Medium")) {
            credits = 500;
            skillPoints = 12;
        } else {
            credits = 100;
            skillPoints = 8;
        }
        skills = new int[]{0, 0, 0, 0};
    }

    //Create layout for skill bar and buttons.
    private void createSkillAllocate() {
        InfoLabel pilotLabel = new InfoLabel("Pilot");
        pilotLabel.setLayoutX(75);
        pilotLabel.setLayoutY(400);
        InfoLabel fighterLabel = new InfoLabel("Fighter");
        fighterLabel.setLayoutX(250);
        fighterLabel.setLayoutY(400);
        InfoLabel merchantLabel = new InfoLabel("Merchant");
        merchantLabel.setLayoutX(425);
        merchantLabel.setLayoutY(400);
        InfoLabel engineerLabel = new InfoLabel("Engineer");
        engineerLabel.setLayoutX(600);
        engineerLabel.setLayoutY(400);
        YellowButton btnRest = new YellowButton("Reset");
        btnRest.setLayoutX(800);
        btnRest.setLayoutY(500);
        YellowButton btnConfirm = new YellowButton("Confirm");
        btnConfirm.setLayoutX(1000);
        btnConfirm.setLayoutY(500);

        SkillBar pilotBar = new SkillBar(SKILL.PILOT);
        pilotBar.setLayoutX(100);
        pilotBar.setLayoutY(375);
        SkillBar fighterBar = new SkillBar(SKILL.FIGHTER);
        fighterBar.setLayoutX(275);
        fighterBar.setLayoutY(375);
        SkillBar merchantBar = new SkillBar(SKILL.MERCHANT);
        merchantBar.setLayoutX(450);
        merchantBar.setLayoutY(375);
        SkillBar engineerBar = new SkillBar(SKILL.ENGINEER);
        engineerBar.setLayoutX(625);
        engineerBar.setLayoutY(375);

        this.getPane().getChildren().addAll(pilotLabel, fighterLabel, merchantLabel, engineerLabel, btnRest,
                btnConfirm, pilotBar, fighterBar, merchantBar, engineerBar);

        pilotLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                skills[0]++;
                skillPoints--;
                pilotBar.setLayoutY(pilotBar.getLayoutY() - 10);
                pilotBar.setPrefHeight(pilotBar.getHeight() + 10);
                pilotBar.setText(skills[0] + "");
            }
        });
        fighterLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                skills[1]++;
                skillPoints--;
                fighterBar.setLayoutY(fighterBar.getLayoutY() - 10);
                fighterBar.setPrefHeight(fighterBar.getHeight() + 10);
                fighterBar.setText(skills[1] + "");
            }
        });
        merchantLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                skills[2]++;
                skillPoints--;
                merchantBar.setLayoutY(merchantBar.getLayoutY() - 10);
                merchantBar.setPrefHeight(merchantBar.getHeight() + 10);
                merchantBar.setText(skills[2] + "");
            }
        });
        engineerLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                skills[3]++;
                skillPoints--;
                engineerBar.setLayoutY(engineerBar.getLayoutY() - 10);
                engineerBar.setPrefHeight(engineerBar.getHeight() + 10);
                engineerBar.setText(skills[3] + "");
            }
        });
        btnRest.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //TO BE IMPLEMENTED
            }
        });
    }

    //The skill scene will move down once you select level.
    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.5));
        transition.setNode(this);

        if(isHidden) {
            //setToY(y) is move down y units instead move to y coordinate.
            transition.setToY(750);
            isHidden = false;
        } else {
            transition.setToY(-750);
            isHidden = true;
        }

        transition.play();
    }

    public AnchorPane getPane() {
        return (AnchorPane) this.getRoot();
    }
}
