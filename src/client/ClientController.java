package client;

import javax.swing.*;
import a32Pack.Config;
import java.awt.Color;
import java.awt.event.*;

/**
 * Public class for Client controller
 */
public class ClientController implements ActionListener, FocusListener, WindowListener, Config {

    /**
     * ClientModel obj
     */
    private ClientModel model;

    /**
     * ClientView obj
     */
    private ClientView view;

    /**
     * ControllableTimer obj
     */
    private ControllableTimer timer;

    /**
     * Constructor for controller
     * 
     * @param view  Clientview obj
     * @param model Clientmodel obj
     */
    public ClientController(ClientView view, ClientModel model) {
        this.model = model;
        this.view = view;
        timer = new ControllableTimer(view);
        // Timer Start
        timer.start();
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
     * Text area accessor
     * 
     * @return returns a jtextarea obj
     */
    public JTextArea getEventLog() {
        return view.getEventLog();
    }

    /**
     * Text area accessor
     * 
     * @return returns a jtextarea obj
     */
    public JTextArea getEventLogDuplicate() {
        return view.getDuplicateLog();
    }

    /**
     * Method getter for functionbuttons
     * 
     * @return JButton array
     */
    public JButton[] getFunctionButtons() {
        return view.getFButtons();
    }

    /**
     * Method getter for dim list
     * 
     * @return String array
     */
    public String[] getDimList() {
        return model.getDimList();
    }

    /**
     * Method getter for Textfield
     * 
     * @return Jtextfield array
     */
    public JTextField[] getTextFields() {
        return view.getTextField();
    }

    /**
     * launch method
     */
    public void launch() {
        System.out.println("Launching...");
        view.setTitle(model.getLabel("TITLE"));
        view.addSoloPanel(view.getSplashScreen());
        try {
            Thread.sleep(SPLASHTIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            int x = view.mainMenuHelp(this);
            if (x == 0) {
                model.updateLocale(view.getComboIndex());
                break;
            }
        }
        view.removeSoloPanel(view.getSplashScreen());
        view.setClientUI(this);
        view.addSoloPanel(view.getUI());
    }

    /**
     * Send button setter
     */
    public void setSendButton() {
        view.setSendButton();
    }

    /**
     * Getter for send button
     * 
     * @return Jbutton array
     */
    public JButton[] getSendButton() {
        return view.getSendButton();
    }

    /**
     * Board setter
     * 
     * @param message String form of solution
     */
    public void setBoard(String message) {
        model.readingBoardFromServer(message);
    }

    /**
     * Setter for buttons
     * 
     * @param dim int dimension
     */
    public void setButtons(int dim) {
        view.setGameButtons(dim);
    }

    /**
     * Method getter for connected status
     * 
     * @return boolean true/false
     */
    public Boolean isConnected() {
        return model.getPortvalid();
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
     * Dimension getter
     * 
     * @return int dimension
     */
    public int getDim() {
        return model.getDim();
    }

    /**
     * method getter for buttons(board)
     * 
     * @return Jbutton array
     */
    public JButton[][] getButtons() {
        return view.getSButtons();
    }

    /**
     * method getter for solution
     * 
     * @return 2d int array
     */
    public int[][] getSolution() {
        return model.getSolution();
    }

    /**
     * Mark setter method
     * 
     * @param jCheckBox setmark obj
     */
    public void setMark(JCheckBox jCheckBox) {
        view.setMarkBox(jCheckBox);
    }

    /**
     * getter for mark check box
     * 
     * @return JCheckbox for mark
     */
    public JCheckBox getMarkCheckBox() {
        return view.getMarkBox();
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
     * Timer textfield getter
     * 
     * @return retrun a textfield obj
     */
    public JTextField getTimer() {
        return view.getTimeKeep();
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
     * Method that disables all button
     */
    public void disableAll() {
        for (int i = 0; i < model.getDimension(); i++) {
            for (int j = 0; j < model.getDimension(); j++) {
                view.getSButtons()[i][j].setEnabled(false);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "SEND":
                if (getTextFields()[3].getText().equals("") || getTextFields()[3].getText().equals(getLabel("CHATMSG")))
                    break;

                System.out.println("Send!");

                model.sendToServer(P3 + getTextFields()[3].getText());
                model.eventLogAppend(this, getLabel("YOU") + getTextFields()[3].getText());

                getTextFields()[3].setText(getLabel("CHATMSG"));
                break;
            case "SEND1":
                System.out.println("Send1!");

                if (getTextFields()[4].getText().equals("") || getTextFields()[4].getText().equals(getLabel("CHATMSG")))
                    break;

                System.out.println("Send!");

                model.sendToServer(P3 + getTextFields()[4].getText());
                model.eventLogAppend(this, getLabel("YOU") + getTextFields()[4].getText());

                getTextFields()[4].setText(getLabel("CHATMSG"));
                break;
            case "CONNECTLBL":
                System.out.println("Connect");

                if (model.connectAction(this)) {
                    getTextFields()[3].setEnabled(true);
                    getSendButton()[0].setEnabled(true);
                    timer.setStatus(timer.RESET);
                    timer.setStatus(timer.STOP);
                }

                break;
            case "ENDLBL":
                System.out.println("End");
                model.disconnect(this);
                getTextFields()[3].setEnabled(false);

                getSendButton()[0].setEnabled(false);

                break;
            case "BUTNEWGAME":
                System.out.println("New Game");
                if (view.mainMenuPlay(this) == 0) {
                    model.newGame(view.getComboIndex());
                    getFunctionButtons()[6].setEnabled(true);
                    timer.setStatus(timer.RESET);
                    timer.setStatus(timer.STOP);
                }

                break;
            case "SENDGLBL":
                System.out.println("Send Game");
                if (model.sendBoard())
                    model.eventLogAppend(this, getLabel("BSENDMSG"));
                break;
            case "RECLBL":
                System.out.println("Receive Game");
                model.requestBoard();
                getFunctionButtons()[6].setEnabled(true);

                break;
            case "SENDDLBL":
                System.out.println("SendData");
                model.sendStats();

                break;
            case "BUTRESET": // Game Mode reset

                view.getFrame().dispose();

            case "BUTPLAYGAME":
                System.out.println("Play Game");
                view.setGameBoard(this);
                timer.setStatus(timer.START);

                view.setVisible(false);

                break;
            case "BUTMAINMENU":
                view.setVisible(true);
                view.getFrame().dispose();
                timer.setStatus(timer.STOP);

                // set stats
                model.setStats(view.getPointKeep().getText(), view.getTimeKeep().getText());

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
            case "BUTEXIT": // Exit
                timer.setStatus(timer.TERMINATE);
                System.exit(0);
                break;
            case "BUTABOUT":
                view.getDuplicateLog().append(getLabel("ABOUT1") + "\n");
                view.getDuplicateLog().append(getLabel("ABOUT2") + "\n");
                break;
            case "BUTSOL": // Show solution BUTGOMAINMENU
                System.out.println("Solution:");
                timer.setStatus(timer.STOP);
                model.logSolution(this);
                model.showBoardSol(this);
                break;
            case "BUTGOMAINMENU":
                view.removeSoloPanel(view.getEndScreen());
                view.addSoloPanel(view.getUI());
                break;
            default:
                if (model.isInteger(e.getActionCommand())) {
                    for (int i = 1; i <= (model.getDimension() * model.getDimension()); i++) {
                        if (i == Integer.parseInt(e.getActionCommand())) {
                            if (view.getMarkBox().isSelected()) {
                                if (((JButton) e.getSource()).getBackground() == Color.WHITE) {
                                    ((JButton) e.getSource()).setBackground(view.getColors()[1]);
                                    model.checkSqr(this, i, false);
                                } else {
                                    ((JButton) e.getSource()).setBackground(Color.WHITE);
                                    model.checkSqr(this, i, false);
                                }
                            } else {
                                if (((JButton) e.getSource()).getBackground() == view.getColors()[1])
                                    break;
                                if (model.checkSqr(this, i, false)) {
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

                    if (model.isGameDone()) {
                        System.out.println("Game is done yey");
                        disableAll();
                        getEventLogDuplicate().append("Game is Done!!!\n");
                        timer.setStatus(timer.STOP);
                        view.setEndGamePanel(model.isPerfect(), this);
                        view.setVisible(false);
                        view.removeSoloPanel(view.getUI());
                        view.getFrame().dispose();
                        view.addSoloPanel(view.getEndScreen());
                        // addPanel(view.getEndScreen());
                    }

                }
                break;
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == getTextFields()[3])
            getTextFields()[3].setText("");
        if (e.getSource() == getTextFields()[4])
            getTextFields()[4].setText("");
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == getTextFields()[3]) {
            if (getTextFields()[3].getText().equals(""))
                getTextFields()[3].setText(getLabel("CHATMSG"));
        }
        if (e.getSource() == getTextFields()[4]) {
            if (getTextFields()[4].getText().equals(""))
                getTextFields()[4].setText(getLabel("CHATMSG"));
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == view.getFrame()) {
            view.setVisible(true);
            view.getFrame().dispose();
            timer.setStatus(timer.STOP);
            view.getDuplicateLog().setText("");
            // set stats
            model.setStats(view.getPointKeep().getText(), view.getTimeKeep().getText());
        }
        System.out.println("Window closed");
    }

    /*************
     * Unused methods
     */
    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

}
