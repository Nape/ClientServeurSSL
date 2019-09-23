package ClientAPP;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *  Classe qui représentent un essai de connection.
 */
public class Attempt
{

    public static void attempt(Button login, TextField nom, TextField pass)
    {
        login.setDisable(true);
        nom.setEditable(false);
        pass.setEditable(false);
        nom.clear();
        pass.clear();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Login désactivé pour 15 secondes.");
        alert.show();

        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(new Runnable()
        {
            @Override
            public void run()
            {
                login.setDisable(false);
                nom.setEditable(true);
                pass.setEditable(true);
            }
        }, 15, TimeUnit.SECONDS);
    }

}
