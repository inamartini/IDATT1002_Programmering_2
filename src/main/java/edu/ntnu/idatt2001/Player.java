package edu.ntnu.idatt2001;

import java.util.ArrayList;
import java.util.List;

public class Player {

  private String name;
  private int health;
  private int score;
  private int gold;
  List<String> inventory;

  /**
   * Constructor for the Player class.
   *
   * @param name The name of the player.
   * @param health The health of the player.
   * @param score The score of the player.
   * @param gold The gold of the player.
   */
  public Player(String name, int health, int score, int gold) {
    if (name == null) {
      throw new IllegalArgumentException("Name can't be empty");
    }
    if (health < 0) {
      throw new IllegalArgumentException("Health can't be negative");
    }
    this.name = name;
    this.health = health;
    this.score = score;
    this.gold = gold;
    this.inventory = new ArrayList<>();
  }

  /**
   * Method that returns the name of the player.
   *
   * @return The name of the player as String.
   */
  public String getName() {
    return name;
  }

  /**
   * Method that adds health to the player.
   *
   * @param health The health to add to the player.
   */
  public void addHealth(int health) {
    if (health < 0) {
      throw new IllegalArgumentException("Health can't be negative");
    }
    this.health += health;
  }

  /**
   * Method that returns the health of the player.
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
   * Method that returns the score of the player.
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
   * Method that returns the gold of the player.
   *
   * @return The gold of the player as int.
   */
  public int getGold() {
    return gold;
  }

  /**
   * Method that adds an item to the inventory of the player.
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
   * Method that returns the inventory of the player.
   *
   * @return The inventory of the player as List.
   */
  public List<String> getInventory() {
    return inventory;
  }
}
