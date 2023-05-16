package edu.ntnu.idatt2001.action;

import edu.ntnu.idatt2001.base.Player;

/**
 * HealthAction is a class that represents an action that adds health to a player.
 * The amount of health points to add is specified in the constructor.
 * This class implements the `Action` interface.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.MM.DD
 */
public class HealthAction implements Action {

    /**
     * The number of health points to add to the player.
     */
    private final int points;
    /**
     * Constructs a new HealthAction with the specified number of health points to add.
     *
     * @param points the number of health points to add
     */
    public HealthAction(int points) {
        this.points = points;
    }
    /**
     * Executes the HealthAction by adding the specified number of health points to the player.
     *
     * @param player the player to add the health points to
     * @throws IllegalArgumentException if the player parameter is null
     */
    @Override
    public void execute(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        player.addHealth(points);
    }

    @Override
    public String toString() {
        return "{" +
                "Health}" + "(" + points +
                ')';
    }
}
