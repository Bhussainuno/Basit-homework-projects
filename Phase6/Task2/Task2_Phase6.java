import java.io.BufferedReader;  
import java.io.FileReader;      
import java.io.IOException;     
import java.util.HashMap;       
import java.util.HashSet;       

public class Task2_Phase6 {  

    public static void main(String[] args) {    // Main method
        // Specify the path to the house and agent files.
        String agentFilePath = "agent.txt";     
        String houseFilePath = "house.txt";     
        
        // To show agents who have at least one house listing, utilize the call technique.
        displayAgentsWithHouseListings(agentFilePath, houseFilePath);
        
        // Use the call technique to get the average cost of homes in each neighborhood.
        displayAveragePriceByNeighborhood(houseFilePath);
    }

    // show the names of every agent who has at least one house listed
    public static void displayAgentsWithHouseListings(String agentFilePath, String houseFilePath) {
        // constructing a hash set to hold the IDs of real estate agents
        HashSet<String> agentIdsWithListings = new HashSet<>();
        
        // To ensure appropriate resource management
        try (BufferedReader agentReader = new BufferedReader(new FileReader(agentFilePath));
             BufferedReader houseReader = new BufferedReader(new FileReader(houseFilePath))) { 

            String houseRecord;  // Declare a variable
            // Go over every line in the house file in a loop.
            while ((houseRecord = houseReader.readLine()) != null) {
                String[] houseDetails = houseRecord.split(",");  // Split the house data by commas
                if (houseDetails.length > 6) {  // Check if the record has at least 7 fields
                    String agentId = houseDetails[6].trim();  // Extracting the agent ID
                    agentIdsWithListings.add(agentId);  // Add the agent ID to the HashSet
                }
            }

            String agentRecord;  // To store every line from the agent file
            System.out.println("Agents with at least one house listing:");
            // Loop through each line in the agent file
            while ((agentRecord = agentReader.readLine()) != null) {
                String[] agentDetails = agentRecord.split(",");  // Split agent data by comma
                if (agentDetails.length > 1) {  // Check if the record has at least 2 fields
                    String agentId = agentDetails[0].trim();  // Extracting the agent ID 
                    String agentName = agentDetails[1].trim();  // Extracting the agent name
                    
                    // Verifying whether the agent ID is present in the agent's hash set
                    if (agentIdsWithListings.contains(agentId)) {
                        System.out.println(agentName);  
                    }
                }
            }

        } catch (IOException e) {  // Handle all possible IOExceptions during file reading
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();  
        }
    }

    // Show how each neighborhood's average home price is displayed
    public static void displayAveragePriceByNeighborhood(String houseFilePath) {
        // Make a HashMap that stores the total price and number of houses in each neighborhood.
        HashMap<String, Double> totalPricesByNeighborhood = new HashMap<>();
        HashMap<String, Integer> houseCountByNeighborhood = new HashMap<>();

        try (BufferedReader houseReader = new BufferedReader(new FileReader(houseFilePath))) {

            String houseRecord;  
            // Loop through each line in the house file
            while ((houseRecord = houseReader.readLine()) != null) {
                String[] houseDetails = houseRecord.split(",");  // Split the house data by commas
                if (houseDetails.length > 3) {  // Check if the record has at least 4 fields
                    String neighborhood = houseDetails[2].trim();  // Extract the neighborhood name 
                    double housePrice = Double.parseDouble(houseDetails[3].trim());  // Extract and parse the house price

                    // Updating the overall price for the neighborhood by adding the current house's price.
                    totalPricesByNeighborhood.put(neighborhood, totalPricesByNeighborhood.getOrDefault(neighborhood, 0.0) + housePrice);
                    // Incrementing the house count for the neighborhood
                    houseCountByNeighborhood.put(neighborhood, houseCountByNeighborhood.getOrDefault(neighborhood, 0) + 1);
                }
            }

            // Print the average house price for each neighborhood
            System.out.println("Average house price by neighborhood:");
            // Loop through each neighborhood in the total price map
            for (String neighborhood : totalPricesByNeighborhood.keySet()) {
                // Calculate the average price for the neighborhood
                double avgPrice = totalPricesByNeighborhood.get(neighborhood) / houseCountByNeighborhood.get(neighborhood);
                // Print the neighborhood name and its average house price
                System.out.printf("%s: %.2f%n", neighborhood, avgPrice);
            }

        } catch (IOException e) {  
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();  
        }
    }
}
