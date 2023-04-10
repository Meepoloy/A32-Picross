package client;

import java.net.*;
import java.io.*;

public class MultiThreadedClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        System.out.println("Connected to server...");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        Thread inputThread = new Thread(() -> {
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String inputLine;
            try {
                while ((inputLine = userInput.readLine()) != null) {
                    // it receives
                    out.println(inputLine);
                    System.out.println("You: " + inputLine);
                }
            } catch (IOException e) {
                System.err.println("Error reading user input: " + e);
            }
        });
        inputThread.start();

        Thread outputThread = new Thread(() -> {
            String outputLine;
            try {
                while ((outputLine = in.readLine()) != null) {
                    System.out.println("Server response: " + outputLine);
                }
            } catch (IOException e) {
                System.err.println("Error reading server response: " + e);
            }
        });
        outputThread.start();
    }
}