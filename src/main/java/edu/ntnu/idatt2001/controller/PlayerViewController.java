package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.base.Player;
import edu.ntnu.idatt2001.base.PlayerBuilder;
import javafx.scene.image.Image;

public class PlayerViewController {

  private static PlayerViewController playerViewController = new PlayerViewController();
  private Image playerImage;
  private Player player;


  /**
   * Private constructor to make this a singleton class
   */
  private PlayerViewController() {
  }

  /**
   * Returns the instance of this class
   *
   * @return the instance of this class
   */
  public static PlayerViewController getInstance() {
    return playerViewController;
  }

  /**
   * Returns the player image
   *
   * @return the player image
   */
  public Image getPlayerImage() {
    return playerImage;
  }

  /**
   * Sets the player image
   *
   * @param playerImage the player image
   */
  public void setPlayerImage(Image playerImage) {
    this.playerImage = playerImage;
  }

  /**
   * Returns the player
   *
   * @return the player
   */
  public Player getPlayer() {
    return this.player;
  }

  /**
   * Sets the player
   *
   * @param player the player
   */
  public void setPlayer(Player player) {
    this.player = player;
  }

  /**
   * Creates a player
   *
   * @param playerName the player name
   * @param playerHealth the player health
   * @param playerGold the player gold
   * @return the player
   */
  public Player createPlayer(String playerName,int playerHealth, int playerGold) {
    this.player= new PlayerBuilder(playerName)
            .health(playerHealth)
            .gold(playerGold)
            .build();
    return player;
  }

  /**
   * Resets the player
   */
  public void resetPlayer() {
    this.player = null;
  }

}
