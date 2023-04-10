package game;

import java.awt.*;
import javax.swing.*;

/**
 * This class makes a frame that responsible for showing a board for designing
 * custom solution
 */
public class DesignFrame extends JFrame {

    /**
     * Constant measurement for this frame
     */
    private final int BOARD_SIZE = 600;
    private final int FRAME_HEIGHT = 600;
    private final int FRAME_WIDTH = 820;

    /**
     * JButtons for reset and save
     */
    private JButton resetButton, saveButton;

    /**
     * Constructor that accepts GameController object
     */
    DesignFrame(GameController control) {
        System.out.println("In game frame");
        this.setLayout(new BorderLayout(0, 0));
        this.setTitle(control.getLabel("TITLE") + " (" + control.getLabel("BUTDESIGN") + ")");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Sets the 2d JButton arr
        control.setButtons(new JButton[control.getDim()][control.getDim()]);

        // Initialize different Panel
        setCenterPanel(control);
        setRightPanel(control);
        setMenuBar(control);

        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    /**
     * Private method that sets the center panel
     * 
     * @param control GameController obj
     */
    private void setCenterPanel(GameController control) {

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(control.getDim(), control.getDim(), 0, 0)); // Grid layout for the center
                                                                                        // board
        boardPanel.setBackground(Color.LIGHT_GRAY);
        boardPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        boardPanel.setPreferredSize(new Dimension(BOARD_SIZE, BOARD_SIZE));
        for (int i = 0; i < control.getDim(); i++)
            for (int j = 0; j < control.getDim(); j++) {
                control.getButtons()[i][j] = new JButton();
                control.getButtons()[i][j].setBackground(Color.WHITE); // Color.white as default color
                control.getButtons()[i][j].setFocusable(false);
                control.getButtons()[i][j].setActionCommand("DESIGN");
                control.getButtons()[i][j].addActionListener(control);
                boardPanel.add(control.getButtons()[i][j]);
                this.add(boardPanel, BorderLayout.CENTER);
            }

    }

    /**
     * Private method that sets the right panel
     * 
     * @param control
     */
    private void setRightPanel(GameController control) {

        JPanel comPanel = new JPanel();
        comPanel.setPreferredSize(new Dimension(222, 600));
        comPanel.setLayout(new BoxLayout(comPanel, BoxLayout.Y_AXIS));
        comPanel.setBackground(Color.LIGHT_GRAY);

        /*
         * Top right
         */
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.LIGHT_GRAY);
        logoPanel.setPreferredSize(new Dimension(220, 120));
        logoPanel.setLayout(new GridLayout());
        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon("images/piccross1.png"));
        logoPanel.add(logo);

        control.setTextArea(new JTextArea());
        control.getTextArea().setEditable(false);
        JScrollPane scrollV = new JScrollPane(control.getTextArea());
        scrollV.setPreferredSize(new Dimension(180, 480));
        control.getTextArea().append(control.getLabel("GAMEMSG") + "\n");

        JPanel funcPanel = new JPanel();
        funcPanel.setPreferredSize(new Dimension(120, 100));
        funcPanel.setBackground(Color.CYAN);
        funcPanel.setLayout(null);

        // funcPanel.setOpaque(false);

        JButton[] colors = new JButton[2];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = new JButton();
            colors[i].setFocusable(false);
            colors[i].setEnabled(false);
            funcPanel.add(colors[i]);
        }
        colors[0].setBounds(0, 19, 50, 50);
        colors[0].setBackground(Color.BLUE);
        colors[1].setBounds(20, 39, 50, 50);
        colors[1].setBackground(Color.WHITE);

        JPanel resetPanel = new JPanel();
        resetPanel.setLayout(new BoxLayout(resetPanel, BoxLayout.X_AXIS));
        resetPanel.setOpaque(false);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.MAGENTA);
        rightPanel.setLayout(new GridLayout(2, 1, 0, 12));
        rightPanel.setPreferredSize(new Dimension(70, 100));

        resetButton = new JButton(control.getLabel("BUTRESET"));
        resetButton.addActionListener(control);
        resetButton.setActionCommand("BUTRESET");

        saveButton = new JButton(control.getLabel("BUTSAVE"));
        saveButton.addActionListener(control);
        saveButton.setActionCommand("SAVEDESIGN");

        rightPanel.add(saveButton);

        rightPanel.add(resetButton);

        resetPanel.add(funcPanel);
        resetPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        resetPanel.add(rightPanel);

        comPanel.add(logoPanel);
        comPanel.add(scrollV);
        comPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        comPanel.add(resetPanel);
        comPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        this.add(comPanel, BorderLayout.EAST);
    }

    /**
     * Private method that sets the Menu bar
     * 
     * @param control
     */
    private void setMenuBar(GameController control) {
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu(control.getLabel("BUTGAME"));
        JMenuItem newGame = new JMenuItem(control.getLabel("BUTNEWGAME"));
        newGame.setIcon(new ImageIcon("images/piciconnew.gif"));
        newGame.setActionCommand("BUTNEWGAME");
        newGame.addActionListener(control);
        gameMenu.add(newGame);

        JMenuItem solution = new JMenuItem(control.getLabel("BUTSOL"));
        solution.setIcon(new ImageIcon("images/piciconsol.gif"));
        solution.setActionCommand("BUTSOL");
        solution.addActionListener(control);
        solution.setEnabled(false);
        gameMenu.add(solution);

        JMenuItem mainMenu = new JMenuItem(control.getLabel("BUTMAINMENU"));
        mainMenu.setIcon(new ImageIcon("images/iconmmenu.png"));
        mainMenu.setActionCommand("BUTMAINMENU");
        mainMenu.addActionListener(control);
        gameMenu.addSeparator();
        gameMenu.add(mainMenu);

        JMenuItem exitGame = new JMenuItem(control.getLabel("BUTEXIT"));
        exitGame.setIcon(new ImageIcon("images/piciconext.gif"));
        exitGame.setActionCommand("BUTEXIT");
        exitGame.addActionListener(control);
        gameMenu.addSeparator();
        gameMenu.add(exitGame);

        menuBar.add(gameMenu);

        JMenu helpMenu = new JMenu(control.getLabel("BUTHELP"));
        JMenuItem colors = new JMenuItem(control.getLabel("BUTCOLOR"));
        colors.setIcon(new ImageIcon("images/picicondes.gif"));
        colors.setActionCommand("BUTCOLOR");
        colors.addActionListener(control);
        helpMenu.add(colors);

        JMenuItem about = new JMenuItem(control.getLabel("BUTABOUT"));
        about.setIcon(new ImageIcon("images/piciconcol.gif"));
        about.setActionCommand("BUTABOUT");
        about.addActionListener(control);
        helpMenu.addSeparator();
        helpMenu.add(about);

        menuBar.add(helpMenu);

        this.setJMenuBar(menuBar);
    }

}
