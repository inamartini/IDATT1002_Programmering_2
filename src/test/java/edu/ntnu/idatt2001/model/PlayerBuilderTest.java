package edu.ntnu.idatt2001.model;

import edu.ntnu.idatt2001.model.player.Player;
import edu.ntnu.idatt2001.model.player.PlayerBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for PlayerBuilder
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 *
 */
public class PlayerBuilderTest {

    @Test
    @DisplayName("Correct values are set")
    void correctValuesInBuilderPatternAreSet() {
        List<String> inventory = new ArrayList<>();
        inventory.add("Item");

        Player player = new PlayerBuilder("Player")
                    .gold(100)
                    .health(100)
                    .score(100)
                    .build();
            assertEquals(100, player.getGold());
            assertEquals(100, player.getHealth());
            assertEquals(100, player.getScore());
            assertEquals("Player", player.getName());
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if inventory is null")
    void constructorThrowsIllegalArgumentExceptionIfInventoryIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new PlayerBuilder("Player").inventory(null));
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if inventory is empty")
    void constructorThrowsIllegalArgumentExceptionIfInventoryIsEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                new PlayerBuilder("Player").inventory(new ArrayList<>()));
    }

}
