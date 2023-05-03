package edu.ntnu.idatt2001.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.io.File;

import edu.ntnu.idatt2001.base.Link;
import edu.ntnu.idatt2001.base.Passage;
import edu.ntnu.idatt2001.base.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Test class for StoryReader
 * @author Malin Haugland Høli
 * @author Ina Martini
 *
 * @version 2023.MM.DD
 *
 */

public class StoryReaderTest {

    private File file;
    private Story story;

    @BeforeEach
    void setUp() {
        file = new File("src/test/resources/test.paths");
    }

    @Nested
    class TestExceptions {
        @Test
        @DisplayName("IllegalArgumentException is thrown if filename does not end with .paths")
        void shouldThrowIllegalArgumentExceptionIfFilenameDoesNotEndWithPaths() {
            File testFile = new File("src/test/resources/test.txt");
            assertThrows(IllegalArgumentException.class, () -> StoryReader.readStoryFromFile(testFile));
        }
        @Test
        @DisplayName("Test1 file throws IllegalArgumentException")
        void shouldThrowIllegalArgumentExceptionIfTest1FileIsRead() {
            File testFile = new File("src/test/resources/test1.paths");
            assertThrows(IllegalArgumentException.class, () -> StoryReader.readStoryFromFile(testFile));
        }
        @Test
        @DisplayName("Test2 file throws IllegalArgumentException")
        void shouldThrowIllegalArgumentExceptionIfTest2FileIsRead() {
            File testFile = new File("src/test/resources/test2.paths");
            assertThrows(IllegalArgumentException.class, () -> StoryReader.readStoryFromFile(testFile));
        }
    }
    @Nested
    @DisplayName("Test if functionality works as intended")
    class TestFunctionality {
        @Test
        @DisplayName("Story is correctly read from file")
        void shouldReadStoryFromFile() {
            Passage passage = new Passage("Title of passage", "Content");
            story = new Story("Test Story", passage);
            Link link = new Link("Test Link 1", "Passage 2");
            passage.addLink(link);
            story.addPassage(passage);
            Passage passage1 = new Passage("Passage 2", "Content 2");
            story.addPassage(passage1);
            StoryWriter.writeStoryToFile(story, file.getPath());
            Story testStory = StoryReader.readStoryFromFile(file);
            assertEquals(story, testStory);
        }
    }
}