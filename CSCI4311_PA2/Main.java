import java.awt.Color;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args){
        // Create a JFrame object
        JFrame obj = new JFrame();
        // Create a Gameplay object (this is your custom JPanel)
        Gameplay gameplay = new Gameplay();
        
        // Set up the JFrame window
        obj.setBounds(10, 10, 905, 700); // Set window size and position
        obj.setBackground(Color.DARK_GRAY); // Set window background color
        obj.setResizable(false); // Disable resizing the window
        obj.setVisible(true); // Make the window visible
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit when closing the window
        obj.add(gameplay); // Add the Gameplay panel to the window
    }
}
