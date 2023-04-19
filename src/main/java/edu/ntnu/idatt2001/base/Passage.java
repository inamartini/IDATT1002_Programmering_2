package edu.ntnu.idatt2001.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a passage in a story.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 *
 * @version 2023.MM.DD
 */

public class Passage {

  private String title ;
  private String content ;
  private List<Link> links ;

  /**
   * Constructor for the Passage class.
   *
   * @param title The title of the passage.
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
   * Method that returns the title of the passage.
   *
   * @return The title of the passage as String.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Method that returns the content of the passage.
   *
   * @return The content of the passage as String.
   */
  public String getContent() {
    return content;
  }

  public boolean addLink(Link link) {
    if(link == null) {
      throw new IllegalArgumentException("Link can't be null");
    }
    links.add(link);
    return true;
  }

  /**
   * Method that returns the list of links.
   *
   * @return The list of links as List.
   */
  public List<Link> getListOfLinks() {
    return links;
  }

  /**
   * Method that checks if the passage has links.
   *
   * @return True if the passage has links, false if not as boolean.
   */
  public boolean hasLinks() {
    return !links.isEmpty();
  }

  /**
   * Method that returns the passage as a String.
   *
   * @return The passage as a String.
   */
  @Override
  public String toString() {
    return "Title='" + title + '\'' +
            ", Content='" + content + '\'' +
            ", Links=" + links;
  }

  /**
   * Method that checks if the passage is equal to another object.
   *
   * @param o The object to be checked.
   * @return True if the passage is equal to the object, false if not as boolean.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Passage passage = (Passage) o;
    return Objects.equals(title, passage.title) && Objects.equals(content, passage.content)
            && Objects.equals(links, passage.links);
  }

  /**
   * Method that returns the hashcode of the passage.
   *
   * @return The hashcode of the passage as int.
   */
  @Override
  public int hashCode() {
    return Objects.hash(title, content, links);
  }
}
