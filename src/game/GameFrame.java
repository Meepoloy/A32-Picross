package game;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class GameFrame extends JFrame {

    private final int BOARD_SIZE = 600; // 600px
    private final int FRAME_HEIGHT = 720; // 600px
    private final int FRAME_WIDTH = 940; // 600px

    private final int DIM;

    private Font myFont;

    private int maxSol;

    private JButton resetButton;

    /**
     * Drop down list for language
     */
    JComboBox<String> langList;

    GameFrame(int index, GameController control) {
        System.out.println("GameFrame Launched");

        this.setLayout(new BorderLayout(0, 0));
        this.setTitle(control.getLabel("TITLE"));
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);// 600x400
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        myFont = new Font("Monospaced", Font.BOLD, 15);
        System.out.println("Index = " + index);
        if (index == -1) {
            DIM = control.getDim();
        } else {
            DIM = Integer.parseInt(control.getDimList()[index]);
        }

        control.setButtons(new JButton[DIM][DIM]);

        if (DIM % 2 != 0) {
            maxSol = DIM / 2 + 1;
        } else {
            maxSol = DIM / 2;
        }
        /*
         * Setting up panels
         */
        setCenterPanel(control);
        setLeftPanel(control);
        setTopPanel(control);
        setRightPanel(control);
        setMenuBar(control);

        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    /**
     * Setting up board panel
     * 
     * @param control Controller class
     */
    private void setCenterPanel(GameController control) {

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(DIM, DIM, 0, 0));
        boardPanel.setBackground(Color.LIGHT_GRAY);
        boardPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        boardPanel.setPreferredSize(new Dimension(BOARD_SIZE, BOARD_SIZE));
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                control.getButtons()[i][j] = new JButton();
                control.getButtons()[i][j].setBackground(Color.WHITE);
                control.getButtons()[i][j].setFocusable(false);
                control.getButtons()[i][j].setActionCommand(Integer.toString(DIM * i + j + 1));
                control.getButtons()[i][j].addActionListener(control);

                boardPanel.add(control.getButtons()[i][j]);
                this.add(boardPanel, BorderLayout.CENTER);
            }
        }
    }

    /**
     * Setting up Left-side panel
     * 
     * @param control Controller class
     */
    private void setLeftPanel(GameController control) {
        System.out.println("Initializing Left Panel...");
        JPanel colPanel = new JPanel();
        colPanel.setPreferredSize(new Dimension(120, BOARD_SIZE));
        colPanel.setBackground(Color.LIGHT_GRAY);
        colPanel.setLayout(new GridLayout(DIM, 1, 1, 1));
        for (int i = 0; i < DIM; i++) {
            JPanel sidePanelSol = new JPanel();
            sidePanelSol.setLayout(new GridLayout());
            int count = 0;
            String leftMark = "";

            for (int j = 0; j < DIM; j++) {
                if (control.getSolution()[i][j] == 1) {
                    count++;
                } else {
                    if (count != 0)
                        leftMark += Integer.toString(count) + " ";
                    count = 0;
                }
                if (j == DIM - 1 && count != 0)
                    leftMark += Integer.toString(count);
            }
            if (leftMark.equals(""))
                leftMark = "0";
            JTextField sideMark = new JTextField(leftMark);
            sideMark.setEditable(false);
            sideMark.setFont(myFont);
            sideMark.setHorizontalAlignment(0);
            sideMark.setBackground(Color.LIGHT_GRAY);
            sideMark.setForeground(Color.BLACK);
            sideMark.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            sidePanelSol.add(sideMark);
            sidePanelSol.setBackground(Color.LIGHT_GRAY);
            colPanel.add(sidePanelSol);
        }
        this.add(colPanel, BorderLayout.WEST);
    }

    /**
     * Setting up Top-side panel
     * 
     * @param control Controller class
     */
    private void setTopPanel(GameController control) {
        System.out.println("Initializing Top Panel...");

        /*
         * Top Panel
         */
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(920, 120));
        topPanel.setLayout(new BorderLayout(0, 0));
        topPanel.setBackground(Color.LIGHT_GRAY);
        /*
         * Top Left Panel
         */
        JPanel markPanel = new JPanel();
        markPanel.setPreferredSize(new Dimension(120, 120));
        markPanel.setBackground(Color.LIGHT_GRAY);
        markPanel.setLayout(new BorderLayout(1, 1));
        control.setMark(new JCheckBox(control.getLabel("LBLMARK")));
        control.getMarkCheckBox().setSize(new Dimension(25, 25));
        control.getMarkCheckBox().setActionCommand("LBLMARK");
        control.getMarkCheckBox().addActionListener(control);
        // markBox.addActionListener(control);
        //
        control.getMarkCheckBox().setOpaque(false);
        markPanel.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.WEST);
        markPanel.add(control.getMarkCheckBox(), BorderLayout.CENTER);
        /*
         * Top Center Panel
         */
        JPanel rowPanel = new JPanel();
        rowPanel.setPreferredSize(new Dimension(BOARD_SIZE, 120));
        rowPanel.setBackground(Color.LIGHT_GRAY);
        rowPanel.setLayout(new GridLayout(1, DIM, 1, 1));

        for (int i = 0; i < DIM; i++) {
            JPanel topPanelSol = new JPanel();
            topPanel.setPreferredSize(new Dimension(120, 120));
            int count = 0, spaces = maxSol;
            String topMarker = "";

            for (int j = 0; j < DIM; j++) {
                if (control.getSolution()[j][i] == 1) {
                    count++;
                } else {
                    if (count != 0) {
                        topMarker += Integer.toString(count) + "\n";
                        spaces--;
                    }
                    count = 0;
                }
                if (j == DIM - 1 && count != 0) {
                    topMarker += Integer.toString(count);
                    spaces--;
                }
            }
            if (topMarker.equals(""))
                topMarker = "0";

            for (int k = spaces - 1; k >= 0; k--)
                topMarker = "\n" + topMarker;

            JTextPane topMark = new JTextPane();
            topMark.setText(topMarker);
            StyledDocument doc = topMark.getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            doc.setParagraphAttributes(0, doc.getLength(), center, false);

            topMark.setEditable(false);
            topMark.setFont(myFont);
            topMark.setBackground(Color.LIGHT_GRAY);
            topMark.setForeground(Color.BLACK);
            topMark.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            topPanelSol.add(topMark);
            topPanelSol.setBackground(Color.LIGHT_GRAY);
            rowPanel.add(topPanelSol);

        }

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

        topPanel.add(markPanel, BorderLayout.WEST);
        topPanel.add(rowPanel, BorderLayout.CENTER);
        topPanel.add(logoPanel, BorderLayout.EAST);
        this.add(topPanel, BorderLayout.NORTH);

    }

    /**
     * Setting up Right-side panel
     * 
     * @param control Controller class
     */
    private void setRightPanel(GameController control) {
        System.out.println("Initializing Right Panel...");

        JPanel comPanel = new JPanel();
        comPanel.setPreferredSize(new Dimension(220, 600));
        comPanel.setLayout(new BoxLayout(comPanel, BoxLayout.Y_AXIS));
        comPanel.setBackground(Color.PINK);

        control.setTextArea(new JTextArea());
        control.getTextArea().setEditable(false);
        JScrollPane scrollV = new JScrollPane(control.getTextArea());
        scrollV.setPreferredSize(new Dimension(180, 480));
        control.getTextArea().append(control.getLabel("GAMEMSG") + "\n");

        /*
         * Defining and adding actions to function buttons
         */
        JPanel funcPanel = new JPanel();
        funcPanel.setLayout(new BoxLayout(funcPanel, BoxLayout.X_AXIS));
        funcPanel.setOpaque(false);

        JLabel points = new JLabel(control.getLabel("LBLSCORE"));
        funcPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        funcPanel.add(points);
        // JTextField score = new JTextField("POINTs");
        // score.setSize(new Dimension(50, 25));
        control.getPoints().setSize(new Dimension(50, 25));
        control.getPoints().setText("0");
        funcPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        funcPanel.add(control.getPoints());
        control.getPoints().setPreferredSize(new Dimension(30, 25));
        control.getPoints().setMaximumSize(new Dimension(30, 25));
        funcPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        JLabel time = new JLabel(control.getLabel("LBLTIME"));
        funcPanel.add(time);
        funcPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        control.getTimer().setPreferredSize(new Dimension(30, 25));
        control.getTimer().setMaximumSize(new Dimension(40, 25));
        funcPanel.add(control.getTimer());
        funcPanel.add(Box.createRigidArea(new Dimension(20, 0)));

        JPanel resetPanel = new JPanel();
        resetPanel.setLayout(new BoxLayout(resetPanel, BoxLayout.X_AXIS));
        resetPanel.setOpaque(false);
        resetButton = new JButton(control.getLabel("BUTRESET"));
        resetButton.addActionListener(control);
        resetButton.setActionCommand("BUTRESET");
        resetButton.setMargin(new Insets(1, 1, 1, 1));
        resetPanel.add(resetButton);

        comPanel.add(scrollV);
        comPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        comPanel.add(funcPanel);
        comPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        comPanel.add(resetPanel);
        comPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        this.add(comPanel, BorderLayout.EAST);
    }

    private void setMenuBar(GameController control) {
        System.out.println("Initializing Menu Bar...");

        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu(control.getLabel("BUTGAME"));
        JMenuItem newGame = new JMenuItem(control.getLabel("BUTNEWGAME"));
        newGame.setIcon(new ImageIcon("images/piciconnew.gif"));
        newGame.setActionCommand("BUTNEWGAME");
        newGame.addActionListener(control);
        gameMenu.add(newGame);

        JMenuItem save = new JMenuItem(control.getLabel("BUTSAVE"));
        save.setIcon(new ImageIcon("images/iconsave.png"));
        save.setActionCommand("SAVEDESIGN");
        save.addActionListener(control);
        if (control.getIsLoad())
            save.setEnabled(false);
        gameMenu.add(save);

        JMenuItem solution = new JMenuItem(control.getLabel("BUTSOL"));
        solution.setIcon(new ImageIcon("images/piciconsol.gif"));
        solution.setActionCommand("BUTSOL");
        solution.addActionListener(control);
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
