import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    public static void main(String[] args) {
        int port = 5050; // Updated port number to 5050

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started, waiting for players...");

            while (true) {
                // Accept a connection from a client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Player connected: " + clientSocket.getInetAddress());

                // Here, you would typically start a new thread to handle each player's actions
                // new Thread(new PlayerHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
