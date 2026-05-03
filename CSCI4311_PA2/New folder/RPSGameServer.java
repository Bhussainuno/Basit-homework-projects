import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RPSGameServer {
    private static final int PORT = 5000;
    private static ExecutorService pool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        System.out.println("Server started, waiting for players to connect...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket player1 = serverSocket.accept();
                System.out.println("Player 1 connected.");
                PrintWriter out1 = new PrintWriter(player1.getOutputStream(), true);
                out1.println("Waiting for Player 2...");

                Socket player2 = serverSocket.accept();
                System.out.println("Player 2 connected.");
                PrintWriter out2 = new PrintWriter(player2.getOutputStream(), true);

                out1.println("Player 2 connected! Start playing!");
                out2.println("Connected to Player 1! Start playing!");

                pool.execute(new GameSession(player1, player2));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class GameSession implements Runnable {
    private Socket player1;
    private Socket player2;

    public GameSession(Socket player1, Socket player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void run() {
        try (
            BufferedReader in1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
            PrintWriter out1 = new PrintWriter(player1.getOutputStream(), true); // Auto-flush enabled
            BufferedReader in2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));
            PrintWriter out2 = new PrintWriter(player2.getOutputStream(), true); // Auto-flush enabled
        ) {
            // Prompt Player 1 for a move
            out1.println("Enter your move (rock, paper, scissors): ");
            System.out.println("Waiting for Player 1's move...");
            String move1 = in1.readLine();
            if (move1 == null) {
                System.out.println("Player 1 disconnected.");
                out2.println("Player 1 disconnected. Ending game.");
                return;
            }
            System.out.println("Received move from Player 1: " + move1);

            // Prompt Player 2 for a move
            out2.println("Enter your move (rock, paper, scissors): ");
            System.out.println("Waiting for Player 2's move...");
            String move2 = in2.readLine();
            if (move2 == null) {
                System.out.println("Player 2 disconnected.");
                out1.println("Player 2 disconnected. Ending game.");
                return;
            }
            System.out.println("Received move from Player 2: " + move2);

            // Determine and send the game result
            String result = determineWinner(move1, move2);
            System.out.println("Game result: " + result);

            out1.println("Game Result: " + result);
            out2.println("Game Result: " + result);
            System.out.println("Result sent to both players.");
        } catch (IOException e) {
            System.err.println("Error during game session: " + e.getMessage());
        } finally {
            try {
                player1.close();
                player2.close();
            } catch (IOException e) {
                System.err.println("Error closing player sockets: " + e.getMessage());
            }
        }
    }

    private String determineWinner(String move1, String move2) {
        if (move1.equals(move2)) {
            return "Draw";
        } else if ((move1.equals("rock") && move2.equals("scissors")) ||
                   (move1.equals("scissors") && move2.equals("paper")) ||
                   (move1.equals("paper") && move2.equals("rock"))) {
            return "Player 1 wins!";
        } else {
            return "Player 2 wins!";
        }
    }
}
