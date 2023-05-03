package edu.ntnu.idatt2001.base;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a player in a game.
 * The player has a name, health, score, gold and inventory.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.MM.DD
 */
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

  /**
   * Private constructor that takes a Builder object.
   *
   * @param builder The Builder object used to create the player object.
   */
  private Player(Builder builder) {
    this.name = builder.name;
    this.health = builder.health;
    this.score = builder.score;
    this.gold = builder.gold;
    this.inventory = builder.inventory;
  }

  /**
   * Builder class for creating player objects.
   * This class is used to create a player object with the builder pattern.
   */
  public static class Builder {
    private String name;
    private int health;
    private int score;
    private int gold;
    private List<String> inventory;

    /**
     * Constructor for the Builder class with the specified name.
     *
     * @param name The name of the player.
     */
    public Builder(String name) {
      this.name = name;
    }

    /**
     * Method that sets the health of the player.
     *
     * @param health The health of the player.
     * @return The Builder object.
     */
    public Builder health(int health) {
      this.health = health;
      return this;
    }

    /**
     * Method that sets the score of the player.
     *
     * @param score The score of the player.
     * @return The Builder object.
     */
    public Builder score(int score) {
      this.score = score;
      return this;
    }

    /**
     * Method that sets the gold of the player.
     *
     * @param gold The gold of the player.
     * @return The Builder object.
     */
    public Builder gold(int gold) {
      this.gold = gold;
      return this;
    }

    /**
     * Method that sets the inventory of the player.
     *
     * @param inventory The inventory of the player.
     * @return The Builder object.
     */
    public Builder inventory(List<String> inventory) {
      this.inventory = inventory;
      return this;
    }

    /**
     * Method that builds the player object with the provided parameters..
     *
     * @return A new player object.
     */
    public Player build() {
      return new Player(this);
    }
  }
}
