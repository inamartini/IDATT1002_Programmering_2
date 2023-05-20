package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.action.InventoryIcon;
import edu.ntnu.idatt2001.action.InventoryType;
import edu.ntnu.idatt2001.base.*;
import edu.ntnu.idatt2001.util.StoryReader;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameViewController {

  private static GameViewController gameViewController= new GameViewController();

  private Story story;
  private Game game;
  private String storyPath ;
  private InventoryIcon inventoryIcon = new InventoryIcon();
  private List<String> inventoryList = new ArrayList<>();

  /**
   * Private constructor to make this a singleton class
   */
  private GameViewController() {
  }

  /**
   * Returns the singleton instance of this class
   * @return the singleton instance of this class
   */
  public static GameViewController getInstance() {
    return gameViewController;
  }

  /**
   * Method that returns the Story object
   * @return the Story object
   */
  public Story getStory() {
    return story;
  }

  /**
   * Method that sets the story object
   * @param story the story object
   */
  public void setStory(Story story) {
    this.story = story;
  }

  /**
   * Method that initializes the game with the filepath to the story
   */
  public void initializeGame() {
    File storyFile = new File(storyPath);
    try {
      this.story = StoryReader.readStoryFromFile(storyFile);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Method that gets the game
   * @return the game
   */
  public Game getGame() {
    return game;
  }

  /**
   * Method that sets the current game
   * @param game the current game
   */
  public void setGame(Game game) {
    this.game = game;
  }

  /**
   * Method that returns the game path
   * @param path the game path
   */
  public void setGamePath(String path) {
    this.storyPath = path;
  }

  /**
   * Method that returns the player image from the PlayerViewController
   * @return the player image
   */
  public Image getPlayerImage() {
    return PlayerViewController.getInstance().getPlayerImage();
  }

  /**
   * Method that updates the players inventory images
   * @param inventory the inventory
   */
  public void updateInventoryIcon(String inventory) {
    if (inventory.equalsIgnoreCase(InventoryType.SHIELD.toString())) {
      inventoryIcon.setInventoryIcon(InventoryType.SHIELD);
    } else if (inventory.equalsIgnoreCase(InventoryType.SWORD.toString())) {
      inventoryIcon.setInventoryIcon(InventoryType.SWORD);
    } else if (inventory.equalsIgnoreCase(InventoryType.POTION.toString())) {
      inventoryIcon.setInventoryIcon(InventoryType.POTION);
    } else {
      inventoryList.add(inventory);
    }
  }

  /**
   * Method that returns the inventory image
   * @return the inventory image
   */
  public List<Image> getInventoryImages() {
    return inventoryIcon.getInventoryIcons();
  }

  /**
   * Method that returns the inventory without images
   * @return the inventory without images
   */
  public List<String> getInventoryList() {
    return inventoryList;
  }

  /**
   * Method that resets the inventory images
   */
  public void resetInventoryImages() {
    inventoryIcon.getInventoryIcons().clear();
  }


  public void resetPlayer() {
    //getPlayer();
    //getGame().getPlayer().setPlayer(PlayerViewController.getInstance().getPlayer());
    PlayerViewController.getInstance().resetPlayer();
    //PlayerViewController.getInstance().getPlayer();
  }
}
