package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.view.ViewSwitcher;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;


public class ChoosePlayerController {

  private ViewSwitcher viewSwitcher;
  private Button btnPrincess;
  private Button btnPrince;
  private ImageView princessImageView;
  private ImageView princeImageView;

  public ChoosePlayerController(ViewSwitcher viewSwitcher) {
    this.viewSwitcher = viewSwitcher;

    Image princessImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/princess.png")));
    princessImageView = new ImageView(princessImage);

    Image princeImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/prince.png")));
    princeImageView = new ImageView(princeImage);

    btnPrincess = new Button("Princess");
    btnPrincess.setOnAction(e -> {
      viewSwitcher.getPlayGameView().setCharacterImageView(princessImageView);
      viewSwitcher.switchToPlayGameView();
    });
    btnPrincess.getStyleClass().add("princess");

    btnPrince = new Button("Prince");
    btnPrince.setOnAction(e -> {
      viewSwitcher.getPlayGameView().setCharacterImageView(princeImageView);
      viewSwitcher.switchToPlayGameView();
    });
    btnPrince.getStyleClass().add("prince");
  }

  public Button getBtnPrincess() {
    return btnPrincess;
  }

  public Button getBtnPrince() {
    return btnPrince;
  }
}
