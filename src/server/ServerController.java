package server;

import javax.swing.*;

import a32Pack.Config;

import java.awt.event.*;

public class ServerController implements ActionListener, Config {
    /**
     * ServerModel obj
     */
    private ServerModel model;

    /**
     * ServerView obj
     */
    private ServerView view;

    public ServerController(ServerView view, ServerModel model) {
        this.model = model;
        this.view = view;

        // view.setVisible(true);
    }

    /**
     * Label getter
     * 
     * @param String key
     * @return returns a string lbl
     */
    public String getLabel(String s) {
        return model.getLabel(s);
    }

    /**
     * TextArea setter
     * 
     */
    public void setTextArea() {
        view.setEventLog();
    }

    /**
     * Text area accessor
     * 
     * @return returns a jtextarea obj
     */
    public JTextArea getEventLog() {
        return view.getEventLog();
    }

    public void setPortField() {
        view.setPortField();
    }

    public JTextField getPortField() {
        return view.getPortField();
    }

    public void setFinalCheckBox() {
        view.setFinalCheckBox(this);
    }

    public JCheckBox getFinalCheckBox() {
        return view.getFinalCheckBox();
    }

    public JButton[] getFunctionButtons() {
        return view.getFButtons();
    }

    public String[] getDimList() {
        return model.getDimList();
    }

    public void run() {
        System.out.println("Launching...");
        view.setTitle(model.getLabel("TITLE"));
        view.addSoloPanel(view.getSplashScreen());
        try {
            Thread.sleep(SPLASHTIME);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while (true) {
            int x = view.mainMenuHelp(this);
            if (x == 0) {
                model.updateLocale(view.getComboIndex());
                break;
            }
        }
        view.removeSoloPanel(view.getSplashScreen());
        view.addSoloPanel(new ServerUI(this));

        // view.dispose();
    }

    public String getBoard() {

        return model.boardSend();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        switch (e.getActionCommand()) {
            case "EXECBUT":
                model.eventLogAppend(this, e.getActionCommand());
                if (model.isPortValid(getPortField().getText())) {
                    if (view.mainMenuPlay(this) != 0)
                        break;
                    model.setDimension(view.getComboIndex());
                }

                if (!model.execButAction(this)) {
                    view.showInvalidPortDialog(this);
                } else {
                    model.eventLogAppend(this, getLabel("WAITCONMSG"));
                    model.generatePicross();
                    getFunctionButtons()[1].setEnabled(true);
                    getFinalCheckBox().setEnabled(false);

                }
                break;
            case "RESBUT":
                model.scoreCheck();
                break;
            case "ENDBUT":
                model.eventLogAppend(this, e.getActionCommand());
                model.endButAction(this);
                model.stopServer();
                getFinalCheckBox().setEnabled(true);

                break;
            default:
                System.out.println("In default");
                model.eventLogAppend(this, e.getActionCommand());

                break;
        }
    }

}
