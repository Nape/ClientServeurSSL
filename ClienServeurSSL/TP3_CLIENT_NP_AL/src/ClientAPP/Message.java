package ClientAPP;

import java.util.ArrayList;

/**
 * Protocol de communication entre les applications clientes et le serveur.
 * [Action], [Data #1], ..., [Data #n]
 */
public class Message
{
    public ClientAPP.Action action;
    public ArrayList<String> data;

    public Message(Action action, ArrayList<String> data)
    {
        this.action = action;
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "[Message] Action: " + this.action;
    }
}
