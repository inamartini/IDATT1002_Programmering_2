package edu.ntnu.idatt2001.base;

import edu.ntnu.idatt2001.action.GoldAction;
import edu.ntnu.idatt2001.action.HealthAction;
import edu.ntnu.idatt2001.base.Link;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Link.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.MM.DD
 */

public class LinkTest {

  Link link;
  GoldAction goldAction;
  HealthAction healthAction;

  @Nested
  class GetMethodsWorkAsExpected {

    @BeforeEach
    public void setUp() {
      link = new Link("Text", "Reference");
    }

    @Test
    @DisplayName("getText() returns the correct text")
    void getTextShouldReturnCorrectText() {
      assertEquals("Text", link.getText());
    }

    @Test
    @DisplayName("getReference() returns the correct reference")
    void getReferenceShouldReturnCorrectReference() {
      assertEquals("Reference", link.getReference());
    }

    @Test
    @DisplayName("getActions() returns an empty list")
    void getActionsReturnsEmptyList() {
      assertTrue(link.getActions().isEmpty());
    }
  }

  @Nested
  class ActionMethodsWorkAsExpected {
    @BeforeEach
    public void setUp() {
      link = new Link("Text", "Reference");
      healthAction = new HealthAction(5);
    }

    @Test
    @DisplayName("addActions() returns the correct actions")
    void addActionsReturnsCorrectActions() {
      goldAction = new GoldAction(10);
      healthAction = new HealthAction(13);
      link.addAction(healthAction);
      link.addAction(goldAction);
      assertEquals(2, link.getActions().size());
    }

    @Test
    @DisplayName("actionIsValid() throws IllegalArgumentException if action is null")
    void throwsIllegalArgumentExceptionIfActionIsNull() {
      assertThrows(IllegalArgumentException.class, () -> link.actionIsValid(null));
    }

    @Test
    @DisplayName("actionIsValid() returns true if action is not null")
    void returnsTrueIfActionIsNotNull() {
      assertTrue(link.actionIsValid(healthAction));
    }
  }

  @Nested
  class ExceptionsAreThrown {
    String notEmptyString;

    @BeforeEach
    public void setUp() {
      notEmptyString = "Test";
    }

    @Test
    @DisplayName("Constructor throws IllegalArgumentException if text is null")
    void textThrowIllegalArgumentExceptionTest() {
      assertThrows(IllegalArgumentException.class, () -> link = new Link(null, notEmptyString));
    }

    @Test
    @DisplayName("Constructor throws IllegalArgumentException if reference is null")
    void throwsIllegalArgumentExceptionIfReferenceIsNull() {
      assertThrows(IllegalArgumentException.class, () -> link = new Link(notEmptyString, null));
    }
  }
}







