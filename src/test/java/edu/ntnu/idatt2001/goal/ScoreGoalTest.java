package edu.ntnu.idatt2001.goal;

import edu.ntnu.idatt2001.model.player.Player;
import edu.ntnu.idatt2001.model.player.PlayerBuilder;
import edu.ntnu.idatt2001.model.goal.ScoreGoal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ScoreGoal
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 */
public class ScoreGoalTest {

  private Player player;
  private ScoreGoal scoreGoal;

  @BeforeEach
  public void setUp() {
    scoreGoal = new ScoreGoal(10);
  }

  @Nested
  @DisplayName("Functionality works as expected")
  class IsFulfilledWorksAsExpected {

    @Test
    @DisplayName("True is returned if player has more than minimum points")
    void returnsTrueIfPlayerHasMorePointsThanMinimum() {
      player = new PlayerBuilder("Player 2")
          .score(15)
          .build();
      assertTrue(scoreGoal.isFulfilled(player));
    }

    @Test
    @DisplayName("False is returned if player has less gold than minimum")
    void returnsFalseIfPlayerHasLessPointsThanMinimum() {
      player = new PlayerBuilder("Player 1")
          .score(5)
          .build();
      assertFalse(scoreGoal.isFulfilled(player));
    }
  }

  @Nested
  @DisplayName("Exceptions are thrown as expected")
  class ExceptionsAreThrown {

    @Test
    @DisplayName("IllegalArgumentException is thrown if minimumScore is negative")
    void throwsIllegalArgumentExceptionIfMinimumScoreIsNegative() {
      assertThrows(IllegalArgumentException.class, () -> new ScoreGoal(-1));
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if player is null")
    void throwsIllegalArgumentExceptionIfPlayerIsNull() {
      assertThrows(IllegalArgumentException.class, () -> scoreGoal.isFulfilled(null));
    }
  }
}


