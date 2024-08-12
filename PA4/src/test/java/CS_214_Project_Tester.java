import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CS_214_Project_Tester {
      private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;

    @BeforeAll
    public static void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterAll
    public static void restoreStreams() {
        System.setOut(originalOut);
        try {
            outContent.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNormalizeRatings() {
        CS_214_Project.UserRating userRating = new CS_214_Project.UserRating();
        userRating.rawRatings.addAll(Arrays.asList(1, 2, 3, 4, 5));
        userRating.normalizeRatings();

        assertEquals(5, userRating.normalizedRatings.size());
        assertNotNull(userRating.mean);
        assertNotNull(userRating.stdDev);
    }

    @Test
    public void testReadSongTitles() throws IOException {
        File tempFile = null;
        try {
            tempFile = File.createTempFile("test", ".txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
            bw.write("Song1\nSong2\nSong3");
            bw.close();

            List<String> songs = CS_214_Project.readSongTitles(tempFile.getAbsolutePath());
            assertEquals(3, songs.size());
            assertTrue(songs.contains("Song1"));
            assertTrue(songs.contains("Song2"));
            assertTrue(songs.contains("Song3"));
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }
    }

     @Test
    public void testReadSongTitlesFileNotFound() {
        // This file does not exist, so reading should cause a FileNotFoundException
        String nonExistentFilePath = "nonexistentfile.txt";
        
        // assertThrows checks that the specified exception is thrown when executing the lambda expression
        assertThrows(FileNotFoundException.class, () -> {
            CS_214_Project.readSongTitles(nonExistentFilePath);
        });
    }

    @Test
public void testProcessSongRatings() throws IOException {
    // Assume the songs list is already populated
    List<String> songs = Arrays.asList("Song1", "Song2", "Song3");

    // Prepare a temporary file with song ratings
    File tempFile = File.createTempFile("testRatings", ".txt");
    BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
    bw.write("1 2 3\n4 5 3\n2 2 1"); // Each line represents ratings for a song from various users
    bw.close();

    List<List<Integer>> userRatings = new ArrayList<>();
    CS_214_Project.processSongRatings(tempFile.getAbsolutePath(), userRatings); // Updated to match the method signature

    // Verify the number of users (columns) matches the expected count. Here, we expected 3 users.
    assertTrue(userRatings.stream().allMatch(ratings -> ratings.size() == songs.size()));

    // Cleanup
    tempFile.delete();
}
    

    @Test
    public void testCalculateSimilarityForSongs() {
        List<CS_214_Project.UserRating> userRatings = new ArrayList<>();
        CS_214_Project.UserRating userRating1 = new CS_214_Project.UserRating();
        userRating1.normalizedRatings.addAll(Arrays.asList(1.0, 0.0));
        userRatings.add(userRating1);

        CS_214_Project.UserRating userRating2 = new CS_214_Project.UserRating();
        userRating2.normalizedRatings.addAll(Arrays.asList(0.0, 1.0));
        userRatings.add(userRating2);

        double[][] similarity = CS_214_Project.calculateSimilarity(userRatings, 2, true);

        assertEquals(2, similarity.length);
        assertEquals(2, similarity[0].length);
        assertEquals(2, similarity[1].length);
    }

    @Test
    public void testCalculateSimilarityForUsers() {
        List<CS_214_Project.UserRating> userRatings = new ArrayList<>();
        CS_214_Project.UserRating userRating1 = new CS_214_Project.UserRating();
        userRating1.normalizedRatings.addAll(Arrays.asList(1.0, 0.0));
        userRatings.add(userRating1);

        CS_214_Project.UserRating userRating2 = new CS_214_Project.UserRating();
        userRating2.normalizedRatings.addAll(Arrays.asList(0.0, 1.0));
        userRatings.add(userRating2);

        double[][] similarity = CS_214_Project.calculateSimilarity(userRatings, 2, false);

        assertEquals(2, similarity.length);
        assertEquals(2, similarity[0].length);
        assertEquals(2, similarity[1].length);
    }

    @Test
    public void testWriteSimilarityScores() throws IOException {
        double[][] similarityMatrix = {{1.0, 0.5}, {0.5, 1.0}};

        // Create a temporary file
        File tempFile = File.createTempFile("similarity", ".txt");

        CS_214_Project.writeSimilarityScores(tempFile.getAbsolutePath(), similarityMatrix);

        // Verify the contents of the file
        BufferedReader br = new BufferedReader(new FileReader(tempFile));
        assertEquals("1.0 0.5", br.readLine());
        assertEquals("0.5 1.0", br.readLine());
        br.close();

        // Clean up
        tempFile.delete();
    }

    @Test
    public void testMainWithInvalidArguments() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            // Call main with incorrect number of arguments
            CS_214_Project.main(new String[] {"arg1", "arg2", "arg3"}); // Less than 4 arguments

            // Check if the usage message is printed to the console
            String expectedOutput = "Usage: java CS_214_Project <songsFile> <ratingsFile> <songsSimilarityFile> <usersSimilarityFile>";
            assertTrue(outContent.toString().contains(expectedOutput));
        } finally {
            // Restore the original System.out after the test
            System.setOut(originalOut);
        }
    }

    @Test //(expected = IOException.class)
    public void testMainWithInvalidFiles() {
        CS_214_Project.main(new String[] {"nonexistentfile1.txt", "nonexistentfile2.txt", "outputfile1.txt", "outputfile2.txt"});
    }
}
