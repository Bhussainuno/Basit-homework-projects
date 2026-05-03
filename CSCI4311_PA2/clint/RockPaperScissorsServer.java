import java.io.*;
import java.net.*;
import java.util.Scanner;

public class RockPaperScissorsServer {

    private static final int PORT = 5000;
    private static Player player1 = null;
    private static Player player2 = null;
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                // Accept new player connections
                Socket socket = serverSocket.accept();
                System.out.println("New player connected: " + socket.getInetAddress());

                // Assign player to Player 1 or Player 2
                if (player1 == null) {
                    player1 = new Player(socket, "Player 1");
                    new Thread(player1).start();
                } else if (player2 == null) {
                    player2 = new Player(socket, "Player 2");
                    new Thread(player2).start();
                }

                // When both players are connected, start the game
                if (player1 != null && player2 != null) {
                    startGame();
                }
            }
        } catch (IOException e) {
            System.out.println("Error starting the server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Start the game logic
    private static void startGame() {
        System.out.println("Both players are connected. Starting the game...");

        Scanner scanner = new Scanner(System.in);

        // Game loop
        while (true) {
            // Player 1 makes a choice
            String player1Choice = player1.getChoice();
            if (player1Choice == null) break;

            // Send Player 1's choice to Player 2
            player2.sendMessage("Player 1 chose: " + player1Choice);

            // Player 2 makes a choice
            String player2Choice = player2.getChoice();
            if (player2Choice == null) break;

            // Send Player 2's choice to Player 1
            player1.sendMessage("Player 2 chose: " + player2Choice);

            // Determine the winner
            String result = determineWinner(player1Choice, player2Choice);
            player1.sendMessage("Result: " + result);
            player2.sendMessage("Result: " + result);

            // Ask if both players want to play again
            player1.sendMessage("Do you want to play again? (yes/no)");
            player2.sendMessage("Do you want to play again? (yes/no)");

            String player1Response = player1.getResponse();
            String player2Response = player2.getResponse();

            if (player1Response == null || player2Response == null || 
                !player1Response.equalsIgnoreCase("yes") || 
                !player2Response.equalsIgnoreCase("yes")) {
                System.out.println("Ending game.");
                break;
            }
        }

        // Close the connections after the game ends
        try {
            player1.close();
            player2.close();
        } catch (IOException e) {
            System.out.println("Error closing connections: " + e.getMessage());
        }
    }

    // Determine the winner based on choices
    private static String determineWinner(String choice1, String choice2) {
        if (choice1.equals(choice2)) {
            return "It's a tie!";
        }
        if (choice1.equals("rock")) {
            return choice2.equals("scissors") ? "Player 1 wins!" : "Player 2 wins!";
        } else if (choice1.equals("paper")) {
            return choice2.equals("rock") ? "Player 1 wins!" : "Player 2 wins!";
        } else if (choice1.equals("scissors")) {
            return choice2.equals("paper") ? "Player 1 wins!" : "Player 2 wins!";
        } else {
            return "Invalid choice!";
        }
    }

    // Player class that handles communication with each player
    static class Player implements Runnable {
        private Socket socket;
        private String name;
        private PrintWriter out;
        private BufferedReader in;
        private Scanner scanner;

        public Player(Socket socket, String name) {
            this.socket = socket;
            this.name = name;
            try {
                this.out = new PrintWriter(socket.getOutputStream(), true);
                this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.scanner = new Scanner(socket.getInputStream());
            } catch (IOException e) {
                System.out.println("Error initializing player I/O: " + e.getMessage());
            }
        }

        @Override
        public void run() {
            try {
                out.println(name + ", welcome to Rock Paper Scissors game!");

                // Start the game interaction loop
                out.println("Enter your choice (rock, paper, or scissors):");
                out.println("Waiting for your choice...");

            } catch (Exception e) {
                System.out.println("Error during player interaction: " + e.getMessage());
                e.printStackTrace();
            }
        }

        public String getChoice() {
            try {
                String choice = in.readLine();
                if (choice != null && (choice.equals("rock") || choice.equals("paper") || choice.equals("scissors"))) {
                    return choice;
                } else {
                    out.println("Invalid choice. Please enter 'rock', 'paper', or 'scissors'.");
                    return getChoice();
                }
            } catch (IOException e) {
                System.out.println("Error reading player choice: " + e.getMessage());
                return null;
            }
        }

        public String getResponse() {
            try {
                String response = in.readLine();
                return response != null ? response : "no";
            } catch (IOException e) {
                System.out.println("Error reading response: " + e.getMessage());
                return null;
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }

        public void close() throws IOException {
            in.close();
            out.close();
            socket.close();
        }
    }
}
