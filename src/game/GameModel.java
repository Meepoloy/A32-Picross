package game;

import java.io.File;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JTextArea;

/**
 * This class contains all the logic of this program
 */
public class GameModel {

    /**
     * String arr of Languages supported
     */
    private String[] languages = { "en", "fr", "sp" };

    /**
     * Array of countries
     */
    private String[] countries = { "US", "FR", "ES" };

    /**
     * Array of supported dimension
     */
    private String[] dimensionList = {
            "3", "4", "5", "6",
            "7", "8", "9", "10" };

    /**
     * int Diemnsion
     */
    private int dimension;

    /**
     * int points
     */
    private int points;

    /**
     * int number of try
     */
    private int numOfTry;

    /**
     * int number of correct tile
     */
    private int numOfCorrectTile;

    /**
     * int correct moves
     */
    private int correctMove;

    /**
     * int wrong move
     */
    private int wrongMove;

    /**
     * Boolean for loading state
     */
    private boolean isLoad;

    /**
     * Temp array for designing
     */
    private int[][] tempArr;

    /**
     * Default 2d solution
     */
    private int[][] solution = {
            { 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 0 },
            { 1, 1, 1, 1, 1 },
            { 0, 1, 1, 1, 0 },
            { 0, 1, 0, 1, 0 } };

    /**
     * Locale
     */
    private Locale currentLocale;

    /**
     * Resource bundle
     */
    private ResourceBundle texts;

    /**
     * Resource file name.
     */
    private String SYSTEMMESSAGES = "game/resources/texts";

    /**
     * String language
     */
    private String lang = "";

    /**
     * String country
     */
    private String country = "";

    GameModel() {
        // default language / settings
        setCountry("US");
        setLang("en");
        buildingLocale();
        dimension = 5; // default dimension
        numOfCorrectTile = 12;
        points = 0;
        correctMove = 0;
        wrongMove = 0;
        numOfTry = dimension * dimension;
    }

    // public void setLoadSolution(int[][] arr) {
    // System.out.println("Loading Solution from File");
    // System.out.println("Dimension from file = " + arr.length);
    // solution = new int[arr.length][arr.length];
    // for (int i = 0; i < arr.length; i++)
    // for (int j = 0; j < arr.length; j++)
    // solution[i][j] = arr[i][j];
    // }

    /**
     * Method that check if the tempArray only has 0's in it
     * 
     * @return return true if success, false otherwise
     */
    private boolean isTempArrEmpty() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tempArr[i][j] == 1)
                    return false;
            }
        }
        return true;
    }

    /**
     * Setter for temp array
     */
    public void setTempArr() {
        tempArr = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                tempArr[i][j] = 0;
            }
        }
    }

    /**
     * Print temp array
     */
    public void printTempArr() {
        if (tempArr == null)
            return;
        for (int[] is : tempArr) {
            for (int i = 0; i < is.length; i++) {
                System.out.print(is[i] + " ");
            }
            System.out.println();
        }
        System.out.println("Total possible points is:" + numOfCorrectTile);
    }

    /**
     * Getter for temp array
     * 
     * @return return 2d int array
     */
    public int[][] getTempArr() {
        return tempArr;
    }

    /**
     * Getter for isLoad
     * 
     * @return return boolean
     */
    public boolean getBoolIsLoad() {
        return isLoad;
    }

    /**
     * String getter for Dimension list
     * 
     * @return
     */
    public String[] getDimesionList() {
        return dimensionList;
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
     * This method makes the program thread sleeps
     * 
     * @param millis int how long does a sleep last
     */
    public void programSleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Loading solution. Envoke loadboard()
     * 
     * @param filePath File chosen
     * @return boolean status of the process
     */
    public boolean loadSolution(File filePath) {
        return new LoadBoard(filePath, this).getStatus();
    }

    /**
     * Save solution to a file
     * 
     * @param filePath File chosen
     * @return boolean status of the process
     */
    public boolean saveSolution(File filePath) {
        if (!isTempArrEmpty())
            new SaveBoard(filePath, this);
        return isTempArrEmpty();
    }

    /**
     * Method that appends string to UI's textarea
     * 
     * @param log     JTextArea obj
     * @param message String message
     */
    public void eventLog(JTextArea log, String message) {
        log.append(message);
    }

    /**
     * Method that checks square and logs the actions in the TextArea
     * 
     * @param log        JTextArea obj
     * @param lbl        int position of label
     * @param isSolution Boolean for show solution
     * @return returns true if square is right, false otherwise
     */
    public boolean checkSqr(JTextArea log, int lbl, boolean isSolution) {
        int x, y;
        if (lbl % getDimension() == 0) {
            x = ((lbl - 1) / getDimension());

        } else
            x = (lbl / getDimension());

        y = lbl - 1 - (getDimension() * x);

        if (!isSolution)
            eventLog(log, "but[" + x + "][" + y + "] is pressed\n");
        if (getSolution()[x][y] == 1) {

            return true;
        }
        return false;
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
     * Language setter
     * 
     * @param lang String language abbrv
     */
    private void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * Country setter
     * 
     * @param country string country abbrv
     */
    private void setCountry(String country) {
        this.country = country;
    }

    /**
     * Locale updated
     * 
     * @param index int langauge 0:English 1:French 2:Spanish
     */
    public void updateLocale(int index) {
        System.out.println("Index: " + index);
        setLang(languages[index]);
        setCountry(countries[index]);
        buildingLocale();
    }

    /**
     * Dimension Setter
     * 
     * @param dim int dimension
     */
    public void setDimension(int dim) {
        dimension = dim;
        numOfTry = dim * dim;

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
     * This method generate picross problem using Secure random
     * 
     * @param fromFile Boolean that confirms whether picross is
     *                 generated from file or randomly
     * @param arr      int 2d array
     */
    public void generatePicross(boolean fromFile, int[][] arr) {
        numOfCorrectTile = 0;
        points = 0;
        correctMove = 0;
        wrongMove = 0;
        solution = new int[dimension][dimension];
        tempArr = new int[dimension][dimension];

        SecureRandom rand;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (fromFile) {
                    solution[i][j] = arr[i][j];
                    isLoad = true;

                } else {
                    rand = new SecureRandom();
                    solution[i][j] = rand.nextInt(2);
                    tempArr[i][j] = solution[i][j];
                }
                if (solution[i][j] == 1)
                    numOfCorrectTile++;
            }
        }
    }

    /**
     * This method prints the solution to the console
     */
    public void printSolution() {
        if (solution == null)
            return;
        System.out.println("Printing Solution!");
        for (int[] is : solution) {
            for (int i = 0; i < is.length; i++) {

                System.out.print(is[i] + " ");
            }
            System.out.println();
        }
        System.out.println("Total possible points is:" + numOfCorrectTile);
    }

    /**
     * This method appends solution in the UI's textarea
     * 
     * @param log JTextArea obj
     */
    public void logSolution(JTextArea log) {
        if (solution == null)
            return;
        for (int[] is : solution) {
            for (int i = 0; i < is.length; i++) {
                eventLog(log, is[i] + " ");
            }
            eventLog(log, "\n");
        }
    }

    /**
     * Solution getter
     * 
     * @return 2d int array
     */
    public int[][] getSolution() {
        return solution;
    }

    /**
     * Method that increments wrong move
     */
    public void wrongMove() {
        wrongMove++;
    }

    /**
     * Method that increments correct move
     */
    public void correctMove() {
        correctMove++;
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
     * Method that checks whether game is done or not
     * 
     * @return returns true if done, false otherwise
     */
    public boolean isGameDone() {
        if (correctMove == numOfCorrectTile || numOfTry == 0)
            return true;
        return false;
    }

    /**
     * Method for check if user solve it perfect
     * 
     * @return return true if perfect, false otherwise
     */
    public boolean isPerfect() {
        if (getPoints() == numOfCorrectTile)
            return true;
        return false;
    }

    /**
     * Method for action Mainmenu
     * 
     * @param control GameController obj
     */
    public void actionMainMenu(GameController control) {
        System.out.println("Main Menu...");
        control.getControlTimer().setStatus(control.getControlTimer().TERMINATE);
        control.getMainFrame().getGameBoard().dispose();
        programSleep(500);
        control.addMainMenu();
        control.getMainFrame().setVisible(true);
    }

    /**
     * Method for NewGame action
     * 
     * @param control GameController obj
     */
    public void actionNewGame(GameController control) {
        System.out.println("New Game...");
        control.getControlTimer().setStatus(control.getControlTimer().STOP);
    }

    /**
     * Method for PlayGame action
     * 
     * @param control GameController obj
     */
    public void actionPlayGame(GameController control) {
        System.out.println("Play game...");
        control.getControlTimer().setStatus(control.getControlTimer().STOP);
        if (control.getMainFrame().mainMenuPlay(control) != 0) {
            control.getControlTimer().setStatus(control.getControlTimer().START);
            return;
        }
        if (control.getMainFrame().getGameBoard() != null)
            control.getMainFrame().getGameBoard().dispose();
        setDimension(Integer.parseInt(control.getDimList()[control.getMainFrame().getComboIndex()]));
        generatePicross(false, null);
        printSolution();
        control.getControlTimer().setStatus(control.getControlTimer().START);
        control.playGame();
    }

    /**
     * Method for Design action
     * 
     * @param control GameController obj
     */
    public void actionDesign(GameController control) {
        System.out.println("Designing...");
        if (control.getMainFrame().mainMenuPlay(control) != 0)
            return;

        setDimension(Integer.parseInt(control.getDimList()[control.getMainFrame().getComboIndex()]));
        setTempArr();
        control.getMainFrame().setVisible(false);
        control.getMainFrame().setDesignBoard(control);
    }

    /**
     * Method for Option action
     * 
     * @param control GameController obj
     */
    public void actionOption(GameController control) {
        control.getMainFrame().openOption(control);
    }

    /**
     * Method for Load action
     * 
     * @param control GameController obj
     */
    public void actionLoad(GameController control) {
        if (control.getMainFrame().getFile() == 0) {
            if (!loadSolution(control.getMainFrame().getFileChooser().getSelectedFile())) {
                control.getMainFrame().showLoadDialog(control);
                return;
            }
            control.getMainFrame().disposeOptFrame();
            control.getControlTimer().setStatus(control.getControlTimer().START);
            printSolution();
            control.playGame();
        }
    }

    /**
     * Method for Save action
     * 
     * @param control GameController obj
     */
    public void actionSave(GameController control) {
        printTempArr();
        if (control.getMainFrame().saveFile() == 0) {
            if (saveSolution(control.getMainFrame().getFileChooser().getSelectedFile())) {
                control.getMainFrame().showSaveDialog(control);
                return;
            }
            control.getMainFrame().getDesignBoard().dispose();
            control.getMainFrame().setVisible(true);
        }
    }

    /**
     * Method for Change language action
     * 
     * @param control GameController obj
     */
    public void actionLang(GameController control) {
        if (control.getMainFrame().mainMenuHelp(control) != 0)
            return;
        control.getMainFrame().disposeOptFrame();
        updateLocale(control.getMainFrame().getComboIndex());
        System.out.println("Language is set to: " + getLabel("LANGNAME"));
        control.updateInterface(control.getMainFrame().getMainMenu());
    }

    /**
     * Method for MarkState action
     * 
     * @param control GameController obj
     */
    public void changeMarkState(GameController control) {

        for (int i = 0; i < getDimension(); i++) {
            for (int j = 0; j < getDimension(); j++) {
                if (control.getMainFrame().getMarkBox().isSelected()) {
                    if (control.getButtons()[i][j].getBackground() == control.getMainFrame().getColors()[1])
                        control.getButtons()[i][j].setEnabled(true);
                } else {
                    if (control.getButtons()[i][j].getBackground() == control.getMainFrame().getColors()[1])
                        control.getButtons()[i][j].setEnabled(false);
                }
            }
        }
    }

    /**
     * Method for show board solution action
     * 
     * @param control GameController obj
     */
    public void showBoardSol(GameController control) {
        for (int i = 0; i < getDimension(); i++) {
            for (int j = 0; j < getDimension(); j++) {
                if (checkSqr(control.getMainFrame().getEventLog(), getDimension() * i + j + 1, true)) {
                    control.getMainFrame().getSqrButtons()[i][j].setBackground(control.getMainFrame().getColors()[0]);
                    control.getMainFrame().getSqrButtons()[i][j].setEnabled(false);
                } else {
                    control.getMainFrame().getSqrButtons()[i][j].setBackground(control.getMainFrame().getColors()[2]);
                    control.getMainFrame().getSqrButtons()[i][j].setEnabled(false);
                }
            }
        }
    }
}
