package edu.ntnu.idatt2001.goal;

import edu.ntnu.idatt2001.model.player.Player;
import edu.ntnu.idatt2001.model.player.PlayerBuilder;
import edu.ntnu.idatt2001.model.goal.GoldGoal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for GoldGoal
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 */

public class GoldGoalTest {

  private Player player;
  private GoldGoal goldGoal;

  @BeforeEach
  public void setUp() {
    goldGoal = new GoldGoal(10);
  }

  @Nested
  @DisplayName("Functionality works as expected")
  class IsFulfilledWorksAsExpected {

    @Test
    @DisplayName("True is returned if player has more gold than minimum")
    void returnsTrueIfPlayerHasMoreGoldThanMinimum() {
      player = new PlayerBuilder("Player 2")
          .gold(15)
          .build();
      assertTrue(goldGoal.isFulfilled(player));
    }

    @Test
    @DisplayName("False is returned if player has less gold than minimum")
    void returnsFalseIfPlayerHasLessGoldThanMinimum() {
      player = new PlayerBuilder("Player 1")
          .gold(5)
          .build();
      assertFalse(goldGoal.isFulfilled(player));
    }
  }

  @Nested
  @DisplayName("Exceptions are thrown as expected")
  class ExceptionsAreThrown {

    @Test
    @DisplayName("IllegalArgumentException is thrown if minimumGold is negative")
    void throwsIllegalArgumentExceptionIfMinimumGoldIsNegative() {
      assertThrows(IllegalArgumentException.class, () -> new GoldGoal(-1));
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if player is null")
    void throwsIllegalArgumentExceptionIfPlayerIsNull() {
      assertThrows(IllegalArgumentException.class, () -> goldGoal.isFulfilled(null));
    }
  }
}
