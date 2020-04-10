package materials;

import application.Main;
import component.Broom;
import component.Player;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;
import scene.PlayerSheetPage;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

//Commented out unused imports for now.
//import javafx.scene.Parent;
//import javafx.scene.input.MouseButton;

public class ConfigSkillSubscene extends SubScene {

    private static DimensionsHandler dim = new DimensionsHandler();
    private static final int HEIGHT = dim.getHeight();
    private static final int WIDTH = dim.getWidth();
    private static final int FONTSIZE = dim.getFontsize();

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
        super(new AnchorPane(), dim.customWidth(1200), dim.customHeight(600));
        prefHeight(dim.customHeight(600));
        prefWidth(dim.customWidth(1200));

        AnchorPane root2 = (AnchorPane) this.getRoot();

        setBackgroundImage(root2);
        enterName(root2);
        selectShip(root2);

        isHidden = true;

        this.difficulty = difficulty;
        difficultyMode(difficulty);
        //Create layout for skill bar and buttons.
        createSkillAllocate(root2);

        setLayoutX(dim.customWidth((200)));
        setLayoutY(dim.customHeight((-700)));
    }

    private void setBackgroundImage(AnchorPane root) {
        Image backgroundImage = new Image(BACKGROUND_IMAGE,
                dim.customWidth(1200),
                dim.customHeight(600),
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
        creditsStat = displayInfo("Credits ", credits,
                dim.customWidth(775), dim.customHeight(100));
        creditsStat.setStyle("-fx-font-weight: bold");
        creditsStat.setStyle("-fx-border-color: SADDLEBROWN ; -fx-background-color:"
                + "BURLYWOOD; -fx-border-width: 2px");
        skillPointsStat = displayInfo("Skill Points ", skillPoints,
                dim.customWidth(775), dim.customHeight(165));
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
        btnReset.setLayoutX(dim.customWidth((800)));
        btnReset.setLayoutY(dim.customHeight((500)));
        btnConfirm = new YellowButton("Confirm");
        btnConfirm.setLayoutX(dim.customWidth((1000)));
        btnConfirm.setLayoutY(dim.customHeight((500)));
        btnConfirm.setOnMouseClicked(e -> {
            if (nameValue.getText().trim().isEmpty()
                    && !broomCheckBox.isSelected()) {
                //subscene.nameValue.setStyle("-fx-border-color: red");
                Alert a = new Alert(Alert.AlertType.NONE,
                        "Please enter your name and select your broom", ButtonType.OK);

                a.show();

            } else if (nameValue.getText().trim().isEmpty()) {
                //subscene.nameValue.setStyle("-fx-border-color: red");
                Alert a = new Alert(Alert.AlertType.NONE,
                        "Please enter your name", ButtonType.OK);

                a.show();

            } else if (!broomCheckBox.isSelected()) {
                //subscene.nameValue.setStyle("-fx-border-color: red");
                Alert a = new Alert(Alert.AlertType.NONE,
                        "Please select your broom", ButtonType.OK);

                a.show();

            } else {
                Player.getInstance().reset();
                Player.getInstance().setDifficulty(difficulty);
                Player.getInstance().setCredits(credits);
                Player.getInstance().setSkillPoints(skillPoints);
                Player.getInstance().setSkills(skills);
                Player.getInstance().setName(nameValue.getText());
                //create broom
                Broom.getInstance().reset();
                if (Player.getInstance().getDifficulty().equalsIgnoreCase("easy")) {
                    Broom.getInstance().setHealth(2000);
                } else if (Player.getInstance().getDifficulty().equalsIgnoreCase("medium")) {
                    Broom.getInstance().setHealth(1000);
                } else if (Player.getInstance().getDifficulty().equalsIgnoreCase("hard")) {
                    Broom.getInstance().setHealth(500);
                }
                PlayerSheetPage.getInstance().reset();
                Main.setScene(PlayerSheetPage.getInstance().getMainScene());
            }
        });

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
        temp.setLayoutX(dim.customWidth(100 + skillIndex * 175));
        temp.setLayoutY(dim.customHeight((375)));
        return temp;
    }

    private InfoLabel createInfoLabel(String skillName, int skillIndex) {
        InfoLabel temp = new InfoLabel(skillName);
        temp.setLayoutX(dim.customWidth(75 + skillIndex * 175));
        temp.setLayoutY(dim.customHeight((400)));
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
                    bar.setLayoutY(bar.getLayoutY() - dim.customHeight(10));
                    bar.setPrefHeight(bar.getHeight() + dim.customHeight(10));
                    bar.setText(skills[skillIndex] + "");
                } else {
                    button.setHasSkillPoints(false);
                }
            }
        });
    }

    private void reset(InfoLabel button, SkillBar bar) {
        bar.setLayoutY(dim.customHeight((375)));
        bar.setPrefHeight(dim.customHeight((0)));
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
            transition.setToY(dim.customHeight(750));
            isHidden = false;
        } else {
            transition.setToY(-dim.customHeight(750));
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
            nameLabel.setFont(Font.loadFont(new FileInputStream(FONT_PATH), FONTSIZE));
        } catch (FileNotFoundException e) {
            nameLabel.setFont(Font.font("Verdana", FONTSIZE));
        }
        //nameLabel.setStyle("-fx-background-color: blue");
        TextField inputName = new TextField();
        inputName.setPrefWidth(dim.customWidth((250)));
        inputName.setPrefHeight(dim.customHeight((30)));
        inputName.setLayoutX(dim.customWidth((875)));
        inputName.setLayoutY(dim.customHeight((54.5)));
        nameLabel.setLayoutX(dim.customWidth((775)));
        nameLabel.setLayoutY(dim.customHeight((53)));
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
            shipLabel.setFont(Font.loadFont(new FileInputStream(FONT_PATH), FONTSIZE));
        } catch (FileNotFoundException e) {
            shipLabel.setFont(Font.font("Verdana", FONTSIZE));
        }
        //shipLabel.setStyle("-fx-background-color: blue");
        broomCheckBox = new CheckBox();
        //checkBox.setStyle("-fx-border-color: blue");
        Image broom = new Image(BROOM_PATH);
        ImageView broomView = new ImageView(broom);
        shipLabel.setLayoutX(dim.customWidth((775)));
        shipLabel.setLayoutY(dim.customHeight((250)));
        broomCheckBox.setLayoutX(dim.customWidth((950)));
        broomCheckBox.setLayoutY(dim.customHeight((300)));
        broomView.setLayoutX(dim.customWidth((890)));
        broomView.setLayoutY(dim.customHeight((300)));
        broomView.setFitHeight(dim.customHeight((200)));
        broomView.setFitWidth(dim.customWidth((200)));
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
            temp.setFont(Font.loadFont(new FileInputStream(FONT_PATH), FONTSIZE));
        } catch (FileNotFoundException e) {
            temp.setFont(Font.font("Verdana", FONTSIZE));
        }
        temp.setAlignment(Pos.CENTER);
        //temp.setStyle("-fx-background-color: blue");
        temp.setPrefWidth(dim.customWidth((325)));
        temp.setPrefHeight(dim.customHeight((50)));
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

    public String getDifficulty() {
        return this.difficulty;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public CheckBox getBroomCheckBox() {
        return this.broomCheckBox;
    }
}
