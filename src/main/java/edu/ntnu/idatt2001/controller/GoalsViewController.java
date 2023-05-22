package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.model.goal.Goal;
import edu.ntnu.idatt2001.model.goal.GoalFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;

/**
 * Controller class for the GoalsView. Handles the logic for the GoalsView.
 * Includes several methods to handle the goals chosen by the user.
 *
 * @author Malin Haugland Holi
 * @author Ina Martini
 * @version 2023.05.22
 */
public class GoalsViewController {

  /**
   * List of goals to be used in the game.
   */
  private List<Goal> goals;
  private static final String INVENTORY_GOAL = "inventory_goal";

  /**
   * Constructor for GoalsViewController. Initializes the goals list.
   */
  public GoalsViewController() {
    this.goals = new ArrayList<>();
  }

  /**
   * Method for getting the goals chosen by the user.
   *
   * @return the goals
   */
  public List<Goal> getGoals() {
    return goals;
  }

  /**
   * Method for saving the goals chosen by the user. The goals are taken in as Toggles and added
   * to a toggleMap with the corresponding goalType. Based on the goal type the goal is created
   * and added to the goals list. An exception is thrown if the goal cannot be created.
   *
   * @param gold      the gold chosen by the user
   * @param score     the score chosen by the user
   * @param health    the health chosen by the user
   * @param inventory the inventory chosen by the user
   */
  public void saveGoals(Toggle gold, Toggle score, Toggle health, Toggle inventory) {
    this.goals = new ArrayList<>();
    try {
      Map<Toggle, String> toggleMap = new HashMap<>();
      toggleMap.put(gold, "Gold_goal");
      toggleMap.put(score, "Score_goal");
      toggleMap.put(health, "Health_goal");
      toggleMap.put(inventory, INVENTORY_GOAL);

      for (Map.Entry<Toggle, String> entry : toggleMap.entrySet()) {
        Toggle toggle = entry.getKey();
        String goalType = entry.getValue();
        if (toggle instanceof ToggleButton toggleButton && (toggleButton.isSelected())) {
          String goalValue = toggleButton.getText();
          if (goalType.equalsIgnoreCase(INVENTORY_GOAL)) {
            goals.add(GoalFactory.createInventoryGoal(goalType, goalValue));
          } else {
            goals.add(GoalFactory.createGoal(goalType, Integer.parseInt(goalValue)));
          }

        }
      }
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Failed to create goal with given parameters");
    }
  }

  /**
   * Method for saving the custom goals chosen by the user.
   * The goals are taken in as Strings and added if the String is not null
   * and not empty, the goal is created and added to the goals list.
   * An exception is thrown if the goal cannot be created.
   *
   * @param goldGoal      the gold goal chosen by the user
   * @param scoreGoal     the score goal chosen by the user
   * @param healthGoal    the health goal chosen by the user
   * @param inventoryGoal the inventory goal chosen by the user
   */
  public void saveCustomGoals(String goldGoal, String scoreGoal, String healthGoal,
                              String inventoryGoal) {
    this.goals.clear();
    try {
      if (goldGoal != null && !goldGoal.isEmpty()) {
        goals.add(GoalFactory.createGoal("Gold_goal", Integer.parseInt(goldGoal)));
      }
      if (scoreGoal != null && !scoreGoal.isEmpty()) {
        goals.add(GoalFactory.createGoal("Score_goal", Integer.parseInt(scoreGoal)));
      }
      if (healthGoal != null && !healthGoal.isEmpty()) {
        goals.add(GoalFactory.createGoal("Health_goal", Integer.parseInt(healthGoal)));
      }
      if (inventoryGoal != null && !inventoryGoal.isEmpty()) {
        goals.add(GoalFactory.createInventoryGoal(INVENTORY_GOAL, inventoryGoal));
      }
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Failed to create goal with given parameters");
    }
  }
}




