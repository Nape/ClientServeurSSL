package logging;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Timestamp;

/**
 * Singleton class Logger.
 * Mainly used through it's @Log function, it prints everything passed to a log file.
 * If the file is unavailable, it prints to the standard output.
 */
public class Logger
{
    private static Logger instance;
    private File file;
    private PrintStream out;
    private String path = "/Users/Nadir/Desktop/LOGTP3/";

    private Logger()
    {
        this.file = new File(path + Double.toString(System.currentTimeMillis()) + ".log");
        try
        {
            this.out = new PrintStream(this.file);
        }
        catch (IOException e)
        {
            this.out = null;
            Log(LogSeverity.ERROR, this.getClass(), "Cannot initialize the file. Logging to standard output.\n" + e.getMessage());
        }
    }

    public void Log(LogSeverity severity, Class classname, String message)
    {
        String timestamp = '[' + (new Timestamp(System.currentTimeMillis())).toString() + "][" + classname + "]";
        String entry = timestamp + message;

        if (this.out != null)
            this.out.println(timestamp + message);

        System.out.println(entry);
    }

    public void informational(Class classname, String message)
    {
        Log(LogSeverity.INFORMATIONAL, classname, message);
    }

    public void error(Class classname, String message)
    {
        Log(LogSeverity.ERROR, classname, message);
    }

    public static Logger getInstance()
    {
        if (instance == null)
        {
            instance = new Logger();
        }

        return instance;
    }
}
