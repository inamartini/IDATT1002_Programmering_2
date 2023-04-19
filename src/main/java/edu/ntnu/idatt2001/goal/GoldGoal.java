package edu.ntnu.idatt2001.goal;

import edu.ntnu.idatt2001.base.Player;

/**
 * The GoldGoal class represents a goal that requires a player to have a certain amount of gold.
 * The goal is considered fulfilled if the player has at least the specified minimum amount of gold.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.MM.DD
 */
public class GoldGoal implements Goal {

    /**
     * The minimum amount of gold required to fulfill the goal.
     */
    private int minimumGold;

    /**
     * Creates a new GoldGoal with the specified minimum amount of gold required to fulfill the goal.
     *
     * @param minimumGold the minimum amount of gold required to fulfill the goal
     * @throws IllegalArgumentException if the minimumGold parameter is negative
     */
    public GoldGoal(int minimumGold) {
        if (minimumGold < 0) {
            throw new IllegalArgumentException("Minimum gold must be positive");
        }
        this.minimumGold = minimumGold;
    }

    /**
     * Checks whether the player has the required amount of gold to fulfill the goal.
     *
     * @param player the player to check
     * @return true if the player has the required amount of gold, false otherwise.
     * @throws IllegalArgumentException if the player parameter is null
     */
    @Override
    public boolean isFulfilled(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        return player.getGold() >= minimumGold;
    }
}
