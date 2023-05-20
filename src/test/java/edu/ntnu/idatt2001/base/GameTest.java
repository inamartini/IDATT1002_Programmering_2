package edu.ntnu.idatt2001.base;

import edu.ntnu.idatt2001.goal.Goal;
import edu.ntnu.idatt2001.goal.ScoreGoal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
 * @version 2023.05.20
 */

public class GameTest {

  private Game game;
  private Passage openingPassage;
  private List<Goal> goals ;
  private Story story ;
  private Player player ;

  @BeforeEach
  void setUp() {
    player = new PlayerBuilder("Player")
            .score(2)
            .health(1)
            .gold(3)
            .build();
    openingPassage = new Passage("PassageTitle", "Content");
    story = new Story("Title", openingPassage);
    goals = new ArrayList<>();
    goals.add(new ScoreGoal(10));

    game = new Game(player, story, goals);
  }

  @Nested
  @DisplayName("Tests that exceptions are thrown")
  class ExceptionsAreThrown {

    @Test
    @DisplayName("IllegalArgumentException is thrown when player is null")
    void illegalArgumentIsThrownIfPlayerIsNull() {
      assertThrows(IllegalArgumentException.class, () -> new Game(null, story, goals));
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown when story is null")
    void illegalArgumentIsThrownIfStoryIsNull() {
      assertThrows(IllegalArgumentException.class, () -> new Game(player, null, goals));
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown when goal is null")
    void illegalArgumentIsThrownIfGoalIsNull() {
      assertThrows(IllegalArgumentException.class, () -> new Game(player, story, null));
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown when link is null")
    void IllegalArgumentIsThrownIfLinkIsNull() {
      assertThrows(IllegalArgumentException.class, () -> game.go(null));
    }
  }

  @Nested
  @DisplayName("Tests that correct values are returned")
  class CorrectValuesAreReturned {

    @Test
    @DisplayName("Correct players are returned")
    void getPlayerTest() {
      assertAll("Player",
              () -> assertEquals("Player", game.getPlayer().getName()),
              () -> assertEquals(1, game.getPlayer().getHealth()),
              () -> assertEquals(2, game.getPlayer().getScore()),
              () -> assertEquals(3, game.getPlayer().getGold()));
    }

    @Test
    @DisplayName("Correct info about the story is returned")
    void correctValuesForStoryIsReturned() {
      assertAll("Story",
              () -> assertEquals("Title", game.getStory().getTitle()),
              () -> assertEquals("PassageTitle", game.getStory().getOpeningPassage().getTitle()),
              () -> assertEquals("Content", game.getStory().getOpeningPassage().getContent()));
    }

    @Test
    @DisplayName("Correct goals are returned")
    void correctValuesForTheGoalsAreReturned() {
      assertEquals(goals, game.getGoals());
    }

    @Test
    @DisplayName("Correct opening passage is returned")
    void correctValuesForTheOpeningPassageIsReturned() {
      assertEquals(openingPassage, game.begin());
    }

    @Test
    @DisplayName("Correct passage is returned")
    void correctValuesForThePassageIsReturned() {
      Link link = new Link("Text", "PassageTitle end");
      Passage end = new Passage("PassageTitle end", "Content end");
      story.addPassage(end);
      story.getOpeningPassage().addLink(link);
      assertEquals(end, game.go(link));
    }

    @Test
    @DisplayName("Current passage is returned")
    void correctValuesForTheCurrentPassageIsReturned() {
      assertEquals(openingPassage, game.getCurrentPassage());
    }

    @Test
    @DisplayName("Current passage is set as current passage")
    void correctValuesForTheCurrentPassageIsSet() {
      Link link = new Link("Text", "PassageTitle end");
      Passage newCurrentPassage = new Passage("PassageTitle end", "Content end");
      story.addPassage(newCurrentPassage);
      story.getOpeningPassage().addLink(link);
      game.go(link);
      game.setCurrentPassage(newCurrentPassage);
      assertEquals(newCurrentPassage, game.getCurrentPassage());
    }
  }
}
