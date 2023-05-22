package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.model.Game;
import edu.ntnu.idatt2001.model.Story;
import edu.ntnu.idatt2001.model.goal.Goal;
import edu.ntnu.idatt2001.model.goal.GoldGoal;
import edu.ntnu.idatt2001.model.goal.HealthGoal;
import edu.ntnu.idatt2001.model.goal.InventoryGoal;
import edu.ntnu.idatt2001.model.goal.ScoreGoal;
import edu.ntnu.idatt2001.model.player.InventoryIcon;
import edu.ntnu.idatt2001.model.player.InventoryType;
import edu.ntnu.idatt2001.model.player.Player;
import edu.ntnu.idatt2001.util.StoryReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Pair;


/**
 * Singleton class that handles the game view. The class includes several methods
 * that are used to display the game view, and to update the game view.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 */
public class GameViewController {

  /**
   * The singleton instance of this class.
   */
  private static GameViewController gameViewController = new GameViewController();

  /**
   * The singleton instance of the player controller used to update the player.
   */
  private PlayerController playerController = PlayerController.getInstance();

  /**
   * The story of the game to be played.
   */
  private Story story;

  /**
   * The game object.
   */
  private Game game;


  /**
   * The player of the game.
   */
  private Player player;

  /**
   * The path to the story file.
   */
  private String storyPath;

  /**
   * Inventory icon to update the inventory.
   */
  private InventoryIcon inventoryIcon = new InventoryIcon();

  /**
   * The list of inventory items.
   */
  private List<String> inventoryList = new ArrayList<>();

  /**
   * Private constructor to make this a singleton class.
   */
  private GameViewController() {
  }

  /**
   * Returns the singleton instance of this class.
   *
   * @return the singleton instance of this class
   */
  public static GameViewController getInstance() {
    return gameViewController;
  }

  /**
   * Method that returns the Story object.
   *
   * @return the Story object
   */
  public Story getStory() {
    return story;
  }

  /**
   * Method that sets the story object.
   *
   * @param story the story object
   */
  public void setStory(Story story) {
    this.story = story;
  }

  /**
   * Method that initializes the game with the filepath to the story.
   * If the story is not readable, and exception is thrown.
   */
  public void initializeGame() {
    File storyFile = new File(storyPath);
    try {
      this.story = StoryReader.readStoryFromFile(storyFile);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Failed to read story from file");
    }
  }

  /**
   * Method that gets the game.
   *
   * @return the game
   */
  public Game getGame() {
    return game;
  }

  /**
   * Method that sets the current game.
   *
   * @param game the current game
   */
  public void setGame(Game game) {
    this.game = game;
  }

  /**
   * Method that returns the game path.
   *
   * @param path the game path
   */
  public void setGamePath(String path) {
    this.storyPath = path;
  }

  /**
   * Method that returns the player image from the PlayerViewController.
   *
   * @return the player image
   */
  public Image getPlayerImage() {
    return PlayerController.getInstance().getPlayerImage();
  }

  /**
   * Method that updates the players inventory images. The method takes in inventory as a string,
   * and checks if the String is not null. If the inventory string matches the inventory type,
   * the inventory icon is set to the inventory type. If the inventory string does not match
   * the String is added to the inventory list and will be displayed as text instead.
   *
   * @param inventory the inventory
   */
  public void updateInventoryIcon(String inventory) {
    if (inventory == null) {
      throw new IllegalArgumentException("Inventory can't be null");
    }
    try {
      if (inventory.equalsIgnoreCase(InventoryType.SHIELD.toString())) {
        inventoryIcon.setInventoryIcon(InventoryType.SHIELD);
      } else if (inventory.equalsIgnoreCase(InventoryType.SWORD.toString())) {
        inventoryIcon.setInventoryIcon(InventoryType.SWORD);
      } else if (inventory.equalsIgnoreCase(InventoryType.POTION.toString())) {
        inventoryIcon.setInventoryIcon(InventoryType.POTION);
      } else if (inventory.equalsIgnoreCase(InventoryType.COMPUTER.toString())) {
        inventoryIcon.setInventoryIcon(InventoryType.COMPUTER);
      } else {
        inventoryList.add(inventory);
      }
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Failed to update inventory icon");
    }
  }

  /**
   * Method that returns the inventory image of the inventory.
   *
   * @return the inventory image
   */
  public List<Image> getInventoryImages() {
    return inventoryIcon.getInventoryIcons();
  }

  /**
   * Method that returns the inventory without images if the inventory does not match.
   * the enum types set in the InventoryType class
   *
   * @return the inventory without images
   */
  public List<String> getInventoryList() {
    return inventoryList;
  }

  /**
   * Method that resets the inventory images.
   */
  public void resetInventoryImages() {
    inventoryIcon.getInventoryIcons().clear();
  }

  /**
   * Method that resets the inventory list.
   */
  public void resetInventoryList() {
    inventoryList.clear();
  }

  /**
   * Method that resets game.
   * The method resets the current passage to the opening passage, and resets the player.
   */
  public void resetGame() {
    game.setCurrentPassage(story.getOpeningPassage());
    playerController.resetPlayer();
  }

  /**
   * Method that returns a VBox with the goal status of the player.
   * The goal status is displayed as progress bar, and will be updated continuously
   * as the player progresses in the game based on what goals the player is achieving.
   * To make the player info more readable, a region spacer aligns
   * the goalProgress to the right and the goalName to the left.
   *
   * @return a VBox with the goal status of the player
   */
  public VBox goalStatus() {
    player = playerController.getPlayer();
    VBox goalStatus = new VBox();
    goalStatus.setSpacing(5);

    Label goalLabel = new Label("Goals");
    goalStatus.getChildren().add(goalLabel);
    goalLabel.setAlignment(Pos.CENTER_LEFT);

    for (Goal goal : game.getGoals()) {
      HBox goalBox = new HBox();
      goalBox.setSpacing(10);
      goalBox.setAlignment(Pos.CENTER_RIGHT);

      Label goalName = new Label(goal.toString());
      goalName.setAlignment(Pos.CENTER_LEFT);

      ProgressBar goalProgress = new ProgressBar();
      goalProgress.setProgress(0.0);
      goalProgress.getStyleClass().add("progress-bar");
      double progress = 0.0;

      if (goal.getClass() == InventoryGoal.class) {
        progress = checkIfInventoryGoalsAreFulfilled();
      } else if (goal.getClass() == GoldGoal.class) {
        progress = (double) player.getGold() / ((GoldGoal) goal).getMinimumGold();
      } else if (goal.getClass() == ScoreGoal.class) {
        progress = (double) player.getScore() / ((ScoreGoal) goal).getMinimumPoints();
      } else if (goal.getClass() == HealthGoal.class) {
        progress = (double) player.getHealth() / ((HealthGoal) goal).getMinimumHealth();
      }

      if (progress != 0.0) {
        goalProgress.setProgress(progress);
      } else {
        goalProgress.setProgress(0.0);
      }

      Region spacer = new Region();
      HBox.setHgrow(spacer, Priority.ALWAYS);

      goalBox.getChildren().addAll(goalName, spacer, goalProgress);
      goalStatus.getChildren().add(goalBox);
    }
    return goalStatus;
  }

  /**
   * Method that checks if all the goals are fulfilled.
   *
   * @return true if all the goals are fulfilled, false otherwise
   */
  public boolean checkIfAllGoalsAreFulfilled() {
    return game.getGoals().stream().allMatch(goal -> goal.isFulfilled(player));
  }

  /**
   * Method that checks if the inventory goals are fulfilled.
   *
   * @return the total number of inventory goals fulfilled
   */
  public double checkIfInventoryGoalsAreFulfilled() {
    return game.getGoals().stream()
        .filter(InventoryGoal.class::isInstance)
        .mapToDouble(goal -> goal.isFulfilled(player) ? 1.0 : 0.0)
        .sum();
  }

  /**
   * Method that returns the goal summary.
   *
   * @return the goal summary
   */
  public List<Pair<String, String>> getGoalSummary() {
    List<Pair<String, String>> summaries = new ArrayList<>();

    for (Goal goal : game.getGoals()) {
      String goalName = goal.toString();
      boolean fulfilled = goal.isFulfilled(player);
      String status = fulfilled ? "Fulfilled" : "Not Fulfilled";

      summaries.add(new Pair<>(goalName, status));
    }

    return summaries;
  }
}
