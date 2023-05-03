package edu.ntnu.idatt2001.action;

import edu.ntnu.idatt2001.base.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for InventoryAction
 * @author Malin Haugland Høli
 * @author Ina Martini
 *
 * @version 2023.MM.DD
 *
 */

public class InventoryActionTest {

    private Player player;
    private InventoryAction inventoryAction;

    @BeforeEach
    void setUp() {
        player = new Player("Malin", 100, 4, 3);
        inventoryAction = new InventoryAction("Sword");
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if item is null")
    void throwsIllegalArgumentIfItemIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new InventoryAction(null));
    }

    @Test
    @DisplayName("Item is added to the player's inventory")
    void executeAddsItemToInventory() {
        inventoryAction.execute(player);
        assertEquals("Sword", player.getInventory().get(0));
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if player is null")
    void throwsIllegalArgumentIfPlayerIsNull() {
        assertThrows(IllegalArgumentException.class, () -> inventoryAction.execute(null));
    }
}