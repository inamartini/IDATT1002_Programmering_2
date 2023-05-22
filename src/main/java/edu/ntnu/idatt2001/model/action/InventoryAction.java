package edu.ntnu.idatt2001.model.action;

import edu.ntnu.idatt2001.model.player.Player;

/**
 * InventoryAction is a class that adds an item to the player's inventory.
 * The item to add is specified in the constructor.
 * This class implements the `Action` interface.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 */

public class InventoryAction implements Action {

    /**
     * The item to add to the player's inventory.
     */
    private String item;

    /**
     * Constructs a new InventoryAction with the specified item to add to the player's inventory.
     *
     * @param item the item to add to the player's inventory
     * @throws IllegalArgumentException if the item parameter is null
     */
    public InventoryAction(String item) {
        if (item == null) {
            throw new IllegalArgumentException("Item can't be null");
        }
        this.item = item;
    }
    /**
     * Executes the InventoryAction by adding the specified item to the player's inventory.
     *
     * @param player the player to add the item to
     * @throws IllegalArgumentException if the player parameter is null
     */
    @Override
    public void execute(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player can't be null");
        } else {
            try {
                player.addToInventory(item);
            } catch (IllegalArgumentException e) {
               throw new IllegalArgumentException("Failed to execute inventory action with given parameters");
            }
        }
    }
    @Override
    public String toString() {
        return "{" +
                "Inventory}" + "(" + item +
                ')';
    }
}
