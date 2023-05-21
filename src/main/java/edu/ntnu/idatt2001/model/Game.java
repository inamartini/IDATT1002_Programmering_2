package edu.ntnu.idatt2001.model;

import edu.ntnu.idatt2001.model.goal.Goal;
import edu.ntnu.idatt2001.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Game class that represents a game.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.MM.DD
 */

public class Game {

  private Player player;
  private Story story;
  private List<Goal> goals;
  private Passage currentPassage;

  /**
   * Constructor for the Game class.
   *
   * @param player The player of the game.
   * @param story The story of the game.
   * @param goals The goals of the game.
   */
  public Game(Player player, Story story, List<Goal> goals) {
    if (player == null) {
      throw new IllegalArgumentException("Player can't be null");
    }
    if (story == null) {
      throw new IllegalArgumentException("Story can't be null");
    }
    if (goals == null) {
      throw new IllegalArgumentException("Goals can't be null");
    }
    this.player = player;
    this.story = story;
    this.goals = new ArrayList<>(goals);
    this.currentPassage = story.getOpeningPassage();
  }

  /**
   * Method that returns the player of the game.
   *
   * @return The player of the game as Player.
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Method that returns the story of the game.
   *
   * @return The story of the game as Story.
   */
  public Story getStory() {
    return story;
  }

  /**
   * Method that returns the goals of the game.
   *
   * @return The goals of the game as List.
   */
  public List<Goal> getGoals() {
    return goals;
  }

  /**
   * Method that returns the current passage of the game.
   *
   * @return The current passage of the game as Passage.
   */
  public Passage getCurrentPassage() {
    return currentPassage;
  }

  /**
   * Method that sets the current passage of the game.
   *
   * @param currentPassage The current passage of the game.
   */
  public Passage setCurrentPassage(Passage currentPassage) {
    if (currentPassage == null) {
      throw new IllegalArgumentException("Current passage can't be null");
    }
    return this.currentPassage = currentPassage;
  }

  /**
   * Method that returns the opening passage of the story.
   *
   * @return The opening passage of the story as Passage.
   */
  public Passage begin() {
    return story.getOpeningPassage();
  }

  /**
   * Method that returns the passage that is linked to the given link.
   *
   * @param link The link to the passage.
   * @return The passage that is linked to the given link as Passage.
   */
  public Passage go(Link link) {
    if (link == null) {
      throw new IllegalArgumentException("Link can't be null");
    }
    try {
      return story.getPassage(link);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Link is not valid");
    }
  }
}