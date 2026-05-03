import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Gameplay extends JPanel {
    private int playerX = 50, playerY = 50; // Player's starting position

    public Gameplay() {
        // Set up the panel
        this.setBackground(Color.BLACK); // Game background
        this.setFocusable(true); // Make the panel focusable to capture key events

        // Add a key listener to capture player input
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                // Move the player based on key presses (arrow keys)
                if (keyCode == KeyEvent.VK_LEFT) {
                    movePlayer(-10, 0); // Move left
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    movePlayer(10, 0); // Move right
                } else if (keyCode == KeyEvent.VK_UP) {
                    movePlayer(0, -10); // Move up
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    movePlayer(0, 10); // Move down
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Calls the parent method to ensure proper rendering

        // Draw the player (for example, a red rectangle)
        g.setColor(Color.RED);
        g.fillRect(playerX, playerY, 50, 50); // Drawing player as a rectangle
    }

    // Game logic: Update player position
    public void movePlayer(int dx, int dy) {
        playerX += dx;
        playerY += dy;
        repaint(); // Repaint the screen with the new player position
    }
}
