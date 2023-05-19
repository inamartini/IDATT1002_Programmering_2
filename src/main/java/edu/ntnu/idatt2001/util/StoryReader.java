package edu.ntnu.idatt2001.util;

import edu.ntnu.idatt2001.action.Action;
import edu.ntnu.idatt2001.action.ActionFactory;
import edu.ntnu.idatt2001.action.ActionType;
import edu.ntnu.idatt2001.action.GoldAction;
import edu.ntnu.idatt2001.action.HealthAction;
import edu.ntnu.idatt2001.action.InventoryAction;
import edu.ntnu.idatt2001.action.ScoreAction;
import edu.ntnu.idatt2001.base.Link;
import edu.ntnu.idatt2001.base.Passage;
import edu.ntnu.idatt2001.base.Story;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for reading a story from a file.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2021.04.19
 */
public class StoryReader {

  /**
   * Regex pattern to match passages in a story.
   */
  private static final String PASSAGE_PATTERN = "^(::)(.*)";

  /**
   * Regex pattern to match links in a passage.
   */
  private static final String LINK_PATTERN = "\\[(.*?)\\]\\((.*?)\\)";

  /**
   * Regex pattern to match actions in a passage.
   */
  private static final String ACTION_PATTERN =  "\\{(.*?)\\}\\((.*?)\\)";

  /**
   * Pattern object that compiles the regex pattern for passages.
   */
  private static final Pattern PASSAGE = Pattern.compile(PASSAGE_PATTERN);

  /**
   * Pattern object that compiles the regex pattern for links.
   */
  private static final Pattern LINK = Pattern.compile(LINK_PATTERN);

  /**
   * Pattern object that compiles the regex pattern for actions.
   */
  private static final Pattern ACTION = Pattern.compile(ACTION_PATTERN);

  /**
   * Method that reads a story from a file and returns a story object. Before reading the file, the file is validated.
   * The file must be in the correct format, if not, an exception will be thrown from wrong formatted files.
   * A scanner object is used to read in the lines from the given file. The first line is the title of the story.
   * The second line is blank. Each new passage starts with a new line and "::" followed by the title of the passage.
   * The second line in each passage should be the content of the passage. The third line should be blank.
   * Optional links follows the format: [link text](passage reference), and should be written on the next lines.
   * <p>
   * If the Matcher object finds a match, the passage title and content is added to a new Passage object. The first passage
   * added will be the opening passage, and the next passage will be the set as the current passage.
   * The passage is then added to the story object. If the Matcher object finds a match for links, the link text and
   * passage reference is added to a new Link object. The link is then added to the current passage. The story object will
   * be returned when the scanner object has no more lines to read.
   *
   * @param file The file to read from.
   * @throws FileNotFoundException if the file is not found.
   * @throws IllegalArgumentException if the file format is wrong.
   * @return Story object.
   */
  public static Story readStoryFromFile(File file) throws FileNotFoundException, IllegalArgumentException {
    validateFile(file);

    Passage openingPassage = null;
    Passage currentPassage = null;
    Story story = null;
    String storyTitle;
    Action action;

    try (Scanner sc = new Scanner(file)) {
      storyTitle = sc.nextLine();

      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        if(line.isEmpty()){
          continue;
        }

        Matcher passageMatcher = PASSAGE.matcher(line);
        Matcher linkMatcher = LINK.matcher(line);
        Matcher actionMatcher = ACTION.matcher(line);

        if (passageMatcher.find()) {
          String passageTitle = passageMatcher.group(2);
          String passageContent = sc.nextLine();
          Passage newPassage = new Passage(passageTitle, passageContent);

          if (openingPassage == null) {
            openingPassage = newPassage;
            currentPassage = openingPassage;
            story = new Story(storyTitle, openingPassage);
          } else {
            currentPassage = newPassage;
            story.addPassage(currentPassage);
          }
        }
        else if (linkMatcher.find()) {
          String linkText = linkMatcher.group(1);
          String linkReference = linkMatcher.group(2);
          Link link = new Link(linkText, linkReference);
          currentPassage.addLink(link);

          while(actionMatcher.find()){
            String actionType = actionMatcher.group(1).toUpperCase();
            String actionValue = actionMatcher.group(2);

            if(actionType.equalsIgnoreCase("Inventory")) {
              action = ActionFactory.createInventoryAction(actionType, actionValue);
            } else {
              int actionValueInt = Integer.parseInt(actionValue);
              action = ActionFactory.createAction(actionType, actionValueInt);
            }
            link.addAction(action);
          }
        }
        else {
          throw new IllegalArgumentException("Line is not on the correct format: " + line);
        }
      }
    } catch(FileNotFoundException e){
      e.printStackTrace();
    }
    return story;
  }

  /**
   * Method that validates the file. Checks for several conditions for the file to be valid.
   *
   * @param file The file to validate.
   * @throws FileNotFoundException if the file is null
   * @throws IllegalArgumentException if the file is not a .paths file, if the file does not exist,
   * if the file is not readable or if the file is empty.
   */

  private static void validateFile(File file) throws FileNotFoundException, IllegalArgumentException {
    if(file == null) {
      throw new FileNotFoundException("File is null.");
    }
    if (!file.getName().endsWith(".paths")) {
      throw new IllegalArgumentException("File is not a .paths file. This file is not supported.");
    }
    if (!file.exists()) {
      throw new IllegalArgumentException("File does not exist. : " + file.getAbsolutePath());
    }
    if (!file.canRead()) {
      throw new IllegalArgumentException("File is not readable.");
    }
    if (file.length() == 0) {
      throw new IllegalArgumentException("File is empty.");
    }
  }
}













