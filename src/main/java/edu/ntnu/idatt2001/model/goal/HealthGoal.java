package edu.ntnu.idatt2001.model.goal;

import edu.ntnu.idatt2001.model.player.Player;

/**
 * HealthGoal is a class that checks if the player has a certain amount of health.
 * This class implements the Goal interface and defines a goal that is fulfilled
 * when the player has a specified minimum amount of health.
 * The goal is checked by calling the isFulfilled() method,
 * which takes a Player object as input and returns a boolean indicating
 * whether the goal is fulfilled or not.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 */
public class HealthGoal implements Goal {

  /**
   * The minimum health required to fulfill the goal.
   */
  private int minimumHealth;

  /**
   * Constructs a new HealthGoal object with the specified minimum health.
   *
   * @param minimumHealth the minimum health required to fulfill the goal.
   * @throws IllegalArgumentException if the minimum health is not positive.
   */
  public HealthGoal(int minimumHealth) {
    if (minimumHealth <= 0) {
      throw new IllegalArgumentException("Minimum health must be positive");
    }
    this.minimumHealth = minimumHealth;
  }

  /**
   * Returns the minimum health required to fulfill the goal.
   *
   * @return the minimum health required to fulfill the goal.
   */
  public int getMinimumHealth() {
    return minimumHealth;
  }

  /**
   * Checks whether the given player has the required minimum health to fulfill the goal.
   *
   * @param player the player to check the health of.
   * @return true if the player's health is greater than or equal to the minimum health
   *         required to fulfill the goal, false otherwise.
   * @throws IllegalArgumentException if the player is null.
   */
  @Override
  public boolean isFulfilled(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    try {
      return player.getHealth() >= minimumHealth;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Failed to retrieve health from player");
    }
  }

  /**
   * ToString method for the goal.
   */
  @Override
  public String toString() {
    return "Health goal: " + minimumHealth + " health";
  }
}
