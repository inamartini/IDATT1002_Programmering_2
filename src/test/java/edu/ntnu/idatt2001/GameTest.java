package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.base.*;
import edu.ntnu.idatt2001.goal.Goal;
import edu.ntnu.idatt2001.goal.ScoreGoal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class represents a test class for the Game class.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 *
 * @version 2023.MM.DD
 */

public class GameTest {

  private Game game;
  private Passage openingPassage;
  private List<Goal> goals ;
  private Story story ;
  private Player player ;

  @BeforeEach
  void setUp() {
    player = new Player("Name", 1, 2, 3);
    openingPassage = new Passage("PassageTitle", "Content");
    story = new Story("Title", openingPassage);
    goals = new ArrayList<>();
    goals.add(new ScoreGoal(10));

    game = new Game(player, story, goals);
  }

  @Test
  @DisplayName("Tests if the constructor throws an exception when player is null")
  void constructorPlayerNullTest() {
    assertThrows(IllegalArgumentException.class, () -> new Game(null, story, goals));
  }

  @Test
  @DisplayName("Tests if the constructor throws an exception when story is null")
  void constructorStoryNullTest() {
    assertThrows(IllegalArgumentException.class, () -> new Game(player, null, goals));
  }

  @Test
  @DisplayName("Tests if the constructor throws an exception when goals is null")
  void constructorGoalsNullTest() {
    assertThrows(IllegalArgumentException.class, () -> new Game(player, story, null));
  }

  @Test
  @DisplayName("Tests if getPlayer() returns the correct values")
  void getPlayerTest() {
    assertAll("Player",
            () -> assertEquals("Name", game.getPlayer().getName()),
            () -> assertEquals(1, game.getPlayer().getHealth()),
            () -> assertEquals(2, game.getPlayer().getScore()),
            () -> assertEquals(3, game.getPlayer().getGold()));
  }

  @Test
  @DisplayName("Tests if getStory() returns the correct values")
  void getStoryTest() {
    assertAll("Story",
            () -> assertEquals("Title", game.getStory().getTitle()),
            () -> assertEquals("PassageTitle", game.getStory().getOpeningPassage().getTitle()),
            () -> assertEquals("Content", game.getStory().getOpeningPassage().getContent()));
  }


  @Test
  @DisplayName("Tests if getGoals() returns the correct values")
  void getGoalsTest() {
    assertEquals(goals, game.getGoals());
  }

  @Test
  @DisplayName("Tests if begin() returns the right value")
  void beginTest() {
    assertEquals(openingPassage, game.begin());
  }

  @Test
  @DisplayName("Tests if go() returns the right value")
  void goTest() {
    Link link = new Link("Text", "PassageTitle end");
    Passage end = new Passage("PassageTitle end", "Content end");
    story.addPassage(end);
    story.getOpeningPassage().addLink(link);
    assertEquals(end, game.go(link));
  }

  @Test
  @DisplayName("Tests if go() returns the right value when the link is null")
  void goNullTest() {
    assertThrows(IllegalArgumentException.class, () -> game.go(null));
  }
}
