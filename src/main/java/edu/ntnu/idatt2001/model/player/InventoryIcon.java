package edu.ntnu.idatt2001.model.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.scene.image.Image;

/**
 * This class represents the inventory icon of the player.
 * The inventory icon is set depending on the inventory type.
 *
 * @author Malin Haugland Holi
 * @author Ina Martini
 * @version 2023.05.22
 */
public class InventoryIcon {

  /**
   * The list of inventory icons that the player can have.
   */
  private List<Image> inventoryIcons;

  /**
   * Constructs a new inventory icon.
   */
  public InventoryIcon() {
    this.inventoryIcons = new ArrayList<>();
  }

  /**
   * Returns the list of inventory icons that the player can have.
   *
   * @return the list of inventory icons
   */
  public List<Image> getInventoryIcons() {
    return inventoryIcons;
  }

  /**
   * Sets the inventory icon depending on the inventory type through a switch case.
   * The icon is added to the list of inventory icons.
   * If the inventory type is null, an exception is thrown.
   */
  public void setInventoryIcon(InventoryType inventoryType) {
    if (inventoryType == null) {
      throw new IllegalArgumentException("InventoryType can't be null");
    }
    try {
      switch (inventoryType) {
        case SWORD -> inventoryIcons.add(
            new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/sword.png"))));
        case SHIELD -> inventoryIcons.add(new Image(
            Objects.requireNonNull(getClass().getResourceAsStream("/images/shield.png"))));
        case POTION -> inventoryIcons.add(new Image(
            Objects.requireNonNull(getClass().getResourceAsStream("/images/potion.png"))));
        case COMPUTER -> inventoryIcons.add(new Image(
            Objects.requireNonNull(getClass().getResourceAsStream("/images/computer.png"))));
        default ->
            throw new IllegalArgumentException("InventoryType not found");

      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to set inventory icon");
    }
  }
}
