import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class P1 {

    public static void main(String[] args) {
        /*  will accept file input only as a command line argument
        (since this is an input specified in READ.me) */
        if (args.length == 0) {
            System.err.println("Error: Please provide the file name as a command-line argument.");
            return;
        }

        String fileName = args[0];
        calculations(fileName);
    }

    public static void calculations(String fileName) {

        // Read input file
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            int count = 0;
            double sum = 0;
            double sumOfSquares = 0;

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();

                if (!line.isEmpty()) {
                    String[] numbers = line.split("\\s+");
                    for (String numStr : numbers) {
                        try {
                            double number = Double.parseDouble(numStr);
                            count++;
                            sum += number;
                            sumOfSquares += number * number;
                        } catch (NumberFormatException e) {
                            System.err.println("Error: The file contains non-numeric data.");
                            return;
                        }
                    }
                }
            }

            if (count == 0) {
                System.err.println("Error: The file is empty or does not contain valid numeric data.");
                return;
            }
        

            // Following code does necessary computation for mean and std deviation 

            double mean = sum / count;
            double variance;
            double stdDeviation;

            // Bessel's correction applied only when there are at least two data points
            if (count > 1) {
                variance = (sumOfSquares - (sum * sum / count)) / (count - 1);
                stdDeviation = Math.sqrt(variance);
            } else {
                // Standard deviation is undefined for a single value
                stdDeviation = Double.NaN;
            }

            System.out.print(mean + " ");
            System.out.println(Double.isNaN(stdDeviation) ? "UNDEFINED" : stdDeviation);
        
        // Non-existent file error
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found at path " + fileName);
        }
    }
}