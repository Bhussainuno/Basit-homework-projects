import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SQLInsertGenerator {
    public static void main(String[] args) {
        // checking if exactly one command-line argument
        if (args.length != 1) {
            // printing an error message and usage
            System.err.println("Usage: java SQLInsertGenerator <tableName>");
            return;
        }

        // getting the table name from the command line 
        String tableName = args[0];
        
        // creating the source file name by appending ".txt" in the table
        String sourceFile = tableName + ".txt";
        
        // creating the results file name by appending ".sql" to the table
        String resultFile = tableName + ".sql";

        try {
            // initializing a Scanner to read from the file source
            Scanner fileReader = new Scanner(new File(sourceFile));
            
            // initializing a printwriter to write to the result for SQL file
            PrintWriter fileWriter = new PrintWriter(new FileWriter(resultFile));

            // Processing the each line from source file
            while (fileReader.hasNextLine()) {
                // Reading the0 line from the source file
                String line = fileReader.nextLine();
                
                // Creating a list to store formatted values
                List<String> formattedValues = new ArrayList<>();
                
                // Spliting the current line into values by comma
                String[] values = line.split(",");

                // Processing each value
                for (String value : values) {
                    // Removing the leading and trailing whitespace
                    String trimmedValue = value.trim();
                    
                    // Checking the value is NULL
                    if (trimmedValue.equalsIgnoreCase("NULL")) {
                        // Add NULL to the list of formatted values
                        formattedValues.add("NULL");
                    }
                    // Checking the value is integer
                    else if (isInteger(trimmedValue)) {
                        // Add the integer value 
                        formattedValues.add(trimmedValue);
                    }
                    // Checking if the value is a float
                    else if (isFloat(trimmedValue)) {
                        // Add the float value
                        formattedValues.add(trimmedValue);
                    }
                
                    else {
                        // Add the value wrapped in single quote
                        formattedValues.add("'" + trimmedValue + "'");
                    }
                }

                // Creating the SQL INSERT statement using the formatted values
                String sqlInsert = String.format("INSERT INTO %s VALUES(%s);", tableName, String.join(", ", formattedValues));
                
                // Writing the SQL INSERT statement to the output file
                fileWriter.println(sqlInsert);
            }

            // Close the file reader and writer to free system resources
            fileReader.close();
            fileWriter.close();

            // Print a success message to the console
            System.out.println("Processing completed. SQL statements saved in " + resultFile);

        } catch (IOException ex) {
            // Handle any file I/O exceptions
            ex.printStackTrace();
        }
    }

    // Method to check if a string can be parsed as an integer
    private static boolean isInteger(String value) {
        try {
            // Try parsing the string as an integer
            Integer.parseInt(value);
            return true; // Return true if successful
        } catch (NumberFormatException ex) {
            return false; // Return false if parsing fails
        }
    }

    // Method to check if a string can be parsed as a float
    private static boolean isFloat(String value) {
        try {
            // Try parsing the string as a float
            Float.parseFloat(value);
            return true; // Return true if successful
        } catch (NumberFormatException ex) {
            return false; // Return false if parsing fails
        }
    }
}
