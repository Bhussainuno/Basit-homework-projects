import java.io.*;
import java.net.*;
import java.util.*;

public class GameServer {
    private static final int PORT = 12345;
    private static final int BOARD_SIZE = 10; // Assuming the board size is 10x10
    private static ServerSocket serverSocket;
    private static Socket playerSocket;
    private static PrintWriter out;
    private static BufferedReader in;

    public static void main(String[] args) {
        System.out.println("Server started, waiting for players...");
        
        try {
            serverSocket = new ServerSocket(PORT);
            playerSocket = serverSocket.accept(); // Wait for player to connect
            System.out.println("PLAYER1 has joined the game!");

            // Setup input and output streams for communication
            in = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
            out = new PrintWriter(playerSocket.getOutputStream(), true);

            // Initialize the player at a central position
            Player player1 = new Player(5, 5); // Center position (5, 5)
            out.println("Welcome PLAYER1. Use UP, DOWN, LEFT, RIGHT to move.");
            
            String command;
            while ((command = in.readLine()) != null) {
                command = command.toUpperCase().trim(); // Make command case-insensitive

                System.out.println("PLAYER1 sent command: " + command);

                // Validate command
                if (!isValidCommand(command)) {
                    out.println("Invalid command received. Please use UP, DOWN, LEFT, or RIGHT.");
                    continue; // Skip invalid commands
                }

                // Process movement based on command
                int newX = player1.getX();
                int newY = player1.getY();

                switch(command) {
                    case "UP": 
                        newY -= 1;
                        break;
                    case "DOWN": 
                        newY += 1;
                        break;
                    case "LEFT": 
                        newX -= 1;
                        break;
                    case "RIGHT": 
                        newX += 1;
                        break;
                }

                // Check if new position is out of bounds
                if (isOutOfBounds(newX, newY)) {
                    System.out.println("PLAYER1 attempted to move out of bounds.");
                    out.println("Invalid move! You can't move out of bounds.");
                    continue; // Don't update position if out of bounds
                } else {
                    player1.setPosition(newX, newY); // Update position if valid
                    System.out.println("PLAYER1 moved to: (" + newX + ", " + newY + ")");
                    out.println("You moved to: (" + newX + ", " + newY + ")");
                }
            }

            // Clean up when game ends
            in.close();
            out.close();
            playerSocket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Check if the command is valid (UP, DOWN, LEFT, RIGHT)
    private static boolean isValidCommand(String command) {
        return command.equals("UP") || command.equals("DOWN") || command.equals("LEFT") || command.equals("RIGHT");
    }

    // Check if the new position is out of bounds
    private static boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE;
    }

    // Inner class to represent a player
    static class Player {
        private int x;
        private int y;

        public Player(int startX, int startY) {
            this.x = startX;
            this.y = startY;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
