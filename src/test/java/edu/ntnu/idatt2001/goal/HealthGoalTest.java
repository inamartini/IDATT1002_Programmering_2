package edu.ntnu.idatt2001.goal;

import edu.ntnu.idatt2001.model.player.Player;
import edu.ntnu.idatt2001.model.player.PlayerBuilder;
import edu.ntnu.idatt2001.model.goal.HealthGoal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for HealthGoal
 * @author Malin Haugland Høli
 * @author Ina Martini
 *
 * @version 2023.MM.DD
 *
 */
public class HealthGoalTest {

    private Player player;
    private HealthGoal healthGoal;

    @BeforeEach
    public void setUp() {
        healthGoal = new HealthGoal(10);
    }

    @Nested
    @DisplayName("Functionality works as expected")
    class IsFulfilledWorksAsExpected {

        @Test
        @DisplayName("True is returned if player has more than minimum health")
        void returnsTrueIfPlayerHasMoreHealthThanMinimum() {
            player = new PlayerBuilder("Player 2")
                    .health(15)
                    .build();
            assertTrue(healthGoal.isFulfilled(player));
        }

        @Test
        @DisplayName("False is returned if player has less health than minimum")
        void returnsFalseIfPlayerHasLessHealthThanMinimum() {
            player = new PlayerBuilder("Player 1")
                    .health(5)
                    .build();
            assertFalse(healthGoal.isFulfilled(player));
        }
    }

    @Nested
    @DisplayName("Exceptions are thrown as expected")
    class ExceptionsAreThrown {

        @Test
        @DisplayName("IllegalArgumentException is thrown if minimumHealth is negative")
        void throwsIllegalArgumentExceptionIfMinimumHealthIsNegative() {
            assertThrows(IllegalArgumentException.class, () -> new HealthGoal(-1));
        }

        @Test
        @DisplayName("IllegalArgumentException is thrown if minimumHealth is 0")
        void throwsIllegalArgumentExceptionIfMinimumHealthIsZero() {
            assertThrows(IllegalArgumentException.class, () -> new HealthGoal(0));
        }
        @Test
        @DisplayName("IllegalArgumentException is thrown if player is null")
        void throwsIllegalArgumentExceptionIfPlayerIsNull() {
            assertThrows(IllegalArgumentException.class, () -> healthGoal.isFulfilled(null));
        }
    }
}

