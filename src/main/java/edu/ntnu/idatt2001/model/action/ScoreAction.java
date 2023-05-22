package edu.ntnu.idatt2001.model.action;

import edu.ntnu.idatt2001.model.player.Player;

/**
 * ScoreAction is an action that adds a certain amount of points to the player's score.
 * The number of points to add is specified in the constructor.
 * This class implements the `Action` interface.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 */

public class ScoreAction implements Action {

  /**
   * The number of points to add to the player's score.
   */
  private int points;

  /**
   * Constructs a new ScoreAction with the specified number of points to add to the player's score.
   *
   * @param points the number of points to add to the player's score
   */
  public ScoreAction(int points) {
    this.points = points;
  }

  /**
   * Executes the ScoreAction by adding the specified number of points to the player's score.
   *
   * @param player the player whose score should be updated
   * @throws IllegalArgumentException if the player parameter is null
   */
  @Override
  public void execute(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player can't be null");
    }
    try {
      player.addScore(points);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Failed to execute score action with given parameters");
    }
  }

  /**
   * Returns a string representation of the ScoreAction.
   *
   * @return score action as a string
   */
  @Override
  public String toString() {
    return "{"
        + "Score}"
        + "("
        + points
        + ')';
  }
}
