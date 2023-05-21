package edu.ntnu.idatt2001.model.player;

import java.util.List;

/**
 * This class represents a player in a game.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.MM.DD
 */
public class PlayerBuilder {

  public String name;
  public int health;
  public int score;
  public int gold;
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
     * Method that sets the health of the player.
     *
     * @param health The health of the player.
     * @return The Builder object.
     */
  public PlayerBuilder health(int health) {
      if (health < 0) {
          throw new IllegalArgumentException("Health can't be negative");
      }
      this.health = health;
      return this;
  }

  /**
     * Method that sets the score of the player.
     *
     * @param score The score of the player.
     * @return The Builder object.
   */
  public PlayerBuilder score(int score) {
      if(score < 0) {
          throw new IllegalArgumentException("Score can't be negative");
      }
      this.score = score;
      return this;
  }

  /**
     * Method that sets the gold of the player.
     *
     * @param gold The gold of the player.
     * @return The Builder object.
   */
  public PlayerBuilder gold(int gold) {
     if(gold < 0) {
         throw new IllegalArgumentException("Gold can't be negative");
     }
     this.gold = gold;
     return this;
  }

    /**
     * Method that sets the inventory of the player.
     *
     * @param inventory The inventory of the player.
     * @return The Builder object.
     */
    public PlayerBuilder inventory(List<String> inventory) {
        if(inventory == null || inventory.isEmpty()) {
            throw new IllegalArgumentException("Inventory can't be null");
        }
        this.inventory = inventory;
        return this;
    }

    /**
     * Method that builds the player.
     * @return The player.
     */
    public Player build() {
        return new Player(this);
    }
}
