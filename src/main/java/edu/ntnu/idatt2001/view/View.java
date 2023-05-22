package edu.ntnu.idatt2001.view;

import javafx.scene.layout.Pane;

/**
 * Abstract class for all views
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 *
 */

public abstract class View {

  /**
   * Sets up the view
   */
  public abstract void setUp();

  /**
   * Resets the view
   */
  abstract void resetPane();

  /**
   * Returns the pane
   * @return pane
   */
  public abstract Pane getPane();

}
