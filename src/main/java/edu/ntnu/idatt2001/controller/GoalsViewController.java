package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.model.goal.Goal;
import edu.ntnu.idatt2001.model.goal.GoalFactory;
import edu.ntnu.idatt2001.util.AlertUtil;
import edu.ntnu.idatt2001.view.GoalsView;
import javafx.scene.control.Alert;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoalsViewController {

  private static GoalsViewController goalsViewController = new GoalsViewController();

  private GoalsView goalsView;
  private List<Goal> goals;

  private GoalsViewController() {
    this.goals = new ArrayList<>();
  }

  public static GoalsViewController getInstance() {
    return goalsViewController;
  }


  public List<Goal> getGoals() {
    return goals;
  }

  public void saveGoals(Toggle gold, Toggle score, Toggle health, Toggle inventory) {
    try {
      Map<Toggle, String> toggleMap = new HashMap<>();
      toggleMap.put(gold, "Gold_Goal");
      toggleMap.put(score, "Score_GOAL");
      toggleMap.put(health, "Health_goal");
      toggleMap.put(inventory, "Inventory_goal");

      for (Map.Entry<Toggle, String> entry : toggleMap.entrySet()) {
        Toggle toggle = entry.getKey();
        String goalType = entry.getValue();
        if (toggle instanceof ToggleButton toggleButton) {
          if (toggleButton.isSelected()) {
            String goalValue = toggleButton.getText();
            if (goalType.equalsIgnoreCase("Inventory_goal")) {
              goals.add(GoalFactory.createInventoryGoal(goalType, goalValue));
            } else {
              goals.add(GoalFactory.createGoal(goalType, Integer.parseInt(goalValue)));
            }
          }
        }
      }
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Failed to create goal with given parameters");
    }
  }

  public void saveCustomGoals(String goldGoal, String scoreGoal, String healthGoal, String inventoryGoal) {
    if (goldGoal == null || scoreGoal == null || healthGoal == null || inventoryGoal == null) {
      throw new IllegalArgumentException("Please fill in all the fields");
    }

    try {
      goals.add(GoalFactory.createGoal("Gold_Goal", Integer.parseInt(goldGoal)));
      goals.add(GoalFactory.createGoal("Score_GOAL", Integer.parseInt(scoreGoal)));
      goals.add(GoalFactory.createGoal("Health_goal", Integer.parseInt(healthGoal)));
      goals.add(GoalFactory.createInventoryGoal("Inventory_goaL", inventoryGoal));
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Failed to create goal with given parameters");
    }
  }
}




