import java.io.*;
import java.net.*;

public class SnakeClient {
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;

    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", 12345); // Connect to the server
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println(in.readLine()); // Receive the welcome message
            System.out.print("Enter a command (UP, DOWN, LEFT, RIGHT): ");
            String command = userInput.readLine();
            out.println(command); // Send command to server

            System.out.println(in.readLine()); // Receive server response
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
