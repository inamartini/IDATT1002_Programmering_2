package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.controller.ChoosePlayerController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Objects;

public class ChoosePlayerView extends Scene {

  private ChoosePlayerController controller;
  private ImageView playerImageView;
  private VBox playerDetails;
  private HBox buttonsBox;

  public ChoosePlayerView(ChoosePlayerController controller) {
    super(new VBox());

    this.controller = controller;

    String cssPath = Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm();
    getStylesheets().add(cssPath);

    Text title = new Text("Choose your character");
    title.getStyleClass().add("scene2-title");

  /*  Image princessImage = new Image("images/princess.png");
    ImageView princessImageView = new ImageView(princessImage);

    Image princeImage = new Image("images/prince.png");
    ImageView princeImageView = new ImageView(princeImage);*/

    playerImageView = new ImageView();

    //VBox button1Box = new VBox(10, princessImageView, controller.getBtnPrincess());
    VBox button1Box = new VBox(10, controller.getPrincessImageView(), controller.getBtnPrincess());

    //VBox button2Box = new VBox(10, princeImageView, controller.getBtnPrince());
    VBox button2Box = new VBox(10, controller.getPrinceImageView(), controller.getBtnPrince());

    buttonsBox = new HBox(100, button1Box, button2Box);
    buttonsBox.setAlignment(Pos.CENTER);

    //VBox vbox = new VBox(10, title, hbox);
    //vbox.setAlignment(Pos.CENTER);


    Text playerName = new Text("Name:");
    controller.getNameField().setPromptText("Name");
    controller.getNameField().setMaxWidth(200);

    Text playerHealth = new Text("Health:");
    controller.getHealthField().setPromptText("Health");
    controller.getHealthField().setMaxWidth(200);

    Text playerGold = new Text("Gold:");
    controller.getGoldField().setPromptText("Gold");
    controller.getGoldField().setMaxWidth(200);

    playerDetails = new VBox(playerName, controller.getNameField(), playerHealth,
            controller.getHealthField(), playerGold, controller.getGoldField(), controller.getConfirmBtn());
    playerDetails.setVisible(false);
    playerDetails.setAlignment(Pos.CENTER);

    VBox vbox2 = new VBox(10, title, buttonsBox, playerImageView, playerDetails);
    vbox2.setAlignment(Pos.CENTER);

    HBox hbox2 = new HBox(10, vbox2);
    hbox2.setAlignment(Pos.CENTER);

    setRoot(hbox2);
  }

  public void setPlayerImageView(ImageView playerImageView) {
      this.playerImageView.setImage(playerImageView.getImage());
  }

  public void showPlayerDetails() {
    playerDetails.setVisible(true);
    buttonsBox.setVisible(false);
  }
}
