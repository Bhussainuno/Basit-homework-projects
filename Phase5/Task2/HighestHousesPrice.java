import java.io.File; 
import java.io.FileNotFoundException; 
import java.util.ArrayList; 
import java.util.Scanner; 


public class HighestHousesPrice {

    public static void main(String[] args) {
        findHighestHousesPrice(); // Use the procedure to locate and print the homes with the highest prices.
    }

// locating the house or houses that are most expensive
public static void findHighestHousesPrice() {
        String fileName = "house.txt"; // To read the house data, specify the filename.
        double highestPrice = 0.0; // Tracking the maximum price found
        StringBuilder highestPriceHouses = new StringBuilder(); // storing the specifics of the most expensive homes using StringBuilder
        try (Scanner scanner = new Scanner(new File(fileName))) { // Scanner can be used to read the file.

            // Reading the file line by line
            while (scanner.hasNextLine()) { // Proceed to the end of the file.
                String line = scanner.nextLine(); // Go to the next line in the file.
                String[] houseDetails = line.split(","); // Divide the line into sections, utilizing a comma as the separator.

                // Considering that, following splitting, the price is in position 4 (index 3).
                double currentPrice = Double.parseDouble(houseDetails[3].trim()); // Parse the amount and remove any unnecessary spaces.

                // Verify whether the current price exceeds the greatest amount discovered.
                if (currentPrice > highestPrice) {
                    highestPrice = currentPrice; // Revising the highest cost
                    highestPriceHouses.setLength(0); // Clearing the StringBuilder
                    highestPriceHouses.append(line); // Storing the entire house information 
                } else if (currentPrice == highestPrice) { // If the cost matches the highest amount discovered
                    highestPriceHouses.append("\n").append(line); // Add this home's details to the list.
                }
            }

            // If the highest-priced house is located, print the results.
            if (highestPriceHouses.length() > 0) {
                System.out.println("House(s) with the highest price:\n" + highestPriceHouses);
            } else {
                System.out.println("No houses found."); // If no homes were read, print a message.
            }

        } catch (FileNotFoundException e) { // Seize any potential FileNotFoundExceptions.
            System.out.println("Error reading the file: " + e.getMessage()); // Publish a notice of error
        }
    }
}
