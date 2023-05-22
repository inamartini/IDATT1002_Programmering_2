package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.model.player.Player;
import edu.ntnu.idatt2001.model.player.PlayerBuilder;
import javafx.scene.image.Image;

/**
 * Class that handles the player view. This class is responsible for creating the player
 * and resetting the player when a game is restarted.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 */
public class PlayerController {

  /**
   * The singleton instance of this class.
   */
  private static PlayerController playerController = new PlayerController();

  /**
   * The player image.
   */
  private Image playerImage;

  /**
   * The player.
   */
  private Player player;

  /**
   * The initial player name.
   */
  private String initialPlayerName;

  /**
   * The initial player health.
   */
  private int initialPlayerHealth;

  /**
   * The initial player gold.
   */
  private int initialPlayerGold;

  /**
   * Private constructor to make this a singleton class.
   */
  private PlayerController() {
  }

  /**
   * Returns the instance of this class.
   *
   * @return the instance of this class
   */
  public static PlayerController getInstance() {
    return playerController;
  }

  /**
   * Returns the player image.
   *
   * @return the player image
   */
  public Image getPlayerImage() {
    return playerImage;
  }

  /**
   * Sets the player image.
   *
   * @param playerImage the player image
   */
  public void setPlayerImage(Image playerImage) {
    this.playerImage = playerImage;
  }

  /**
   * Returns the player.
   *
   * @return the player
   */
  public Player getPlayer() {
    return this.player;
  }

  /**
   * Sets the player.
   *
   * @param player the player
   */
  public void setPlayer(Player player) {
    this.player = player;
  }

  /**
   * Creates a player when the game is started by using the PlayerBuilder class.
   *
   * @param playerName   the player name
   * @param playerHealth the player health
   * @param playerGold   the player gold
   */
  public void createPlayer(String playerName, int playerHealth, int playerGold) {
    this.initialPlayerName = playerName;
    this.initialPlayerHealth = playerHealth;
    this.initialPlayerGold = playerGold;

    this.player = new PlayerBuilder(playerName)
        .health(playerHealth)
        .gold(playerGold)
        .build();
  }

  /**
   * Resets the player to the initial player when the game is restarted.
   */
  public void resetPlayer() {
    this.player = new PlayerBuilder(initialPlayerName)
        .health(initialPlayerHealth)
        .gold(initialPlayerGold)
        .build();
  }
}
