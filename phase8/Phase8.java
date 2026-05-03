import java.sql.*;

public class Phase8 {
    public static void main(String[] args) {
        // Checking if user provided the SSN 
        if (args.length != 1) {
            System.out.println("Usage: java Phase8 <BuyerSSN>");
            return;
        }

        String ssn = args[0];  // Using the command-line parameter to retrieve the SSN.

        try (Connection connection = establishDbConnection()) {
            // Using the buyer's SSN to create a report.
            generateReportForBuyer(connection, ssn);
        } catch (Exception e) {
            // Printing any errors that arise when generating a report or connect to a database.
            System.out.println("Error: " + e.getMessage());
        }
    }

     // Creating a database connection.

    private static Connection establishDbConnection() throws Exception {
        // Loading the Oracle JDBC driver
        Class.forName("oracle.jdbc.driver.OracleDriver");
        
        // Database connection details
        String dbUrl = "jdbc:oracle:thin:@localhost:1521:Oracle21c";
        String user = "BHUSSAIN", pass = "Class2024";  // Username and password 
        
        // Given the database connection back
        return DriverManager.getConnection(dbUrl, user, pass);
    }

    // Compiling the buyer's information and present the available homes in a report
    private static void generateReportForBuyer(Connection connection, String ssn) throws SQLException {
        // Using SSN, retrieve buyer information from the database.
        BuyerDetails buyerDetails = fetchBuyerDetails(connection, ssn);

        if (buyerDetails == null) {
            // If no buyer is found, print a message and exit
            System.out.println("No buyer found with SSN: " + ssn);
            return;
        }

        // Print the buyer's complete name and SSN in the report header.
        System.out.println("Report for " + ssn + ": " + buyerDetails.fullName);
        System.out.println("HouseID     Address           Neighborhood     Price");
        System.out.println("---------------------------------------------------");

        // Show the available homes that fit the buyer's budget.
        showAvailableProperties(connection, buyerDetails.minPrice, buyerDetails.maxPrice);
    }

    // Retrieve the buyer's information (name, price range) from the database using their SSN.
    private static BuyerDetails fetchBuyerDetails(Connection connection, String ssn) throws SQLException {
        // SQL query to retrieve buyer information
        String query = "SELECT Name, LowerPrice, UpperPrice FROM BUYER WHERE SSN = ?";
        
        // Create and run the query to retrieve buyer data.
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, ssn);  // Set the SSN in the query parameter
            ResultSet result = statement.executeQuery();

            // If a result is found, return the buyer details
            if (result.next()) {
                String name = result.getString("Name");
                double minPrice = result.getDouble("LowerPrice");
                double maxPrice = result.getDouble("UpperPrice");
                return new BuyerDetails(name, minPrice, maxPrice);
            }
        }
        return null;  // Return null if no buyer is found
    }

    // Show the properties that are available within the given budget.
    private static void showAvailableProperties(Connection connection, double minPrice, double maxPrice) throws SQLException {
        // SQL query to retrieve homes that fit the budget
        String query = "SELECT ID, Address, Neighborhood, Price FROM HOUSE WHERE Price BETWEEN ? AND ?";
        int houseCount = 0;  // The number of homes counter
        double totalPrice = 0.0;  // variable used to determine the homes' overall cost

        // Create and run the query to retrieve the house's data.
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, minPrice);  // In the query parameter, set the lowest price.
            statement.setDouble(2, maxPrice);  // In the query parameter, set the maximum price.
            ResultSet result = statement.executeQuery();

            // Iterate through the results and print the house details
            while (result.next()) {
                String houseID = result.getString("ID");
                String address = result.getString("Address");
                String neighborhood = result.getString("Neighborhood");
                double price = result.getDouble("Price");

                // Print house details in a formatted manner
                System.out.printf("%-10s %-15s %-15s $%.2f%n", houseID, address, neighborhood, price);

                houseCount++;  // Increment house count
                totalPrice += price;  // Add the price to the total
            }
        }

        // If no houses were found, print a message
        if (houseCount == 0) {
            System.out.println("No properties found within the specified price range.");
        } else {
            // Calculate and print the average house price
            double avgPrice = totalPrice / houseCount;
            System.out.printf("Average Price : $%.2f%n", avgPrice);
        }
    }
}

// Class to store buyer details
class BuyerDetails {
    String fullName;  
    double minPrice;  
    double maxPrice;  

    // Constructor to set up the buyer's information
    BuyerDetails(String fullName, double minPrice, double maxPrice) {
        this.fullName = fullName;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
}
