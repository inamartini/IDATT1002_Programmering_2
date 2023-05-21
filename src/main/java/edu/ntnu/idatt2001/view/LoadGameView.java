package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.model.Story;
import edu.ntnu.idatt2001.controller.GameViewController;
import edu.ntnu.idatt2001.controller.LoadGameViewController;
import edu.ntnu.idatt2001.controller.ScreenController;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Optional;


public class LoadGameView extends View {

    private TableView<File> fileInfoTableView;
    private ScreenController screenController;
    private LoadGameViewController loadGameViewController = LoadGameViewController.getInstance();
    private BorderPane borderPane;
    protected StackPane root;
    private GameViewController gameViewController = GameViewController.getInstance();

    public LoadGameView(ScreenController screenController) {
        this.screenController = screenController;
        this.root = new StackPane();
        this.borderPane = new BorderPane();
        borderPane.setCenter(root);
    }
    public Pane getPane() {
        return this.borderPane;
    }

    public void setUp() {
    try {
    createTable();
    } catch (IOException e) {
        e.printStackTrace();
    }

        Text title = new Text("Choose the file you want to load your game with or upload your own file!");
        title.getStyleClass().add("title");
        title.getStyleClass().add("loadGameView-title");
        Button uploadFileButton = new Button("Upload file");
        uploadFileButton.getStyleClass().add("loadGameView-loadGameButton");
        uploadFileButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Paths Files", "*.paths"));
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                String fileName = selectedFile.getName();
                loadGameViewController.uploadFile(fileName, selectedFile);
                try {
                    updateTableData();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });
        VBox content = new VBox(10, title, fileInfoTableView, uploadFileButton);
        content.setAlignment(Pos.CENTER);
        root.getChildren().addAll(content);
    }


    protected void resetPane() {
        root.getChildren().clear();
    }

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

        fileLocationColumn.setCellFactory(column -> {
            return new TableCell<File, String>() {
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
            };
        });

        fileLocationColumn.getStyleClass().add("table-view");

        TableColumn<File, String> brokenLinks = new TableColumn<>("Broken links");
        brokenLinks.setCellValueFactory(cellData -> {
            try {
                return new ReadOnlyStringWrapper(loadGameViewController.getBrokenLinksAsString(cellData.getValue()));
            } catch (Exception e) {
                throw new RuntimeException(e);
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
                    Story story = loadGameViewController.loadGame(file);
                    gameViewController.setStory(story);
                    gameViewController.setGamePath(file.getPath());

                    if (story.getBrokenLinks().size() > 0) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Broken links");
                        alert.setContentText("The story you are trying to load has broken links. You can still play the game, but the story will not be complete."
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
    public void updateTableData() throws IOException {
        fileInfoTableView.setItems(loadGameViewController.getFiles());
    }
}
