package edu.ntnu.idatt2001.model.player;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryIcon {

  private List<Image> inventoryIcons;

  public InventoryIcon() {
    this.inventoryIcons = new ArrayList<>();
  }

  public List<Image> getInventoryIcons() {
    return inventoryIcons;
  }


  public void setInventoryIcon(InventoryType inventoryType) {
    if(inventoryType == null) {
      throw new IllegalArgumentException("InventoryType can't be null");
    }
    try {
      switch (inventoryType) {
        case SWORD -> inventoryIcons.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/sword.png"))));
        case SHIELD -> inventoryIcons.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/shield.png"))));
        case POTION -> inventoryIcons.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/potion.png"))));
        case COMPUTER -> inventoryIcons.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/computer.png"))));
      }
    } catch (Exception e) {
        throw new IllegalArgumentException("Failed to set inventory icon");
    }
  }
}
