package edu.ntnu.idatt2001.view;

import edu.ntnu.idatt2001.base.Link;
import edu.ntnu.idatt2001.base.Story;
import edu.ntnu.idatt2001.controller.LoadGameViewController;
import edu.ntnu.idatt2001.util.StoryReader;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class LoadGameView extends View {

    private TableView<File> fileInfoTableView;
    private ScreenController screenController;
    private LoadGameViewController loadGameViewController = LoadGameViewController.getInstance();
    protected StackPane root;

    public LoadGameView(ScreenController screenController) {
        this.screenController = screenController;
        this.root = new StackPane();
        this.setUp();
    }
    public Pane getPane() {
        return this.root;
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
        uploadFileButton.getStyleClass().add("homeView-button");
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
        fileInfoTableView.setMaxWidth(600);
        fileInfoTableView.setMaxHeight(300);

        TableColumn<File, String> fileNameColumn = new TableColumn<>("File name");
        fileNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        fileNameColumn.setMinWidth(133);
        fileNameColumn.getStyleClass().add("table-view");

        TableColumn<File, String> fileLocationColumn = new TableColumn<>("File location");
        fileLocationColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAbsolutePath()));
        fileLocationColumn.setMinWidth(133);
        fileLocationColumn.getStyleClass().add("table-view");

        TableColumn<File, String> brokenLinks = new TableColumn<>("Broken links");
        brokenLinks.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(loadGameViewController.getBrokenLinksAsString(cellData.getValue())));
        brokenLinks.setMinWidth(133);
        brokenLinks.getStyleClass().add("table-view");

        TableColumn<File, String> loadGame = new TableColumn<>("Load game");

        loadGame.setCellFactory(tableCell -> new TableCell<>() {

            private final Button loadGameButton = new Button("Load game");

            {
                loadGameButton.setOnAction(event -> {
                    File file = getTableView().getItems().get(getIndex());
                    loadGameViewController.loadGame(file);
                    screenController.activate("gameView");
                });
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
