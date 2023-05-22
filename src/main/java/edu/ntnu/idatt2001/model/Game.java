package edu.ntnu.idatt2001.model;

import edu.ntnu.idatt2001.model.goal.Goal;
import edu.ntnu.idatt2001.model.player.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * Game class that represents a game. A game has a player, a story and goals.
 * This class provides methods for getting and setting the player, story and goals.
 * It also provides methods for getting and setting the current passage of the game.
 * The game class is used in order to start a game and go between passages.
 *
 * @author Malin Haugland Holi
 * @author Ina Martini
 * @version 2023.05.22
 */
public class Game {

  /**
   * The player of the game.
   */
  private Player player;

  /**
   * The story of the game.
   */
  private Story story;

  /**
   * The goals of the game.
   */
  private List<Goal> goals;

  /**
   * The current passage of the game.
   */
  private Passage currentPassage;

  /**
   * Constructor for the Game class. Takes in a player, a story and goals and checks
   * if the parameters are valid. Sets the current passage to the opening passage of the story.
   *
   * @param player The player of the game.
   * @param story  The story of the game.
   * @param goals  The goals of the game.
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
   * Returns the player of the game.
   *
   * @return The player of the game as Player.
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Returns the story of the game.
   *
   * @return The story of the game as Story.
   */
  public Story getStory() {
    return story;
  }

  /**
   * Returns the goals of the game.
   *
   * @return The goals of the game as List.
   */
  public List<Goal> getGoals() {
    return goals;
  }

  /**
   * Returns the current passage of the game.
   *
   * @return The current passage of the game as Passage.
   */
  public Passage getCurrentPassage() {
    return currentPassage;
  }

  /**
   * Sets the current passage of the game.
   *
   * @param currentPassage The current passage of the game.
   */
  public Passage setCurrentPassage(Passage currentPassage) {
    if (currentPassage == null) {
      throw new IllegalArgumentException("Current passage can't be null");
    }
    this.currentPassage = currentPassage;
    return currentPassage;
  }

  /**
   * Returns the opening passage of the story.
   *
   * @return The opening passage of the story as Passage.
   */
  public Passage begin() {
    return story.getOpeningPassage();
  }

  /**
   * Returns the passage that is linked to the given link.
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