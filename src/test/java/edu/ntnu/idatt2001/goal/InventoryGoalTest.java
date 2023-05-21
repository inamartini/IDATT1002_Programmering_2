package edu.ntnu.idatt2001.goal;

import java.util.List;

import edu.ntnu.idatt2001.model.player.Player;
import edu.ntnu.idatt2001.model.player.PlayerBuilder;
import edu.ntnu.idatt2001.model.goal.InventoryGoal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for InventoryGoal
 * @author Malin Haugland Høli
 * @author Ina Martini
 *
 * @version 2023.MM.DD
 *
 */
public class InventoryGoalTest {

    private Player player;
    private InventoryGoal inventoryGoal;
    private List<String> mandatoryItems;

    @BeforeEach
    public void setUp() {
        mandatoryItems = List.of("item1");
        inventoryGoal = new InventoryGoal(mandatoryItems);
        player = new PlayerBuilder("Player 1")
                .inventory(List.of("item1"))
                .build();
        player.addToInventory("item1");
    }

    @Nested
    @DisplayName("Functionality works as expected")
    class IsFulfilledWorksAsExpected {

        @Test
        @DisplayName("True is returned if player has the same inventory as the mandatoryItems")
        void returnsTrueIfPlayerHasSameInventoryAsMandatoryItems() {
            assertTrue(inventoryGoal.isFulfilled(player));
        }
    }

    @Nested
    @DisplayName("Exceptions are thrown as expected")
    class ExceptionsAreThrown {

        @Test
        @DisplayName("IllegalArgumentException is thrown if mandatoryItems is null")
        void throwsIllegalArgumentExceptionIfMandatoryItemsIsNull() {
            assertThrows(IllegalArgumentException.class, () -> new InventoryGoal(null));
        }
        @Test
        @DisplayName("IllegalArgumentException is thrown if player is null")
        void throwsIllegalArgumentExceptionIfPlayerIsNull() {
            assertThrows(IllegalArgumentException.class, () -> inventoryGoal.isFulfilled(null));
        }
    }
}
