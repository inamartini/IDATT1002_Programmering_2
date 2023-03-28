package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.action.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a link in a story.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.MM.DD
 */

public class Link {

  private String text;
  private String reference;
  private List<Action> actions;

  /**
   * Constructor for the Link class.
   *
   * @param text The text of the link.
   * @param reference The reference of the link.
   */
  public Link(String text, String reference) {
    if (text == null) {
      throw new IllegalArgumentException("The text cannot be blank. Enter text.");
    }
    if (reference == null) {
      throw new IllegalArgumentException("The reference cannot be blank. Enter reference.");
    }
    this.text = text;
    this.reference = reference;
    this.actions = new ArrayList<>();
  }

  /**
   * Method that returns the text of the link.
   *
   * @return The text of the link as String.
   */
  public String getText() {
    return text;
  }

  /**
   * Method that returns the reference of the link.
   *
   * @return The reference of the link as String.
   */
  public String getReference() {
    return reference;
  }

  /**
   * Method that adds an action to the link.
   *
   * @param action The action to be added.
   */
  public void addAction(Action action) {
    if (actionIsValid(action)) {
      this.actions.add(action);
    }
  }

  /**
   * Method that checks if the action is valid.
   *
   * @param action The action to be checked.
   * @return If the action is valid as boolean.
   */
  public boolean actionIsValid(Action action) {
    if (action == null) {
      throw new IllegalArgumentException("The action cannot be blank. Enter action.");
    } else {
      return true;
    }
  }

  /**
   * Method that returns the actions of the link.
   *
   * @return The actions of the link as List.
   */
  public List<Action> getActions() {
    return actions;
  }

  /**
   * @return Representation of the object as String
   */
  public String toString() {
    return "Link: " + this.text + this.reference;
  }

  /**
   * Method to identify if one object equals the other argument.
   *
   * @param object the reference object
   * @return If the object equals the other argument as boolean
   */
  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    Link link = (Link) object;
    return Objects.equals(text, link.text) && Objects.equals(reference, link.reference);
  }

  /**
   * @return the hash code value for the object as int
   */
  @Override
  public int hashCode() {
    return Objects.hash(text, reference, actions);
  }
}