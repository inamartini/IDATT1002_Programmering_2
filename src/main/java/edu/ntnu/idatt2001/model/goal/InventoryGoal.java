package edu.ntnu.idatt2001.model.goal;

import edu.ntnu.idatt2001.model.player.Player;
import java.util.List;

/**
 * This class represents a goal that is fulfilled when the player has a certain inventory.
 * The inventory goal is fulfilled when the player has a certain set of mandatory items in their inventory.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 */
public class InventoryGoal implements Goal {

    /**
     * The list of mandatory items that the player must have in their inventory to fulfill the goal.
     */
    private List<String> mandatoryItems;

    /**
     * Constructs a new inventory goal with the given mandatory items.
     *
     * @param mandatoryItems the mandatory items required to fulfill the goal
     * @throws IllegalArgumentException if the mandatory items parameter is null
     */
    public InventoryGoal(List<String> mandatoryItems) {
        if (mandatoryItems == null) {
            throw new IllegalArgumentException("Mandatory items can't be null");
        }
        this.mandatoryItems = mandatoryItems;
    }

    /**
     * Returns the list of mandatory items required to fulfill the goal.
     * @return the list of mandatory items required to fulfill the goal
     */
    public List<String> getMandatoryItems() {
        return mandatoryItems;
    }
    /**
     * Determines if the inventory goal is fulfilled by the given player.
     * The goal is considered fulfilled if the player has all the mandatory items in their inventory.
     *
     * @param player the player to check for the fulfillment of the goal
     * @return true if the player has all of the mandatory items in their inventory, false otherwise
     * @throws IllegalArgumentException if the player parameter is null
     */
    @Override
    public boolean isFulfilled(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player can't be null");
        }
        try {
            return player.getInventory().containsAll(mandatoryItems);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Failed to retrieve inventory from player");
        }
    }

    /**
     * ToString method for the goal.
     */
    @Override
    public String toString() {
        return "Inventory goal: " + mandatoryItems;
    }
}
