package edu.ntnu.idatt2001.model.action;

import edu.ntnu.idatt2001.util.AlertUtil;
import javafx.scene.control.Alert;

public class ActionFactory {

    /**
     * Creates an action based on the action type and the action.
     * @param actionType the action type
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
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Failed to create action with given parameters");
        }
    }

    /**
     * Creates an action based on the action type and the action.
     * @param actionType the action type
     * @param actionValue the action
     * @return the action
     */
    public static Action createInventoryAction(String actionType, String actionValue) {
        ActionType type = ActionType.valueOf(actionType.toUpperCase());

        try {
            if (type == ActionType.INVENTORY) {
                return new InventoryAction(actionValue);
            }
            throw new IllegalArgumentException("Action type not supported");
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Failed to create inventory action with given parameters");
        }
    }
}
