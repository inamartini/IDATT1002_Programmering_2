package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.base.Game;
import edu.ntnu.idatt2001.base.Link;
import edu.ntnu.idatt2001.base.Story;
import edu.ntnu.idatt2001.util.StoryReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LoadGameViewController {

    private static LoadGameViewController instance;


    private LoadGameViewController() {
    }

    public static LoadGameViewController getInstance() {
        if (instance == null) {
            instance = new LoadGameViewController();
        }
        return instance;
    }

    public void uploadFile(String fileName, File selectedFile)  {

        Path path = Paths.get("src/main/resources/paths");

        if(!Files.exists(path)){
            try {
                Files.createDirectories(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Path source = selectedFile.toPath();
        Path target = path.resolve(fileName);
        try {
            boolean validFileFormat = validateFileFormat(source);
            if(!validFileFormat) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("File format is not correct.");
                alert.setContentText("Please choose a file with the correct format.");
                alert.showAndWait();
            } else {
                Files.copy(source, target, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Story loadGame(File file) {
        try {
            return StoryReader.readStoryFromFile(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean validateFileFormat(Path filePath) throws IOException {
        List<String> lines = Files.readAllLines(filePath);

        String openingPassageLine = lines.get(0);
        return openingPassageLine.startsWith("::");
    }

    public ObservableList<File> getFiles() throws IOException {
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
    public String getBrokenLinksAsString(File file) {
        Story story = loadGame(file);

        List<Link> brokenLinks = null;

        if(story != null) {
            brokenLinks = story.getBrokenLinks();
        }
        if(brokenLinks != null && brokenLinks.size() > 0) {
            return brokenLinks.stream().map(Link::getText).collect(Collectors.joining(", "));
        } else {
            return "No broken links.";
        }
    }
}




