package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.controller.HomePageController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Objects;

public class HomePageView extends Scene {

  public HomePageView(HomePageController controller) {
    super(new StackPane());

    String cssPath = Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm();
    getStylesheets().add(cssPath);

    Image backgroundImage = new Image("images/background.png");
    ImageView backgroundImageView = new ImageView(backgroundImage);
    backgroundImageView.fitWidthProperty().bind(widthProperty());
    backgroundImageView.fitHeightProperty().bind(heightProperty());

    Text title = new Text("Welcome to the Troll Game!");
    title.getStyleClass().add("title");

    VBox content = new VBox(10, title, controller.getBtnStart(), controller.getBtnHelp());
    content.setAlignment(Pos.CENTER);

    StackPane root = (StackPane) getRoot();
    root.getChildren().addAll(backgroundImageView, content);
  }
}
