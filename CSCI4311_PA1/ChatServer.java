/* 
 * @author Basit Hussain, 2542788
 * CSCI 4311/5311, Fall 2024
 * Due Date: Oct 2, 2024, 11:59 PM
 * Programming Assignment #1
 */

/*
 * This is a multi-threaded chat program implemented server-side. The class ChatServer: 
 * - Manages messages and client connections   
 * - Uses threads to manage client communication   
 * - Sends messages to every client that is connected.
 */

 import java.io.*;               // Input/Output operations
 import java.net.*;              // networking and socket programming
 import java.util.*;             // utility classes like ArrayList, HashMap, etc.
 import java.nio.*;              // non-blocking I/O operations
 import java.nio.channels.*;      // working with channels and selectors
 import java.util.concurrent.*;    // concurrency utilities
 
 // Defining the ChatServer class
 public class ChatServer {
 
     // List to hold all active clients connected to the server
     public static ArrayList<ClientConnection> clientConnections = new ArrayList<>();
 
     // A counter that keeps track of how many clients are logging in to the server
     static int clientCounter = 1;
 
     /*
      * The server is initialized and accepts client connections by the main method.
      */
     public static void main(String[] args) throws Exception {
         ChatServer server = new ChatServer(8989);  // Server starts on port 8989
     }
 
     /*
      * The server is configured by the ChatServer constructor to accept incoming client connections.
      */
     public ChatServer(int port) throws Exception {
         ServerSocket serverSocket = new ServerSocket(port);
         System.out.println("Chat Server is now running.");
         System.out.println("Waiting for clients...");
 
         Socket clientSocket;
 
         // To keep the server running and taking in new clients, loop
         while (true) {
             clientSocket = serverSocket.accept();  // Accept new client connection
             System.out.println("New client connection accepted.");
 
             // Creating a new thread to handle for the client connection
             ClientConnection clientHandler = new ClientConnection(clientSocket, "User_" + clientCounter);
 
             // Startiing the client which handle thread
             clientHandler.start();
 
             // Adding the new client which is listed of connected clients
             clientConnections.add(clientHandler);
 
             // Displaying all connected clients
             clientConnections.forEach(client -> {
                 System.out.println(client.username);
             });
 
             // Incrementing
             clientCounter++;
         }
     }
 
     /* 
      * The ClientConnection class manages, in a separate thread, 
      * the communication between the server and each client.
      */
     class ClientConnection extends Thread {
         private Socket socket;  // Socket for the client connection
         private String username;  // Client's username
         private boolean isActive;  // Status of the client's connection
         private BufferedReader inputReader;  // Reader for incoming messages
         private PrintWriter outputWriter;  // Writer for outgoing messages
 
         /* 
          * Constructor for the ClientConnection class, 
          * which creates input/output streams for client communication.
          */
         public ClientConnection(Socket socket, String username) throws Exception {
             this.socket = socket;  // Initializing the socket
             this.username = username;  // Setting up the username
             this.isActive = true;  // Setting up the connection status
             this.inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));  // Initializing the input stream
             this.outputWriter = new PrintWriter(socket.getOutputStream(), true);  // Initializing the output stream
         }
 
         /* 
          * The messages sent by the client are processed by the run() method, 
          * which then broadcasts them to all other clients that are connected. 
          * It controls client disconnections as well.
          */
         @Override
         public void run() {
             String message;  // Variable which is holding the incoming messages
 
             try {
                 // client to supply a valid username in the format
                 outputWriter.println("Please provide your username in the format: 'username = YourName': ");
                 String inputMessage = inputReader.readLine();  // Reading user input
 
                 // Verifying the format of the username
                 if (inputMessage.startsWith("username = ")) {
                     this.username = inputMessage.substring(11).trim();  // Extracting the username from the input
                 } else {
                     outputWriter.println("Invalid username format. Connection closing.");  // Error message
                     clientConnections.remove(this);  // Removing the client from the list
                     socket.close();  // Closing the socket into connections
                     return;  // Exit if the username is invalid
                 }
 
                 // Notifying all clients that a new user has joined
                 for (ClientConnection client : clientConnections) {
                     client.outputWriter.println("Server: Welcome " + this.username);  // Welcome message
                 }
                 System.out.println("Server: Welcome " + this.username);  // Server-side log
 
             } catch (Exception e) {
                 System.out.println("Server error: " + e.getMessage());  // Log error
                 e.printStackTrace();  // Printing the stack trace for debugging
                 return;  // Exit if an error occurs during login
             }
 
             // This loop responded to the messages that arrive from the client
             try {
                 while (true) {
                     message = inputReader.readLine();  // Read message from client
                     System.out.println(this.username + ": " + message);  // Printing client's message
 
                     // If the client requests a list of connected users
                     if (message.toLowerCase().equals("allusers")) {
                         outputWriter.println("\nConnected users:");  // Response header
                         clientConnections.forEach(client -> {
                             outputWriter.println(client.username);  // List each connected client
                         });
                         outputWriter.println("\n");
 
                     // If the client chooses to disconnect
                     } else if (message.toLowerCase().equals("bye")) {
                         outputWriter.println("Server: Goodbye " + this.username);  // Farewell message
                         this.isActive = false;  // Mark as inactive
                         clientConnections.remove(this);  // Remove client from the list
                         socket.close();  // Close socket connection
                         break;  // Exit the loop
 
                     // Send a message from the client to every other client that is connected
                     } else {
                         for (ClientConnection client : clientConnections) {
                             if (!client.username.equals(this.username) && client.isActive) {
                                 client.outputWriter.println(this.username + ": " + message);  // Broadcast message
                             }
                         }
                     }
                 }
             } catch (Exception e) {
                 System.out.println("Error handling client: " + e.getMessage());  // Log error
                 e.printStackTrace();  // Print stack trace for debugging
             }
 
             // After the client disconnects, make sure the socket and input/output streams are closed
             try {
                 inputReader.close();  // Closing the input stream
                 outputWriter.close();  // Closing the output stream
             } catch (Exception e) {
                 System.out.println("Error closing resources: " + e.getMessage());  // Log error
                 e.printStackTrace();  // Printing the stack trace
             }
         }
     }
 }
 