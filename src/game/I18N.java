package game;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * This class implements the Internationalization part
 */
public class I18N {

    /**
     * language list - Provided from A11
     */
    private String languages[] = new String[3];

    /**
     * I18N constructor
     * 
     * @param control GameController Obj
     * @param frame   MainFrame obj
     */
    I18N(GameController control, JFrame frame) {
        languages[0] = control.getLabel("LANG1");
        languages[1] = control.getLabel("LANG2");
        languages[2] = control.getLabel("LANG3");
        String[] options = { control.getLabel("BUTOK"), control.getLabel("BUTCANCEL") };

        JComboBox<String> combo = new JComboBox<>(languages);
        JOptionPane.showOptionDialog(frame, combo, control.getLabel("BUTHELP"),
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

    }
}
