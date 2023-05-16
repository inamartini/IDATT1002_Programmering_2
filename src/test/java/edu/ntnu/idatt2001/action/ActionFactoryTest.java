package edu.ntnu.idatt2001.action;

import edu.ntnu.idatt2001.base.Player;
import edu.ntnu.idatt2001.base.PlayerBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the ActionFactory class.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.MM.DD
 *
 */
public class ActionFactoryTest {

    private Action action;

    @Test
    @DisplayName("The action created is of type HealthAction")
    void testCreateAction() {
        action = ActionFactory.createAction("HEALTH", 10);
        assertTrue(action instanceof HealthAction);
    }
    @Test
    @DisplayName("The action created is of type GoldAction")
    void testCreateGoldAction() {
        action = ActionFactory.createAction("GOLD", 10);
        assertTrue(action instanceof GoldAction);
    }
    @Test
    @DisplayName("The action created is of type ScoreAction")
    void testCreateScoreAction() {
        action = ActionFactory.createAction("SCORE", 10);
        assertTrue(action instanceof ScoreAction);
    }

    @Test
    @DisplayName("The action created is of type InventoryAction")
    void testCreateInventoryAction() {
        action = ActionFactory.createInventoryAction("inventory", "Sword");
        assertTrue(action instanceof InventoryAction);
    }
}
