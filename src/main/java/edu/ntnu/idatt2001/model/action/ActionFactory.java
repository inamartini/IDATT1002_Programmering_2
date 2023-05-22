package edu.ntnu.idatt2001.model.action;

/**
 * ActionFactory class responsible for creating actions.
 * Includes two separate methods for creating inventory actions
 * and the other types of actions. This class uses the ActionType
 * class in order to decide what type of action to create.
 *
 * @author Malin Haugland Holi
 * @author Ina Martini
 * @version 2023.05.22
 */

public class ActionFactory {

  /**
   * Creates a Health, Gold or Score action based on the action type and the action value.
   * If the action type is not supported or if the method failed
   * to create the action, an exception is thrown.
   *
   * @param actionType  the action type
   * @param actionValue the action
   * @return the action
   */
  public static Action createAction(String actionType, int actionValue) {
    ActionType type = ActionType.valueOf(actionType.toUpperCase());
    try {
      return switch (type) {
        case HEALTH -> new HealthAction(actionValue);
        case GOLD -> new GoldAction(actionValue);
        case SCORE -> new ScoreAction(actionValue);
        default -> throw new IllegalArgumentException("Action type not supported");
      };
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Failed to create action with given parameters");
    }
  }

  /**
   * Creates an inventory action from the given action type and action value as a String.
   * Throws exception if the action type is not supported or
   * if the method failed to create the action.
   *
   * @param actionType  the action type
   * @param actionValue the action
   * @return the inventory action
   */
  public static Action createInventoryAction(String actionType, String actionValue) {
    ActionType type = ActionType.valueOf(actionType.toUpperCase());
    try {
      if (type == ActionType.INVENTORY) {
        return new InventoryAction(actionValue);
      }
      throw new IllegalArgumentException("Action type not supported");
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Failed to create inventory action with given parameters");
    }
  }

  /**
   * Private constructor to prevent instantiation.
   */
  private ActionFactory() {
  }
}
