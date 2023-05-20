package edu.ntnu.idatt2001.view;

import javafx.scene.layout.Pane;

public abstract class View {

  public abstract void setUp();

  abstract void resetPane();

  public abstract Pane getPane();

}
