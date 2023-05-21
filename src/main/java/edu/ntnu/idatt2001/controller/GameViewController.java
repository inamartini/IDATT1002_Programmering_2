package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.model.player.InventoryIcon;
import edu.ntnu.idatt2001.model.player.InventoryType;
import edu.ntnu.idatt2001.model.*;
import edu.ntnu.idatt2001.model.goal.Goal;
import edu.ntnu.idatt2001.model.goal.GoldGoal;
import edu.ntnu.idatt2001.model.goal.HealthGoal;
import edu.ntnu.idatt2001.model.goal.InventoryGoal;
import edu.ntnu.idatt2001.model.goal.ScoreGoal;
import edu.ntnu.idatt2001.model.player.Player;
import edu.ntnu.idatt2001.util.AlertUtil;
import edu.ntnu.idatt2001.util.StoryReader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Class that handles the game view
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.MM.DD
 */
public class GameViewController {

  private static GameViewController gameViewController= new GameViewController();
  private PlayerViewController playerViewController = PlayerViewController.getInstance();

  private Story story;
  private Game game;
  private Player player;
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
      AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
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
    if(inventory == null) {
      throw new IllegalArgumentException("Inventory can't be null");
    }
    try {
      if (inventory.equalsIgnoreCase(InventoryType.SHIELD.toString())) {
        inventoryIcon.setInventoryIcon(InventoryType.SHIELD);
      } else if (inventory.equalsIgnoreCase(InventoryType.SWORD.toString())) {
        inventoryIcon.setInventoryIcon(InventoryType.SWORD);
      } else if (inventory.equalsIgnoreCase(InventoryType.POTION.toString())) {
        inventoryIcon.setInventoryIcon(InventoryType.POTION);
      } else {
        inventoryList.add(inventory);
      }
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Failed to update inventory icon");
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

  /**
   * Method that resets game
   */
  public void resetGame() {
    game.setCurrentPassage(story.getOpeningPassage());
    playerViewController.resetPlayer();
  }

  /**
   * Method that returns a VBox with the goal status of the player
   * @return a VBox with the goal status of the player
   */

  public VBox goalStatus() {
    player = playerViewController.getPlayer();
    VBox goalStatus = new VBox();

    Label goalLabel = new Label("Goals");
    goalStatus.getChildren().add(goalLabel);

    for (Goal goal : game.getGoals()) {
      HBox goalBox = new HBox();
      Label goalName = new Label(goal.toString());
      ProgressBar goalProgress = new ProgressBar();
      goalProgress.setProgress(0.0);
      goalProgress.getStyleClass().add("progress-bar");
      double progress = 0.0;

      if(goal.getClass() == InventoryGoal.class) {
        progress = (double) player.getInventory().size() / ((InventoryGoal) goal).getMandatoryItems().size();
      } else if (goal.getClass() == GoldGoal.class) {
        progress = (double) player.getGold() / ((GoldGoal) goal).getMinimumGold();
      } else if (goal.getClass() == ScoreGoal.class) {
        progress = (double) player.getScore() / ((ScoreGoal) goal).getMinimumPoints();
      } else if (goal.getClass() == HealthGoal.class) {
        progress = (double) player.getHealth() / ((HealthGoal) goal).getMinimumHealth();
      }
      if(progress != 0.0) {
        goalProgress.setProgress(progress);
      } else {
        goalProgress.setProgress(0.0);
      }
      goalBox.getChildren().addAll(goalName, goalProgress);
      goalStatus.getChildren().add(goalBox);
    }
    return goalStatus;
  }
}
