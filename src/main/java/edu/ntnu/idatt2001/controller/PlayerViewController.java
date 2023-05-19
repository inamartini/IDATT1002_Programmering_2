package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.base.Player;
import javafx.scene.image.Image;

public class PlayerViewController {

  private static PlayerViewController playerViewController = new PlayerViewController();
  private Image playerImage;
  private Player player;
  private String playerName;
  private int playerHealth;
  private int playerGold;


  private PlayerViewController() {
  }

  public static PlayerViewController getInstance() {
    return playerViewController;
  }

  public Image getPlayerImage() {
    return playerImage;
  }

  public void setPlayerImage(Image playerImage) {
    this.playerImage = playerImage;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public Player createPlayer(String playerName,int playerHealth, int playerGold) {
     this.player = new Player.Builder(playerName)
        .health(playerHealth)
        .gold(playerGold)
        .build();
     return player;
  }

}
