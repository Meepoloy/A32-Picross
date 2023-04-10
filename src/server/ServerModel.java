package server;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import a32Pack.*;

public class ServerModel implements Config {
    /**
     * 2d solution
     */
    private int[][] boardSolution;

    /**
     * Locale
     */
    private Locale currentLocale;

    /**
     * Resource bundle
     */
    private ResourceBundle texts;

    /**
     * String language
     */
    private String lang = "";

    /**
     * String country
     */
    private String country = "";

    private boolean portValid;

    private GameServer server;

    private Thread sThread;

    /**
     * int Diemnsion
     */
    private int dimension;

    public ServerModel() {
        // default language / settings
        setCountry("US");
        setLang("en");
        buildingLocale();
        System.out.println("Success");
    }

    /**
     * Dimension Setter
     * 
     * @param index int index
     */
    public void setDimension(int index) {
        dimension = Integer.valueOf(DIMENSIONLIST[index]);
    }

    /**
     * Dimension getter
     * 
     * @return int dimensino
     */
    public int getDimension() {
        return dimension;
    }

    /**
     * Language setter
     * 
     * @param str String language abbrv
     */
    private void setLang(String str) {
        lang = str;
    }

    /**
     * Country setter
     * 
     * @param str string country abbrv
     */
    private void setCountry(String str) {
        country = str;
    }

    /**
     * Label getter
     * 
     * @param s string key
     * @return return string label
     */
    public String getLabel(String s) {
        return texts.getString(s);
    }

    /**
     * Building Locale for I18N
     */
    public void buildingLocale() {
        try {
            currentLocale = new Locale(lang, country);
            texts = ResourceBundle.getBundle(SYSTEMMESSAGES, currentLocale);
        } catch (Exception e) {
            System.out.println("Error!!!");
        }

    }

    /**
     * Locale updated
     * 
     * @param index int langauge 0:English 1:French 2:Spanish
     */
    public void updateLocale(int index) {
        System.out.println("Index: " + index);
        setLang(LANGUAGES[index]);
        setCountry(COUNTRIES[index]);
        buildingLocale();
    }

    public void generatePicross() {
        SecureRandom rand = new SecureRandom();
        boardSolution = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++) {
                boardSolution[i][j] = rand.nextInt(2);
            }
    }

    public String boardToString() {
        String solution = "Solution:";
        for (int i = 0; i < dimension; i++) {
            solution += "\n";
            for (int j = 0; j < dimension; j++)
                solution += boardSolution[i][j] + " ";
        }
        return solution;
    }

    public void eventLogAppend(ServerController control, String str) {
        control.getEventLog().append(str + "\n");
    }

    public boolean isPortValid(String str) {
        // 10k-65535
        // it has to be an integer
        portValid = false;
        int port = -1;
        try {
            port = Integer.valueOf(str);
        } catch (NumberFormatException e) {
            return portValid;
        }

        if (port < MIN_PORT_LIMIT || port > MAX_PORT_LIMIT)
            return portValid;

        portValid = true;
        return portValid;
    }

    public boolean execButAction(ServerController control) {
        if (!isPortValid(control.getPortField().getText())) {
            eventLogAppend(control, "Port Invalid!");
            control.getPortField().setText(null);
            return false;
        }
        eventLogAppend(control, "Port Valid!");

        control.getFunctionButtons()[0].setEnabled(false);
        control.getPortField().setEditable(false);

        return setServer(Integer.valueOf(control.getPortField().getText()), control);
    }

    private boolean setServer(int port, ServerController control) {
        try {
            server = new GameServer(port);
            if (!server.isPortBound())
                return false;
            sThread = new Thread(new Runnable() {
                public void run() {
                    server.startServer(control);
                }
            });
            sThread.start();
        } catch (IOException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;

        }
        return true;
    }

    public void stopServer() {
        // sThread.interrupt();
        sThread.stop();
        System.out.println("Server stopping");
        server.stopServer();
        // server.stopServer();
    }

    public void endButAction(ServerController control) {
        control.getPortField().setText(null);

        control.getFunctionButtons()[0].setEnabled(true);
        control.getPortField().setEditable(true);
    }

    public String[] getDimList() {
        return DIMENSIONLIST;
    }

    public int[][] getBoard() {
        return boardSolution;
    }

    public String boardSend() {
        if (boardSolution == null)
            return null;
        // String[] boardSol = new String[dimension + 1];
        String boardSol = REPLY + Integer.toString(dimension);
        // boardSol[0] = Integer.toString(dimension);

        for (int i = 0; i < dimension; i++) {
            boardSol += "," + Integer.toString(covertToBase10(boardSolution[i]));
        }
        return boardSol;
    }

    public int covertToBase10(int[] arr) {
        String base2 = Arrays.toString(arr);
        System.out.println(base2);
        base2 = "";
        for (int i = 0; i < arr.length; i++)
            base2 += Integer.toString(arr[i]);

        System.out.println(base2);
        return Integer.parseInt(base2, 2);
    }

    public void scoreCheck() {
        server.scoreCheck();
    }

}
