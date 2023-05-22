package edu.ntnu.idatt2001.model.goal;

import edu.ntnu.idatt2001.model.player.Player;

/**
 * ScoreGoal is a class that checks if the player has reached a certain score.
 * It implements the Goal interface and overrides the isFulfilled method to check if the
 * player's score is greater than or equal to a minimum score required to fulfill the goal.
 *
 * @author Malin Haugland Holi
 * @author Ina Martini
 * @version 2023.05.22
 */

public class ScoreGoal implements Goal {

  /**
   * The minimum amount of points required to fulfill the goal.
   */

  private int minimumPoints;

  /**
   * Constructs a new ScoreGoal object with the given minimumPoints.
   *
   * @param minimumPoints the minimum score required to fulfill the goal.
   * @throws IllegalArgumentException if the minimumPoints is negative.
   */
  public ScoreGoal(int minimumPoints) {
    if (minimumPoints < 0) {
      throw new IllegalArgumentException("Minimum points must be positive");
    }
    this.minimumPoints = minimumPoints;
  }

  /**
   * Returns the minimumPoints required to fulfill the goal.
   *
   * @return the minimumPoints required to fulfill the goal.
   */
  public int getMinimumPoints() {
    return minimumPoints;
  }

  /**
   * Checks if the player's score is greater
   * than or equal to the minimumPoints required to fulfill the goal.
   *
   * @param player the Player object to check the score of.
   * @return true if the player's score is greater than or equal to minimumPoints, false otherwise.
   * @throws IllegalArgumentException if the player object is null.
   */
  @Override
  public boolean isFulfilled(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    try {
      return player.getScore() >= minimumPoints;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Failed to retrieve player score");
    }
  }

  /**
   * ToString method for the goal.
   */
  @Override
  public String toString() {
    return "Score goal: " + minimumPoints + " points";
  }
}