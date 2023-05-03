package edu.ntnu.idatt2001.action;

import edu.ntnu.idatt2001.base.Player;

/**
 * GoldAction is a class that represents an action that adds gold to a player.
 * The amount of gold to add is specified in the constructor.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.MM.DD
 */

public class GoldAction implements Action {

    /**
     * The amount of gold to add.
     */
    private int gold;

    /**
     * Constructs a new GoldAction with the specified amount of gold to add.
     *
     * @param gold the amount of gold to add
     * @throws IllegalArgumentException if the gold amount is negative
     */

    public GoldAction(int gold) {
        if (gold < 0) {
            throw new IllegalArgumentException("Gold can't be negative");
        }
        this.gold = gold;
    }

    /**
     * Executes the GoldAction by adding the specified amount of gold to the player's balance.
     *
     * @param player the player to add the gold to
C     * @throws IllegalArgumentException if the player parameter is null
     */

    @Override
    public void execute(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player can't be null");
        }
        player.addGold(gold);
    }
}
