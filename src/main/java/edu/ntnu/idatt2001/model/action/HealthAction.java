package edu.ntnu.idatt2001.model.action;

import edu.ntnu.idatt2001.model.player.Player;

/**
 * HealthAction is a class that represents an action that adds health to a player.
 * The amount of health points to add is specified in the constructor.
 * This class implements the `Action` interface.
 *
 * @author Malin Haugland Holi
 * @author Ina Martini
 * @version 2023.05.22
 */
public class HealthAction implements Action {

  /**
   * The number of health points to add to the player.
   */
  private final int points;

  /**
   * Constructs a new HealthAction with the specified number of health points to add.
   *
   * @param points the number of health points to add
   */
  public HealthAction(int points) {
    this.points = points;
  }

  /**
   * Executes the HealthAction by adding the specified number of health points to the player.
   *
   * @param player the player to add the health points to
   * @throws IllegalArgumentException if the player parameter is null
   */
  @Override
  public void execute(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    try {
      player.addHealth(points);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Failed to execute health action with given parameters");
    }
  }

  /**
   * Returns a string representation of the HealthAction.
   *
   * @return health action as a string
   */
  @Override
  public String toString() {
    return "{"
        + "Health}"
        + "("
        + points
        + ')';
  }
}
