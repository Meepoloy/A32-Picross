package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class operates all the action. Bridges the UI side and Logic
 * side of this program.
 */
public class GameController implements ActionListener {

    /**
     * GameModel obj
     */
    private GameModel model;

    /**
     * GameView obj
     */
    private GameView view;

    /**
     * ControllableTimer obj
     */
    private ControllableTimer timer;

    /**
     * Game Controller constructor
     * 
     * @param view  GameView obj from Driver class
     * @param model GameModel obj from Driver class
     */
    public GameController(GameView view, GameModel model) {
        this.model = model;
        this.view = view;
        timer = new ControllableTimer(view);
        // Timer Start
        timer.start();
    }

    /**
     * Setter for frame Title
     */
    private void setTitle() {
        view.setTitle(getLabel("TITLE"));
    }

    /**
     * Setter for Mark check box
     * 
     * @param box JCheckbox obj
     */
    public void setMark(JCheckBox box) {
        view.setMarkBox(box);
    }

    /**
     * setter for 2d-arr Jbuttons
     * 
     * @param buttons
     */
    public void setButtons(JButton[][] buttons) {
        view.setSqrButtons(buttons);
    }

    /**
     * TextArea setter
     * 
     * @param log
     */
    public void setTextArea(JTextArea log) {
        view.setEventLog(log);
    }

    /**
     * ContrallableTimer getter
     * 
     * @return retunrs a ContrallableTimer obj
     */
    public ControllableTimer getControlTimer() {
        return timer;
    }

    /**
     * GameView getter
     * 
     * @return retuns a GameView Obj
     */
    public GameView getMainFrame() {
        return view;
    }

    /**
     * Dimension getter
     * 
     * @return int dimensino
     */
    public int getDim() {
        return model.getDimension();
    }

    /**
     * Label getter
     * 
     * @param String key
     * @return returns a string lbl
     */
    public String getLabel(String s) {
        return model.getLabel(s);
    }

    /**
     * Dimension list getter
     * 
     * @return returnds 2d arr obj
     */
    public String[] getDimList() {
        return model.getDimesionList();
    }

    /**
     * Solution accesor
     * 
     * @return returns the 2d-arr solution
     */
    public int[][] getSolution() {
        return model.getSolution();
    }

    /**
     * MarkCheckBox getters
     * 
     * @return retunrs a Jcheckbox obj
     */
    public JCheckBox getMarkCheckBox() {
        return view.getMarkBox();
    }

    /**
     * 2d-arr Jbutton accesor
     * 
     * @return returns the 2d-arr Jbutton
     */
    public JButton[][] getButtons() {
        return view.getSqrButtons();
    }

    /**
     * Text area accessor
     * 
     * @return returns a jtextarea obj
     */
    public JTextArea getTextArea() {
        return view.getEventLog();
    }

    /**
     * Timer textfield getter
     * 
     * @return retrun a textfield obj
     */
    public JTextField getTimer() {
        return view.getTimeKeep();
    }

    /**
     * points textfield getter
     * 
     * @return return a textfield obj
     */
    public JTextField getPoints() {
        return view.getPointKeep();
    }

    /**
     * Color getter
     * 
     * @return 2d arr Color obj
     */
    public Color[] getColor() {
        return view.getColors();
    }

    /**
     * Boolean getter
     * 
     * @return returns true if Loading, false otherwise
     */
    public boolean getIsLoad() {
        return model.getBoolIsLoad();
    }

    /**
     * Initial launch method
     * 
     * @return true/false depending if the launch is succesful
     */
    public boolean launch() {
        if (view == null)
            return false;
        setTitle();
        addPanel(view.getSplashScreen());
        System.out.println("Adding Splash ... " + addPanel(view.getSplashScreen()));
        model.programSleep(3000);
        System.out.println("Disposing Splash ... " + disposePanel(view.getSplashScreen()));
        playGame();
        return true;
    }

    /**
     * Private method for adding panel to the frame
     * 
     * @param panel accepts a panel object
     * @return boolean status of the method whether successful or not
     */
    private boolean addPanel(JPanel panel) {
        if (panel == null)
            return false;
        if (view == null)
            return false;
        if (view.addSoloPanel(panel))
            return true;
        return false;
    }

    /**
     * Private method for removing panel from the frame
     * 
     * @param panel accepts a panel object
     * @return boolean status of the method whether successful or not
     */
    private boolean disposePanel(JPanel panel) {
        if (view == null)
            return false;
        if (view.removeSoloPanel(panel))
            return true;

        timer.run();
        return false;
    }

    /**
     * method for adding main menu panel from the frame
     * 
     * @return boolean status of the method whether successful or not
     */
    public boolean addMainMenu() {
        if (view == null)
            return false;
        view.setMainMenu(this);
        if (view.getMainMenu() == null)
            return false;
        if (addPanel(view.getMainMenu()))
            return true;
        return false;
    }

    /**
     * Method for updating the UI
     * 
     * @param panel accepts panel to remove
     * @return boolean status of the method whether successful or not
     */
    public boolean updateInterface(JPanel panel) {
        if (panel != null)
            view.removeSoloPanel(panel);
        model.programSleep(500);
        view.setMainMenu(this);
        if (view.addSoloPanel(view.getMainMenu()))
            return true;
        return false;
    }

    // public boolean disposeSplashScreen() {
    // if (view == null)
    // return false;
    // view.setVisible(false);
    // return true;
    // }

    /**
     * Method the initiates the playing part
     */
    public void playGame() {
        System.out.println("Play Game...");
        view.setVisible(false);

        view.setGameBoard(this);
        timer.setStatus(timer.RESET);
        model.printSolution();

    }

    /**
     * Method that disables all button
     */
    public void disableAll() {
        for (int i = 0; i < model.getDimension(); i++) {
            for (int j = 0; j < model.getDimension(); j++) {
                view.disableButton(view.getSqrButtons()[i][j]);
            }
        }
    }

    /********************* */

    /******************* */
    @Override
    /**
     * Action performed method. Using setActionCommand to filter
     * the appropriate response to an action performed.
     */
    public void actionPerformed(ActionEvent e) {

        String action = e.getActionCommand();

        /*
         * Case-switch to filter all known actions
         */
        switch (action) {

            case "BUTGOMAINMENU":
                disposePanel(view.getEndScreen());
            case "BUTMAINMENU":// Main Menu
                model.actionMainMenu(this);
                break;
            case "BUTNEWGAME":// New Game
                model.actionNewGame(this);
            case "BUTPLAYGAME": // Main Menu Play Game
                model.actionPlayGame(this);
                break;
            case "BUTDESIGN": // Go to Design
                model.actionDesign(this);
                break;
            case "BUTOPTIONS":// Mainmenu Option
                model.actionOption(this);
                break;
            case "BUTLOAD":// Load Game
                model.actionLoad(this);
                break;
            case "SAVEDESIGN": // Save to file
                model.actionSave(this);
                break;

            case "BUTLANG":// I18N implementaion
                model.actionLang(this);
                break;

            case "LBLMARK": // Mark action
                System.out.println("Mark");
                if (view.getMarkBox().isSelected()) {
                    System.out.println("Mark is set");
                    getTextArea().append("Mark is set\n");
                } else {
                    System.out.println("Mark is unset");
                    getTextArea().append("Mark is unset\n");
                }
                model.changeMarkState(this);
                break;

            case "BUTSOL": // Show solution
                System.out.println("Solution:");
                timer.setStatus(timer.STOP);
                model.logSolution(getTextArea());
                model.showBoardSol(this);
                break;

            case "BUTRESET": // Game Mode reset
                if (timer.getStatus() == timer.STOP)
                    timer.setStatus(timer.START);
                view.getGameBoard().dispose();

                playGame();
                break;

            case "frame0 2":// Help/Color Chooser
                System.out.println("Help");
                view.colorChanger(this);
                break;

            case "BUTEXIT": // Exit
                timer.setStatus(timer.TERMINATE);
                System.exit(0);
                break;
            case "BUTCOLOR": // Colors Menu
                view.setColorFrame(this);
                break;

            case "COLORLBL1": // Color 1
                view.setColorChooser();
                view.setColor(view.getColorChooser().showDialog(null, getLabel("COLORTITLE"),
                        view.getColors()[0]), 0, this);
                view.getColorFrame().dispose();
                view.setColorFrame(this);

                break;
            case "COLORLBL2": // Color 2
                view.setColorChooser();
                view.setColor(view.getColorChooser().showDialog(null, getLabel("COLORTITLE"),
                        view.getColors()[1]), 1, this);
                view.getColorFrame().dispose();
                view.setColorFrame(this);
                break;
            case "COLORLBL3": // Color 3
                view.setColorChooser();
                view.setColor(view.getColorChooser().showDialog(null, getLabel("COLORTITLE"),
                        view.getColors()[2]), 2, this);
                view.getColorFrame().dispose();
                view.setColorFrame(this);
                break;

            case "DESIGN": // JButtons in Designmode
                System.out.println("In design mode");
                for (int i = 0; i < model.getDimension(); i++) {
                    for (int j = 0; j < model.getDimension(); j++) {
                        if (view.getSqrButtons()[i][j] == ((JButton) e.getSource())) {
                            if (((JButton) e.getSource()).getBackground() == view.getColors()[0]) {
                                ((JButton) e.getSource()).setBackground(view.getColors()[2]);
                                model.getTempArr()[i][j] = 0;
                                model.eventLog(getTextArea(), "but[" + i + "][" + j + "] is set to 0\n");
                            } else if (((JButton) e.getSource()).getBackground() == view.getColors()[2]) {
                                ((JButton) e.getSource()).setBackground(Color.WHITE);
                            } else {
                                ((JButton) e.getSource()).setBackground(view.getColors()[0]);
                                model.getTempArr()[i][j] = 1;
                                model.eventLog(getTextArea(), "but[" + i + "][" + j + "] is set to 1\n");
                            }
                        }
                    }
                }
                break;
            case "BUTABOUT":
                model.eventLog(getTextArea(), getLabel("ABOUT1") + "\n");
                model.eventLog(getTextArea(), getLabel("ABOUT2") + "\n");
                break;

            default: // Jbuttons in Game mode
                System.out.println(action);
                if (model.isInteger(action)) {
                    for (int i = 1; i <= (model.getDimension() * model.getDimension()); i++) {
                        if (i == Integer.parseInt(action)) {
                            if (view.getMarkBox().isSelected()) {
                                if (((JButton) e.getSource()).getBackground() == Color.WHITE) {
                                    ((JButton) e.getSource()).setBackground(view.getColors()[1]);
                                    model.checkSqr(getTextArea(), i, false);
                                } else {
                                    ((JButton) e.getSource()).setBackground(Color.WHITE);
                                    model.checkSqr(getTextArea(), i, false);
                                }
                            } else {
                                if (((JButton) e.getSource()).getBackground() == view.getColors()[1])
                                    break;
                                if (model.checkSqr(getTextArea(), i, false)) {
                                    System.out.println("True");
                                    ((JButton) e.getSource()).setEnabled(false);
                                    ((JButton) e.getSource()).setBackground(view.getColors()[0]);
                                    model.correctMove();
                                } else {
                                    System.out.println("False");
                                    ((JButton) e.getSource()).setEnabled(false);
                                    ((JButton) e.getSource()).setBackground(view.getColors()[2]);
                                    model.wrongMove();
                                }
                                model.setPoints();
                            }

                        }
                    }
                    view.updateScore(model.getPoints());
                    System.out.println("Points set");
                    // Game is done
                    if (model.isGameDone()) {
                        System.out.println("Game is done yey");
                        disableAll();
                        model.logSolution(getTextArea());
                        model.eventLog(getTextArea(), "Game is Done!!!\n");
                        timer.setStatus(timer.STOP);
                        view.setEndGamePanel(model.isPerfect(), this);
                        view.getGameBoard().dispose();
                        addPanel(view.getEndScreen());
                    }

                }
                break;
        }

    }

}
