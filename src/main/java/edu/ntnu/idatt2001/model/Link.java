package edu.ntnu.idatt2001.model;

import edu.ntnu.idatt2001.model.action.Action;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a link in a story. It contains the text of the link,
 * reference to the next and a list of actions connected to the link.
 *
 * @author Malin Haugland Holi
 * @author Ina Martini
 * @version 2023.05.22
 */
public class Link {

  /**
   * The text of the link.
   */
  private String text;

  /**
   * The reference of the link.
   */
  private String reference;

  /**
   * The actions of the link.
   */
  private List<Action> actions;

  /**
   * Constructor for the Link class.It takes in the text
   * and reference and sets this if the parameters are valid.
   *
   * @param text      The text of the link.
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
   * Returns the text of the link.
   *
   * @return The text of the link as String.
   */
  public String getText() {
    return text;
  }

  /**
   * Returns the reference of the link.
   *
   * @return The reference of the link as String.
   */
  public String getReference() {
    return reference;
  }

  /**
   * Adds an action to the link. The action must be valid in
   * order to add it to the actions list.
   *
   * @param action The action to be added.
   */
  public void addAction(Action action) {
    if (actionIsValid(action)) {
      try {
        this.actions.add(action);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("The action cannot be blank.");
      }
    }
  }

  /**
   * Checks if the action is valid.
   *
   * @param action The action to be checked.
   * @return If the action is valid as boolean.
   */
  public boolean actionIsValid(Action action) {
    if (action == null) {
      throw new IllegalArgumentException("The action cannot be blank.");
    } else {
      return true;
    }
  }

  /**
   * Returns the actions of the link.
   *
   * @return The actions of the link as List.
   */
  public List<Action> getActions() {
    return actions;
  }

  /**
   * Returns the link as String.
   *
   * @return Representation of the object as String
   */
  public String toString() {
    return "Link: " + this.text + this.reference;
  }


  /**
   * Returns a String of a link with its actions.
   * Uses a string builder to properly format the string.
   *
   * @return The link with actions as String.
   */
  public String toStringWithActions() {
    StringBuilder sb = new StringBuilder();
    sb.append("[")
        .append(this.text)
        .append("](")
        .append(this.reference)
        .append(")");

    if (!actions.isEmpty()) {
      sb.append("[");
      for (Action action : actions) {
        sb.append(action.toString());
      }
      sb.append("]");
    }
    return sb.toString();
  }


  /**
   * Identifies if one object equals the other argument.
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
   * Returns the hash code value for the object.
   *
   * @return the hash code value for the object as int
   */
  @Override
  public int hashCode() {
    return Objects.hash(text, reference, actions);
  }
}