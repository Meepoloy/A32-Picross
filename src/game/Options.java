package game;

import java.awt.*;
import javax.swing.*;

/**
 * This class makes the frame thats allows the user to change colors
 * and update UI
 */
public class Options extends JFrame {

    /**
     * JButton[] array for Colorbuttons
     */
    private JButton[] colorButtons;

    /**
     * Options constructor
     * 
     * @param control GameController obj
     */
    Options(GameController control) {
        colorButtons = new JButton[3];
        this.setLayout(new BorderLayout());
        this.setTitle(control.getLabel("BUTOPTIONS"));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400, 200);
        this.setResizable(false);

        this.add(colorPanel(control), BorderLayout.CENTER);
        this.add(optionPanel(control), BorderLayout.SOUTH);
        this.setLocationRelativeTo(rootPane);
        this.setVisible(true);
    }

    /**
     * Private method that sets the panel for color swatches
     * 
     * @param control GameController control
     * @return return a Jpanel
     */
    private JPanel colorPanel(GameController control) {
        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new GridLayout());
        for (int i = 0; i < colorButtons.length; i++) {
            colorButtons[i] = new JButton();
            colorButtons[i].setEnabled(false);
            colorButtons[i].setBackground(control.getColor()[i]);
            colorPanel.add(colorButtons[i]);
        }
        return colorPanel;
    }

    /**
     * Private method that sets up the option panel for changing the colors
     * 
     * @param control GameController obj
     * @return return a Jpanel
     */
    private JPanel optionPanel(GameController control) {
        JPanel optPanel = new JPanel();
        optPanel.setLayout(new GridLayout());
        optPanel.setPreferredSize(new Dimension(this.getWidth(), 25));
        JButton color1 = new JButton(control.getLabel("COLORLBL1"));
        color1.setActionCommand("COLORLBL1");
        color1.addActionListener(control);
        optPanel.add(color1);
        JButton color2 = new JButton(control.getLabel("COLORLBL2"));
        color2.setActionCommand("COLORLBL2");
        color2.addActionListener(control);
        optPanel.add(color2);
        JButton color3 = new JButton(control.getLabel("COLORLBL3"));
        color3.setActionCommand("COLORLBL3");
        color3.addActionListener(control);
        optPanel.add(color3);

        return optPanel;
    }
}
