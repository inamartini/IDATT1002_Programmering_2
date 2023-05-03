package edu.ntnu.idatt2001.util;

import edu.ntnu.idatt2001.base.Link;
import edu.ntnu.idatt2001.base.Story;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class writes a story to a file.
 * The .paths format is used to represent a story with passages and links.
 * Each passage in the story are represented by a title, content and links.
 * The passages are stored in a story object, which represents a complete story.
 * The file should be structured as follows:
 * - The first line should be the title of the story.
 * - The second line should be blank.
 * - Each new passage starts with a new line and "::" followed by the title of the passage.
 * - The second line in each passage should be the content of the passage.
 * - The third line should be blank.
 * - Optional links follows the format: [link text](passage reference).And should be written on the next lines.
 * - The last line of each passage should be blank.
 *
 * This class provides a static method for writing a Story object to a file.
 * The method takes a Story object and a path as parameters to create a file with the .paths format.
 * The method throws an IllegalArgumentException if the path is null,
 * the story is null or the file is not a .paths file.
 *
 * Example usage:
 * Story story = new Story("Story title", new Passage("Passage title", "Passage content"));
 * StoryWriter.writeStoryToFile(story, "path/to/file.paths");
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2021.04.19
 */
public class StoryWriter {

  private static final String NEWLINE = "\n";
  private static ArrayList<String> passagesAlreadyWritten = new ArrayList<>();
  private static ArrayList<Link> allLinks = new ArrayList<>();

  /**
   * Method that writes a story to a file on the format .paths.
   *
   * @param story The story object to write to file.
   * @param path The path to write the file to. Should be a .paths file.
   */
  public static void writeStoryToFile(Story story, String path) {
    if(path == null) {
      throw new IllegalArgumentException("Path can't be empty");
    }
    if(story == null) {
      throw new IllegalArgumentException("Story can't be empty");
    }
    if(!path.endsWith(".paths")) {
      throw new IllegalArgumentException("File must be a .paths file");
    }
    File file = new File(path);

    String storyTitle = story.getTitle();
    try (FileWriter fileWriter = new FileWriter(file)) {
      fileWriter.write(storyTitle + NEWLINE);
      fileWriter.write(NEWLINE + "::" + story.getOpeningPassage().getTitle());
      fileWriter.write(NEWLINE + story.getOpeningPassage().getContent());
      for (Link link : story.getOpeningPassage().getListOfLinks()) {
        fileWriter.write(NEWLINE +"[" + link.getText() + "]" + "(" + link.getReference() + ")");
      }
      passagesAlreadyWritten.add(story.getOpeningPassage().getTitle());
      allLinks.addAll(story.getOpeningPassage().getListOfLinks());

      story.getPassages().forEach(passage -> {
        if (!passagesAlreadyWritten.contains(passage.getTitle())) {
          try {
            fileWriter.write(NEWLINE + NEWLINE + "::" + passage.getTitle());
            fileWriter.write(NEWLINE + passage.getContent());
            for (Link link : passage.getListOfLinks()) {
              fileWriter.write(NEWLINE + "[" + link.getText() + "]" + "(" + link.getReference() + ")");
            }
            passagesAlreadyWritten.add(passage.getTitle());
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      });
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
