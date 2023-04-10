package game;

/**
 * This is the Driver class of this program
 */
public class Driver {

    GameView view;
    GameModel model;
    GameController control;

    /**
     * Default Constructor
     */
    Driver() {
        view = new GameView();
        model = new GameModel();
        control = new GameController(view, model);

        System.out.println("Launching ... " + control.launch());
    }

    /**
     * Main method
     * 
     * @param args code sequence
     */
    public static void main(String[] args) {
        new Driver();
    }
}
