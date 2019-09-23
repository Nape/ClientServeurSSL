import logging.Logger;
import server.Server;

import java.io.IOException;

/**
 * Point d'entré du serveur.
 */
public class App {
    public static void main(String[] args) throws Exception {
        try {
            new Server(44444).start();
        } catch (IOException e) {
            Logger.getInstance().error(App.class, "Unexpected error while trying to run the server.\n" + e.getMessage());
        }
    }
}

