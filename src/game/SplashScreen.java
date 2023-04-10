package game;

import java.awt.*;

import javax.swing.*;

public class SplashScreen extends JPanel {

    public SplashScreen() {
        this.setLayout(new BorderLayout(0, 0));
        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon("images/Splash.png"));
        this.setMinimumSize(new Dimension(630, 300));
        // this.setLayout(null);
        this.add(logo, BorderLayout.CENTER);
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }
}