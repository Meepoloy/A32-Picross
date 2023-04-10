package server;

import javax.swing.*;

import a32Pack.*;

public class ServerView extends JFrame implements Config {
    /**
     * TextField for port
     */
    private JTextField port;

    /**
     * JCheckBox for port
     */
    private JCheckBox finalize;

    /**
     * Textarea
     */
    private JTextArea eventLog;

    /**
     * Arr of JButtons
     */
    private JButton[] serverFuncBut = new JButton[3];

    /**
     * Combo box
     */
    private JComboBox<String> combo;

    public ServerView() {
        // splash = new SplashScreen();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    public void setPortField() {
        port = new JTextField();
    }

    public JTextField getPortField() {
        return port;
    }

    public JButton[] getFButtons() {
        return serverFuncBut;
    }

    /**
     * Text Area setter
     * 
     */
    public void setEventLog() {
        eventLog = new JTextArea();
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
     * Splashscreen getter
     * 
     * @return Jpanel obj
     */
    public JPanel getSplashScreen() {
        return splash;
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

    public void setFinalCheckBox(ServerController control) {
        finalize = new JCheckBox(control.getLabel("FINALLBL"));
    }

    public JCheckBox getFinalCheckBox() {
        return finalize;
    }

    /**
     * This method shows dialog when error occurs during loading from file
     * 
     * @param control ServerController
     * @return int 0;
     */
    public int showInvalidPortDialog(ServerController control) {
        JOptionPane.showMessageDialog(this, control.getLabel("PORTINVMSG"));
        return 0;
    }

    /**
     * This method prompts the user to choose dimension
     * 
     * @param control ServerController obj
     * @return Option Dialog
     */
    public int mainMenuPlay(ServerController control) {
        combo = new JComboBox<>(control.getDimList());
        combo.setSelectedIndex(2);

        String[] options = { control.getLabel("BUTOK"), control.getLabel("BUTCANCEL")
        };

        return JOptionPane.showOptionDialog(this, combo, control.getLabel("DIMMSG"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    /**
     * This method prompts the user to choose language
     * 
     * @param control GameController obj
     * @return Option Dialog
     */
    public int mainMenuHelp(ServerController control) {

        combo = new JComboBox<>(
                new String[] { control.getLabel("LANG1"), control.getLabel("LANG2"),
                        control.getLabel("LANG3") });

        String[] options = { control.getLabel("BUTOK"), control.getLabel("BUTCANCEL")
        };

        return JOptionPane.showOptionDialog(this, combo, control.getLabel("BUTLANG"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    public int getComboIndex() {
        return combo.getSelectedIndex();
    }

}
