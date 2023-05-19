package edu.ntnu.idatt2001.goal;

import java.util.Collections;
import java.util.List;

/**
 * The GoalFactory class represents a factory for creating goals.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.MM.DD
 */

public class GoalFactory {

    /**
     * Creates a goal based on the goal type and the goal.
     * @param goalType the goal type
     * @param goal the goal
     * @return the goal
     */
    public static Goal createGoal(String goalType, int goal) {
        GoalType type = GoalType.valueOf(goalType.toUpperCase());

        return switch (type) {
            case HEALTH_GOAL -> new HealthGoal(goal);
            case SCORE_GOAL -> new ScoreGoal(goal);
            case GOLD_GOAL -> new GoldGoal(goal);
            default -> throw new IllegalArgumentException("Goal type not supported");
        };
    }
    public static Goal createInventoryGoal(String goalType, String... goal) {
        GoalType type = GoalType.valueOf(goalType.toUpperCase());

        if (type == GoalType.INVENTORY_GOAL) {
            return new InventoryGoal(List.of(goal));
        }
        throw new IllegalArgumentException("Goal type not supported");
    }
}
