package edu.ntnu.idatt2001.model.action;

import edu.ntnu.idatt2001.model.player.Player;

/**
 * GoldAction is a class that represents an action that adds gold to a player.
 * The amount of gold to add is specified in the constructor.
 *
 * @author Malin Haugland Holi
 * @author Ina Martini
 * @version 2023.05.22
 */

public class GoldAction implements Action {

  /**
   * The amount of gold to add.
   */
  private int gold;

  /**
   * Constructs a new GoldAction with the specified amount of gold to add.
   *
   * @param gold the amount of gold to add
   * @throws IllegalArgumentException if the gold amount is negative
   */
  public GoldAction(int gold) {
    if (gold < 0) {
      throw new IllegalArgumentException("Gold can't be negative");
    }
    this.gold = gold;
  }

  /**
   * Executes the GoldAction by adding the specified amount of gold to the player's balance.
   *
   * @param player the player to add the gold to
   *               C     * @throws IllegalArgumentException if the player parameter is null
   */
  @Override
  public void execute(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player can't be null");
    }
    try {
      player.addGold(gold);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Failed to execute gold action with given parameters");
    }
  }

  /**
   * Returns a string representation of the GoldAction.
   *
   * @return gold action as a string
   */
  @Override
  public String toString() {
    return "{"
        + "Gold}"
        + "("
        + gold
        + ')';
  }
}
