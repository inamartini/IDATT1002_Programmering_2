package edu.ntnu.idatt2001.util;

import edu.ntnu.idatt2001.model.Link;
import edu.ntnu.idatt2001.model.Story;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class writes a story to a file. Includes a method for writing.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2021.05.22
 */
public class StoryWriter {

  /**
   * The newline character.
   */
  private static final String NEWLINE = "\n";

  /**
   * List of passages already written to the file.
   */
  private static ArrayList<String> passagesAlreadyWritten = new ArrayList<>();

  /**
   * List of all links in the story.
   */
  private static ArrayList<Link> allLinks = new ArrayList<>();


  /**
   * Writes a story to a file on the format .paths.
   * The .paths format is used to represent a story with passages and links.
   * Each passage in the story are represented by a title, content and links.
   * The passages are stored in a story object, which represents a complete story.
   * <p>
   * The file should be structured as follows:
   * - The first line should be the title of the story.
   * - The second line should be blank.
   * - Each new passage starts with a new line and "::" followed by the title of the passage.
   * - The second line in each passage should be the content of the passage.
   * - The third line should be blank.
   * - Optional links follows the format: [link text](passage reference), and should be written on the next lines.
   * - Optional actions follows the format: {actiontype}(actionvalue), and should be written on the same line as the link
   * - The last line of each passage should be blank.
   * <p>
   * The method takes a Story object and a String path as parameters to create a file with the .paths format.
   * The method throws several exceptions if the parameters are invalid. The fileWriter will write the story to a file
   * with the given path based on the content of the story object. The method starts by writing the opening passage,
   * and adds this to the list of passages already written. The following passages will then be written if they are not already written.
   * The method will check for both link and actions in the passage. If the passage has any links, the links will be added
   * to the list of all links. If it has actions, the actions will be written with use of a toString method.
   * If the passage has no actions, the link will be written only with the text and reference.
   * Exceptions are thrown if any issues occur during the writing of the file.
   * <p>
   *
   * @param story the story to be written to the file
   * @param path the path to the file
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

        if(link.getActions() != null) {
          fileWriter.write(NEWLINE + link.toStringWithActions());
        } else {
          fileWriter.write(NEWLINE +"[" + link.getText() + "]" + "(" + link.getReference() + ")");
        }
      }
      passagesAlreadyWritten.add(story.getOpeningPassage().getTitle());
      allLinks.addAll(story.getOpeningPassage().getListOfLinks());

      story.getPassages().forEach(passage -> {
        if (!passagesAlreadyWritten.contains(passage.getTitle())) {
          try {
            fileWriter.write(NEWLINE + NEWLINE + "::" + passage.getTitle());
            fileWriter.write(NEWLINE + passage.getContent());
            for (Link link : passage.getListOfLinks()) {
                if(link.getActions() != null) {
                    fileWriter.write(NEWLINE + link.toStringWithActions());
                } else {
                    fileWriter.write(NEWLINE +"[" + link.getText() + "]" + "(" + link.getReference() + ")");
                }
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
