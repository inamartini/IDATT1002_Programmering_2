package edu.ntnu.idatt2001.model.goal;

import edu.ntnu.idatt2001.model.player.Player;

/**
 * The GoldGoal class represents a goal that requires a player to have a certain amount of gold.
 * The goal is considered fulfilled if the player has at least the specified minimum amount of gold.
 *
 * @author Malin Haugland Holi
 * @author Ina Martini
 * @version 2023.05.22
 */
public class GoldGoal implements Goal {

  /**
   * The minimum amount of gold required to fulfill the goal.
   */
  private int minimumGold;

  /**
   * Creates a new GoldGoal with the specified minimum amount of gold required to fulfill the goal.
   *
   * @param minimumGold the minimum amount of gold required to fulfill the goal
   * @throws IllegalArgumentException if the minimumGold parameter is negative
   */
  public GoldGoal(int minimumGold) {
    if (minimumGold < 0) {
      throw new IllegalArgumentException("Minimum gold must be positive");
    }
    this.minimumGold = minimumGold;
  }

  /**
   * Returns the minimum amount of gold required to fulfill the goal.
   *
   * @return the minimum amount of gold required to fulfill the goal
   */
  public int getMinimumGold() {
    return minimumGold;
  }

  /**
   * Checks whether the player has the required amount of gold to fulfill the goal.
   *
   * @param player the player to check
   * @return true if the player has the required amount of gold, false otherwise.
   * @throws IllegalArgumentException if the player parameter is null
   */
  @Override
  public boolean isFulfilled(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    try {
      return player.getGold() >= minimumGold;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Failed to retrieve gold from player");
    }
  }

  /**
   * ToString method for the goal.
   */
  @Override
  public String toString() {
    return "Gold goal: " + minimumGold + " gold";
  }
}
