import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataTypeOfValues {
    public static void main(String[] args) {
        // File paths for reading input and writing output
        String sourceFile = "phase1_fall2024.txt";
        String resultFile = "output.txt";

        try {
            // Initialize Scanner for reading the file
            Scanner fileReader = new Scanner(new File(sourceFile));
            // Initialize PrintWriter for writing the file
            PrintWriter fileWriter = new PrintWriter(new FileWriter(resultFile));

            // Process the file line-by-line
            while (fileReader.hasNextLine()) {
                // Reading a line from the file
                String line = fileReader.nextLine();
                // List to store data types of values in the line
                List<String> typeList = new ArrayList<>();
                // Split the values by commas
                String[] values = line.split(",");

                // Evaluate each value in the line
                for (String value : values) {

                    // Remove leading/trailing whitespace from the value 
                    // Check if the value is an integer and float
                    String trimmedValue = value.trim();
                    
                    if (isInteger(trimmedValue)) 
                    {
                        typeList.add("Integer");

                    } else if (isFloat(trimmedValue)) 
                    {
                        typeList.add("Float");
                    // If not an integer or float, consider it a string
                    } else {
                        typeList.add("String");
                    }
                }

                // use data types for commas and spaces
                String outputLine = String.join(", ", typeList);
                // Write the result to the output file
                fileWriter.println(outputLine);
            }

            // Close the Scanner
            fileReader.close();
            fileWriter.close();
            
            // Inform if processing has completed successfully
            System.out.println("Processing completed. Results saved in " + resultFile);

        } catch (IOException ex) {
            // Handling exception related to file reading/writing
            ex.printStackTrace();
        }
    }

    // Boolean method to check if a string can be parsed as an integer
    private static boolean isInteger(String value) {
        try {
            // Try parsing the string as an integer
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException ex) {
            // return false
            return false;
        }
    }

    // // using boolean method for a string can be parsed as float
    private static boolean isFloat(String value) {
        try {
            // Try parsing the string as a float
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException ex) {
            // return false for parsing fails
            return false;
        }
    }
}
