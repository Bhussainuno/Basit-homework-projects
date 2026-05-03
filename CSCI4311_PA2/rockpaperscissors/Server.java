import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 5000; // Port where the server will listen for connections
    private static final int MAX_PLAYERS = 2; // We need two players for this multiplayer game
    private static Socket[] players = new Socket[MAX_PLAYERS]; // Array to store player connections
    private static String[] choices = new String[MAX_PLAYERS]; // Array to store player choices (rock, paper, scissors)
    private static int playerCount = 0; // Keeps track of connected players

    public static void main(String[] args) {
        System.out.println("Server is running...");
        ExecutorService pool = Executors.newFixedThreadPool(MAX_PLAYERS); // Thread pool to handle multiple players

        try (ServerSocket listener = new ServerSocket(PORT)) {
            // Wait for two players to connect
            while (playerCount < MAX_PLAYERS) {
                Socket client = listener.accept(); // Accept a new player connection
                players[playerCount] = client; // Store the connection in the players array
                System.out.println("Player " + (playerCount + 1) + " connected.");
                pool.execute(new PlayerHandler(client, playerCount)); // Create a new thread for the player
                playerCount++; // Increment the count of connected players
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class PlayerHandler implements Runnable {
        private Socket socket; // The socket connection for this player
        private int playerId; // Player's ID (0 for Player 1, 1 for Player 2)

        public PlayerHandler(Socket socket, int playerId) {
            this.socket = socket; // Store the socket
            this.playerId = playerId; // Assign the player ID
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                // Send a welcome message to the player
                out.println("Welcome, Player " + (playerId + 1) + "! Waiting for the other player...");

                // Wait until both players are connected
                while (playerCount < MAX_PLAYERS) {
                    Thread.sleep(100); // Wait a short time before checking again
                }

                // Inform the player that the game is starting
                out.println("Game starts now!");

                // Ask the player for their choice
                out.println("Enter your choice (rock, paper, scissors):");
                String choice = in.readLine(); // Read the player's choice
                if (choice == null) {
                    System.out.println("Player " + (playerId + 1) + " disconnected.");
                    return; // Stop handling this player if they disconnected
                }
                choices[playerId] = choice; // Store the player's choice

                // Wait until both players have made their choices
                while (choices[0] == null || choices[1] == null) {
                    Thread.sleep(100); // Wait a short time before checking again
                }

                // Only Player 1 determines the winner
                if (playerId == 0) {
                    String result = determineWinner(choices[0], choices[1]); // Determine the winner

                    // Send the result to both players
                    synchronized (players) {
                        for (Socket player : players) {
                            if (player != null && !player.isClosed()) {
                                try {
                                    PrintWriter playerOut = new PrintWriter(player.getOutputStream(), true);
                                    playerOut.println(result); // Send the result
                                    playerOut.println("Game over. Server is shutting down."); // Notify the game is over
                                } catch (IOException e) {
                                    System.out.println("Error sending result to player: " + e.getMessage());
                                }
                            }
                        }
                    }

                    // Give clients time to process messages before shutting down
                    Thread.sleep(500);

                    // Shut down the server after the game ends
                    System.out.println("Game finished. Shutting down server.");
                    System.exit(0); // Terminate the server
                }

            } catch (IOException | InterruptedException e) {
                System.out.println("Error in PlayerHandler: " + e.getMessage());
            } finally {
                try {
                    socket.close(); // Ensure the socket is closed properly
                } catch (IOException e) {
                    System.out.println("Error closing socket: " + e.getMessage());
                }
            }
        }

        private String determineWinner(String choice1, String choice2) {
            // Compare the choices to determine the winner
            if (choice1.equals(choice2)) {
                return "It's a tie!";
            }
            if ((choice1.equals("rock") && choice2.equals("scissors")) ||
                (choice1.equals("scissors") && choice2.equals("paper")) ||
                (choice1.equals("paper") && choice2.equals("rock"))) {
                return "Player 1 wins!";
            }
            return "Player 2 wins!";
        }
    }
}
