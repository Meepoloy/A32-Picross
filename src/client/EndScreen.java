package client;

import java.awt.*;
import javax.swing.*;

/**
 * This class shows the end screen
 */
public class EndScreen extends JPanel {

    /**
     * Endscreen constructor
     */
    EndScreen(boolean isPerfect, ClientController control) {
        System.out.println("in end screen");
        this.setLayout(new BorderLayout(0, 0));
        JButton clearButton = new JButton();
        clearButton.setActionCommand("BUTGOMAINMENU");
        clearButton.addActionListener(control);
        clearButton.setOpaque(false);
        clearButton.setContentAreaFilled(false);
        clearButton.setBorderPainted(false);
        clearButton.setPreferredSize(new Dimension(710, 343));
        clearButton.setBorder(null);
        JLabel logo = new JLabel();
        if (isPerfect) {
            logo.setIcon(new ImageIcon("images/gamewinner.png"));
        } else {
            logo.setIcon(new ImageIcon("images/gameend.png"));
        }
        clearButton.add(logo);
        this.setPreferredSize(new Dimension(708, 343));
        this.add(clearButton, BorderLayout.CENTER);
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }
}