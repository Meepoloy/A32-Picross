package client;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import a32Pack.Config;

// import server.GameServer;

public class GameClient implements Config {

    private Socket clientSocket;
    // private Thread toServer, fromServer;
    private String outputLine;
    private BufferedReader in;
    private BufferedWriter out;

    GameClient(ClientController control, int port)
            throws ConnectException, UnknownHostException, IOException, SocketException {
        clientSocket = new Socket(control.getTextFields()[1].getText(), port);
        System.out.println("Connected...");

        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        // Sending username
        writeToServer(control.getTextFields()[0].getText());

        // Listening
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromServer;
                System.out.println("Listening thread started..");
                while (clientSocket.isConnected()) {
                    try {
                        messageFromServer = in.readLine();
                        appendInput(control, messageFromServer);
                        System.out.println(messageFromServer);

                    } catch (SocketException se) {
                        System.out.println("Socket closed");
                        // appendInput(control, messageFromServer);
                        break;
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        System.out.println("Error in listening..");
                        e.printStackTrace();
                        break;
                    }
                }
            }

        }).start();

    }

    public void appendInput(ClientController control, String message) {
        System.out.println("on top of append");
        String protocol = String.valueOf(message.charAt(0)) + String.valueOf(message.charAt(1)) + SEPARATOR;
        if (protocol.equals(REPLY)) {
            message = message.substring(3);
            System.out.println("in first if");
            System.out.println(protocol);

            // String[] boardSolution = message.split(",");
            if (message.equals(P1_3)) {
                System.out.println("Disconnecting...");
                System.out.println(P1_3);
                disconnect();
            } else {
                System.out.println("Setting board");
                control.setBoard(message);
                System.out.println(message);

            }
        } else
            control.getEventLog().append(message + "\n");
    }

    public void writeToServer(String message) throws IOException {
        if (!clientSocket.isConnected())
            return;

        out.write(message);
        System.out.println("Write to server");
        out.newLine();
        out.flush();
    }

    public void startClient() {

    }

    public void disconnect() {
        System.out.println("in disconnect");
        if (clientSocket.isClosed())
            return;

        try {
            clientSocket.close();
            // cThread.interrupt();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void setOutput(String text) {
        System.out.println("Setting output line");
        outputLine = text;
    }
}
