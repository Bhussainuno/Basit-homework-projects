import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "localhost"; // Server address
    private static final int SERVER_PORT = 5000; // Server port

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); // Connect to the server
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Read messages from the server
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // Send messages to the server
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) { // Read user input from the console

            System.out.println(in.readLine()); // Display the welcome message from the server

            // Main game loop
            while (true) {
                String serverMessage = in.readLine(); // Read messages from the server
                if (serverMessage == null) {
                    // If the server disconnects, exit the loop
                    System.out.println("Server disconnected. Exiting...");
                    break;
                }
                System.out.println(serverMessage); // Display the server's message

                if (serverMessage.startsWith("Enter your choice")) {
                    // If prompted for a choice, read input and send it to the server
                    String playerChoice = console.readLine();
                    out.println(playerChoice);
                } else if (serverMessage.startsWith("Game over")) {
                    // Exit the loop when the game ends
                    System.out.println("Exiting as game is over.");
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace(); // Handle connection errors
        }
    }
}
