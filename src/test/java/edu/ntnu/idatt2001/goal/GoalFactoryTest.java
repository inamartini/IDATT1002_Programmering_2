package edu.ntnu.idatt2001.goal;


import edu.ntnu.idatt2001.model.action.Action;
import edu.ntnu.idatt2001.model.action.ActionFactory;
import edu.ntnu.idatt2001.model.action.GoldAction;
import edu.ntnu.idatt2001.model.action.HealthAction;
import edu.ntnu.idatt2001.model.action.InventoryAction;
import edu.ntnu.idatt2001.model.action.ScoreAction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the GoalFactory class.
 *
 * @author Malin Haugland Holi
 * @author Ina Martini
 * @version 2023.05.22
 */
public class GoalFactoryTest {

  private Action action;

  @Test
  @DisplayName("The goal created is of type HealthGoal")
  void testCreateGoal() {
    action = ActionFactory.createAction("HEALTH", 10);
    assertTrue(action instanceof HealthAction);
  }

  @Test
  @DisplayName("The goal created is of type InventoryGoal")
  void testCreateInventoryGoal() {
    action = ActionFactory.createInventoryAction("inventory", "Sword");
    assertTrue(action instanceof InventoryAction);
  }

  @Test
  @DisplayName("The goal created is of type ScoreGoal")
  void testCreateScoreGoal() {
    action = ActionFactory.createAction("SCORE", 10);
    assertTrue(action instanceof ScoreAction);
  }

  @Test
  @DisplayName("The goal created is of type GoldGoal")
  void testCreateGoldGoal() {
    action = ActionFactory.createAction("GOLD", 10);
    assertTrue(action instanceof GoldAction);
  }

  @Test
  @DisplayName("The goal created is of the correct type and is not a different type")
  void testCreateHealthGoal() {
    action = ActionFactory.createAction("HEALTH", 10);
    assertFalse(action instanceof GoldAction);
    assertFalse(action instanceof ScoreAction);
    assertFalse(action instanceof InventoryAction);
  }
}
