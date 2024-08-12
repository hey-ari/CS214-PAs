
/**
 * @author Ariadna Shamraeva
 * @version 3.14
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

/**
 * Implements K-Means clustering to find clusters of similar songs based on normalized user ratings.
 */
public class CS_214_Project {

    /**
     * Main method to execute the program, handling command-line arguments for input and output.
     * @param args Command-line arguments: <songFile> <ratingsFile> <outputFile> <K> <N>
     */
    public static void main(String[] args) {
        if (args.length < 5) {
            System.out.println("Usage: java CS_214_Project <songFile> <ratingsFile> <outputFile> <K> <N>");
            System.exit(1);
        }

        String songFile = args[0];
        String ratingsFile = args[1];
        String outputFile = args[2];
        int K = Integer.parseInt(args[3]);
        int N = Integer.parseInt(args[4]);

        if (N < 1 || N > K) {
            System.err.println("Error: N must be between 1 and K.");
            System.exit(1);
        }

        try {
            SongData songData = loadData(songFile, ratingsFile);
            Random random = new Random(42);
            OptimizedKMeans kMeans = new OptimizedKMeans(songData.getData(), K, random);
            kMeans.run(10); // Run K-Means for exactly 10 iterations as specified
            kMeans.outputResults(outputFile, songData.getSongNames(), N);
            System.out.println("Output successfully written to " + outputFile);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

  /**
 * Loads and preprocesses song data and ratings, handling missing values and normalizing data.
 * @param songFile Path to the file containing song names.
 * @param ratingsFile Path to the file containing song ratings.
 * @return SongData object containing loaded and normalized data.
 * @throws FileNotFoundException If the specified file is not found.
 */
public static SongData loadData(String songFile, String ratingsFile) throws FileNotFoundException {
    List<String> songNames = new ArrayList<>();
    List<double[]> ratings = new ArrayList<>();

    try (Scanner songScanner = new Scanner(new File(songFile));
         Scanner ratingsScanner = new Scanner(new File(ratingsFile))) {

        while (songScanner.hasNextLine() && ratingsScanner.hasNextLine()) {
            String songName = songScanner.nextLine();
            String[] ratingTokens = ratingsScanner.nextLine().split("\\s+");
            double[] ratingArray = new double[ratingTokens.length];

            boolean validSong = false;
            double sum = 0;
            for (int i = 0; i < ratingTokens.length; i++) {
                ratingArray[i] = Double.parseDouble(ratingTokens[i]);
                if (ratingArray[i] != 0) {
                    validSong = true;
                }
                sum += ratingArray[i];
            }

            if (validSong) { // Only add songs with at least one non-zero rating
                double mean = sum / ratingTokens.length;
                for (int i = 0; i < ratingArray.length; i++) {
                    ratingArray[i] -= mean; // Normalize ratings
                }
                songNames.add(songName);
                ratings.add(ratingArray);
            }

            if (songNames.size() != ratings.size()) {
                throw new IllegalStateException("Number of songs does not match number of ratings lines.");
            }
        }
    }

    return new SongData(ratings.toArray(new double[0][]), songNames.toArray(new String[0]));
}
}

/**
 * Optimized implementation of the K-Means algorithm for clustering songs based on user ratings.
 */
class OptimizedKMeans {
    public double[][] data;
    public int K;
    public Random random;
    public int[] assignments;
    public double[][] centroids;

    /**
     * Constructor for OptimizedKMeans.
     * @param data 2D array of song ratings.
     * @param K Number of clusters.
     * @param random Random object for consistent random behavior.
     */
    public OptimizedKMeans(double[][] data, int K, Random random) {
        this.data = data;
        this.K = K;
        this.random = random;
        this.assignments = new int[data.length];
        this.centroids = new double[K][data[0].length];
        initializeCentroids();
    }

    /**
     * Initializes centroids by selecting the first K songs as the initial cluster centers.
     */
    public void initializeCentroids() {
        for (int i = 0; i < K; i++) {
            System.arraycopy(data[i], 0, centroids[i], 0, data[i].length);
        }
    }

    /**
     * Runs the K-Means clustering algorithm for a specified number of iterations.
     * @param iterations Number of iterations to perform.
     */
    public void run(int iterations) {
        for (int iter = 0; iter < iterations; iter++) {
            // Assign clusters
            for (int i = 0; i < data.length; i++) {
                double minDistance = Double.MAX_VALUE;
                int closestCluster = 0;
                for (int j = 0; j < K; j++) {
                    double distance = euclideanDistance(data[i], centroids[j]);
                    if (distance < minDistance) {
                        minDistance = distance;
                        closestCluster = j;
                    }
                }
                assignments[i] = closestCluster;
            }

            // Update centroids
            int[] clusterSizes = new int[K];
            double[][] newCentroids = new double[K][data[0].length];
            for (int i = 0; i < data.length; i++) {
                int cluster = assignments[i];
                clusterSizes[cluster]++;
                for (int j = 0; j < data[i].length; j++) {
                    newCentroids[cluster][j] += data[i][j];
                }
            }

            for (int i = 0; i < K; i++) {
                if (clusterSizes[i] > 0) {
                    for (int j = 0; j < newCentroids[i].length; j++) {
                        centroids[i][j] = newCentroids[i][j] / clusterSizes[i];
                    }
                }
            }
        }
    }

    /**
     * Calculates the Euclidean distance between two arrays.
     * @param a First array.
     * @param b Second array.
     * @return Euclidean distance as a double.
     */
    public double euclideanDistance(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            double diff = a[i] - b[i];
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }

    

    /**
     * Outputs the names of the songs in the specified cluster to an output file.
     * @param outputFile Path to the output file.
     * @param songNames Array of song names.
     * @param N The specific cluster whose song names are to be output.
     * @throws FileNotFoundException If the specified output file cannot be found.
     */
    public void outputResults(String outputFile, String[] songNames, int N) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File(outputFile))) {
            for (int i = 0; i < assignments.length; i++) {
                if (assignments[i] == N - 1) {  // Adjust for zero-based index
                    writer.println(songNames[i]);
                }
            }
        }
    }
}

/**
 * Container class for song data, including ratings and names.
 */
class SongData {
    public double[][] data;
    public String[] songNames;

    /**
     * Constructor for SongData.
     * @param data 2D array of song ratings.
     * @param songNames Array of song names.
     */
    public SongData(double[][] data, String[] songNames) {
        this.data = data;
        this.songNames = songNames;
    }

    /**
     * Gets the ratings data.
     * @return 2D array of ratings.
     */
    public double[][] getData() {
        return data;
    }

    /**
     * Gets the names of the songs.
     * @return Array of song names.
     */
    public String[] getSongNames() {
        return songNames;
    }
}
