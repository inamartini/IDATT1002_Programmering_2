package edu.ntnu.idatt2001.controller;

import javafx.scene.image.Image;

public class PlayerViewController {

  private static PlayerViewController playerViewController = new PlayerViewController();
  private Image playerImage;

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

}
