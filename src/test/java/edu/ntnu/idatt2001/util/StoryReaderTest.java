package edu.ntnu.idatt2001.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import edu.ntnu.idatt2001.base.Link;
import edu.ntnu.idatt2001.base.Passage;
import edu.ntnu.idatt2001.base.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;

/**
 * Test class for StoryReader
 *
 * @author Malin Haugland Høli
 * @author Ina Martini
 *
 * @version 2023.MM.DD
 *
 */

public class StoryReaderTest {

    private String testFilePath;

    @BeforeEach
    void setUp() {
        testFilePath = FileSystems.getDefault().getPath("src", "test", "resources", "precomputedvalues.paths").toString();
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
        @Test
        @DisplayName("Test3 file throws IllegalArgumentException")
        void shouldThrowIllegalArgumentExceptionIfTest3FileIsRead() {
            File testFile = new File("src/test/resources/test3.paths");
            assertThrows(IllegalArgumentException.class, () -> StoryReader.readStoryFromFile(testFile));
        }
    }

    @Nested
    @DisplayName("Test if functionality works as intended")
    class TestFunctionality {

        @Test
        @DisplayName("File is read correctly")
        void readStoryFromFile1() throws FileNotFoundException {
            File testFile = new File(testFilePath);
            Story story = StoryReader.readStoryFromFile(testFile);

            assertEquals("Test Story", story.getTitle());

            Collection<Passage> passages = story.getPassages();
            assertEquals(3, passages.size());

            Passage passage1 = passages.stream().filter(p -> p.getTitle().equals("Title of the opening passage")).findFirst().orElse(null);
            assertEquals("Title of the opening passage", passage1.getTitle());
            assertEquals("Content in the opening passage", passage1.getContent());

            Passage passage2 = passages.stream().filter(p -> p.getTitle().equals("Title of the second passage")).findFirst().orElse(null);
            assertEquals("Title of the second passage", passage2.getTitle());
            assertEquals("Content in the second passage", passage2.getContent());

            Passage passage3 = passages.stream().filter(p -> p.getTitle().equals("Title of the third passage")).findFirst().orElse(null);
            assertEquals("Title of the third passage", passage3.getTitle());
            assertEquals("Content in the third passage", passage3.getContent());

            ArrayList<Link> links = new ArrayList<>();
            passages.forEach(p -> links.addAll(p.getListOfLinks()));
            assertEquals(2, links.size());

            Link link = links.get(0);
            assertEquals("Title of the second passage", link.getReference());
            assertEquals("Test Link 1", link.getText());

            Link link2 = links.get(1);
            assertEquals("Title of the third passage", link2.getReference());
            assertEquals("Test Link 2", link2.getText());
            }
        }
    }







