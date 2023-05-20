package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.goal.*;
import edu.ntnu.idatt2001.view.GoalsView;
import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GoalsViewController {

  private static GoalsViewController goalsViewController= new GoalsViewController();

  private GoalsView goalsView;
  private List<Goal> goals;

  private GoalsViewController() {
    this.goals = new ArrayList<>();
  }

  public static GoalsViewController getInstance() {
    return goalsViewController;
  }

  public void updateGoalValue(String goalValue, String goalName) {
/*    for (Goal goal : goals) {
      if (goal.getName().equals(goalName)) {
        goal.setValue(goalValue);
        break;
      }
    }*/
  }

  public void addGoal(Goal goal) {
    if (!goals.contains(goal) && goal != null) {
      goals.add(goal);
    }
  }

  public void removeGoal(Goal goal) {
    if (goal != null) {
      goals.remove(goal);
    }
  }

  public void saveGoals(String goalType, String goalValue) {
    switch (goalType) {
      case "GoldGoal" -> {
        Goal goldGoal = new GoldGoal(Integer.parseInt(goalValue));
        addGoal(goldGoal);
      }
      case "ScoreGoal" -> {
        Goal scoreGoal = new ScoreGoal(Integer.parseInt(goalValue));
        addGoal(scoreGoal);
      }
      case "HealthGoal" -> {
        Goal healthGoal = new HealthGoal(Integer.parseInt(goalValue));
        addGoal(healthGoal);
      }
      case "InventoryGoal" -> {
        //Goal inventoryGoal = new InventoryGoal();
        //addGoal(inventoryGoal);
      }
      default -> throw new IllegalArgumentException("Invalid goal type: " + goalType);
    }
  }
}

