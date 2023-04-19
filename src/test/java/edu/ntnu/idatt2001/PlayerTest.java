package edu.ntnu.idatt2001;


import edu.ntnu.idatt2001.base.Player;
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
 * @version 2023.MM.DD
 */


public class PlayerTest {

  private Player player;

  @Nested
  class ExceptionsAreThrown {
    @Test
    @DisplayName("Constructor should throw IllegalArgumentException if name is null")
    void constructorIllegalArgumentExceptionIfNameIsNull() {
      assertThrows(IllegalArgumentException.class, () -> player = new Player(null, 100, 100, 100));
    }

    @Test
    @DisplayName("Constructor should throw IllegalArgumentException if health is negative")
    void constructorThrowsIllegalArgumentExceptionIfHealthIsNegative() {
      assertThrows(IllegalArgumentException.class,
              () -> player = new Player("Player", -1, 100, 100));
    }

    @Test
    @DisplayName("addToInventory() should throw IllegalArgumentException if item is null")
    void throwsIllegalArgumentExceptionIfItemIsNull() {
      player = new Player("Player", 100, 100, 100);
      assertThrows(IllegalArgumentException.class, () -> player.addToInventory(null));
    }

    @Test
    @DisplayName("addHealth() should throw IllegalArgumentException if health is negative")
    void throwsIllegalArgumentExceptionIfHealthIsNegative() {
      player = new Player("Player", 100, 100, 100);
      assertThrows(IllegalArgumentException.class, () -> player.addHealth(-1));
    }
  }

  @Nested
  class AddMethodsWorkAsExpected {

    @BeforeEach
    void setUp() {
      player = new Player("Player", 100, 100, 100);
    }

    @Test
    @DisplayName("addGold() should add gold to player")
    void addGoldToPlayer() {
      player.addGold(10);
      assertEquals(110, player.getGold());
    }

    @Test
    @DisplayName("addHealth() should add health to player")
    void addHealthToPlayer() {
      player.addHealth(10);
      assertEquals(110, player.getHealth());
    }

    @Test
    @DisplayName("addScore() should add score to player")
    void addScoreToPlayer() {
      player.addScore(10);
      assertEquals(110, player.getScore());
    }

    @Test
    @DisplayName("addToInventory() should add the item to inventory")
    void addItemToInventory() {
      String item = "Item";
      player.addToInventory(item);
      assertEquals(item, player.getInventory().get(0));
    }
  }

  @Nested
  class GetMethodsWorkAsExpected {

    @BeforeEach
    void setUp() {
      player = new Player("Player", 100, 100, 100);
    }

    @Test
    @DisplayName("getGold() should return the amount of gold")
    void getGold() {
      assertEquals(100, player.getGold());
    }

    @Test
    @DisplayName("getHealth() should return the amount of health")
    void getHealth() {
      assertEquals(100, player.getHealth());
    }

    @Test
    @DisplayName("getScore() should return the amount of score")
    void getScore() {
      assertEquals(100, player.getScore());
    }

    @Test
    @DisplayName("getInventory() should return the inventory")
    void getInventory() {
      String item = "Item";
      player.addToInventory(item);
      assertEquals(item, player.getInventory().get(0));
    }

    @Test
    @DisplayName("getName() should return the name")
    void getName() {
      assertEquals("Player", player.getName());
    }
  }
}

