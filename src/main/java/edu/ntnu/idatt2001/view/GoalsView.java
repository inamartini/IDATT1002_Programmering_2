package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.model.Game;
import edu.ntnu.idatt2001.model.player.Player;
import edu.ntnu.idatt2001.controller.GameViewController;
import edu.ntnu.idatt2001.controller.GoalsViewController;
import edu.ntnu.idatt2001.controller.PlayerViewController;
import edu.ntnu.idatt2001.controller.ScreenController;
import edu.ntnu.idatt2001.util.AlertUtil;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class GoalsView extends View {

  private ScreenController screenController;
  private StackPane root;
  private BorderPane borderPane;
  private GoalsViewController goalsViewController = GoalsViewController.getInstance();
  private GameViewController gameViewController = GameViewController.getInstance();
  private PlayerViewController playerViewController = PlayerViewController.getInstance();
  private String goalValueString = null;
  private String goalName;
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
    title.getStyleClass().add("goalsAndStoryView-title");

    HBox titleBox = new HBox(title);
    titleBox.setAlignment(Pos.CENTER);

    HBox goldGoalBox = setupGoal("Gold Goal:      ", "goldGoalButton", "25", "50", "100");
    HBox healthGoalBox = setupGoal("Health Goal:    ", "healthGoalButton", "1", "3", "5");
    HBox scoreGoalBox = setupGoal("Score Goal:     ", "scoreGoalButton", "100", "200", "500");
    HBox inventoryGoalBox = setupGoal("Inventory Goal:", "inventoryGoalButton", "Sword", "Shield", "Potion");

    // Add difficulty toggle buttons and assign them to the difficultyToggleGroup
    ToggleButton btnEasy = new ToggleButton("Easy");
    ToggleButton btnNormal = new ToggleButton("Normal");
    ToggleButton btnHard = new ToggleButton("Hard");

    difficultyToggleGroup = new ToggleGroup();
    btnEasy.setToggleGroup(difficultyToggleGroup);
    btnNormal.setToggleGroup(difficultyToggleGroup);
    btnHard.setToggleGroup(difficultyToggleGroup);

    // Add event handlers for difficulty buttons
    btnEasy.setOnAction(e -> selectFirstGoalToggle(goldGoalButtons, healthGoalButtons, scoreGoalButtons, inventoryGoalButtons));
    btnNormal.setOnAction(e -> selectFirstGoalToggle(goldGoalButtons, healthGoalButtons, scoreGoalButtons, inventoryGoalButtons));
    btnHard.setOnAction(e -> selectFirstGoalToggle(goldGoalButtons, healthGoalButtons, scoreGoalButtons, inventoryGoalButtons));

    HBox difficultyBox = new HBox(10, btnEasy, btnNormal, btnHard);
    difficultyBox.setAlignment(Pos.CENTER);

    VBox goalsBox = new VBox(10, titleBox, difficultyBox, goldGoalBox, healthGoalBox, scoreGoalBox, inventoryGoalBox);
    goalsBox.setAlignment(Pos.CENTER);

    Button btnSaveGoals = new Button("Save Goals");
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
    btnCreateCustomGoals.setOnAction(e -> {
        Text customTitle = new Text("Customize your goals");
        customTitle.getStyleClass().add("goalsAndStoryView-title");

        TextField customInventoryGoal = new TextField();
        customInventoryGoal.setPromptText("Enter custom inventory goal");
        customInventoryGoal.setPrefSize(50, 20);

        TextField customScoreGoal = new TextField();
        customScoreGoal.setPromptText("Enter custom score goal");
        customScoreGoal.setPrefSize(50, 20);

        TextField customHealthGoal = new TextField();
        customHealthGoal.setPromptText("Enter custom health goal");
        customHealthGoal.setPrefSize(50, 20);

        TextField customGoldGoal = new TextField();
        customGoldGoal.setPromptText("Enter custom gold goal");
        customGoldGoal.setPrefSize(50, 20);

        Button btnCreateCustomGoals2 = new Button("Create Custom Goals");
        btnCreateCustomGoals2.setOnAction(e2 -> {
            goalsViewController.saveCustomGoals(customGoldGoal.getText(), customScoreGoal.getText(),
                    customHealthGoal.getText(), customInventoryGoal.getText());
            gameViewController.setGame(new Game(playerViewController.getPlayer(), gameViewController.getStory(), goalsViewController.getGoals()));
            screenController.activate("gameView");
        });

        VBox customGoalsBox = new VBox(10, customTitle, customInventoryGoal,
                customScoreGoal, customHealthGoal, customGoldGoal, btnCreateCustomGoals2);
        customGoalsBox.setMaxSize(100, 100);
        customGoalsBox.setAlignment(Pos.CENTER);
        root.getChildren().addAll(customGoalsBox);
        goalsBox.setVisible(false);
        btnCreateCustomGoals.setVisible(false);
    });

    VBox buttonBox = new VBox(10, btnSaveGoals, btnCreateCustomGoals);
    buttonBox.setAlignment(Pos.CENTER);

    VBox centerBox = new VBox(10, goalsBox, buttonBox);
    centerBox.setAlignment(Pos.CENTER);

    root.getChildren().addAll(centerBox);
  }

  private void selectFirstGoalToggle(ToggleButton[]... goalButtons) {
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
          String goalValue = toggleButton.getText();
          //goalsViewController.updateGoalValue(goalValue, goalName);
        }
      }
    }
  }

  private ToggleGroup getGoalsToggleGroup(HBox goalBox) {
    for (Node node : goalBox.getChildren()) {
      if (node instanceof ToggleButton) {
        ToggleButton toggleButton = (ToggleButton) node;
        return toggleButton.getToggleGroup();
      }
    }
    return null;
  }

  private HBox setupGoal(String goalName, String buttonStyle, String... options) {
    Text goalText = new Text(goalName);
    goalText.getStyleClass().add("goalsAndStoryView-goalText");

    Text goalValue = new Text(goalValueString);
    goalValue.getStyleClass().add("goalsAndStoryView-goalValue");

    ToggleGroup goalGroup = new ToggleGroup();

    Player player = playerViewController.getPlayer();

    HBox buttonBox = new HBox(10);

    final String trimmedGoalName = goalName.trim();

    ToggleButton[] buttons = new ToggleButton[options.length];

    for (int i = 0; i < options.length; i++) {
      ToggleButton button = new ToggleButton(options[i]);
      button.setPrefSize(70, 20);
      button.setToggleGroup(goalGroup);
      button.setOnAction(e -> {
        if (button.isSelected()) {
          goalValueString = button.getText();
          //goalsViewController.updateGoalValue(goalValueString, trimmedGoalName);
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
    if (buttonStyle.equals("goldGoalButton")) {
      goldGoalButtons = buttons;
    } else if (buttonStyle.equals("healthGoalButton")) {
      healthGoalButtons = buttons;
    } else if (buttonStyle.equals("scoreGoalButton")) {
      scoreGoalButtons = buttons;
    } else if (buttonStyle.equals("inventoryGoalButton")) {
      inventoryGoalButtons = buttons;
    }

    return goalBox;
  }

  public void resetPane() {
    this.root.getChildren().clear();
  }
}
