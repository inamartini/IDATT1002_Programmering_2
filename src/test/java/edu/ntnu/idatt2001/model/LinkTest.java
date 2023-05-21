package edu.ntnu.idatt2001.model;

import edu.ntnu.idatt2001.model.action.GoldAction;
import edu.ntnu.idatt2001.model.action.HealthAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Link.
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 * @version 2023.MM.DD
 */

public class LinkTest {

  private Link link;
  private GoldAction goldAction;
  private HealthAction healthAction;
  private String notEmptyString;

  @Nested
  class GetMethodsWorkAsExpected {

    @BeforeEach
    public void setUp() {
      link = new Link("Text", "Reference");
    }

    @Test
    @DisplayName("The correct text is returned")
    void getTextShouldReturnCorrectText() {
      assertEquals("Text", link.getText());
    }

    @Test
    @DisplayName("The correct reference is returned")
    void getReferenceShouldReturnCorrectReference() {
      assertEquals("Reference", link.getReference());
    }
  }

  @Nested
  class FunctionalityWorksAsExpected {
    @BeforeEach
    public void setUp() {
      link = new Link("Text", "Reference");
      healthAction = new HealthAction(5);
    }
    @Test
    @DisplayName("The correct actions are returned")
    void addActionsReturnsCorrectActions() {
      goldAction = new GoldAction(10);
      healthAction = new HealthAction(13);
      link.addAction(healthAction);
      link.addAction(goldAction);
      assertEquals(2, link.getActions().size());
    }
    @Test
    @DisplayName("Returns true if action is not null")
    void returnsTrueIfActionIsNotNull() {
      assertTrue(link.actionIsValid(healthAction));
    }
    @Test
    @DisplayName("Empty list is returned when no actions are added")
    void getActionsReturnsEmptyList() {
      assertTrue(link.getActions().isEmpty());
    }
  }

  @Nested
  class ExceptionsAreThrown {

    @BeforeEach
    public void setUp() {
      notEmptyString = "Test";
    }
    @Test
    @DisplayName("IllegalArgumentException is thrown if text is null")
    void textThrowIllegalArgumentExceptionTest() {
      assertThrows(IllegalArgumentException.class, () -> link = new Link(null, notEmptyString));
    }

    @Test
    @DisplayName("IllegalArgumentException is thrown if reference is null")
    void throwsIllegalArgumentExceptionIfReferenceIsNull() {
      assertThrows(IllegalArgumentException.class, () -> link = new Link(notEmptyString, null));
    }
    @Test
    @DisplayName("IllegalArgumentException is thrown if action is null")
    void throwsIllegalArgumentExceptionIfActionIsNull() {
      link = new Link("Text", "Reference");
      healthAction = new HealthAction(5);
      assertThrows(IllegalArgumentException.class, () -> link.actionIsValid(null));
    }
  }
}







