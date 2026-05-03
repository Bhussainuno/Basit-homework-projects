// importing classes for user input handling, networking, socket programming, and input/output operations
import java.io.*;                     
import java.net.*;                    
import java.util.Scanner;            

public class ChatClient {             // Declaring the ChatClient class
    final static int SERVER_PORT = 8989;  // specifying the port number for the server connection continuously

    public static void main(String[] args) throws UnknownHostException, IOException {
        Scanner inputScanner = new Scanner(System.in);  // launching a Scanner instance and using the terminal to read user input

        // Obtain the server's local IP address.
        InetAddress hostAddress = InetAddress.getByName("localhost");  // converting "localhost" as the hostname to an IP address


        // establishing a socket to establish a connection with the message server
        Socket clientSocket = new Socket(hostAddress, SERVER_PORT);  // connecting to the server using the designated port

        // Set up the input and output streams for the communication with the server.
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  // in order to view messages from the server
        PrintWriter streamWriter = new PrintWriter(clientSocket.getOutputStream(), true);  // In order to communicate with the server

        // Request the user to input their username
        System.out.println(streamReader.readLine());  // Displaying the server's prompt for username input
        String userName = inputScanner.nextLine();    // Capture the username input from the user
        streamWriter.println(userName);                // Send the captured username to the server

        // Thread responsible for sending messages to the server
        Thread senderThread = new Thread(new Runnable() { // Creating a new thread for sending messages
            @Override
            public void run() {  // Overriding the run method to define the thread's behavior
                while (true) {  // Infinite loop to continuously accept user input
                    // Get the message to send
                    String outgoingMessage = inputScanner.nextLine();  // Read the message from user input
                    // Try-catch block for error handling while sending messages
                    try {
                        streamWriter.println(outgoingMessage);  // Send the message to the server
                    } catch (Exception e) {
                        System.out.println("Failed to send message: " + e.getMessage() + "\n");  // Log any errors that occur during sending
                    }
                }
            }
        });

        // Thread responsible for receiving messages from the server
        Thread receiverThread = new Thread(new Runnable() { // Creating a new thread for receiving messages
            private String receivedMessage;  // Variable to hold the incoming messages

            @Override
            public void run() {  // Overriding the run method to define the thread's behavior
                try {
                    while ((receivedMessage = streamReader.readLine()) != null) {  // Read messages from the server until null is received
                        // Output the received message to the console
                        System.out.println(receivedMessage);  // Print the incoming message to the console
                    }
                } catch (Exception e) {
                    System.out.println("Failed to receive message: " + e.getMessage() + "\n");  // Log any errors that occur during receiving
                }
            }
        });

        // Start the threads for sending and receiving messages
        receiverThread.start();  // Begin the thread responsible for receiving messages
        senderThread.start();     // Begin the thread responsible for sending messages
    }
}
