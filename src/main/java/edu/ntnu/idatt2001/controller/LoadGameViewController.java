package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.model.Link;
import edu.ntnu.idatt2001.model.Story;
import edu.ntnu.idatt2001.util.StoryReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

/**
 * Controller class for the LoadGameView. Handles the logic for the LoadGameView.
 * Includes method to upload files from the computer, load a game from a file and displaying the
 * files in the paths folder with the number of broken links.
 *
 * @author Malin Haugland Holi
 * @author Ina Martini
 * @version 2023.05.22
 */
public class LoadGameViewController {

  /**
   * Constructor for LoadGameViewController.
   */
  public LoadGameViewController() {
  }

  GameViewController gameViewController = GameViewController.getInstance();

  /**
   * Method for uploading files to the specified destination path.
   * If the file does not already exist, it is created and an exception
   * is thrown if this fails. When the file already exists,
   * it is replaced by the new one.
   *
   * @param fileName     the name of the file
   * @param selectedFile the file to be uploaded
   * @throws RuntimeException if the file cannot be uploaded
   */
  public void uploadFile(String fileName, File selectedFile) {

    Path path = Paths.get("src/main/resources/paths");

    if (!Files.exists(path)) {
      try {
        Files.createDirectories(path);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    Path source = selectedFile.toPath();
    Path target = path.resolve(fileName);
    try {
      Files.copy(source, target, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  /**
   * Method for loading a game from a file. Tries to load a file using the StoreReader class
   * and throws an exception if this fails. The story read from the file is returned.
   *
   * @param file the file to be loaded
   * @return the story
   * @throws FileNotFoundException if the file cannot be found
   */
  public Story loadGame(File file) throws FileNotFoundException {
    try {
      return StoryReader.readStoryFromFile(file);
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File not found.");
    }
  }

  /**
   * Method for retrieving the files from the specified paths folder. This method is used in
   * the LoadGameView in order to load the wanted file. If the file exists and is a .paths file,
   * it is added to the list of files which will then be returned.
   *
   * @return the files in the paths folder
   */
  public ObservableList<File> getFiles() {
    ObservableList<File> files = FXCollections.observableArrayList();
    Set<File> fileNames = new LinkedHashSet<>();
    try {
      Path path = Paths.get("src/main/resources/paths");
      if (Files.exists(path)) {
        try (Stream<Path> paths = Files.list(path)) {
          paths.filter(file -> Files.isRegularFile(file) && file.toString()
                  .endsWith(".paths"))
              .map(Path::toFile)
              .forEach(fileNames::add);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    files.addAll(fileNames);
    return files;
  }

  /**
   * Method for retrieving the broken links from a file.
   * The method tries to load the game based on the file parameter,
   * and throws exception if the file is null. If the story in the file has broken links,
   * these are returned as a string. If there are no broken links,
   * the method returns "No broken links". If the file is not valid,
   * an exception is thrown.
   *
   * @param file the file to be loaded
   * @return the broken links as a string
   * @throws Exception if the file is not valid
   */
  public String getBrokenLinksAsString(File file) throws Exception {
    if (file == null) {
      throw new IllegalArgumentException("File cannot be null.");
    }
    try {
      Story story = loadGame(file);

      List<Link> brokenLinks = null;

      if (story != null) {
        brokenLinks = story.getBrokenLinks();
      }
      if (brokenLinks != null && !brokenLinks.isEmpty()) {
        return brokenLinks.stream().map(Link::getText).collect(Collectors.joining(", "));
      } else {
        return "No broken links.";
      }
    } catch (Exception e) {
      throw new Exception("File is not valid.");
    }
  }

  /**
   * Opens a file chooser dialog for the user to select a file with the ".paths" extension.
   * If a valid file is selected, it invokes the uploadFile method to upload the file.
   */
  public void uploadFileChooser() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

    fileChooser.getExtensionFilters()
        .add(new FileChooser.ExtensionFilter("Paths Files", "*.paths"));
    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
      String extension =
          selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);

      if (extension.equals("paths")) {
        try {
          loadGame(selectedFile);
        } catch (FileNotFoundException e) {
          throw new RuntimeException("File is not valid.");
        }
        uploadFile(selectedFile.getName(), selectedFile);
      } else {
        throw new RuntimeException("File is not valid.");
      }
    }
  }

  /**
   * Method for creating a table view with the files in the paths folder.
   * The table view is used in the LoadGameView to display the files.
   *
   * @param screenController the screen controller
   * @return the table view
   */
  public TableView<File> createTableView(ScreenController screenController) {
    TableColumn<File, String> fileNameColumn = new TableColumn<>("File name");
    fileNameColumn.setCellValueFactory(
        cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
    fileNameColumn.setMinWidth(140);
    fileNameColumn.getStyleClass().add("table-view");

    TableColumn<File, String> fileLocationColumn = new TableColumn<>("File location");
    fileLocationColumn.setCellValueFactory(
        cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAbsolutePath()));
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
        return new ReadOnlyStringWrapper(getBrokenLinksAsString(cellData.getValue()));
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
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
          Story story;
          try {
            story = loadGame(file);
          } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
          }
          gameViewController.setStory(story);
          gameViewController.setGamePath(file.getPath());

          if (story.getBrokenLinks().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Broken links");
            alert.setContentText("The story you are trying to load has broken links. "
                + "You can still play the game, but the story will not be complete."
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

    TableView<File> tableView = new TableView<>();
    tableView.getColumns().add(fileNameColumn);
    tableView.getColumns().add(fileLocationColumn);
    tableView.getColumns().add(brokenLinks);
    tableView.getColumns().add(loadGame);
    tableView.setItems(getFiles());
    return tableView;
  }
}




