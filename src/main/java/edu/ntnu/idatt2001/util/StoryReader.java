package edu.ntnu.idatt2001.util;

import edu.ntnu.idatt2001.base.Link;
import edu.ntnu.idatt2001.base.Passage;
import edu.ntnu.idatt2001.base.Story;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class reads a story from a file and returns a story object.
 * The file should be a .paths file.
 * Each passage in the story file should contain a title, content and links.
 * The passages are stored in a story object, which represents a complete story.
 * The file should be structured as follows:
 * - The first line should be the title of the story.
 * - The second line should be blank.
 * - Each new passage starts with a new line and "::" followed by the title of the passage.
 * - The second line in each passage should be the content of the passage.
 * - The third line should be blank.
 * - Optional links follows the format: [link text](passage reference). And should be written on the next lines.
 * - The last line of each passage should be blank.
 *
 * This class provides a static method for reading a file and returning a Story object.
 * The method takes a path as parameter to read a file with the .paths format.
 * The method throws an IllegalArgumentException if the path is null,
 * the file is not a .paths file, the file does not exist or the file is not readable.
 *
 * Example usage:
 * Story story = StoryReader.readStoryFromFile("path/to/file.paths");
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2021.04.19
 */
public class StoryReader {

  /**
   * Method that reads a story from a file and returns a story object.
   * @param file The file to read from.
   * @return Story object.
   */
  public static Story readStoryFromFile (File file) {

    if(!file.getName().endsWith(".paths")) {
      throw new IllegalArgumentException("File is not a .paths file. This file is not supported.");
    }
    if(!file.exists()) {
      throw new IllegalArgumentException("File does not exist.");
    }
    if(!file.canRead()) {
      throw new IllegalArgumentException("File is not readable.");
    }
    try (Scanner sc = new Scanner(file)) {
      if(!sc.hasNext()) {
        throw new IOException("File is empty.");
      }
      String storyTitle = sc.nextLine();
      ArrayList<Passage> allPassages = new ArrayList<>();
      sc.nextLine();
      String line;
      while (sc.hasNextLine()) {
        String title = sc.nextLine().substring(2);
        String content = sc.nextLine();

        ArrayList<Link> allLinks = new ArrayList<>();
        if (sc.hasNextLine()) {
          line = sc.nextLine();
          while (!line.equals("")) {
            String link = line;
            if (link.startsWith("[")) {
              String linkText = link.substring(1, link.indexOf("]"));
              String linkReference = link.substring(link.indexOf("(") + 1, link.indexOf(")"));
              allLinks.add(new Link(linkText, linkReference));
            } else {
              break;
            }
            if (sc.hasNextLine()) {
              line = sc.nextLine();
            } else {
              break;
            }
          }
        }
        Passage passage = new Passage(title, content);
        allLinks.forEach(passage::addLink);
        allPassages.add(passage);
      }

      Story story = new Story(storyTitle, allPassages.get(0));
      allPassages.forEach(story::addPassage);
      return story;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
