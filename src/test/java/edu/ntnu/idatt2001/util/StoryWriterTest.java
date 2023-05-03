package edu.ntnu.idatt2001.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import edu.ntnu.idatt2001.base.Link;
import edu.ntnu.idatt2001.base.Passage;
import edu.ntnu.idatt2001.base.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Test class for StoryWriter
 * @author Malin Haugland Høli
 * @author Ina Martini
 *
 * @version 2023.MM.DD
 *
 */
public class StoryWriterTest {

    private Story story;
    private String path;

    @BeforeEach
    void setUp() {
        path = "src/test/resources/test.paths";
    }

    @Nested
    @DisplayName("Tests if functionality of StoryWriter works as expected")
    class TestFunctionality {
        @Test
        @DisplayName("Story is correctly written to file")
        void shouldWriteStoryToFile() throws IOException {
            Passage passage = new Passage("Title of passage", "Content");
            story = new Story("Test Story", passage);
            Link link = new Link("Test Link 1", "Passage 2");
            passage.addLink(link);
            Passage passage1 = new Passage("Passage 2", "Content 2");
            story.addPassage(passage);
            story.addPassage(passage1);
            File file1 = new File("src/test/resources/precomputedvalues.paths");
            StoryWriter.writeStoryToFile(story, path);
            File file = new File(path);
            byte[] f1 = Files.readAllBytes(file1.toPath());
            byte[] f2 = Files.readAllBytes(file.toPath());
            assertArrayEquals(f1, f2);

        }
    }

    @Nested
    @DisplayName("Tests if exceptions are thrown as expected")
    class TestExceptions {

        @Test
        @DisplayName("IllegalArgumentException is thrown if story is null")
        void shouldThrowIllegalArgumentExceptionIfStoryIsNull() {
            assertThrows(IllegalArgumentException.class, () -> StoryWriter.writeStoryToFile(null, path));
        }
        @Test
        @DisplayName("IllegalArgumentException is thrown if path is null")
        void shouldThrowIllegalArgumentExceptionIfPathIsNull() {
            assertThrows(IllegalArgumentException.class, () -> StoryWriter.writeStoryToFile(story, null));
        }

        @Test
        @DisplayName("IllegalArgumentException is thrown if file is not a .paths file")
        void shouldThrowIllegalArgumentExceptionIfFileIsNotAPathsFile() {
            assertThrows(IllegalArgumentException.class,
                    () -> StoryWriter.writeStoryToFile(story, "src/test/resources/test.txt"));
        }
    }
}