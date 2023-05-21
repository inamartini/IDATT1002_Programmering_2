package edu.ntnu.idatt2001.model;

import edu.ntnu.idatt2001.model.player.Player;
import edu.ntnu.idatt2001.model.player.PlayerBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for PlayerBuilder
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.MM.DD
 *
 */
public class PlayerBuilderTest {

    @Test
    @DisplayName("Correct values are set")
    void correctValuesInBuilderPatternAreSet() {
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
    @DisplayName("IllegalArgumentException is thrown if name in builder is null")
    void builderPatternThrowsExceptionIfNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new PlayerBuilder(null));
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if health in builder is negative")
    void builderPatternThrowsExceptionIfHealthIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new PlayerBuilder("Player").health(-1));
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if gold in builder is negative")
    void builderPatternThrowsExceptionIfGoldIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new PlayerBuilder("Player").gold(-1));
    }
}
