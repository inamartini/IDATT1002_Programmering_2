package edu.ntnu.idatt2001.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idatt2001.base.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for HealthAction
 * @author Malin Haugland Høli
 * @author Ina Martini
 *
 * @version 2023.MM.DD
 *
 */
public class HealthActionTest {

    private Player player;
    private HealthAction healthAction;

    @BeforeEach
    public void setUp() {
        player = new Player("Ina", 3,3, 3);
        healthAction = new HealthAction(3);
    }

    @Test
    @DisplayName("The player's health is increased by the correct amount")
    public void executeAddsCorrectHealthToPlayer() {
        healthAction.execute(player);
        assertEquals(6, player.getHealth());
    }
    @Test
    @DisplayName("IllegalArgumentException is thrown if player is null")
    public void executeThrowsIllegalArgumentExceptionIfPlayerIsNull() {
        assertThrows(IllegalArgumentException.class, () -> healthAction.execute(null));
    }
}
