package materials;

import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
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
    private Label skillPointsStat;
    private Label creditsStat;
    private String name;
    private String difficulty;
    public TextField nameValue;

    public YellowButton btnConfirm;

    public ConfigSkillSubscene(String difficulty) {
        super(new AnchorPane(), 1200, 600);
        prefHeight(1200);
        prefWidth(600);

        Image backgroundImage = new Image(BACKGROUND_IMAGE, 1200, 600, false, true);
        BackgroundImage image = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();

        root2.setBackground(new Background(image));

        enterName(root2);

        isHidden = true;

        this.difficulty = difficulty;
        difficultyMode(difficulty);
        //Create layout for skill bar and buttons.
        createSkillAllocate(root2);

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
        skills = new int[]{ 0, 0, 0, 0 };
        creditsStat = displayInfo("Credits", credits, 800, 200);
        skillPointsStat = displayInfo("Skill Points", skillPoints, 800, 300);
        this.getPane().getChildren().addAll(creditsStat, skillPointsStat);
    }

    //Create layout for skill bar and buttons.
    private void createSkillAllocate(AnchorPane root) {
        InfoLabel pilotLabel = createInfoLabel("Pilot", 0);
        InfoLabel fighterLabel = createInfoLabel("Fighter", 1);
        InfoLabel merchantLabel = createInfoLabel("Merchant", 2);
        InfoLabel engineerLabel = createInfoLabel("Engineer", 3);

        SkillBar pilotBar = createSkillBar(SKILL.PILOT, 0);
        SkillBar fighterBar = createSkillBar(SKILL.FIGHTER, 1);
        SkillBar merchantBar = createSkillBar(SKILL.MERCHANT, 2);
        SkillBar engineerBar = createSkillBar(SKILL.ENGINEER, 3);

        YellowButton btnReset = new YellowButton("Reset");
        btnReset.setLayoutX(800);
        btnReset.setLayoutY(500);
        btnConfirm = new YellowButton("Confirm");
        btnConfirm.setLayoutX(1000);
        btnConfirm.setLayoutY(500);

        this.getPane().getChildren().addAll(pilotLabel, fighterLabel, merchantLabel, engineerLabel, btnReset,
                btnConfirm, pilotBar, fighterBar, merchantBar, engineerBar);

        allocatingSkill(pilotLabel, pilotBar, 0);
        allocatingSkill(fighterLabel, fighterBar, 1);
        allocatingSkill(merchantLabel, merchantBar, 2);
        allocatingSkill(engineerLabel, engineerBar, 3);

        btnReset.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                reset(pilotLabel, pilotBar);
                reset(fighterLabel, fighterBar);
                reset(merchantLabel, merchantBar);
                reset(engineerLabel, engineerBar);
            }
        });
    }

    private SkillBar createSkillBar(SKILL skillType, int skillIndex) {
        SkillBar temp = new SkillBar(skillType);
        temp.setLayoutX(100 + skillIndex * 175);
        temp.setLayoutY(375);
        return temp;
    }

    private InfoLabel createInfoLabel(String skillName, int skillIndex) {
        InfoLabel temp = new InfoLabel(skillName);
        temp.setLayoutX(75 + skillIndex * 175);
        temp.setLayoutY(400);
        return temp;
    }

    private void allocatingSkill(InfoLabel button, SkillBar bar, int skillIndex) {
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(skillPoints > 0) {
                    skills[skillIndex]++;
                    skillPoints--;
                    skillPointsStat.setText("Skill Points: " + skillPoints);
                    bar.setLayoutY(bar.getLayoutY() - 10);
                    bar.setPrefHeight(bar.getHeight() + 10);
                    bar.setText(skills[skillIndex] + "");
                } else {
                    button.setHasSkillPoints(false);
                }
            }
        });
    }

    private void reset(InfoLabel button, SkillBar bar) {
        bar.setLayoutY(375);
        bar.setPrefHeight(0);
        bar.setText(0 + "");
        button.setHasSkillPoints(true);
        setName("");
        nameValue.clear();
        difficultyMode(difficulty);
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

    public int getSkillPoints() {
        return skillPoints;
    }

    public int getCredits() {
        return credits;
    }

    public int[] getSkills() {
        return skills;
    }

    // player enter name here
    // todo: make sure to check VALIDATION when submit
    public void enterName(AnchorPane anchorPane){
        Label nameLabel = new Label("Name");
        nameLabel.setFont(new Font(20));
        nameLabel.setStyle("-fx-background-color: blue");
        TextField inputName = new TextField();
        inputName.setPrefWidth(250);
        inputName.setPrefHeight(30);
        inputName.setLayoutX(875);
        inputName.setLayoutY(100);
        nameLabel.setLayoutX(800);
        nameLabel.setLayoutY(103);
        anchorPane.getChildren().add(nameLabel);
        anchorPane.getChildren().add(inputName);
        setName(inputName.getText());
        nameValue = inputName;
    }

    public Label displayInfo(String name, int info, double x, double y) {
        Label temp = new Label(name+ " " + info);
        temp.setFont(new Font(23));
        temp.setAlignment(Pos.CENTER);
        temp.setStyle("-fx-background-color: blue");
        temp.setPrefWidth(325);
        temp.setPrefHeight(100);
        temp.setLayoutX(x);
        temp.setLayoutY(y);
        return temp;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
