package edu.ntnu.idatt2001.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

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
      assertThrows(IllegalArgumentException.class, () -> player = new Player(null, 100, 100, 100));
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if health is negative")
    void constructorThrowsIllegalArgumentExceptionIfHealthIsNegative() {
      assertThrows(IllegalArgumentException.class,
              () -> player = new Player("Player", -1, 100, 100));
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if item is null")
    void throwsIllegalArgumentExceptionIfItemIsNull() {
      player = new Player("Player", 100, 100, 100);
      assertThrows(IllegalArgumentException.class, () -> player.addToInventory(null));
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if health is negative")
    void throwsIllegalArgumentExceptionIfHealthIsNegative() {
      player = new Player("Player", 100, 100, 100);
      assertThrows(IllegalArgumentException.class, () -> player.addHealth(-1));
    }
  }
  @BeforeEach
  void setUp() {
    player = new Player("Player", 100, 100, 100);
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
  @Nested
  @DisplayName("Builder pattern works as expected")
  class builderPatternWorksAsExpected {

    @Test
    @DisplayName("Correct values are set")
    void correctValuesInBuilderPatternAreSet() {
      Player player = new Player.Builder("Player")
              .gold(100)
              .health(100)
              .score(100)
              .inventory(Arrays.asList("Item1", "Item2"))
              .build();
      assertEquals(100, player.getGold());
      assertEquals(100, player.getHealth());
      assertEquals(100, player.getScore());
      assertEquals(Arrays.asList("Item1", "Item2"), player.getInventory());
      assertEquals("Player", player.getName());
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if name in builder is null")
    void builderPatternThrowsExceptionIfNameIsNull() {
      assertThrows(IllegalArgumentException.class, () -> new Player.Builder(null));
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if health in builder is negative")
    void builderPatternThrowsExceptionIfHealthIsNegative() {
      assertThrows(IllegalArgumentException.class, () -> new Player.Builder("Player").health(-1));
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if score in builder is negative")
    void builderPatternThrowsExceptionIfScoreIsNegative() {
      assertThrows(IllegalArgumentException.class, () -> new Player.Builder("Player").score(-1));
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if gold in builder is negative")
    void builderPatternThrowsExceptionIfGoldIsNegative() {
      assertThrows(IllegalArgumentException.class, () -> new Player.Builder("Player").gold(-1));
    }

    @Test
    @DisplayName("The inventory is set correctly")
    void builderPatternSetsInventoryCorrectly() {
      List<String> inventory = Arrays.asList("Item1", "Item2");
      Player player = new Player.Builder("Player")
              .inventory(inventory)
              .build();
      assertEquals(inventory, player.getInventory());
    }
  }
}


