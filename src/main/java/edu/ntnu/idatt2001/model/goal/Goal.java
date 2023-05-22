package edu.ntnu.idatt2001.model.goal;

import edu.ntnu.idatt2001.model.player.Player;

/**
 * The Goal interface represents a goal that can be fulfilled by a player.
 * This interface provides a method for doing certain actions if the goal is fulfilled.
 *
 * @author Malin Haugland Holi
 * @author Ina Martini
 * @version 2023.05.22
 */

public interface Goal {
  /**
   * Checks if the goal is fulfilled by the player.
   *
   * @param player the player
   * @return true if the goal is fulfilled, false otherwise
   */
  boolean isFulfilled(Player player);

  /**
   * ToString method for the goal.
   */
  @Override
  String toString();
}
