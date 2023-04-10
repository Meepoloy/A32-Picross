package a32Pack;

import java.awt.Dimension;
import javax.swing.JPanel;
import game.SplashScreen;

/**
 * Public interface contains all shared information for both Server and Client
 */
public interface Config {

        /**
         * String arr of Languages supported
         */
        final String[] LANGUAGES = { "en", "fr", "sp" };

        /**
         * Array of countries
         */
        final String[] COUNTRIES = { "US", "FR", "ES" };

        /**
         * Array of supported dimension
         */
        final String[] DIMENSIONLIST = {
                        "3", "4", "5", "6",
                        "7", "8", "9", "10" };

        /**
         * Resource file name.
         */
        final String SYSTEMMESSAGES = "game/resources/texts";

        /**
         * Splashscreen panel
         */
        final JPanel splash = new SplashScreen();

        /**
         * Final dimension for server ui
         */
        final Dimension serverDimension = new Dimension(580, 270);

        // Protocols
        final char SEPARATOR = '#';
        final String P0 = "P0"; // Server to All
        final String P1 = "P1"; // Client to Server (Request)
        final String P2 = "P2"; // Server to Client (Reply)
        final String P3 = "P3"; // Client to All (Chat)

        /**
         * Reply protocol
         */
        final String REPLY = P2 + SEPARATOR;

        /**
         * P1 sub-protocol
         */
        final String P1_1 = "SENDGAME";
        final String P1_2 = "RECEIVEGAME";
        final String P1_3 = "DISCONNECT";
        final String P1_4 = "SENDDATA";

        /**
         * Splash time
         */
        final int SPLASHTIME = 1000;

        /**
         * minimum int for port
         */
        static final int MIN_PORT_LIMIT = 10000;

        /**
         * Maximum int for port
         */
        static final int MAX_PORT_LIMIT = 65535;

}
