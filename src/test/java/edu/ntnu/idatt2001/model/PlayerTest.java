package edu.ntnu.idatt2001.model;

import edu.ntnu.idatt2001.model.player.Player;
import edu.ntnu.idatt2001.model.player.PlayerBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for Player.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 *
 * @version 2023.MM.DD
 */
public class PlayerTest {

  private Player player;

  @Nested
  @DisplayName("Tests that exceptions are thrown")
  class ExceptionsAreThrown {
    @Test
    @DisplayName("IllegalArgumentException is thrown if name is null")
    void constructorIllegalArgumentExceptionIfNameIsNull() {
      assertThrows(IllegalArgumentException.class, () ->
              player = new PlayerBuilder(null).build());
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if health is negative")
    void constructorThrowsIllegalArgumentExceptionIfHealthIsNegative() {
      assertThrows(IllegalArgumentException.class, () ->
              player = new PlayerBuilder("Player").health(-1).build());
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if item is null")
    void throwsIllegalArgumentExceptionIfItemIsNull() {
      player = new PlayerBuilder("Player")
              .score(100)
              .health(100)
              .build();
      assertThrows(IllegalArgumentException.class, () -> player.addToInventory(null));
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if health is negative")
    void throwsIllegalArgumentExceptionIfHealthIsNegative() {
      player = new PlayerBuilder("Player")
              .score(100)
              .build();
      assertThrows(IllegalArgumentException.class, () -> player.addHealth(-1));
    }
  }
  @BeforeEach
  void setUp() {
    player = new PlayerBuilder("Player")
            .gold(100)
            .health(100)
            .score(100)
            .build();
  }

  @Nested
  @DisplayName("Tests that add methods work as expected")
  class AddMethodsWorkAsExpected {

    @Test
    @DisplayName("Gold is added to player")
    void addGoldToPlayer() {
      player.addGold(10);
      assertEquals(110, player.getGold());
    }

    @Test
    @DisplayName("Health is added to player")
    void addHealthToPlayer() {
      player.addHealth(10);
      assertEquals(110, player.getHealth());
    }

    @Test
    @DisplayName("Score is added to player")
    void addScoreToPlayer() {
      player.addScore(10);
      assertEquals(110, player.getScore());
    }

    @Test
    @DisplayName("Item is added to inventory")
    void addItemToInventory() {
      String item = "Item";
      player.addToInventory(item);
      assertEquals(item, player.getInventory().get(0));
    }
  }

  @Nested
  @DisplayName("Tests that get methods work as expected")
  class GetMethodsWorkAsExpected {

    @Test
    @DisplayName("The correct amount of gold is returned")
    void getGold() {
      assertEquals(100, player.getGold());
    }

    @Test
    @DisplayName("The correct amount of health is returned")
    void getHealth() {
      assertEquals(100, player.getHealth());
    }

    @Test
    @DisplayName("The correct amount of score is returned")
    void getScore() {
      assertEquals(100, player.getScore());
    }

    @Test
    @DisplayName("The correct inventory is returned")
    void getInventory() {
      String item = "Item";
      player.addToInventory(item);
      assertEquals(item, player.getInventory().get(0));
    }

    @Test
    @DisplayName("The correct name is returned")
    void getName() {
      assertEquals("Player", player.getName());
    }
  }
}


