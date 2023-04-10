package client;

import java.awt.Color;
import javax.swing.*;
import a32Pack.*;

public class ClientView extends JFrame implements Config {

    /**
     * Arr of JButtons
     */
    private JButton[] clientFuncBut = new JButton[7];

    /**
     * TextFields for user[0],server[1],port[2]chat[3]chatdupli[4]
     */
    private JTextField[] tField = new JTextField[5];

    /**
     * Textarea
     */
    private JTextArea eventLog, duplicateLog;

    /**
     * Combo box
     */
    private JComboBox<String> combo;

    /**
     * send button
     */
    private JButton sendButton;

    /**
     * chat panel
     */
    private JPanel chatPanel;

    /**
     * EndScreen panel
     */
    private JPanel endScreen;

    /**
     * GameFrame frame
     */
    private JFrame gameFrame;

    /**
     * ColorFrame frame
     */
    private JFrame colorFrame;

    /**
     * Client ui
     */
    private JPanel clientUI;

    /**
     * JButton array
     */
    private JButton[][] squareButtons;

    /**
     * Colors array
     */
    private Color[] colors = new Color[3];

    /**
     * Colorchooser
     */
    private JColorChooser colorChooser;

    /**
     * Boolean isDefault
     */
    private boolean isDefault;

    /**
     * Mark Box
     */
    private JCheckBox markBox;

    /**
     * TextFields for points and time
     */
    private JTextField timeKeeper, pointsKeeper;

    private JButton sendDupliButton;

    public ClientView() {

        colors[0] = Color.BLUE;
        colors[1] = Color.YELLOW;
        colors[2] = Color.RED;

        for (int i = 0; i < tField.length; i++)
            tField[i] = new JTextField();

        eventLog = new JTextArea();
        duplicateLog = new JTextArea();

        timeKeeper = new JTextField();
        pointsKeeper = new JTextField();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
    }

    /**
     * Clien ui setter
     * 
     * @param control ClientController obj
     */
    public void setClientUI(ClientController control) {
        clientUI = new ClientUI(control);
    }

    /**
     * Getter end screen
     * 
     * @return Jpanel obj
     */
    public JPanel getEndScreen() {
        return endScreen;
    }

    /**
     * Getter for clientui
     * 
     * @return Jpanel obj
     */
    public JPanel getUI() {
        return clientUI;
    }

    /**
     * Getter chat panel
     * 
     * @return Jpanel obj
     */
    public JPanel getChatPanel() {
        return chatPanel;
    }

    /**
     * Chatpanel setter
     * 
     * @param chatPanel chat panel obj
     */
    public void setChatPanel(JPanel chatPanel) {
        this.chatPanel = chatPanel;
    }

    public JButton[] getSendButton() {
        return new JButton[] { sendButton, sendDupliButton };
    }

    public void setSendButton() {
        sendButton = new JButton();
        sendDupliButton = new JButton();
    }

    /**
     * This method sets score in the textfield
     * 
     * @param score int points
     */
    public void updateScore(int score) {
        pointsKeeper.setText(Integer.toString(score));
    }

    public JTextField[] getTextField() {
        return tField;
    }

    public JButton[] getFButtons() {
        return clientFuncBut;
    }

    /**
     * Text area getter
     * 
     * @return eventLog
     */
    public JTextArea getEventLog() {
        return eventLog;
    }

    /**
     * Text area getter
     * 
     * @return eventLog
     */
    public JTextArea getDuplicateLog() {
        return duplicateLog;
    }

    /**
     * This method add solo panel into the frame
     * 
     * @param panel JPanel desired to add
     * @return boolean status of the method whether successful or not
     */
    public boolean addSoloPanel(JPanel panel) {
        if (panel == null)
            return false;
        this.add(panel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        return true;
    }

    /**
     * This method remove solo panel into the frame
     * 
     * @param panel JPanel desired to add
     * @return boolean status of the method whether successful or not
     */
    public boolean removeSoloPanel(JPanel panel) {
        if (panel == null)
            return false;
        this.setVisible(false);

        this.remove(panel);
        return true;
    }

    /**
     * This method shows dialog when error occurs during loading from file
     * 
     * @param control ServerController
     * @return int 0;
     */
    public int showInvalidPortDialog(ClientController control) {
        JOptionPane.showMessageDialog(this, control.getLabel("PORTINVMSG"));
        return 0;
    }

    public JPanel getSplashScreen() {
        return splash;
    }

    /**
     * This method prompts the user to choose language
     * 
     * @param control GameController obj
     * @return Option Dialog
     */
    public int mainMenuHelp(ClientController control) {

        combo = new JComboBox<>(
                new String[] { control.getLabel("LANG1"), control.getLabel("LANG2"),
                        control.getLabel("LANG3") });

        String[] options = { control.getLabel("BUTOK"), control.getLabel("BUTCANCEL")
        };

        return JOptionPane.showOptionDialog(this, combo, control.getLabel("BUTLANG"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    /**
     * This method prompts the user to choose dimension
     * 
     * @param control GameController obj
     * @return Option Dialog
     */
    public int mainMenuPlay(ClientController control) {
        combo = new JComboBox<>(control.getDimList());
        combo.setSelectedIndex(2);

        String[] options = { control.getLabel("BUTOK"), control.getLabel("BUTCANCEL")
        };

        return JOptionPane.showOptionDialog(this, combo, control.getLabel("DIMMSG"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    /**
     * Combo index getter
     * 
     * @return int index
     */
    public int getComboIndex() {
        return combo.getSelectedIndex();
    }

    public void setGameButtons(int dim) {
        squareButtons = new JButton[dim][dim];
    }

    /**
     * Game board setter
     * 
     * @param control ClientController obj
     */
    public void setGameBoard(ClientController control) {
        if (control.getIsLoad()) { // Loading from file
            System.out.println("Loading GameBoard from File...");
            gameFrame = new ClientGameFrame(-1, control);

        } else if (isDefault) { // Default board
            System.out.println("Loading GameBoard on Default...");
            gameFrame = new ClientGameFrame(2, control);
        } else { // Randomize
            System.out.println("Loading GameBoard...");
            gameFrame = new ClientGameFrame(combo.getSelectedIndex(), control);

        }
    }

    /**
     * Sqr Button getter
     * 
     * @return 2d Jbutton array obj
     */
    public JButton[][] getSButtons() {
        return squareButtons;
    }

    /**
     * Setter end game panel
     * 
     * @param isPerfect boolean true is perfect, false otherwise
     * @param control   GameController obj
     */
    public void setEndGamePanel(boolean isPerfect, ClientController control) {
        endScreen = new EndScreen(isPerfect, control);
    }

    /**
     * This method sets time to the textfield
     * 
     * @param i int time
     */
    public void setTime(int i) {
        timeKeeper.setText(Integer.toString(i));
    }

    /**
     * Setter for color frame
     * 
     * @param control GameController obj
     */
    public void setColorFrame(ClientController control) {
        colorFrame = new Colors(control);
    }

    /**
     * MarkBox setter
     * 
     * @param box JCheckBox obj
     */
    public void setMarkBox(JCheckBox box) {
        markBox = box;
    }

    /**
     * Markbox getter
     * 
     * @return JCheckBox obj
     */
    public JCheckBox getMarkBox() {
        return markBox;
    }

    /**
     * Points keeper getter
     * 
     * @return pointsKeeper
     */
    public JTextField getPointKeep() {
        return pointsKeeper;
    }

    /**
     * Time keeper getter
     * 
     * @return timeKeeper
     */
    public JTextField getTimeKeep() {
        return timeKeeper;
    }

    /**
     * Color getter
     * 
     * @return array of Color
     */
    public Color[] getColors() {
        return colors;
    }

    public JFrame getFrame() {
        return gameFrame;
    }

    /**
     * Color Chooser setter
     */
    public void setColorChooser() {
        colorChooser = new JColorChooser();
    }

    /**
     * ColorChooser getter
     * 
     * @return JColorChooser obj
     */
    public JColorChooser getColorChooser() {
        return colorChooser;
    }

    /**
     * This method changes JButton color based on these parameters
     * 
     * @param selectedColor Color selected
     * @param index         Colors index
     * @param control       ClientController obj
     */
    public void setColor(Color selectedColor, int index, ClientController control) {
        control.getDim();
        for (int i = 0; i < control.getDim(); i++) {
            for (int j = 0; j < control.getDim(); j++) {
                if (squareButtons[i][j].getBackground() == colors[index])
                    squareButtons[i][j].setBackground(selectedColor);
            }
        }
        colors[index] = selectedColor;
    }

    /**
     * Color frame getter
     * 
     * @return Color frmae obj
     */
    public JFrame getColorFrame() {
        return colorFrame;
    }

}
