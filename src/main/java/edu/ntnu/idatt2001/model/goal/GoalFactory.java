package edu.ntnu.idatt2001.model.goal;

import java.util.List;

/**
 * GoalFactory class responsible for creating goals.
 * Includes two separate methods for creating inventory goals
 * and the other types of goals. This class uses the goalType
 * class in order to decide what type of goal to create.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 */

public class GoalFactory {

    /**
     * Creates a Health, Gold or Score goal based on the goal type and the goal value.
     * If the goal type is not supported or if the method failed to create the goal,
     * an exception is thrown.
     * @param goalType the goal type
     * @param goal the goal
     * @return the goal
     */
    public static Goal createGoal(String goalType, int goal) {
        GoalType type = GoalType.valueOf(goalType.toUpperCase());
        try {
        return switch (type) {
            case HEALTH_GOAL -> new HealthGoal(goal);
            case SCORE_GOAL -> new ScoreGoal(goal);
            case GOLD_GOAL -> new GoldGoal(goal);
            default -> throw new IllegalArgumentException("Goal type not supported");
        };
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Failed to create goal with given parameters");
        }
    }

    /**
     * Creates an inventory goal from the given action type and goal value as a String.
     * Throws exception if the goal type is not supported or if the
     * method failed to create the goal.
     * @param goalType the goal type
     * @param goal the goal
     * @return the inventory goal
     */
    public static Goal createInventoryGoal(String goalType, String... goal) {
        GoalType type = GoalType.valueOf(goalType.toUpperCase());

        try {
        if (type == GoalType.INVENTORY_GOAL) {
            return new InventoryGoal(List.of(goal));
        }
        throw new IllegalArgumentException("Goal type not supported");
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Failed to create inventory goal with given parameters");
        }
    }
}
