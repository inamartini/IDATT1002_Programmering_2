package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.view.ViewSwitcher;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;


public class ChoosePlayerController {

  private ViewSwitcher viewSwitcher;
  private Button btnPrincess, btnPrince, confirmBtn;
  private ImageView princessImageView;
  private ImageView princeImageView;
  private TextField nameField, healthField, goldField;


  public ChoosePlayerController(ViewSwitcher viewSwitcher) {
    this.viewSwitcher = viewSwitcher;

    Image princessImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/princess.png")));
    princessImageView = new ImageView(princessImage);

    Image princeImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/prince.png")));
    princeImageView = new ImageView(princeImage);

    nameField = new TextField();
    healthField = new TextField();
    goldField = new TextField();

    btnPrincess = new Button("Princess");
    btnPrincess.setOnAction(e -> {
      viewSwitcher.getChoosePlayerView().setPlayerImageView(princessImageView);
      viewSwitcher.getChoosePlayerView().showPlayerDetails();
    });
    btnPrincess.getStyleClass().add("princess");

    btnPrince = new Button("Prince");
    btnPrince.setOnAction(e -> {
      viewSwitcher.getChoosePlayerView().setPlayerImageView(princeImageView);
      viewSwitcher.getChoosePlayerView().showPlayerDetails();
    });
    btnPrince.getStyleClass().add("prince");

    confirmBtn = new Button("Save");
    confirmBtn.setOnAction(e -> {
      //viewSwitcher.getPlayGameView().setPlayerName(nameField.getText());
      //viewSwitcher.getPlayGameView().setPlayerHealth(Integer.parseInt(healthField.getText()));
      //viewSwitcher.getPlayGameView().setPlayerGold(Integer.parseInt(goldField.getText()));
      viewSwitcher.switchToPlayGameView();
    });
  }

  public TextField getNameField() {
    return nameField;
  }

  public TextField getHealthField() {
      return healthField;
  }

  public TextField getGoldField() {
      return goldField;
  }

  public Button getBtnPrincess() {
    return btnPrincess;
  }

  public Button getBtnPrince() {
    return btnPrince;
  }

  public Button getConfirmBtn() {
      return confirmBtn;
  }

  public ImageView getPrincessImageView() {
      return princessImageView;
  }

  public ImageView getPrinceImageView() {
      return princeImageView;
  }
}
