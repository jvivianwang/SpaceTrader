package materials;

import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

import javafx.scene.SubScene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

//Commented out unused imports for now.
//import javafx.scene.Parent;
//import javafx.scene.input.MouseButton;

public class ConfigSkillSubscene extends SubScene {

    private static final String FONT_PATH = "src/materials/font/SEASRN__.ttf";
    private static final String BACKGROUND_IMAGE = "materials/image/configSubsceneBG.png";
    private static final String BROOM_PATH = "materials/image/broom.png";

    private boolean isHidden;
    private int[] skills;
    private int skillPoints;
    private int credits;
    private Label skillPointsStat;
    private Label creditsStat;
    private String name;
    private String difficulty;
    private TextField nameValue;
    private YellowButton btnConfirm;
    private CheckBox broomCheckBox;
    private boolean selected;

    public ConfigSkillSubscene(String difficulty) {
        super(new AnchorPane(), 1200, 600);
        prefHeight(1200);
        prefWidth(600);

        AnchorPane root2 = (AnchorPane) this.getRoot();

        setBackgroundImage(root2);
        enterName(root2);
        selectShip(root2);

        isHidden = true;

        this.difficulty = difficulty;
        difficultyMode(difficulty);
        //Create layout for skill bar and buttons.
        createSkillAllocate(root2);

        setLayoutX(200);
        setLayoutY(-700);
    }

    private void setBackgroundImage(AnchorPane root) {
        Image backgroundImage = new Image(BACKGROUND_IMAGE,
                1200,
                600,
                false,
                true);
        BackgroundImage image = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                null);
        root.setBackground(new Background(image));
    }

    private void difficultyMode(String difficulty) {
        if (difficulty.equals("Easy")) {
            credits = 100000;
            skillPoints = 16;
        } else if (difficulty.equals("Medium")) {
            credits = 500;
            skillPoints = 12;
        } else {
            credits = 100;
            skillPoints = 8;
        }
        skills = new int[]{0, 0, 0, 0};
        creditsStat = displayInfo("Credits ", credits, 775, 100);
        creditsStat.setStyle("-fx-font-weight: bold");
        creditsStat.setStyle("-fx-border-color: SADDLEBROWN ; -fx-background-color:"
                + "BURLYWOOD; -fx-border-width: 2px");
        skillPointsStat = displayInfo("Skill Points ", skillPoints, 775, 165);
        skillPointsStat.setStyle("-fx-font-weight: bold");
        skillPointsStat.setStyle("-fx-border-color: SADDLEBROWN ; -fx-background-color: "
                + "BURLYWOOD; -fx-border-width: 2px");
        this.getPane().getChildren().addAll(creditsStat, skillPointsStat);
    }

    //Create layout for skill bar and buttons.
    private void createSkillAllocate(AnchorPane root) {
        InfoLabel pilotLabel = createInfoLabel("Pilot", 0);
        InfoLabel fighterLabel = createInfoLabel("Fighter", 1);
        InfoLabel merchantLabel = createInfoLabel("Merchant", 2);
        InfoLabel engineerLabel = createInfoLabel("Engineer", 3);

        SkillBar pilotBar = createSkillBar(SkillImage.PILOT, 0);
        SkillBar fighterBar = createSkillBar(SkillImage.FIGHTER, 1);
        SkillBar merchantBar = createSkillBar(SkillImage.MERCHANT, 2);
        SkillBar engineerBar = createSkillBar(SkillImage.ENGINEER, 3);

        YellowButton btnReset = new YellowButton("Reset");
        btnReset.setLayoutX(800);
        btnReset.setLayoutY(500);
        btnConfirm = new YellowButton("Confirm");
        btnConfirm.setLayoutX(1000);
        btnConfirm.setLayoutY(500);

        root.getChildren().addAll(pilotLabel,
                fighterLabel,
                merchantLabel,
                engineerLabel,
                btnReset,
                btnConfirm,
                pilotBar,
                fighterBar,
                merchantBar,
                engineerBar);

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

    private SkillBar createSkillBar(SkillImage skillImageType, int skillIndex) {
        SkillBar temp = new SkillBar(skillImageType);
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
                if (skillPoints > 0) {
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
        broomCheckBox.setSelected(false);
        difficultyMode(difficulty);
    }

    //The skill scene will move down once you select level.
    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.5));
        transition.setNode(this);

        if (isHidden) {
            //setToY(y) is move down y units instead move to y coordinate.
            transition.setToY(750);
            isHidden = false;
        } else {
            transition.setToY(-750);
            isHidden = true;
        }

        transition.play();
    }

    // player enter name here
    public void enterName(AnchorPane anchorPane) {
        Label nameLabel = new Label("Name");
        nameLabel.setStyle("-fx-font-weight: bold");
        nameLabel.setStyle("-fx-border-color: SADDLEBROWN ; -fx-background-color: "
                + "BURLYWOOD; -fx-border-width: 2px");
        try {
            nameLabel.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            nameLabel.setFont(Font.font("Verdana", 23));
        }
        //nameLabel.setStyle("-fx-background-color: blue");
        TextField inputName = new TextField();
        inputName.setPrefWidth(250);
        inputName.setPrefHeight(30);
        inputName.setLayoutX(875);
        inputName.setLayoutY(54.5);
        nameLabel.setLayoutX(775);
        nameLabel.setLayoutY(53);
        anchorPane.getChildren().add(nameLabel);
        anchorPane.getChildren().add(inputName);
        setName(inputName.getText());
        nameValue = inputName;

    }

    //player select broom
    public void selectShip(AnchorPane anchorPane) {
        Label shipLabel = new Label("Select broom");
        shipLabel.setStyle("-fx-font-weight: bold");
        shipLabel.setStyle("-fx-border-color: SADDLEBROWN ; -fx-background-color:"
                + " BURLYWOOD; -fx-border-width: 2px");
        try {
            shipLabel.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            shipLabel.setFont(Font.font("Verdana", 23));
        }
        //shipLabel.setStyle("-fx-background-color: blue");
        broomCheckBox = new CheckBox();
        //checkBox.setStyle("-fx-border-color: blue");
        Image broom = new Image(BROOM_PATH);
        ImageView broomView = new ImageView(broom);
        shipLabel.setLayoutX(775);
        shipLabel.setLayoutY(250);
        broomCheckBox.setLayoutX(950);
        broomCheckBox.setLayoutY(300);
        broomView.setLayoutX(890);
        broomView.setLayoutY(300);
        broomView.setFitHeight(200);
        broomView.setFitWidth(200);
        anchorPane.getChildren().add(shipLabel);
        anchorPane.getChildren().add(broomCheckBox);
        anchorPane.getChildren().add(broomView);
        setSelected(broomCheckBox.isSelected());
        if (broomCheckBox.isSelected()) {
            setSelected(true);
        } else {
            setSelected(false);
        }
    }

    public Label displayInfo(String name, int info, double x, double y) {
        Label temp = new Label(name + " " + info);
        try {
            temp.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            temp.setFont(Font.font("Verdana", 23));
        }
        temp.setAlignment(Pos.CENTER);
        //temp.setStyle("-fx-background-color: blue");
        temp.setPrefWidth(325);
        temp.setPrefHeight(50);
        temp.setLayoutX(x);
        temp.setLayoutY(y);
        return temp;
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


    public void setName(String name) {
        this.name = name;
    }

    public TextField getNameValue() {
        return nameValue;
    }

    public YellowButton getBtnConfirm() {
        return btnConfirm;
    }

    public void setBtnConfirm(YellowButton btnConfirm) {
        this.btnConfirm = btnConfirm;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public CheckBox getBroomCheckBox() {
        return this.broomCheckBox;
    }
}
