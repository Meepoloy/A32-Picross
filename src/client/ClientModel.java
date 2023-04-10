package client;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

//import GameClient;
import a32Pack.*;

/**
 * Client model
 */
public class ClientModel implements Config {
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

    /**
     * int Dim
     */
    private int dimension;

    /**
     * port validty
     */
    private boolean portValid;

    /**
     * GameClient obj
     */
    private GameClient client;

    /**
     * Bollean for puzzle from server
     */
    private boolean isPuzzleFromServer;

    /**
     * number of correct
     */
    private int numOfCorrectTile;

    /**
     * int correct move
     */
    private int correctMove;

    /**
     * int wrong move
     */
    private int wrongMove;

    /**
     * int points
     */
    private int points;

    /**
     * String array of stats
     */
    private String[] stats;

    public ClientModel() {
        // default language / settings
        setCountry("US");
        setLang("en");
        buildingLocale();
        stats = new String[] { "N/A", "N/A" };
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
     * method for generating picross
     */
    public void generatePicross() {
        isPuzzleFromServer = false;
        numOfCorrectTile = 0;

        SecureRandom rand = new SecureRandom();
        boardSolution = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++) {
                boardSolution[i][j] = rand.nextInt(2);
                if (boardSolution[i][j] == 1)
                    numOfCorrectTile++;
            }

    }

    /**
     * reading board from server
     * 
     * @param message board in string from
     */
    public void readingBoardFromServer(String message) {
        isPuzzleFromServer = true;
        numOfCorrectTile = 0;
        String[] boardStrings = message.split(",");
        dimension = Integer.parseInt(boardStrings[0]);
        boardSolution = new int[dimension][dimension];
        int[] solution = new int[boardStrings.length - 1];
        for (int i = 0; i < solution.length; i++) {
            solution[i] = Integer.parseInt(boardStrings[i + 1]);
        }
        for (int i = 0; i < dimension; i++) {
            int[] row = covertToBinary(Integer.parseInt(boardStrings[i + 1]));
            for (int j = 0; j < dimension; j++) {
                boardSolution[i][j] = row[j];
                if (boardSolution[i][j] == 1)
                    numOfCorrectTile++;
            }
        }

        printSolution();
    }

    /**
     * Method that converts int in base to base 2
     * 
     * @param x int base 10
     * @return int[] that contains the binary
     */
    private int[] covertToBinary(int x) {

        String base2 = Integer.toString(x, 2);
        int[] row = new int[dimension];

        int i = dimension - 1, j = base2.length() - 1;
        while (i >= 0) {
            if (j >= 0) {
                row[i] = base2.charAt(j) - '0';
            } else
                row[i] = 0;

            i--;
            j--;
        }

        return row;
    }

    /**
     * Send board metho
     * 
     * @return true/false depending if the metod is successful
     */
    public boolean sendBoard() {
        if (boardSolution == null)
            return false;

        if (isPuzzleFromServer)
            return false;
        // String[] boardSol = new String[dimension + 1];
        String boardSol = P1 + P1_1 + SEPARATOR + Integer.toString(dimension);
        // boardSol[0] = Integer.toString(dimension);

        for (int i = 0; i < dimension; i++) {
            boardSol += "," + Integer.toString(covertToBase10(boardSolution[i]));
        }
        sendToServer(boardSol);
        return true;
    }

    /**
     * method that converts int array to base 10 int
     * 
     * @param arr rows
     * @return return base10 int
     */
    public int covertToBase10(int[] arr) {
        String base2 = Arrays.toString(arr);
        System.out.println(base2);
        base2 = "";
        for (int i = 0; i < arr.length; i++)
            base2 += Integer.toString(arr[i]);

        System.out.println(base2);
        return Integer.parseInt(base2, 2);
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
     * dim list getter
     * 
     * @return String arr
     */
    public String[] getDimList() {
        return DIMENSIONLIST;
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
     * Method that checks square and logs the actions in the TextArea
     * 
     * @param control    ClientController obj
     * @param lbl        int position of label
     * @param isSolution Boolean for show solution
     * @return returns true if square is right, false otherwise
     */
    public boolean checkSqr(ClientController control, int lbl, boolean isSolution) {
        int x, y;
        if (lbl % getDimension() == 0) {
            x = ((lbl - 1) / getDimension());

        } else
            x = (lbl / getDimension());

        y = lbl - 1 - (getDimension() * x);

        if (!isSolution)
            control.getEventLogDuplicate().append("<but[" + x + "][" + y + "] is pressed>\n");
        if (getSolution()[x][y] == 1) {

            return true;
        }
        return false;
    }

    /**
     * This method appends solution in the UI's textarea
     * 
     * @param control ClientController obj
     */
    public void logSolution(ClientController control) {
        if (boardSolution == null)
            return;
        for (int[] is : boardSolution) {
            for (int i = 0; i < is.length; i++) {
                control.getEventLogDuplicate().append(is[i] + " ");
            }
            control.getEventLogDuplicate().append("\n");
        }
    }

    /**
     * Method for show board solution action
     * 
     * @param control ClientController obj
     */
    public void showBoardSol(ClientController control) {
        for (int i = 0; i < getDimension(); i++) {
            for (int j = 0; j < getDimension(); j++) {
                if (checkSqr(control, getDimension() * i + j + 1, true)) {
                    control.getButtons()[i][j].setBackground(control.getColor()[0]);
                    control.getButtons()[i][j].setEnabled(false);
                } else {
                    control.getButtons()[i][j].setBackground(control.getColor()[2]);
                    control.getButtons()[i][j].setEnabled(false);
                }
            }
        }
    }

    /**
     * Method set Points
     */
    public void setPoints() {
        points = correctMove - wrongMove;
    }

    /**
     * Points getter
     * 
     * @return int points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Method that increments correct move
     */
    public void correctMove() {
        correctMove++;
    }

    /**
     * Method that increments wrong move
     */
    public void wrongMove() {
        wrongMove++;
    }

    public void eventLogAppend(ClientController control, String str) {
        control.getEventLog().append(str + "\n");
        control.getEventLogDuplicate().append(str + "\n");
    }

    /**
     * Port validity getter
     * 
     * @return true if valid, false otherwise
     */
    public boolean getPortvalid() {
        return portValid;
    }

    /**
     * Port validity method
     * 
     * @return true if valid, false otherwise
     */
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

    /**
     * method for connecting
     * 
     * @param control ClientController obj
     * @return true if success, false otherwise
     */
    public boolean connectAction(ClientController control) {
        if (!isPortValid(control.getTextFields()[2].getText())) {
            eventLogAppend(control, "Port Invalid!");
            control.getTextFields()[2].setText(null);
            return false;
        }
        eventLogAppend(control, "Port Valid!");
        try {
            client = new GameClient(control, Integer.valueOf(control.getTextFields()[2].getText()));
        } catch (NumberFormatException | IOException e) {
            System.out.println("Error in ConnectAction()");
            e.printStackTrace();
        }

        control.getFunctionButtons()[0].setEnabled(false);
        for (int i = 0; i < 3; i++)
            control.getTextFields()[i].setEditable(false);
        control.getFunctionButtons()[1].setEnabled(true);

        for (int i = 3; i < control.getFunctionButtons().length - 1; i++)
            control.getFunctionButtons()[i].setEnabled(true);
        return portValid;

    }

    /**
     * disconnect method
     * 
     * @param control clientcontroller obj
     */
    public void disconnect(ClientController control) {
        if (client == null)
            return;
        sendToServer(P1 + P1_3 + SEPARATOR);
        System.out.println("after sending to server");

        // client.disconnect();
        control.getFunctionButtons()[1].setEnabled(false);

        for (int i = 3; i < control.getFunctionButtons().length - 1; i++)
            control.getFunctionButtons()[i].setEnabled(false);
        eventLogAppend(control, getLabel("DCMSG"));
        control.getFunctionButtons()[0].setEnabled(true);
        for (int i = 0; i < 3; i++)
            control.getTextFields()[i].setEditable(true);

    }

    /**
     * Metho for newgame
     * 
     * @param comboIndex index for combo
     */
    public void newGame(int comboIndex) {
        isPuzzleFromServer = false;
        dimension = Integer.valueOf(DIMENSIONLIST[comboIndex]);
        generatePicross();
        printSolution();

    }

    /**
     * This method prints the solution to the console
     */
    public void printSolution() {
        if (boardSolution == null)
            return;
        System.out.println("Printing boardSolution!");
        for (int[] is : boardSolution) {
            for (int i = 0; i < is.length; i++) {

                System.out.print(is[i] + " ");
            }
            System.out.println();
        }
        System.out.println("Total possible points is:" + numOfCorrectTile);
    }

    /**
     * method that sends to server
     * 
     * @param text message to server
     */
    public void sendToServer(String text) {
        try {
            client.writeToServer(text);
            System.out.println("Sent to server");
        } catch (IOException e) {
            System.out.println("Error sending to server");
            e.printStackTrace();
        }
    }

    /**
     * method that requests board to server
     */
    public void requestBoard() {
        String request = P1 + P1_2 + SEPARATOR;
        System.out.println(request);
        sendToServer(request);
    }

    /**
     * dimension getter
     * 
     * @return int dimenison
     */
    public int getDim() {
        return dimension;
    }

    /**
     * solution getter
     * 
     * @return 2d int array
     */
    public int[][] getSolution() {
        return boardSolution;
    }

    /**
     * status getter if load from server
     * 
     * @return true/false
     */
    public boolean getBoolIsLoad() {
        return isPuzzleFromServer;
    }

    /**
     * Method that check whether a string can be converted into an int
     * 
     * @param input String
     * @return return true is convertable , false otherwise
     */
    public boolean isInteger(String input) {
        if (input == null)
            return false;
        int length = input.length();
        if (length == 0) {
            return false;
        }
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Stats setter
     * 
     * @param score
     * @param time
     */
    public void setStats(String score, String time) {
        stats[0] = score;
        stats[1] = time;
    }

    /**
     * Stats send requenst
     */
    public void sendStats() {
        sendToServer(P1 + P1_4 + SEPARATOR + stats[0] + "," + stats[1]);
        System.out.println(P1 + P1_4 + SEPARATOR + stats[0] + "," + stats[1]);
    }

    /**
     * Method that checks whether game is done or not
     * 
     * @return returns true if done, false otherwise
     */
    public boolean isGameDone() {
        if (correctMove == numOfCorrectTile)
            return true;
        return false;
    }

    /**
     * status for perfect
     * 
     * @return true/false
     */
    public Boolean isPerfect() {
        if (wrongMove == 0)
            return true;
        return false;
    }
}
