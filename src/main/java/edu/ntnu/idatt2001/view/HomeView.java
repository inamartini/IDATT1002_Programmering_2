
package edu.ntnu.idatt2001.view;


import edu.ntnu.idatt2001.controller.ScreenController;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

import java.util.Objects;

/**
 * This class extends the View class and is responsible for the home view of the application.
 * The home view is the first page of the application.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 */
public class HomeView extends View {

  /**
   * The screen controller of the application.
   */
  private ScreenController screenController;
  /**
   * The root of the application.
   */
  private StackPane root;

  /**
   * The border pane of the application.
   */
  private BorderPane borderPane;


  /**
   * Constructor of the class.
   * @param screenController the screen controller of the application.
   */
  public HomeView(ScreenController screenController) {
    this.root = new StackPane();
    this.borderPane = new BorderPane();
    borderPane.setCenter(root);
    this.screenController = screenController;
  }

  /**
   * Returns the pane.
   * @return pane
   */
  public Pane getPane() {
    return this.borderPane;
  }

  /**
   * Sets up the initial view of the application.
   * It resets the pane, sets a background image, and
   * creates a title along with a "Start New Game" button.
   * The button click event activates the "loadGameView" screen
   */
  public void setUp() {
    this.resetPane();

    Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/background.png")));
    BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
            new BackgroundSize(1.0 , 1.0 , true, true, false, true));

    root.setBackground(new Background(background));

    Text title = new Text("Paths");
    title.getStyleClass().add("title");
    title.getStyleClass().add("homeView-title");

    Button btnStart = new Button("START NEW GAME");
    btnStart.setOnAction(e -> screenController.activate("loadGameView"));
    btnStart.getStyleClass().add("homeView-button");

    VBox content = new VBox(10, title, btnStart);
    content.setAlignment(Pos.CENTER);

    root.getChildren().addAll(content);
  }

  /**
   * Resets the view.
   */
  protected void resetPane() {
    root.getChildren().clear();
  }

}

