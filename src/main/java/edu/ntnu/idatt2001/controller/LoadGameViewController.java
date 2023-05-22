package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.model.Link;
import edu.ntnu.idatt2001.model.Story;
import edu.ntnu.idatt2001.util.StoryReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controller class for the LoadGameView. Handles the logic for the LoadGameView.
 * Includes method to upload files from the computer, load a game from a file and displaying the
 * files in the paths folder with the number of broken links.
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 */
public class LoadGameViewController {

    /**
     * Constructor for LoadGameViewController.
     */
    public LoadGameViewController() {
    }

    /**
     * Method for uploading files to the specified destination path.
     * If the file does not already exist, it is created and an exception
     * is thrown if this fails. When the file already exists,
     * it is replaced by the new one.
     *
     * @param fileName the name of the file
     * @param selectedFile the file to be uploaded
     * @throws RuntimeException if the file cannot be uploaded
     */
    public void uploadFile(String fileName, File selectedFile)  {

        Path path = Paths.get("src/main/resources/paths");

        if(!Files.exists(path)){
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
     * @return the files in the paths folder
     */
    public ObservableList<File> getFiles() {
        ObservableList<File> files = FXCollections.observableArrayList();
        Set<File> fileNames = new LinkedHashSet<>();
        try {
            Path path = Paths.get("src/main/resources/paths");
            if(Files.exists(path)) {
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
     * @param file the file to be loaded
     * @return the broken links as a string
     * @throws Exception if the file is not valid
     */
    public String getBrokenLinksAsString(File file) throws Exception {
        if(file == null) {
            throw new IllegalArgumentException("File cannot be null.");
        }
        try {
            Story story = loadGame(file);

            List<Link> brokenLinks = null;

            if (story != null) {
                brokenLinks = story.getBrokenLinks();
            }
            if (brokenLinks != null && brokenLinks.size() > 0) {
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

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Paths Files", "*.paths"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String extension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);

            if (extension.equals("paths")) {
                uploadFile(selectedFile.getName(), selectedFile);
            } else {
                throw new RuntimeException("File is not valid.");
            }
        }
    }
}




