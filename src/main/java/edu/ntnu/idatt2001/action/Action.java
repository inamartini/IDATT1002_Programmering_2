package edu.ntnu.idatt2001.action;

import edu.ntnu.idatt2001.Player;

/**
 * An interface for actions that can be performed on a player.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.MM.DD
 */
public interface Action {
    /**
     * Executes the action on the player. This method will be implemented by the other action classes.
     *
     * @param player the player
     */
    void execute(Player player);
}

