package edu.ntnu.idatt2001.controller;

import javafx.scene.control.Button;
import edu.ntnu.idatt2001.view.ViewSwitcher;

public class HomePageController {

  private ViewSwitcher viewSwitcher;
  private Button btnStart;
  private Button btnHelp;

  public HomePageController(ViewSwitcher viewSwitcher) {

    this.viewSwitcher = viewSwitcher;

    btnStart = new Button("start game");
    btnStart.setOnAction(e -> viewSwitcher.switchToPlayGameView());
    btnStart.setPrefSize(90, 25);  // Set fixed button size

    btnHelp = new Button("help");
    btnHelp.setOnAction(e -> viewSwitcher.switchToChoosePlayerView());
    btnHelp.setPrefSize(90, 25);  // Set fixed button size
  }

  public Button getBtnStart() {
    return btnStart;
  }

  public Button getBtnHelp() {
    return btnHelp;
  }
}
