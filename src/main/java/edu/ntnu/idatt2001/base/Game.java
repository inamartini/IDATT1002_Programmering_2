package edu.ntnu.idatt2001.base;

import edu.ntnu.idatt2001.goal.Goal;

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
   * @return The goals of the game as List<Goal>.
   */
  public List<Goal> getGoals() {
    return goals;
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
    return story.getPassage(link);
  }
}
