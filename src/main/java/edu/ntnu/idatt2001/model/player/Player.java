package edu.ntnu.idatt2001.model.player;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a player in a game.
 * The player has a name, health, score, gold and inventory.
 *
 * @author Malin Haugland Holi
 * @author Ina Martini
 * @version 2023.05.22
 */
public class Player {

  /**
   * The name of the player.
   */
  private String name;

  /**
   * The health of the player.
   */
  private int health;

  /**
   * The score of the player.
   */
  private int score;

  /**
   * The gold of the player.
   */
  private int gold;

  /**
   * The inventory of the player.
   */
  private List<String> inventory;

  /**
   * Constructor for the Player class.
   * Takes in the playerBuilder class used to build a player.
   *
   * @param playerBuilder The playerBuilder class used to build a player.
   */
  Player(PlayerBuilder playerBuilder) {
    this.name = playerBuilder.name;
    this.health = playerBuilder.health;
    this.score = playerBuilder.score;
    this.gold = playerBuilder.gold;
    this.inventory = new ArrayList<>();
  }

  /**
   * Returns the name of the player.
   *
   * @return The name of the player as String.
   */
  public String getName() {
    return name;
  }

  /**
   * Adds health to the player.
   *
   * @param health The health to add to the player.
   */
  public void addHealth(int health) {
    this.health += health;
    if (this.health <= 0) {
      throw new IllegalArgumentException("Health can't be 0 or less");
    }
  }

  /**
   * Returns the health of the player.
   *
   * @return The health of the player as int.
   */
  public int getHealth() {
    return health;
  }

  /**
   * Method that adds score to the player.
   *
   * @param score The score to add to the player.
   */
  public void addScore(int score) {
    this.score += score;
  }

  /**
   * Returns the score of the player.
   *
   * @return The score of the player as int.
   */
  public int getScore() {
    return score;
  }

  /**
   * Method that adds gold to the player.
   *
   * @param gold The gold to add to the player.
   */
  public void addGold(int gold) {
    this.gold += gold;
  }

  /**
   * Returns the gold of the player.
   *
   * @return The gold of the player as int.
   */
  public int getGold() {
    return gold;
  }

  /**
   * Adds an item to the inventory of the player.
   *
   * @param item The item to add to the inventory.
   */
  public void addToInventory(String item) {
    if (item == null) {
      throw new IllegalArgumentException("Item can't be empty");
    } else {
      inventory.add(item);
    }
  }

  /**
   * Returns the inventory of the player.
   *
   * @return The inventory of the player as List.
   */
  public List<String> getInventory() {
    return inventory;
  }
}
