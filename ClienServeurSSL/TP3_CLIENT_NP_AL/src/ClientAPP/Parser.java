package ClientAPP;

import java.util.ArrayList;

/**
 * A module allowing the parsing of text into Message.
 */
public class Parser {
    public static Message Parse(String transmission) throws ParsingException
    {
        String[] tokens = transmission.split(",");

        // extract and validate the action
        Action action;
        if (tokens.length > 0) {
            action = Action.StringToAction(tokens[0]);
            if (action == null) {
                throw new ParsingException("Invalid Action");
            }
        } else {
            throw new ParsingException("No action found");
        }

        // extract the data
        ArrayList<String> data = new ArrayList<>(tokens.length - 1);
        if (tokens.length > 1) {
            for (int index = 1; index < tokens.length; index++) {
                data.add(tokens[index]);
            }
        }

        return new ClientAPP.Message(action, data);
    }
}
