package server;

import java.net.*;
import java.util.ArrayList;
import java.io.*;
import a32Pack.*;

/**
 * ConnectionHandler - handles server-cloent connectino
 */
public class ConnectionHandler implements Runnable, Config {

    private Socket client;
    private static ArrayList<ConnectionHandler> list = new ArrayList<>();
    private BufferedReader in; // Reader

    private BufferedWriter out; // Writer
    private String username; // Username
    private ServerController control; // Controller
    private String stats; // Stats

    /**
     * Constructor for connection hnadler
     * 
     * @param clientSocket socket obj
     * @param control      Servercontroller obj
     */
    public ConnectionHandler(Socket clientSocket, ServerController control) {
        try {
            client = clientSocket;
            this.control = control;
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            username = in.readLine(); // this works
            list.add(this);
            broadcastMessage(P0 + username);
            control.getEventLog().append(username + " " + control.getLabel("JOINMSG") + "\n");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Error in Connection handler");
            e.printStackTrace();
        }

    }

    /**
     * Run methpd
     */
    public void run() {
        String messageFromClient;
        while (!client.isClosed()) {
            try {
                messageFromClient = in.readLine();
                // Thread.sleep(100);
                broadcastMessage(messageFromClient);
            } catch (IOException io) {
                System.out.println("Error in connection handler");
                if (list.size() == 0 && control.getFinalCheckBox().isSelected()) {
                    // control.getFinalCheckBox().isSelected();
                    System.exit(-1);
                }
                list.remove(this);
                break;
            }
        }
    }

    /**
     * Broadcast message
     * 
     * @param message String message
     */
    public void broadcastMessage(String message) {
        if (message == null)
            return;
        System.out.print("Top of broadcast\t");
        String protocol = String.valueOf(message.charAt(0)) + String.valueOf(message.charAt(1));
        System.out.println(protocol);
        switch (protocol) {
            case P0: // Server to All
                for (ConnectionHandler clientHandler : list) {
                    try {
                        if (!clientHandler.username.equals(username)) {
                            clientHandler.out.write(username + " " + control.getLabel("JOINMSG"));
                            clientHandler.out.newLine();
                            clientHandler.out.flush();
                        }
                    } catch (Exception e) {
                        System.err.println("error in for loop P0");
                        e.printStackTrace();
                    }
                }

                break;
            case P1: // Sent Request from Client
                message = message.substring(2);
                int len = message.length();
                String request = "";
                int j;
                for (j = 0; j < len; j++) {
                    if (message.charAt(j) == '#')
                        break;
                    request += String.valueOf(message.charAt(j));
                }

                switch (request) {

                    case P1_1:// Send Game
                        System.out.println(message.substring(j + 1));

                        break;
                    case P1_2:// Receive Game Request
                        System.out.println(username + " is requesting a gameboard");
                        try {
                            out.write(control.getBoard());
                            out.newLine();
                            out.flush();

                        } catch (Exception e) {
                            System.err.println("Error sending board");
                            e.printStackTrace();
                        }
                        break;
                    case P1_3:// Disconnect Notif/Request
                        // Notif
                        list.remove(this);
                        for (ConnectionHandler clientHandler : list) {
                            try {
                                if (!clientHandler.username.equals(username)) {
                                    clientHandler.out.write(username + " " + control.getLabel("DCMSG"));
                                    clientHandler.out.newLine();
                                    clientHandler.out.flush();
                                }
                            } catch (Exception e) {
                                System.err.println("error in for loop P0");
                                e.printStackTrace();
                            }
                        }

                        try {
                            out.write(REPLY + P1_3);
                            out.newLine();
                            out.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            System.out.println("error in replying");
                            e.printStackTrace();
                        } finally {
                            if (list.size() == 0 && control.getFinalCheckBox().isSelected()) {
                                // control.getFinalCheckBox().isSelected();
                                System.exit(-1);
                            }
                        }

                        break;
                    case P1_4:// Send Data
                        System.out.println(message.substring(j + 1));
                        stats = message.substring(j + 1);

                        break;

                    default:
                        break;
                }

                break;

            case P3:// Chat
                for (ConnectionHandler clientHandler : list) {
                    try {
                        if (!clientHandler.username.equals(username)) {
                            clientHandler.out.write(username + ": " + message.substring(2));
                            clientHandler.out.newLine();
                            clientHandler.out.flush();
                        }
                    } catch (Exception e) {
                        System.err.println("error in for loop P3  ");
                        e.printStackTrace();

                    }
                }
                control.getEventLog().append(username + ": " + message.substring(2) + "\n");

                break;

            default:
                break;
        }
    }

    /**
     * Scorecheck method
     */
    public void scoreCheck() {
        String[] stats = new String[list.size()];
        control.getEventLog().append("Score Check!!!\n");

        for (int i = 0; i < stats.length; i++) {
            if (list.get(i).stats == null) {
                stats[i] = list.get(i).username + ": N/A";
            } else {
                stats[i] = list.get(i).username + ": " + list.get(i).stats;

            }
        }
        for (String string : stats) {
            System.out.println(string);
            control.getEventLog().append(string + "\n");
        }
    }
}