/**
 * @author Ariadna Shamraeva
 * @version 3.14
 * 
 */


 import static org.junit.jupiter.api.Assertions.*;
 import org.junit.jupiter.api.BeforeAll;
 import org.junit.jupiter.api.AfterAll;
 import org.junit.jupiter.api.Test;
 import java.io.FileNotFoundException;
 import java.io.File;
 import java.io.IOException;
 import java.nio.file.Files;
 import java.util.List;
 import java.util.Random;
 
 
 /**
  * Unit tests for the CS_214_Project.java
  */
 public class CS_214_Project_Tester {
 
     private static final String TEST_OUTPUT_FILE = "test_output.txt";
     private static final String TEST_SONG_FILE = "test_song_file.txt";
     private static final String TEST_RATINGS_FILE = "test_ratings_file.txt";
 
     /**
      * Sets up the test environment by creating test song and ratings files.
      * @throws IOException If an I/O error occurs while creating the files.
      */
     @BeforeAll
     public static void setUp() throws IOException {
         // Create test song and ratings files
         Files.write(new File(TEST_SONG_FILE).toPath(), List.of("Song1", "Song2", "Song3"));
         Files.write(new File(TEST_RATINGS_FILE).toPath(), List.of("5 3 1", "4 4 4", "1 2 3"));
     }
 
     /**
      * Cleans up the test environment by deleting test files.
     */
     @AfterAll
     public static void tearDown() {
         // Clean up test files
         new File(TEST_SONG_FILE).delete();
         new File(TEST_RATINGS_FILE).delete();
         new File(TEST_OUTPUT_FILE).delete();
     }
 
     /**
      * Tests the creation and retrieval of song data.
      */
     @Test
     public void testSongData() {
         double[][] data = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } };
         String[] songNames = { "Song1", "Song2" };
         SongData songData = new SongData(data, songNames);
         assertArrayEquals(data, songData.getData());
         assertArrayEquals(songNames, songData.getSongNames());
     }
 
     /**
      * Tests the behavior when the data provided to SongData is empty.
      */
     @Test
     public void testEmptyData() {
         double[][] data = {};
         String[] songNames = {};
         new SongData(data, songNames);
     }
 
     /**
      * Tests the behavior when null data is provided to SongData.
      */
     @Test
     public void testNullData() {
         String[] songNames = { "Song1", "Song2" };
         new SongData(null, songNames);
     }
 
     /**
      * Tests the behavior when null song names are provided to SongData.
      */
     @Test
     public void testNullSongNames() {
         double[][] data = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } };
         new SongData(data, null);
     }
 
     /**
      * Tests the behavior when data and song names have different lengths.
      */
     @Test
     public void testMismatchedDataAndSongNames() {
         double[][] data = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } };
         String[] songNames = { "Song1" };
         new SongData(data, songNames);
     }
 
     /**
      * Tests the initialization of K-Means algorithm.
      */
     @Test
     public void testKMeansInitialization() {
         SongData songData = new SongData(new double[][] { { 0, 0, 0 }, { 1, 1, 1 }, { 2, 2, 2 } },
                 new String[] { "Song1", "Song2", "Song3" });
         OptimizedKMeans kMeans = new OptimizedKMeans(songData.getData(), 2, new Random(42));
         assertNotNull(kMeans.centroids);
         assertEquals(2, kMeans.centroids.length);
         assertArrayEquals(songData.getData()[0], kMeans.centroids[0]);
         assertArrayEquals(songData.getData()[1], kMeans.centroids[1]);
     }
 
     /**
      * Tests the clustering process of K-Means algorithm.
      */
     @Test
     public void testKMeansClustering() {
         SongData songData = new SongData(new double[][] { { 0, 0, 0 }, { 1, 1, 1 }, { 2, 2, 2 } },
                 new String[] { "Song1", "Song2", "Song3" });
         OptimizedKMeans kMeans = new OptimizedKMeans(songData.getData(), 2, new Random(42));
         kMeans.run(10);
         assertNotNull(kMeans.assignments);
         assertEquals(3, kMeans.assignments.length);
     }
 
     /**
      * Tests the behavior when all songs belong to a single cluster.
      * @throws FileNotFoundException If the test file is not found.
      */
     @Test
     public void testKMeansSingleCluster() throws FileNotFoundException {
         SongData songData = new SongData(new double[][] { { 0, 0 }, { 0, 0 }, { 0, 0 } },
                 new String[] { "Song1", "Song2", "Song3" });
         OptimizedKMeans kMeans = new OptimizedKMeans(songData.getData(), 1, new Random(42));
         kMeans.run(10);
         int expectedCluster = kMeans.assignments[0];
         for (int assignment : kMeans.assignments) {
             assertEquals(expectedCluster, assignment, "All songs should be in the same cluster");
         }
     }
 
     /**
      * Tests the initialization of centroids in K-Means algorithm.
      */
     @Test
     void testInitializeCentroids() {
         double[][] data = {{1.0, 2.0}, {3.0, 4.0}, {5.0, 6.0}};
         int K = 2;
         Random random = new Random();
         OptimizedKMeans kMeans = new OptimizedKMeans(data, K, random);
         kMeans.initializeCentroids();
 
         // Check if centroids are initialized correctly
         assertEquals(data[0][0], kMeans.centroids[0][0]);
         assertEquals(data[0][1], kMeans.centroids[0][1]);
         assertEquals(data[1][0], kMeans.centroids[1][0]);
         assertEquals(data[1][1], kMeans.centroids[1][1]);
     }
 
     /**
      * Tests the execution of K-Means algorithm.
      */
     @Test
     void testRun() {
         // Mock data
         double[][] data = {{1.0, 2.0}, {3.0, 4.0}, {5.0, 6.0}};
         int K = 2;
         Random random = new Random();
         OptimizedKMeans kMeans = new OptimizedKMeans(data, K, random);
         // Initialize centroids
         kMeans.initializeCentroids();
         // Run K-Means algorithm
         kMeans.run(10);
 
         // Perform assertions as needed based on expected behavior of run method
         // For example, you can check if assignments are updated correctly
         assertNotNull(kMeans.assignments);
         assertTrue(kMeans.assignments.length == data.length);
     }
 
     /**
      * Tests the calculation of Euclidean distance between two points.
      */
     @Test
     void testEuclideanDistance() {
         double[] a = {1.0, 2.0};
         double[] b = {2.0, 3.0};
         OptimizedKMeans kMeans = new OptimizedKMeans(new double[1][2], 1, new Random());
         double result = kMeans.euclideanDistance(a, b);
         double expected = Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2));
         System.out.println("Euclidean Distance: " + result);
         assertEquals(expected, result, 0.01); // Compare with expected value with tolerance
     }
     
 
     /**
      * Tests the format of the output file generated by K-Means algorithm.
      * @throws FileNotFoundException If the test file is not found.
      * @throws IOException           If an I/O error occurs while reading the file.
      */
     @Test
     public void testOutputFileFormat() throws FileNotFoundException, IOException {
         SongData songData = new SongData(new double[][] { { 0, 0 }, { 1, 1 } }, new String[] { "Song1", "Song2" });
         OptimizedKMeans kMeans = new OptimizedKMeans(songData.getData(), 2, new Random(42));
         kMeans.run(10);
         kMeans.outputResults(TEST_OUTPUT_FILE, songData.getSongNames(), 1);
         List<String> outputLines = Files.readAllLines(new File(TEST_OUTPUT_FILE).toPath());
         assertTrue(outputLines.contains("Song1") || outputLines.contains("Song2"),
                 "Output should contain Song1 or Song2");
         assertEquals(1, outputLines.size(), "Output file should contain exactly one song name");
     }
 
     /**
      * Tests the output results generated by K-Means algorithm.
      * @throws FileNotFoundException If the test file is not found.
      */
     @Test
     public void testOutputResults() throws FileNotFoundException {
         SongData songData = new SongData(new double[][] { { 0, 0, 0 }, { 1, 1, 1 }, { 2, 2, 2 } },
                 new String[] { "Song1", "Song2", "Song3" });
         OptimizedKMeans kMeans = new OptimizedKMeans(songData.getData(), 2, new Random(42));
         kMeans.run(10);
         kMeans.outputResults(TEST_OUTPUT_FILE, songData.getSongNames(), 1);
 
         try {
             List<String> outputLines = Files.readAllLines(new File(TEST_OUTPUT_FILE).toPath());
             assertTrue(outputLines.contains("Song1") || outputLines.contains("Song2"));
         } catch (IOException e) {
             fail("Failed to read from the output file: " + e.getMessage());
         }
     }
 
 
 }