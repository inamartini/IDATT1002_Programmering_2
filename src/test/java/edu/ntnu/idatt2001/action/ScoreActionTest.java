package edu.ntnu.idatt2001.action;

import edu.ntnu.idatt2001.model.player.Player;
import edu.ntnu.idatt2001.model.player.PlayerBuilder;
import edu.ntnu.idatt2001.model.action.ScoreAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ScoreAction
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 */
public class ScoreActionTest {

  private Player player;
  private ScoreAction scoreAction;

  @BeforeEach
  void setUp() {
    player = new PlayerBuilder("Player 1")
        .score(4)
        .build();
    scoreAction = new ScoreAction(5);
  }

  @Test
  @DisplayName("Score is added to the player's score")
  void executeAddsCorrectScoreToPlayer() {
    scoreAction.execute(player);
    assertEquals(9, player.getScore());
  }

  @Test
  @DisplayName("IllegalArgumentException is thrown if player is null")
  void executeThrowsIllegalArgumentExceptionIfPlayerIsNull() {
    assertThrows(IllegalArgumentException.class, () -> scoreAction.execute(null));
  }
}
