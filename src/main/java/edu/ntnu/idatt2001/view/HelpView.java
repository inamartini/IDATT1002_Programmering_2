package edu.ntnu.idatt2001.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HelpView extends View {

  protected StackPane root;
  private ScreenController screenController;

  public HelpView(ScreenController screenController) {
    this.root = new StackPane();
    this.screenController = screenController;
    this.setUp();
  }

  public Pane getPane() {
    return this.root;
  }

  public void setUp() {
    this.resetPane();

    Text title = new Text("How to play");
    title.getStyleClass().add("scene2-title");

    Text howToPlay = new Text("This is the help page");
    howToPlay.getStyleClass().add("help-text");

    Button btnGoBack = new Button("GO BACK");
    btnGoBack.setOnAction(e -> screenController.activate("homeView"));
    btnGoBack.setPrefSize(110, 25);
    btnGoBack.getStyleClass().add("help-button");

    VBox content = new VBox(10, title, howToPlay, btnGoBack);
    content.setAlignment(Pos.CENTER);

    root.getChildren().addAll(content);
  }

  protected void resetPane() {
    root.getChildren().clear();
  }
}
