package edu.ntnu.idatt2001.view;

import javafx.scene.layout.Pane;

abstract class View {

  protected abstract void setUp();

  abstract void resetPane();

  abstract Pane getPane();

}
