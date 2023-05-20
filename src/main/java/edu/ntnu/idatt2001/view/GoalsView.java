package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.controller.GoalsViewController;
import edu.ntnu.idatt2001.controller.ScreenController;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class GoalsView extends View {

  private ScreenController screenController;
  private StackPane root;
  private BorderPane borderPane;
  private GoalsViewController goalsViewController = GoalsViewController.getInstance();
  private String goalValueString = null;
  private String goalName;

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

    VBox goalsBox = new VBox(10, titleBox, goldGoalBox, healthGoalBox, scoreGoalBox, inventoryGoalBox);
    goalsBox.setAlignment(Pos.CENTER);

    Button btnSaveGoals = new Button("Save Goals");
    btnSaveGoals.setOnAction(e -> {
      goalsViewController.saveGoals(goalValueString, goalName);
    });

    Button btnCreateCustomGoals = new Button("Create Custom Goals");
    btnCreateCustomGoals.setOnAction(e -> {
      //screenController.activate("customGoals");
    });

    VBox buttonBox = new VBox(10, btnSaveGoals, btnCreateCustomGoals);
    buttonBox.setAlignment(Pos.CENTER);

    VBox centerBox = new VBox(10, goalsBox, buttonBox);
    centerBox.setAlignment(Pos.CENTER);

    root.getChildren().addAll(centerBox);
  }

  private HBox setupGoal(String goalName, String buttonStyle, String... options) {
    Text goalText = new Text(goalName);
    goalText.getStyleClass().add("goalsAndStoryView-goalText");

    Text goalValue = new Text(goalValueString);
    goalValue.getStyleClass().add("goalsAndStoryView-goalValue");

    ToggleGroup goalGroup = new ToggleGroup();

    HBox buttonBox = new HBox(10);
    for (String option : options) {
      ToggleButton button = new ToggleButton(option);
      button.setPrefSize(70, 20);
      button.setToggleGroup(goalGroup);
      button.setOnAction(e -> {
        if (button.isSelected()) {
          goalValueString = button.getText();
          //goalName = goalName.trim(); // goalName will come from the method's parameter
          //goalsViewController.updateGoalValue(goalValueString, goalName); // use goalValueString and goalName as arguments
          goalGroup.getToggles().forEach(toggle -> {
            Node node = (Node) toggle;
            node.getStyleClass().remove("selectedButton");
          });
          button.getStyleClass().add("selectedButton");
        } else {
          goalValueString = null;
          button.getStyleClass().remove("selectedButton");
        }
      });
      button.getStyleClass().add(buttonStyle);
      buttonBox.getChildren().add(button);
    }

    HBox goalBox = new HBox(10, goalText, buttonBox, goalValue);
    goalBox.setAlignment(Pos.CENTER);

    return goalBox;
  }

  public void resetPane() {
    this.root.getChildren().clear();
  }
}
