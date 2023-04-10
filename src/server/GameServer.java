package server;

// import client.GameClient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;

public class GameServer {

    private ServerSocket serverSocket;
    // private ArrayList<ConnectionHandler> clientList = new ArrayList<>();
    private ConnectionHandler clientHandler;

    public GameServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.isBound();
    }

    public boolean isPortBound() {
        return serverSocket.isBound();
    }

    public void startServer(ServerController control) {
        System.out.println("Server Running");
        // control.getFinalCheckBox().isSelected();

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.printf("Client %s\n", clientSocket.toString());
                clientHandler = new ConnectionHandler(clientSocket, control);

                Thread t1 = new Thread(clientHandler);
                t1.start();
                Thread.sleep(100);
                System.out.println("Afterstart");
                // control.getEventLog().append(getUsername() + " " +
                //
                // clientSocket.close();
            } catch (IOException | InterruptedException e) {
                // isRunning = false;
                System.out.println("Error here");
                e.printStackTrace();
            }
        }
    }

    public void stopServer() {
        try {
            if (serverSocket == null)
                return;
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scoreCheck() {
        try {
            clientHandler.scoreCheck();

        } catch (Exception e) {
            System.out.println("No Clients connected...");
        }
    }

}
