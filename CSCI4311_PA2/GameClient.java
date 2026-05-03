import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;
import java.util.*;

public class GameClient extends JFrame {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String playerName;
    private Map<String, Point> snakePositions;
    private Point foodPosition;

    public GameClient(String serverAddress) {
        snakePositions = new HashMap<>();
        foodPosition = new Point(5, 5); // Initial food position
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            socket = new Socket(serverAddress, 12345);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Get player name
            playerName = JOptionPane.showInputDialog("Enter your username:");
            out.println(playerName); // Send the player's name to the server

            // Start a thread to listen for updates from the server
            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println("Server: " + serverMessage);
                        // Update the game state with the received message
                        updateGameState(serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Key listener to send commands to the server
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                String command = "";
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP: command = "UP"; break;
                    case KeyEvent.VK_DOWN: command = "DOWN"; break;
                    case KeyEvent.VK_LEFT: command = "LEFT"; break;
                    case KeyEvent.VK_RIGHT: command = "RIGHT"; break;
                }
                out.println(command);  // Send the command to the server
            }
        });
        setFocusable(true);
    }

    // Method to update the game state (called when server sends updates)
    private void updateGameState(String serverMessage) {
        // You can parse the server message and update the game state
        // For example, serverMessage might be something like:
        // "Game State: {player1=Point[5, 5]} Food: Point[10, 10]"
        // You'll need to extract this data and update the snake positions and food
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.clearRect(0, 0, getWidth(), getHeight());

        // Draw the snakes
        g.setColor(Color.GREEN);
        for (Point p : snakePositions.values()) {
            g.fillRect(p.x * 20, p.y * 20, 20, 20);
        }

        // Draw the food
        g.setColor(Color.RED);
        g.fillRect(foodPosition.x * 20, foodPosition.y * 20, 20, 20);
    }

    public static void main(String[] args) {
        String serverAddress = JOptionPane.showInputDialog("Enter server IP address:");
        GameClient client = new GameClient(serverAddress);
        client.setTitle("Multiplayer Snake Game");
        client.setVisible(true);
    }
}
