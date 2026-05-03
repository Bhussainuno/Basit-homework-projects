package rockpaperscissors;

import java.io.*;
import java.net.*;

public class RockPaperScissorsClient {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;
    private static Socket socket;
    private static BufferedReader in;
    private static PrintWriter out;

    public static void main(String[] args) {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Listen for messages from the server
            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                System.out.println(serverMessage);

                if (serverMessage.contains("Enter your choice")) {
                    sendChoice();
                } else if (serverMessage.contains("Do you want to play again")) {
                    String response = getUserInput();
                    out.println(response);
                }
            }
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing the socket: " + e.getMessage());
            }
        }
    }

    private static void sendChoice() {
        String choice = getUserInput();
        out.println(choice);
    }

    private static String getUserInput() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading user input: " + e.getMessage());
            return null;
        }
    }
}
