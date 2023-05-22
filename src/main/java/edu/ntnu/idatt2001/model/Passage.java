package edu.ntnu.idatt2001.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a passage in a story and contains the information about the passage.
 * It is responsible for creating a passage with a title and content, and
 * includes a method to add links to the passage as well as several getters to retrieve
 * the passage content, title and link.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.05.22
 */

public class Passage {

  /**
   * The title of the passage.
   */
  private String title;

  /**
   * The content of the passage.
   */
  private String content;

  /**
   * The links of the passage.
   */
  private List<Link> links;

  /**
   * Constructor for the Passage class. Takes in the title and
   * content and sets this if the parameters are valid.
   * Creates a new list of links.
   *
   * @param title   The title of the passage.
   * @param content The content of the passage.
   */
  public Passage(String title, String content) {
    if (title == null) {
      throw new IllegalArgumentException("Title can't be empty");
    }
    if (content == null) {
      throw new IllegalArgumentException("Content can't be empty");
    }
    this.title = title;
    this.content = content;
    this.links = new ArrayList<>();
  }

  /**
   * Returns the title of the passage.
   *
   * @return The title of the passage as String.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Returns the content of the passage.
   *
   * @return The content of the passage as String.
   */
  public String getContent() {
    return content;
  }

  /**
   * Adds a link to the passage.
   *
   * @param link The link to be added.
   *
   * @return True if the link is added, false if not as boolean.
   */
  public boolean addLink(Link link) {
    if (link == null) {
      throw new IllegalArgumentException("Link can't be null");
    }
    links.add(link);
    return true;
  }

  /**
   * Returns the list of links.
   *
   * @return The list of links as List.
   */
  public List<Link> getListOfLinks() {
    return links;
  }

  /**
   * Checks if the passage has links.
   *
   * @return True if the passage has links, false if not as boolean.
   */
  public boolean hasLinks() {
    return !links.isEmpty();
  }

  /**
   * Returns the passage as a String.
   *
   * @return The passage as a String.
   */
  @Override
  public String toString() {
    return "Title='"
        + title
        + '\''
        + ", Content='"
        + content
        + '\''
        + ", Links="
        + links;
  }

  /**
   * Checks if the passage is equal to another object.
   *
   * @param o The object to be checked.
   * @return True if the passage is equal to the object, false if not as boolean.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Passage passage = (Passage) o;
    return Objects.equals(title, passage.title) && Objects.equals(content, passage.content)
        && Objects.equals(links, passage.links);
  }

  /**
   * Returns the hashcode of the passage.
   *
   * @return The hashcode of the passage as int.
   */
  @Override
  public int hashCode() {
    return Objects.hash(title, content, links);
  }
}
