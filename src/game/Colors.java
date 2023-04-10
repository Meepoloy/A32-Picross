package game;

import java.awt.*;
import javax.swing.*;

/**
 * This class is Frame for color
 */
public class Colors extends JFrame {

    /*
     * JButton array for different color
     */
    private JButton[] colorButtons;

    /**
     * Colors constructor
     * 
     * @param control GameController object
     */
    Colors(GameController control) {
        colorButtons = new JButton[3];
        this.setLayout(new BorderLayout());
        this.setTitle(control.getLabel("COLORTITLE"));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400, 120);
        this.setResizable(false);

        // Addind panels in the frame
        this.add(colorPanel(control), BorderLayout.CENTER);
        this.add(optionPanel(control), BorderLayout.SOUTH);
        this.setLocationRelativeTo(rootPane);
        this.setVisible(true);
    }

    /**
     * Private method that sets a panel for colors swatches
     * 
     * @param control GameController object
     * @return returns a JPanel
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
     * Private method that sets the option panel for clickable buttons
     * 
     * @param control GameController object
     * @return returns a JPanel
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

    // public void updateFrame() {
    // this.setVisible(false);
    // this.setVisible(true);
    // }
}
