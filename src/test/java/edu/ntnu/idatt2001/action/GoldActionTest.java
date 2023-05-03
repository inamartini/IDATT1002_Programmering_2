package edu.ntnu.idatt2001.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idatt2001.base.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for GoldAction
 * @author Malin Haugland Høli
 * @author Ina Martini
 *
 * @version 2023.MM.DD
 *
 */
public class GoldActionTest {

    private GoldAction goldAction;
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player("Malin", 4,4,4);
        goldAction = new GoldAction(10);
    }

    @Test
    @DisplayName("The correct amount of gold is added the player")
    public void executeAddsCorrectGoldToPlayer() {
        goldAction.execute(player);
        assertEquals(14, player.getGold());
    }
    @Test
    @DisplayName("IllegalArgumentException is thrown if player is null")
    public void executeThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> goldAction.execute(null));
    }
    @Test
    @DisplayName("IllegalArgumentException is thrown if gold is negative")
    public void constructorThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new GoldAction(-1));
    }
}
