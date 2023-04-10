package game;

import java.awt.*;
import javax.swing.*;

/**
 * This class makes the Main menu panel
 */
public class MainMenu extends JPanel {

    /**
     * String[] for action label
     */
    private String actionLabel[] = new String[] {
            "BUTPLAYGAME", "BUTDESIGN", "BUTOPTIONS" };

    /**
     * JButton[] menuBotton
     */
    private JButton[] menuButton;

    /**
     * MainMenu constructor
     * 
     * @param control GameController obj
     */
    public MainMenu(GameController control) {
        menuButton = new JButton[3];

        this.setLayout(new BorderLayout(0, 0));

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 3));
        menuPanel.setPreferredSize(new Dimension(this.getWidth(), 40));

        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon("images/PICROSS2.png"));

        this.setSize(logo.getPreferredSize());
        for (int i = 0; i < 3; i++) {
            menuButton[i] = new JButton(control.getLabel(actionLabel[i]));
            menuButton[i].setActionCommand(actionLabel[i]);
            menuButton[i].addActionListener(control);
            menuPanel.add(menuButton[i]);
        }

        this.add(logo, BorderLayout.CENTER);
        this.add(menuPanel, BorderLayout.SOUTH);
    }
}
