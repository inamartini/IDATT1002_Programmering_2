package edu.ntnu.idatt2001.action;

import edu.ntnu.idatt2001.model.action.Action;
import edu.ntnu.idatt2001.model.action.ActionFactory;
import edu.ntnu.idatt2001.model.action.GoldAction;
import edu.ntnu.idatt2001.model.action.HealthAction;
import edu.ntnu.idatt2001.model.action.ScoreAction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the ActionFactory class.
 *
 * @author Malin Haugland Holi
 * @author Ina Martini
 * @version 2023.05.22
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
  @DisplayName("The action created is of type InventoryAction and not a different type")
  void testCreateInventoryAction() {
    action = ActionFactory.createInventoryAction("inventory", "Sword");
    assertFalse(action instanceof ScoreAction);
    assertFalse(action instanceof GoldAction);
    assertFalse(action instanceof HealthAction);
  }
}
