package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.model.Game;
import edu.ntnu.idatt2001.controller.GameViewController;
import edu.ntnu.idatt2001.controller.GoalsViewController;
import edu.ntnu.idatt2001.controller.PlayerController;
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

/**
 * Class that represents the goals view. The user can choose goals for the game.
 * The goals are saved in the goalsViewController. The user can also choose to play with a predefined
 * difficulty or to custom their own or play without goals.
 * Includes methods for setting up the view and handling button actions.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 */
public class GoalsView extends View {

  /**
   * The Screen controller.
   */
  private ScreenController screenController;
  /**
   * The Root.
   */
  private StackPane root;

  /**
   * The Border pane.
   */
  private BorderPane borderPane;

  /**
   * The Goals view controller.
   */
  private GoalsViewController goalsViewController = new GoalsViewController();

  /**
   * The Game view controller.
   */
  private GameViewController gameViewController = GameViewController.getInstance();

  /**
   * The Player controller.
   */
  private PlayerController playerController = PlayerController.getInstance();

  /**
   * The Goal value string.
   */
  private String goalValueString = null;

  /**
   * The difficultyToggleGroup.
   */
  private ToggleGroup difficultyToggleGroup;

  /**
   * The goldGoalButtons.
   */
  private ToggleButton[] goldGoalButtons;

  /**
   * The healthGoalButtons.
   */
  private ToggleButton[] healthGoalButtons;

  /**
   * The scoreGoalButtons.
   */
  private ToggleButton[] scoreGoalButtons;

  /**
   * The inventoryGoalButtons.
   */
  private ToggleButton[] inventoryGoalButtons;

  /**
   * Instantiates a new Goals view. Sets up a border pane with a stack pane as root.
   *
   * @param screenController the screen controller
   */
  public GoalsView(ScreenController screenController) {
    this.screenController = screenController;
    this.borderPane = new BorderPane();
    this.root = new StackPane();
    borderPane.setCenter(root);
  }

  /**
   * Returns the pane as a BorderPane.
   * @return the pane
   */
  public Pane getPane() {
    return this.borderPane;
  }

  /**
   * Set up the goals view.
   *
   * Configures the layout and components of the goals view.
   * It initializes the UI elements such as titles, buttons, toggle buttons, and text fields.
   * The goals view allows the player to choose and customize game goals before starting the game.
   *
   * This method performs the following steps:
   * 1. Reset the pane to its initial state.
   * 2. Create and configure the title text.
   * 3. Create and configure the back button to return to the previous view.
   * 4. Set up the layout of the goals view, including goal boxes, difficulty toggle buttons,
   *    and buttons for playing, saving, and creating custom goals.
   * 5. Handle button actions for selecting difficulty levels, playing with goals or playing without goals.
   */

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
      gameViewController.setGame(new Game(playerController.getPlayer(), gameViewController.getStory(), goalsViewController.getGoals()));
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
        gameViewController.setGame(new Game(playerController.getPlayer(), gameViewController.getStory(), goalsViewController.getGoals()));
        screenController.activate("gameView");
      } catch (IllegalArgumentException ex) {
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
          gameViewController.setGame(new Game(playerController.getPlayer(), gameViewController.getStory(), goalsViewController.getGoals()));
          screenController.activate("gameView");
        } catch (NumberFormatException ex) {
          AlertUtil.showAlert(Alert.AlertType.ERROR, "Invalid Input", ex.getMessage());
        } catch (IllegalArgumentException ex) {
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

  /**
   * Sets the selected goal toggles based on the difficulty selected.
   * It takes an array of toggle button arrays, where each inner
   * array represents the toggle buttons for a specific goal.
   * The toggle buttons should be ordered according to the difficulty levels.
   * The first toggle button in each inner array should be the easy difficulty,
   * the second should be medium, and the third should be hard.
   *
   * @param goalButtons the toggle buttons for each goal
   */
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

  /**
   * Creates a HBox containing a goal name, value, and toggle buttons.
   * The toggle buttons are added to a toggle group.
   * The toggle group is used to determine which toggle button is selected.
   * Checks if the toggle button is selected and updates the goal value accordingly.
   *
   * @param goalName the name of the goal
   * @param buttonStyle the style of the toggle buttons
   * @param options the options for the toggle buttons
   * @return the HBox goal box
   */
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

    switch (buttonStyle) {
      case "goldGoalButton" -> goldGoalButtons = buttons;
      case "healthGoalButton" -> healthGoalButtons = buttons;
      case "scoreGoalButton" -> scoreGoalButtons = buttons;
      case "inventoryGoalButton" -> inventoryGoalButtons = buttons;
    }
    return goalBox;
  }

  /**
   * Checks if the string is an integer. Returns true if the string is null or empty.
   *
   * @param str The string to check
   * @return True if the string is an integer, false otherwise
   */
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

  /**
   * Resets the pane and clears all the children
   */
  public void resetPane() {
    this.root.getChildren().clear();
  }
}
