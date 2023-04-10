package client;

import javax.swing.*;
import java.awt.*;
import a32Pack.Config;

public class ClientUI extends JPanel implements Config {

    /**
     * Constructor
     * 
     * @param control clientcontrol obj
     */
    ClientUI(ClientController control) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(580, 350));
        setTopPanel();
        setCenterPanel(control);
        setBotPanel(control);
    }

    /**
     * set bottom panel
     * 
     * @param control Clientcontrol obj
     */
    private void setBotPanel(ClientController control) {

        JPanel botPanel = new JPanel();
        botPanel.setLayout(new BoxLayout(botPanel, BoxLayout.X_AXIS));

        JScrollPane scrollV = new JScrollPane(control.getEventLog());
        control.getEventLog().append(control.getLabel("CLTMSG") + "\n");
        control.getEventLog().setEditable(false);
        scrollV.setPreferredSize(new Dimension(560, 150));
        botPanel.setPreferredSize(scrollV.getPreferredSize());

        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.X_AXIS));
        chatPanel.setPreferredSize(new Dimension(560, 30));

        // chatPanel.setLayout(new BorderLayout());
        chatPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        // control.getTextFields()[3].setActionCommand("SEND");
        // control.getTextFields()[3].addActionListener(control);
        control.getTextFields()[3].addFocusListener(control);
        control.getTextFields()[3].setText(control.getLabel("CHATMSG"));
        control.getTextFields()[3].setEnabled(false);
        chatPanel.add(control.getTextFields()[3]);
        // JButton sendButton = new JButton();
        control.setSendButton();
        control.getSendButton()[0].setActionCommand("SEND");
        control.getSendButton()[0].addActionListener(control);
        control.getSendButton()[0].setOpaque(false);
        control.getSendButton()[0].setContentAreaFilled(false);
        control.getSendButton()[0].setBorderPainted(false);
        control.getSendButton()[0].setBorder(null);
        control.getSendButton()[0].setEnabled(true);
        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon("images/gamesendicon.png"));
        control.getSendButton()[0].add(logo);
        control.getSendButton()[0].setEnabled(false);
        chatPanel.add(control.getSendButton()[0]);
        chatPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        chatPanel.addFocusListener(control);

        botPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        botPanel.add(scrollV);
        botPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(botPanel);
        this.add(chatPanel);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    /**
     * Center panel
     * 
     * @param control clientcontroller obj
     */
    private void setCenterPanel(ClientController control) {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JPanel centerTopPanel = new JPanel();
        centerTopPanel.setLayout(new BoxLayout(centerTopPanel, BoxLayout.X_AXIS));
        //

        centerTopPanel.add(Box.createRigidArea(new Dimension(25, 0)));
        centerTopPanel.add(new JLabel(control.getLabel("USERLBL")));
        centerTopPanel.add(control.getTextFields()[0]);
        centerTopPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        centerTopPanel.add(new JLabel(control.getLabel("SERVERLBL")));
        centerTopPanel.add(control.getTextFields()[1]);
        centerTopPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        centerTopPanel.add(new JLabel(control.getLabel("PORTLBL")));
        centerTopPanel.add(control.getTextFields()[2]);
        centerTopPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        control.getFunctionButtons()[0] = new JButton(control.getLabel("CONNECTLBL"));
        control.getFunctionButtons()[0].addActionListener(control);
        control.getFunctionButtons()[0].setActionCommand("CONNECTLBL");
        centerTopPanel.add(control.getFunctionButtons()[0]);
        centerTopPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        control.getFunctionButtons()[1] = new JButton(control.getLabel("ENDLBL"));
        control.getFunctionButtons()[1].addActionListener(control);
        control.getFunctionButtons()[1].setActionCommand("ENDLBL");
        control.getFunctionButtons()[1].setEnabled(false);
        centerTopPanel.add(control.getFunctionButtons()[1]);
        centerTopPanel.add(Box.createRigidArea(new Dimension(25, 0)));

        JPanel centerBotPanel = new JPanel();
        centerBotPanel.setLayout(new BoxLayout(centerBotPanel, BoxLayout.X_AXIS));
        centerBotPanel.add(Box.createRigidArea(new Dimension(25, 0)));

        //
        control.getFunctionButtons()[2] = new JButton(control.getLabel("BUTNEWGAME"));
        control.getFunctionButtons()[2].addActionListener(control);
        control.getFunctionButtons()[2].setActionCommand("BUTNEWGAME");
        centerBotPanel.add(control.getFunctionButtons()[2]);
        centerBotPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        //
        control.getFunctionButtons()[3] = new JButton(control.getLabel("SENDGLBL"));
        control.getFunctionButtons()[3].addActionListener(control);
        control.getFunctionButtons()[3].setActionCommand("SENDGLBL");

        centerBotPanel.add(control.getFunctionButtons()[3]);
        centerBotPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        //
        control.getFunctionButtons()[4] = new JButton(control.getLabel("RECLBL"));
        control.getFunctionButtons()[4].addActionListener(control);
        control.getFunctionButtons()[4].setActionCommand("RECLBL");
        centerBotPanel.add(control.getFunctionButtons()[4]);
        centerBotPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        //
        control.getFunctionButtons()[5] = new JButton(control.getLabel("SENDDLBL"));
        control.getFunctionButtons()[5].addActionListener(control);
        control.getFunctionButtons()[5].setActionCommand("SENDDLBL");
        centerBotPanel.add(control.getFunctionButtons()[5]);
        centerBotPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        //
        control.getFunctionButtons()[6] = new JButton(control.getLabel("BUTPLAYGAME"));
        control.getFunctionButtons()[6].addActionListener(control);
        control.getFunctionButtons()[6].setEnabled(false);
        control.getFunctionButtons()[6].setActionCommand("BUTPLAYGAME");
        centerBotPanel.add(control.getFunctionButtons()[6]);
        centerBotPanel.add(Box.createRigidArea(new Dimension(25, 0)));

        for (int i = 3; i < control.getFunctionButtons().length - 1; i++)
            control.getFunctionButtons()[i].setEnabled(false);

        //
        centerPanel.add(centerTopPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(centerBotPanel);
        this.add(centerPanel);

    }

    /**
     * Setting top panel
     */
    private void setTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon("images/gameclient.png"));
        topPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        topPanel.add(logo);
        topPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        this.add(topPanel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

    }

}
