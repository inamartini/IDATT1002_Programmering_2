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

  public ChoosePlayerView(ChoosePlayerController controller) {
    super(new VBox());

    this.controller = controller;

    String cssPath = Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm();
    getStylesheets().add(cssPath);

    Text title = new Text("Choose your character");
    title.getStyleClass().add("scene2-title");

    Image princessImage = new Image("images/princess.png");
    ImageView princessImageView = new ImageView(princessImage);

    Image princeImage = new Image("images/prince.png");
    ImageView princeImageView = new ImageView(princeImage);

    VBox button1Box = new VBox(10, princessImageView, controller.getBtnPrincess());

    VBox button2Box = new VBox(10, princeImageView, controller.getBtnPrince());

    HBox hbox = new HBox(100, button1Box, button2Box);
    hbox.setAlignment(Pos.CENTER);

    VBox vbox = new VBox(10, title, hbox);
    vbox.setAlignment(Pos.CENTER);

    setRoot(vbox);
  }
}
