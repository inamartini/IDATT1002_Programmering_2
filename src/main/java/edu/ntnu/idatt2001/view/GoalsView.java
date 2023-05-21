package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.model.Game;
import edu.ntnu.idatt2001.controller.GameViewController;
import edu.ntnu.idatt2001.controller.GoalsViewController;
import edu.ntnu.idatt2001.controller.PlayerViewController;
import edu.ntnu.idatt2001.controller.ScreenController;
import edu.ntnu.idatt2001.util.AlertUtil;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.Objects;

public class GoalsView extends View {

  private ScreenController screenController;
  private StackPane root;
  private BorderPane borderPane;
  private GoalsViewController goalsViewController = GoalsViewController.getInstance();
  private GameViewController gameViewController = GameViewController.getInstance();
  private PlayerViewController playerViewController = PlayerViewController.getInstance();
  private String goalValueString = null;
  private ToggleGroup difficultyToggleGroup;
  private ToggleButton[] goldGoalButtons;
  private ToggleButton[] healthGoalButtons;
  private ToggleButton[] scoreGoalButtons;
  private ToggleButton[] inventoryGoalButtons;

  public GoalsView(ScreenController screenController) {
    this.screenController = screenController;
    this.borderPane = new BorderPane();
    this.root = new StackPane();
    borderPane.setCenter(root);
  }

  public Pane getPane() {
    return this.borderPane;
  }

  public void setUp() {
    this.resetPane();

    Text title = new Text("Choose Goals");
    title.getStyleClass().add("goalsView-title");

    HBox titleBox = new HBox(title);
    titleBox.setAlignment(Pos.CENTER);

    Image backIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/backbutton.png")));
    ImageView backView = new ImageView(backIcon);
    backView.setFitHeight(50);
    backView.setFitWidth(50);
    Button btnBack = new Button();
    btnBack.setGraphic(backView);
    btnBack.getStyleClass().add("goalsView-backButton");
    btnBack.setAlignment(Pos.TOP_LEFT);
    btnBack.setOnAction(e -> {
      screenController.activate("playerView");
    });

    VBox backBox = new VBox();
    backBox.setAlignment(Pos.TOP_LEFT);
    backBox.getChildren().add(btnBack);

    borderPane.setTop(backBox);
    borderPane.getStyleClass().add("view-background");

    HBox goldGoalBox = setupGoal("Gold Goal:      ", "goldGoalButton", "25", "50", "100");
    HBox healthGoalBox = setupGoal("Health Goal:    ", "healthGoalButton", "1", "3", "5");
    HBox scoreGoalBox = setupGoal("Score Goal:     ", "scoreGoalButton", "100", "200", "500");
    HBox inventoryGoalBox = setupGoal("Inventory Goal:", "inventoryGoalButton", "Sword", "Shield", "Potion");

    // Add difficulty toggle buttons and assign them to the difficultyToggleGroup
    ToggleButton btnEasy = new ToggleButton("Easy");
    btnEasy.getStyleClass().add("difficultyButton");

    ToggleButton btnNormal = new ToggleButton("Normal");
    btnNormal.getStyleClass().add("difficultyButton");

    ToggleButton btnHard = new ToggleButton("Hard");
    btnHard.getStyleClass().add("difficultyButton");

    difficultyToggleGroup = new ToggleGroup();
    btnEasy.setToggleGroup(difficultyToggleGroup);
    btnNormal.setToggleGroup(difficultyToggleGroup);
    btnHard.setToggleGroup(difficultyToggleGroup);

    // Add event handlers for difficulty buttons
    btnEasy.setOnAction(e -> setSelectedGoalTogglesByDifficulty(goldGoalButtons, healthGoalButtons, scoreGoalButtons, inventoryGoalButtons));
    btnNormal.setOnAction(e -> setSelectedGoalTogglesByDifficulty(goldGoalButtons, healthGoalButtons, scoreGoalButtons, inventoryGoalButtons));
    btnHard.setOnAction(e -> setSelectedGoalTogglesByDifficulty(goldGoalButtons, healthGoalButtons, scoreGoalButtons, inventoryGoalButtons));

    HBox difficultyBox = new HBox(10, btnEasy, btnNormal, btnHard);
    difficultyBox.setAlignment(Pos.CENTER);

    VBox goalsBox = new VBox(10, titleBox, difficultyBox, goldGoalBox, healthGoalBox, scoreGoalBox, inventoryGoalBox);
    goalsBox.setAlignment(Pos.CENTER);

    Button btnPlayWithoutGoals = new Button("Play Without Goals");
    btnPlayWithoutGoals.getStyleClass().add("goalsView-playButton");

    btnPlayWithoutGoals.setOnAction(e -> {
      gameViewController.setGame(new Game(playerViewController.getPlayer(), gameViewController.getStory(), goalsViewController.getGoals()));
      screenController.activate("gameView");
    });

    Button btnSaveGoals = new Button("Save Goals");
    btnSaveGoals.getStyleClass().add("goalsView-saveButton");

    btnSaveGoals.setOnAction(e -> {
      Toggle goldToggle = goldGoalButtons[0].getToggleGroup().getSelectedToggle();
      Toggle healthToggle = healthGoalButtons[0].getToggleGroup().getSelectedToggle();
      Toggle scoreToggle = scoreGoalButtons[0].getToggleGroup().getSelectedToggle();
      Toggle inventoryToggle = inventoryGoalButtons[0].getToggleGroup().getSelectedToggle();

      goalsViewController.saveGoals(goldToggle, scoreToggle, healthToggle, inventoryToggle);
      try {
        gameViewController.setGame(new Game(playerViewController.getPlayer(), gameViewController.getStory(), goalsViewController.getGoals()));
        screenController.activate("gameView");
      } catch (Exception ex) {
        AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
      }
    });

    Button btnCreateCustomGoals = new Button("Create Custom Goals");
    btnCreateCustomGoals.getStyleClass().add("goalsView-customButton");

    btnCreateCustomGoals.setOnAction(e -> {
        Text customTitle = new Text("Customize your goals");
        customTitle.getStyleClass().add("goalsView-title");

        Text customInventoryText = new Text("Inventory Goal:");
        customInventoryText.getStyleClass().add("goalsView-customText");

        TextField customInventoryGoal = new TextField();
        customInventoryGoal.setPromptText("Enter custom inventory goal");
        customInventoryGoal.setPrefSize(50, 20);

        Text customScoreText = new Text("Score Goal:");
        customScoreText.getStyleClass().add("goalsView-customText");

        TextField customScoreGoal = new TextField();
        customScoreGoal.setPromptText("Enter custom score goal");
        customScoreGoal.setPrefSize(50, 20);

        Text customHealthText = new Text("Health Goal:");
        customHealthText.getStyleClass().add("goalsView-customText");

        TextField customHealthGoal = new TextField();
        customHealthGoal.setPromptText("Enter custom health goal");
        customHealthGoal.setPrefSize(50, 20);

        Text customGoldText = new Text("Gold Goal:");
        customGoldText.getStyleClass().add("goalsView-customText");

        TextField customGoldGoal = new TextField();
        customGoldGoal.setPromptText("Enter custom gold goal");
        customGoldGoal.setPrefSize(50, 20);

        Button btnCreateCustomGoals2 = new Button("Create Custom Goals");
        btnCreateCustomGoals2.getStyleClass().add("goalsView-customButton");

      btnCreateCustomGoals2.setOnAction(e2 -> {
        String scoreGoal = customScoreGoal.getText();
        String healthGoal = customHealthGoal.getText();
        String goldGoal = customGoldGoal.getText();

        try {
          if (!scoreGoal.isEmpty() && !isInteger(scoreGoal)) throw new NumberFormatException("Score goal must be an integer");
          if (!healthGoal.isEmpty() && !isInteger(healthGoal)) throw new NumberFormatException("Health goal must be an integer");
          if (!goldGoal.isEmpty() && !isInteger(goldGoal)) throw new NumberFormatException("Gold goal must be an integer");

          goalsViewController.saveCustomGoals(
                  goldGoal.isEmpty() ? null : goldGoal,
                  scoreGoal.isEmpty() ? null : scoreGoal,
                  healthGoal.isEmpty() ? null : healthGoal,
                  customInventoryGoal.getText().isEmpty() ? null : customInventoryGoal.getText()
          );
          gameViewController.setGame(new Game(playerViewController.getPlayer(), gameViewController.getStory(), goalsViewController.getGoals()));
          screenController.activate("gameView");
        } catch (NumberFormatException ex) {
          AlertUtil.showAlert(Alert.AlertType.ERROR, "Invalid Input", ex.getMessage());
        } catch (Exception ex) {
          AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
        }
      });

      VBox customGoalsBox = new VBox(10, customTitle, customInventoryText, customInventoryGoal, customScoreText,
                customScoreGoal, customHealthText, customHealthGoal, customGoldText,customGoldGoal, btnCreateCustomGoals2);
        customGoalsBox.setMaxSize(100, 100);
        customGoalsBox.setAlignment(Pos.CENTER);
        root.getChildren().addAll(customGoalsBox);
        goalsBox.setVisible(false);
        btnCreateCustomGoals.setVisible(false);
        btnPlayWithoutGoals.setVisible(false);
        btnSaveGoals.setVisible(false);
        btnBack.setOnAction(e3 -> {
          this.setUp();
        });
    });

    VBox buttonBox = new VBox(10, btnSaveGoals, btnCreateCustomGoals, btnPlayWithoutGoals);
    buttonBox.setAlignment(Pos.CENTER);

    VBox centerBox = new VBox(10, goalsBox, buttonBox);
    centerBox.setAlignment(Pos.CENTER);

    root.getChildren().addAll(centerBox);
    borderPane.getStyleClass().add("view-background");
  }

  private void setSelectedGoalTogglesByDifficulty(ToggleButton[]... goalButtons) {
    int difficultyIndex = -1;

    ToggleButton selectedDifficultyButton = (ToggleButton) difficultyToggleGroup.getSelectedToggle();
    if (selectedDifficultyButton != null) {
      difficultyIndex = difficultyToggleGroup.getToggles().indexOf(selectedDifficultyButton);
    }

    if (difficultyIndex >= 0) {
      for (ToggleButton[] buttons : goalButtons) {
        ToggleButton toggleButton = buttons[difficultyIndex];
        if (toggleButton != null) {
          toggleButton.setSelected(true);
        }
      }
    }
  }

  private HBox setupGoal(String goalName, String buttonStyle, String... options) {
    Text goalText = new Text(goalName);
    goalText.getStyleClass().add("goalsView-goalText");

    Text goalValue = new Text(goalValueString);
    goalValue.getStyleClass().add("goalsView-goalValue");

    ToggleGroup goalGroup = new ToggleGroup();

    HBox buttonBox = new HBox(10);

    ToggleButton[] buttons = new ToggleButton[options.length];

    for (int i = 0; i < options.length; i++) {
      ToggleButton button = new ToggleButton(options[i]);
      button.setPrefSize(70, 20);
      button.setToggleGroup(goalGroup);
      button.setOnAction(e -> {
        if (button.isSelected()) {
          goalValueString = button.getText();
          button.getStyleClass().add("selectedButton");
          goalGroup.getToggles().forEach(toggle -> {
            ToggleButton toggleButton = (ToggleButton) toggle;
            if (toggleButton != button) {
              toggleButton.setSelected(false);
              toggleButton.getStyleClass().remove("selectedButton");
            }
          });
          button.getStyleClass().add("selectedButton");
        } else {
          goalValueString = null;
          button.getStyleClass().remove("selectedButton");
        }
      });
      button.getStyleClass().add(buttonStyle);
      buttonBox.getChildren().add(button);

      buttons[i] = button;
    }

    HBox goalBox = new HBox(10, goalText, buttonBox, goalValue);
    goalBox.setAlignment(Pos.CENTER);

    // Store the toggle buttons for each difficulty level
    switch (buttonStyle) {
      case "goldGoalButton" -> goldGoalButtons = buttons;
      case "healthGoalButton" -> healthGoalButtons = buttons;
      case "scoreGoalButton" -> scoreGoalButtons = buttons;
      case "inventoryGoalButton" -> inventoryGoalButtons = buttons;
    }

    return goalBox;
  }

  private boolean isInteger(String str) {
    if (str == null || str.isEmpty()) {
      return true;
    }
    try {
      Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public void resetPane() {
    this.root.getChildren().clear();
  }
}
