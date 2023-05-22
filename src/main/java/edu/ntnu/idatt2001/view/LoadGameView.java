package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.controller.GameViewController;
import edu.ntnu.idatt2001.controller.LoadGameViewController;
import edu.ntnu.idatt2001.controller.ScreenController;
import edu.ntnu.idatt2001.util.AlertUtil;
import java.io.File;
import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * This class extends the View class and is responsible for the load game view of the application.
 * The load game view is the page where the user can choose to load a game or upload a file.
 * The user can also choose to go back to the home view.
 * The load game view is the second page of the application.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 */
public class LoadGameView extends View {

  /**
   * The table view of games to load.
   */
  private TableView<File> fileInfoTableView;

  /**
   * The screen controller of the application.
   */
  private ScreenController screenController;

  /**
   * The load game view controller of the application.
   */
  private LoadGameViewController loadGameViewController = new LoadGameViewController();

  /**
   * The root of the application.
   */
  private BorderPane borderPane;

  /**
   * The root of the application.
   */
  protected StackPane root;

  /**
   * The game view controller of the application.
   */
  private GameViewController gameViewController = GameViewController.getInstance();

  /**
   * The load game view constructor. It takes in the screen controller as a parameter.
   * Sets up the root and the border pane.
   *
   * @param screenController the screen controller of the application.
   */
  public LoadGameView(ScreenController screenController) {
    this.screenController = screenController;
    this.root = new StackPane();
    this.borderPane = new BorderPane();
    borderPane.setCenter(root);
  }

  /**
   * Returns the pane.
   *
   * @return pane
   */
  public Pane getPane() {
    return this.borderPane;
  }

  /**
   * Sets up the initial view of the application.
   * It creates a table, displays a title, and provides an
   * "Upload file" button to select and upload a file.
   * The method also updates the table data after the file is uploaded.
   * If an exception occurs during the table creation or file upload, an error alert is displayed.
   */
  public void setUp() {
    createTable();

    Text title =
        new Text("Choose the file you want to load your game with or upload your own file!");
    title.getStyleClass().add("title");
    title.getStyleClass().add("loadGameView-title");
    Button uploadFileButton = new Button("Upload file");
    uploadFileButton.getStyleClass().add("loadGameView-loadGameButton");
    uploadFileButton.setOnAction(e -> {
      try {
        loadGameViewController.uploadFileChooser();
        updateTableData();
      } catch (Exception ex) {
        AlertUtil.showAlert(Alert.AlertType.ERROR, "Error",
            "Could not upload file due to wrong format of the story");
      }
    });
    VBox content = new VBox(10, title, fileInfoTableView, uploadFileButton);
    content.setAlignment(Pos.CENTER);
    root.getChildren().addAll(content);
  }

  /**
   * Resets the pane by clearing the root.
   */
  protected void resetPane() {
    root.getChildren().clear();
  }

  /**
   * Creates and configures the table view to display file information.
   * The table view includes columns for file name, file location,
   * broken links, and load game button.
   * The file information is retrieved from the loadGameViewController.
   * If any exception occurs during the process, an error alert is displayed.
   *
   */
  public void createTable() {
    try {
      fileInfoTableView = loadGameViewController.createTableView(screenController);
    } catch (Exception e) {
      AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
    }
    fileInfoTableView.setMaxWidth(610);
    fileInfoTableView.setMaxHeight(300);
  }


  /**
   * Updates the table data after the file is uploaded.
   * It clears the table, sets the items and refreshes the table.
   *
   */
  public void updateTableData() {
    fileInfoTableView.getItems().clear();
    fileInfoTableView.setItems(loadGameViewController.getFiles());
    fileInfoTableView.refresh();
  }
}
