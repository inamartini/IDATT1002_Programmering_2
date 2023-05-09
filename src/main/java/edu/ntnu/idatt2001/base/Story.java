package edu.ntnu.idatt2001.base;

import edu.ntnu.idatt2001.base.Link;
import edu.ntnu.idatt2001.base.Passage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Story class that represents a story.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.MM.DD
 */

public class Story {

  private String title;
  private Map<Link, Passage> passages;
  private Passage openingPassage;

  /**
   * Constructor for the Story class.
   *
   * @param title The title of the story.
   * @param openingPassage The opening passage of the story.
   */
  public Story(String title, Passage openingPassage) {
    if (title == null) {
      throw new IllegalArgumentException("Title can't be empty");
    }
    if (openingPassage == null) {
      throw new IllegalArgumentException("Opening passage can't be empty");
    }
    this.title = title;
    this.openingPassage = openingPassage;
    this.passages = new HashMap<>();
    this.addPassage(openingPassage);
  }

  /**
   * Method that returns the title of the story.
   *
   * @return The title of the story as String.
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Method that returns the opening passage of the story.
   *
   * @return The opening passage of the story as Passage.
   */
  public Passage getOpeningPassage() {
    return this.openingPassage;
  }

  /**
   * Method that adds a passage to the story.
   *
   * @param passage The passage to be added to the story.
   */
  public void addPassage(Passage passage) {
    if (passage == null) {
      throw new IllegalArgumentException("Passage can't be null");
    }
    String passageTitle = passage.getTitle();
    Link link = new Link(passageTitle, passageTitle);
    passages.put(link, passage);
  }

  /**
   * Method that returns the passage of the story.
   *
   * @param link The link of the passage.
   * @return The passage of the story as Passage.
   */
  public Passage getPassage(Link link) {
    if (link == null) {
      throw new IllegalArgumentException("Link can't be null");
    }
    return passages.get(new Link(link.getReference(), link.getReference()));
  }

  /**
   * Method that returns the passages of the story.
   *
   * @return The passages of the story as Collection.
   */
  public Collection<Passage> getPassages() {
    return passages.values();
  }

  /**
   * Method that removes a passage from the story.
   *
   * @param link The link of the passage to be removed.
   */
  public void removePassage(Link link) {
    if (link == null) {
      throw new IllegalArgumentException("Link can't be null");
    }

    ArrayList<Link> allLinks = new ArrayList<>(passages.keySet());
    Passage passage = getPassage(link);

    if (allLinks.stream().filter(key -> !link.equals(key))
            .flatMap(key -> passages.get(key).getListOfLinks().stream())
            .anyMatch(link1 -> link1.getReference().equals(passage.getTitle()))) {
      return;
    }
    passages.remove(link);
  }

  /**
   * Method that returns the broken links of the story.
   *
   * @return The broken links of the story as List.
   */
  public List<Link> getBrokenLinks() {
    Stream<Link> allLinks = getPassages().stream().flatMap(p -> p.getListOfLinks().stream());
    return allLinks.distinct().filter(l -> getPassages().stream()
            .noneMatch(p -> p.getTitle().equals(l.getReference()))).toList();
  }
}






