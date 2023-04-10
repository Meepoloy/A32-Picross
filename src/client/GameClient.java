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

        // Sending
        // new Thread(new Runnable() {
        // @Override
        // public void run() {
        // String messageToServer;
        // System.out.println("Output thread started..");
        // while (clientSocket.isConnected()) {
        // try {
        // messageToServer = in.readLine();
        // System.out.println(messageToServer);

        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // System.out.println("Error in listening..");
        // e.printStackTrace();
        // break;
        // }
        // }
        // }

        // }).start();

        // PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        // out.println(control.getTextFields()[0].getText()); // This is working

        // toServer = new Thread(() -> {
        // // BufferedReader userInput = new BufferedReader(new
        // // InputStreamReader(System.in));
        // // String inputLine;
        // while (outputLine != null) {
        // System.out.println("inwhile");
        // // it receives
        // out.println(outputLine);
        // System.out.println("You: " + outputLine);
        // outputLine = "";
        // }
        // });

        // toServer.start();

        // Thread inputThread = new Thread(() -> {
        // BufferedReader userInput = new BufferedReader(new
        // InputStreamReader(System.in));
        // String inputLine;
        // try {
        // while ((inputLine = userInput.readLine()) != null) {
        // // it receives
        // out.println(inputLine);
        // System.out.println("You: " + inputLine);
        // }
        // } catch (IOException e) {
        // System.err.println("Error reading user input: " + e);
        // }
        // });
        // inputThread.start();

        // fromServer = new Thread(() -> {
        // String outputLine;
        // try {
        // while ((outputLine = in.readLine()) != null) {
        // System.out.println("Server response: " + outputLine);
        // control.getEventLog().append(outputLine + "\n");
        // }
        // } catch (IOException e) {
        // System.err.println("Error reading server response: " + e);
        // }
        // });
        // fromServer.start();

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

    // public void sendToServer(String message) {
    // if (message == null)
    // return;

    // }

    // public String getResponse() {
    // try {
    // // inData = new DataInputStream(socket.getInputStream());
    // // return inData.readUTF();
    // } catch (Exception e) {
    // e.getStackTrace();
    // return null;
    // } finally {
    // return null;
    // }
    // }

    // inThread = new Thread(new Runnable() {
    // @Override
    // public void run() {
    // try {
    // BufferedReader userInput = new BufferedReader(new
    // InputStreamReader(clientSocket.getInputStream()));
    // String inputLine;
    // while ((inputLine = userInput.readLine()) != null) {
    // System.out.println(inputLine);
    // }
    // } catch (IOException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }

    // }
    // });
    public void setOutput(String text) {
        System.out.println("Setting output line");
        outputLine = text;
    }
}
