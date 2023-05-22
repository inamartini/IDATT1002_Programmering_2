package edu.ntnu.idatt2001.model.player;

import java.util.List;

/**
 * This class is responsible for building the player and will be
 * used to build the player in the game.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 */
public class PlayerBuilder {

  /**
   * The name of the player.
   */
  public String name;

  /**
   * The health of the player.
   */
  public int health;

  /**
   * The score of the player.
   */
  public int score;

  /**
   * The gold of the player.
   */
  public int gold;

  /**
   * The inventory of the player.
   */
  public List<String> inventory;

  /**
   * Constructor for the Builder class with the name of the player.
   *
   * @param name The name of the player.
   */
  public PlayerBuilder(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name can't be empty");
    }
    this.name = name;
  }

  /**
   * Sets the health of the player.
   *
   * @param health The health of the player.
   * @return The Builder object.
   */
  public PlayerBuilder health(int health) {
    this.health = health;
    return this;
  }

  /**
   * Sets the score of the player.
   *
   * @param score The score of the player.
   * @return The Builder object.
   */
  public PlayerBuilder score(int score) {
    this.score = score;
    return this;
  }

  /**
   * Sets the gold of the player.
   *
   * @param gold The gold of the player.
   * @return The Builder object.
   */
  public PlayerBuilder gold(int gold) {
    this.gold = gold;
    return this;
  }

  /**
   * Sets the inventory of the player.
   *
   * @param inventory The inventory of the player.
   * @return The Builder object.
   */
  public PlayerBuilder inventory(List<String> inventory) {
    if (inventory == null || inventory.isEmpty()) {
      throw new IllegalArgumentException("Inventory can't be null");
    }
    this.inventory = inventory;
    return this;
  }

  /**
   * Builds the player.
   *
   * @return The player.
   */
  public Player build() {
    return new Player(this);
  }
}
