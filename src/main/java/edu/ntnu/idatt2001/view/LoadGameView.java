package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.model.Story;
import edu.ntnu.idatt2001.controller.GameViewController;
import edu.ntnu.idatt2001.controller.LoadGameViewController;
import edu.ntnu.idatt2001.controller.ScreenController;
import edu.ntnu.idatt2001.util.AlertUtil;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

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
     * The table view of games to load
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
     * @return pane
     */
    public Pane getPane() {
        return this.borderPane;
    }

    /**
     * Sets up the initial view of the application.
     * It creates a table, displays a title, and provides an "Upload file" button to select and upload a file.
     * The method also updates the table data after the file is uploaded.
     * If an exception occurs during the table creation or file upload, an error alert is displayed.
     */
    public void setUp() {
    try {
    createTable();
    } catch (IOException e) {
        AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
    }

        Text title = new Text("Choose the file you want to load your game with or upload your own file!");
        title.getStyleClass().add("title");
        title.getStyleClass().add("loadGameView-title");
        Button uploadFileButton = new Button("Upload file");
        uploadFileButton.getStyleClass().add("loadGameView-loadGameButton");
        uploadFileButton.setOnAction(e -> {
            loadGameViewController.uploadFileChooser();
            try {
                updateTableData();
            } catch (IOException ioException) {
                ioException.printStackTrace();
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
     * The table view includes columns for file name, file location, broken links, and load game button.
     * The file information is retrieved from the loadGameViewController.
     * If any exception occurs during the process, an IOException is thrown.
     * @throws IOException if an exception occurs during the process.
     */
    public void createTable() throws IOException {
        fileInfoTableView = new TableView<>();
        fileInfoTableView.setMaxWidth(610);
        fileInfoTableView.setMaxHeight(300);


        TableColumn<File, String> fileNameColumn = new TableColumn<>("File name");
        fileNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        fileNameColumn.setMinWidth(140);
        fileNameColumn.getStyleClass().add("table-view");

        TableColumn<File, String> fileLocationColumn = new TableColumn<>("File location");
        fileLocationColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAbsolutePath()));
        fileLocationColumn.setMinWidth(200);

        fileLocationColumn.setCellFactory(column -> new TableCell<>() {
            private ScrollPane scrollPane;
            private Text text;

            {
                text = new Text();
                scrollPane = new ScrollPane(text);
                scrollPane.getStyleClass().add("loadGameView-scrollPane");
                text.getStyleClass().add("loadGameView-text");
                scrollPane.setFitToWidth(true);
                scrollPane.setFitToHeight(true);
                scrollPane.setPannable(true);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    text.setText(item);
                    setGraphic(scrollPane);
                }
            }
        });

        fileLocationColumn.getStyleClass().add("table-view");

        TableColumn<File, String> brokenLinks = new TableColumn<>("Broken links");
        brokenLinks.setCellValueFactory(cellData -> {
            try {
                return new ReadOnlyStringWrapper(loadGameViewController.getBrokenLinksAsString(cellData.getValue()));
            } catch (Exception e) {
                AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
                return null;
            }
        });
        brokenLinks.setMinWidth(133);
        brokenLinks.getStyleClass().add("table-view");

        TableColumn<File, String> loadGame = new TableColumn<>("Load game");
        loadGame.getStyleClass().add("table-view");

        loadGame.setCellFactory(tableCell -> new TableCell<>() {

            private final Button loadGameButton = new Button("Load game");

            {
                loadGameButton.setOnAction(event -> {
                    File file = getTableView().getItems().get(getIndex());
                    Story story = null;
                    try {
                        story = loadGameViewController.loadGame(file);
                    } catch (FileNotFoundException e) {
                        AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
                    }
                    gameViewController.setStory(story);
                    gameViewController.setGamePath(file.getPath());

                    if (story.getBrokenLinks().size() > 0) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Broken links");
                        alert.setContentText("The story you are trying to load has broken links. " +
                                "You can still play the game, but the story will not be complete."
                            + "\nDo you still want to play this story?");

                        ButtonType yesButton = new ButtonType("Load game");
                        ButtonType noButton = new ButtonType("Cancel");

                        alert.getButtonTypes().setAll(yesButton, noButton);
                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.isPresent() && result.get() == yesButton) {
                            screenController.activate("playerView");
                        } else {
                            screenController.activate("loadGameView");
                        }
                    } else {
                        screenController.activate("playerView");
                    }
                });
                loadGameButton.getStyleClass().add("loadGameView-loadGameButton");
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : loadGameButton);
            }
        });

        fileInfoTableView.getColumns().add(fileNameColumn);
        fileInfoTableView.getColumns().add(fileLocationColumn);
        fileInfoTableView.getColumns().add(brokenLinks);
        fileInfoTableView.getColumns().add(loadGame);
        fileInfoTableView.setItems(loadGameViewController.getFiles());
    }

    /**
     * Updates the table data after the file is uploaded.
     * It clears the table, sets the items and refreshes the table.
     * @throws IOException if an I/O error occurs.
     */
    public void updateTableData() throws IOException {
        fileInfoTableView.getItems().clear();
        fileInfoTableView.setItems(loadGameViewController.getFiles());
        fileInfoTableView.refresh();
    }
}
