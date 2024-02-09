import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PA2 {

    public static void main(String[] args) {
        // Check for the correct number of command-line arguments
        if (args.length < 3) {
            System.err.println("Error: Please provide two input files (song titles and ratings) and one output file as arguments.");
            return;
        }

        try {
            // Reading file names from command-line arguments
            String titlesFileName = args[0];
            String ratingsFileName = args[1];
            String outputFileName = args[2];
            
            // Processing song titles and ratings from input files
            List<String> songTitles = readSongTitles(titlesFileName);
            List<List<Integer>> songRatings = readSongRatings(ratingsFileName, songTitles.size());
            
            // Writing calculated statistics to the output file
            writeSongStatistics(songTitles, songRatings, outputFileName);
        } catch (Exception e) 
        {
            // Error handling for exceptions during file processing
            System.err.println(e.getMessage());
        }
    }

    // Adapted from initial code: Method to read song titles from a file, ensuring no title is empty
    private static List<String> readSongTitles(String fileName) throws Exception {
        List<String> songTitles = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            if (!scanner.hasNextLine()) {
                throw new Exception("Error: The song titles file " + fileName + " is empty.");
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    throw new Exception("Error: Empty song title found in " + fileName);
                }
                songTitles.add(line);
            }
        } catch (FileNotFoundException e) 
        {
            throw new Exception("Error: File not found - " + fileName);
        }
        return songTitles;
    }

    // New method: Reads song ratings from a file, validates ratings, and matches lines with song titles
    private static List<List<Integer>> readSongRatings(String fileName, int expectedSize) throws Exception {
        List<List<Integer>> ratings = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            if (!scanner.hasNextLine()) {
                throw new Exception("Error: The ratings file " + fileName + " is empty.");
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                String[] tokens = line.split("\\s+");
                List<Integer> songRating = new ArrayList<>();
                for (String token : tokens) {
                    int rating = Integer.parseInt(token);
                    if (rating < 1 || rating > 5) {
                        throw new Exception("Error: Invalid rating found in " + fileName + " with value " + rating);
                    }
                    songRating.add(rating);
                }
                ratings.add(songRating);
            }
            if (ratings.size() != expectedSize) {
                throw new Exception("Error: Mismatch in number of songs and ratings in " + fileName);
            }
        } catch (FileNotFoundException e) 
        {
            throw new Exception("Error: File not found - " + fileName);
        }
        return ratings;
    }

    // Adapted from initial code: Writes song statistics to an output file without formatting the results
    private static void writeSongStatistics(List<String> songTitles, List<List<Integer>> songRatings, String outputFileName) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(outputFileName)) {
            for (int i = 0; i < songTitles.size(); i++) {
                double sum = 0;
                double sumOfSquares = 0;
                List<Integer> ratings = songRatings.get(i);
                for (int rating : ratings) {
                    sum += rating;
                    sumOfSquares += rating * rating;
                }
                double mean = ratings.isEmpty() ? Double.NaN : sum / ratings.size();
                double variance = ratings.size() > 1 ? (sumOfSquares - (sum * sum / ratings.size())) / (ratings.size() - 1) : Double.NaN;
                double stdDeviation = Math.sqrt(variance);

                // Printing results directly without formatting
                writer.print(songTitles.get(i) + " ");
                writer.print(mean + " ");
                writer.println(Double.isNaN(stdDeviation) ? "UNDEFINED" : stdDeviation);
            }
        }
    }
}
