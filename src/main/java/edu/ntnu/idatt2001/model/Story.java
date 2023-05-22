package edu.ntnu.idatt2001.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Story class that represents a story. A story has a title, passages and an opening passage.
 * This class provides methods for getting and setting the title and opening passage of the story.
 * It also provides methods for adding or removing passages to the story and getting all passages of the story.
 * There is also a method for getting broken links from the story.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 */

public class Story {

  /**
   * The title of the story.
   */
  private String title;
  /**
   * The passages of the story.
   */
  private Map<Link, Passage> passages;

  /**
   *  The opening passage of the story.
   */
  private Passage openingPassage;

  /**
   * Constructor for the Story class. Takes in a title and an opening passage and checks if the
   * parameters are valid. Sets the opening passage of the story to the opening passage.
   * Tries to add the opening passage to the story, and if it is not able to, an exception is thrown.
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
    try {
      this.addPassage(openingPassage);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Failed to add opening passage to story");
    }
  }

  /**
   * Returns the title of the story.
   *
   * @return The title of the story as String.
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Returns the opening passage of the story.
   *
   * @return The opening passage of the story as Passage.
   */
  public Passage getOpeningPassage() {
    return this.openingPassage;
  }

  /**
   * Adds a passage to the story. If the passage is null, an IllegalArgumentException is thrown.
   * If the passage is not null, the passage is added to the story.
   * The passage is added by adding the link of the passage to the story.
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
   * Returns the passage of the story.
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
   * Returns the passages of the story.
   *
   * @return The passages of the story as Collection.
   */
  public Collection<Passage> getPassages() {
    return passages.values();
  }

  /**
   * Removes a passage from the story. If the link of the passage to be removed is equal to the
   * reference of any link in the story, the passage is not removed. If not, the passage is removed.
   * The passage is removed by removing the link of the passage from the story.
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
    passages.remove(new Link(link.getReference(), link.getReference()));
  }

  /**
   * Returns the broken links of the story. This is done by checking if the reference of the link
   * is equal to the title of any passage in the story. If not, the link is added to the list of broken links.
   * The list of broken links is then returned.
   *
   * @return The broken links of the story as List.
   */
  public List<Link> getBrokenLinks() {
    Stream<Link> allLinks = getPassages().stream().flatMap(p -> p.getListOfLinks().stream());
    return allLinks.distinct().filter(l -> getPassages().stream()
            .noneMatch(p -> p.getTitle().equals(l.getReference()))).toList();
  }
}






