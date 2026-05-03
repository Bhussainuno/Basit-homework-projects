import java.io.*;
import java.net.*;
import java.util.Scanner;

public class RPSGameClient {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int PORT = 5000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // Auto-flush enabled
            Scanner scanner = new Scanner(System.in);

            // Receive initial messages from server
            String message = in.readLine();
            System.out.println(message);

            message = in.readLine();
            System.out.println(message);

            // Only proceed if both players are connected
            if (message.contains("Start playing!")) {
                // Wait for server to prompt for input
                String prompt = in.readLine();
                System.out.println(prompt); // "Enter your move (rock, paper, scissors):"

                // Enter and send the move
                String move = scanner.nextLine().toLowerCase().trim();
                out.println(move);  // Send move to server
                System.out.println("Move sent: " + move);

                // Receive and print the game result
                String result = in.readLine();
                if (result != null) {
                    System.out.println("Game Result: " + result);
                } else {
                    System.out.println("No result received from the server. Connection may have been lost.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error in client connection: " + e.getMessage());
        }
    }
}
