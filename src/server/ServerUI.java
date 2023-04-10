package server;

import javax.swing.*;

import a32Pack.Config;

import java.awt.*;

public class ServerUI extends JPanel implements Config {

    ServerUI(ServerController control) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(serverDimension);
        setTopPanel();
        setCenterPanel(control);
        setBotPanel(control);

    }

    private void setTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon("images/gameserver.png"));
        topPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        topPanel.add(logo);
        topPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        this.add(topPanel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

    }

    private void setCenterPanel(ServerController control) {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));

        // Port Label
        JLabel portLbl = new JLabel(control.getLabel("PORTLBL"));
        control.setPortField();
        // control.getPortField().setPreferredSize(new Dimension(50, 25));

        // JButton Exec

        control.getFunctionButtons()[0] = new JButton(control.getLabel("EXECLBL"));
        control.getFunctionButtons()[0].setActionCommand("EXECBUT");
        control.getFunctionButtons()[0].addActionListener(control);

        // JButton Result
        control.getFunctionButtons()[1] = new JButton(control.getLabel("RESLBL"));
        control.getFunctionButtons()[1].setActionCommand("RESBUT");
        control.getFunctionButtons()[1].addActionListener(control);
        control.getFunctionButtons()[1].setEnabled(false);

        control.setFinalCheckBox();

        control.getFunctionButtons()[2] = new JButton(control.getLabel("ENDLBL"));
        control.getFunctionButtons()[2].setActionCommand("ENDBUT");
        control.getFunctionButtons()[2].addActionListener(control);

        centerPanel.add(Box.createRigidArea(new Dimension(105, 0)));
        centerPanel.add(portLbl);
        centerPanel.add(control.getPortField());
        centerPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        centerPanel.add(control.getFunctionButtons()[0]);
        centerPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        centerPanel.add(control.getFunctionButtons()[1]);
        centerPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        centerPanel.add(control.getFinalCheckBox());
        centerPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        centerPanel.add(control.getFunctionButtons()[2]);
        centerPanel.add(Box.createRigidArea(new Dimension(105, 0)));

        this.add(centerPanel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

    }

    private void setBotPanel(ServerController control) {
        control.setTextArea();
        JPanel botPanel = new JPanel();
        botPanel.setLayout(new BoxLayout(botPanel, BoxLayout.X_AXIS));

        JScrollPane scrollV = new JScrollPane(control.getEventLog());
        control.getEventLog().append("Server started...\n");
        scrollV.setPreferredSize(new Dimension(560, 100));

        botPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        botPanel.add(scrollV);
        botPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        this.add(botPanel);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
    }

}
