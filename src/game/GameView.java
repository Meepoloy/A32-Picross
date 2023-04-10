package game;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {

    /**
     * Splashscreen panel
     */
    private JPanel splash;

    /**
     * Mainmenu panel
     */
    private JPanel mainMenu;

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
     * optionFrame frame
     */
    private JFrame optionFrame;

    /**
     * DesignFrame frame
     */
    private JFrame designFrame;

    /**
     * JButton array
     */
    private JButton[][] squareButtons;

    /**
     * Textarea
     */
    private JTextArea eventLog;

    /**
     * Combo box
     */
    private JComboBox<String> combo;

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

    /**
     * Filechooser
     */
    private JFileChooser fileChooser;

    /**
     * GameView no param-constructor
     */
    GameView() {
        colors[0] = Color.BLUE;
        colors[1] = Color.YELLOW;
        colors[2] = Color.RED;

        splash = new SplashScreen();
        timeKeeper = new JTextField();
        pointsKeeper = new JTextField();
        fileChooser = new JFileChooser();

        if (combo == null)
            isDefault = true;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    /**
     * Splashscreen getter
     * 
     * @return Jpanel obj
     */
    public JPanel getSplashScreen() {
        return splash;
    }

    /**
     * Main menu setter
     * 
     * @param control GameController obj
     */
    public void setMainMenu(GameController control) {
        mainMenu = new MainMenu(control);
    }

    /**
     * Mainmenu getter
     * 
     * @return mainMenu
     */
    public JPanel getMainMenu() {
        return mainMenu;
    }

    /**
     * This method shows dialog when error occurs during loading from file
     * 
     * @param control GameController
     * @return int 0;
     */
    public int showLoadDialog(GameController control) {
        JOptionPane.showMessageDialog(this, control.getLabel("LOADMSG"));
        return 0;
    }

    /**
     * Game board setter
     * 
     * @param control GameController obj
     */
    public void setGameBoard(GameController control) {
        if (control.getIsLoad()) { // Loading from file
            System.out.println("Loading GameBoard from File...");
            gameFrame = new GameFrame(-1, control);

        } else if (isDefault) { // Default board
            System.out.println("Loading GameBoard on Default...");
            gameFrame = new GameFrame(2, control);
        } else { // Randomize
            System.out.println("Loading GameBoard...");
            gameFrame = new GameFrame(combo.getSelectedIndex(), control);

        }
    }

    /**
     * Design board setter
     * 
     * @param control GameController obj
     */
    public void setDesignBoard(GameController control) {
        designFrame = new DesignFrame(control);
    }

    /**
     * Setter for color frame
     * 
     * @param control GameController obj
     */
    public void setColorFrame(GameController control) {
        colorFrame = new Colors(control);
    }

    /**
     * Design board getter
     * 
     * @return Design frame obj
     */
    public JFrame getDesignBoard() {
        return designFrame;
    }

    /**
     * Color frame getter
     * 
     * @return Color frmae obj
     */
    public JFrame getColorFrame() {
        return colorFrame;
    }

    /**
     * This method changes JButton color based on these parameters
     * 
     * @param selectedColor Color selected
     * @param index         Colors index
     * @param control       GameController obj
     */
    public void setColor(Color selectedColor, int index, GameController control) {
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
     * Set FileChooser
     */
    public void setFileChooser() {
        fileChooser = new JFileChooser();
    }

    /**
     * FileChooser getter
     * 
     * @return JFileChooser obj
     */
    public JFileChooser getFileChooser() {
        return fileChooser;
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
     * GameBoard getter
     * 
     * @return GameFrame obj
     */
    public JFrame getGameBoard() {
        return gameFrame;
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
        this.remove(panel);
        this.setVisible(false);
        return true;
    }

    /**
     * This method prompts the user to choose dimension
     * 
     * @param control GameController obj
     * @return Option Dialog
     */
    public int mainMenuPlay(GameController control) {
        isDefault = false;
        combo = new JComboBox<>(control.getDimList());
        combo.setSelectedIndex(2);

        String[] options = { control.getLabel("BUTOK"), control.getLabel("BUTCANCEL")
        };

        return JOptionPane.showOptionDialog(this, combo, control.getLabel("DIMMSG"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    /**
     * This method launches the option frame
     * 
     * @param control GameController obj
     */
    public void openOption(GameController control) {
        JButton[] optButtons = new JButton[2];
        optButtons[0] = new JButton(control.getLabel("BUTLOAD"));
        optButtons[0].setActionCommand("BUTLOAD");
        optButtons[0].addActionListener(control);
        optButtons[1] = new JButton(control.getLabel("BUTLANG"));
        optButtons[1].setActionCommand("BUTLANG");
        optButtons[1].addActionListener(control);

        optionFrame = new JFrame(control.getLabel("BUTOPTIONS"));
        // optionFrame.setUndecorated(false);
        optionFrame.setSize(250, 80);
        optionFrame.setResizable(false);
        optionFrame.setLayout(new GridLayout());
        optionFrame.add(optButtons[0]);
        optionFrame.add(optButtons[1]);

        optionFrame.setLocationRelativeTo(this);
        optionFrame.setVisible(true);
    }

    /**
     * This method disposes Option frame
     */
    public void disposeOptFrame() {
        optionFrame.dispose();
        optionFrame = null;
    }

    /**
     * This method prompts the user to choose language
     * 
     * @param control GameController obj
     * @return Option Dialog
     */
    public int mainMenuHelp(GameController control) {

        combo = new JComboBox<>(
                new String[] { control.getLabel("LANG1"), control.getLabel("LANG2"),
                        control.getLabel("LANG3") });

        String[] options = { control.getLabel("BUTOK"), control.getLabel("BUTCANCEL")
        };

        return JOptionPane.showOptionDialog(this, combo, control.getLabel("BUTHELP"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    /**
     * This method prompts the user to choose color
     * 
     * @param control GameController obj
     * @return Option Dialog
     */
    public int colorChanger(GameController control) {
        combo = new JComboBox<>(
                new String[] { control.getLabel("LANG1"), control.getLabel("LANG2"),
                        control.getLabel("LANG3") });

        String[] options = { control.getLabel("COLORLBL1"),
                control.getLabel("COLORLBL2"), control.getLabel("COLORLBL3")
        };

        return JOptionPane.showOptionDialog(this, combo,
                control.getLabel("COLORTITLE"),
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
     * Color getter
     * 
     * @return array of Color
     */
    public Color[] getColors() {
        return colors;
    }

    /**
     * Square Buttons setter
     * 
     * @param buttons 2d Jbutton array
     */
    public void setSqrButtons(JButton[][] buttons) {
        squareButtons = buttons;
    }

    /**
     * Sqr Button getter
     * 
     * @return 2d Jbutton array obj
     */
    public JButton[][] getSqrButtons() {
        return squareButtons;
    }

    /**
     * Text Area setter
     * 
     * @param log JTextArea obj
     */
    public void setEventLog(JTextArea log) {
        eventLog = log;
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
     * This method sets time to the textfield
     * 
     * @param i int time
     */
    public void setTime(int i) {
        timeKeeper.setText(Integer.toString(i));
    }

    /**
     * This method sets score in the textfield
     * 
     * @param score int points
     */
    public void updateScore(int score) {
        pointsKeeper.setText(Integer.toString(score));
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
     * Points keeper getter
     * 
     * @return pointsKeeper
     */
    public JTextField getPointKeep() {
        return pointsKeeper;
    }

    /**
     * Disable selected button
     * 
     * @param but Selected JButton
     */
    public void disableButton(JButton but) {
        but.setEnabled(false);
    }

    /**
     * Shows Open Dialog
     * 
     * @return return 0:okay 1:Cancel
     */
    public int getFile() {
        return fileChooser.showOpenDialog(this);
    }

    /**
     * Shows Save Dialog
     * 
     * @return return 0:okay 1:Cancel
     */
    public int saveFile() {
        return fileChooser.showSaveDialog(this);
    }

    /**
     * Save dialog when error occur
     */
    public void showSaveDialog(GameController control) {
        JOptionPane.showMessageDialog(this, control.getLabel("SAVEMSG"));
    }

    /**
     * Setter end game panel
     * 
     * @param isPerfect boolean true is perfect, false otherwise
     * @param control   GameController obj
     */
    public void setEndGamePanel(boolean isPerfect, GameController control) {
        endScreen = new EndScreen(isPerfect, control);
    }

    /**
     * End Screen getter
     * 
     * @return endScreen JPanel obj
     */
    public JPanel getEndScreen() {
        return endScreen;
    }
}
