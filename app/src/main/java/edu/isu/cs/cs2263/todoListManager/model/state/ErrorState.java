package edu.isu.cs.cs2263.todoListManager.model.state;

import edu.isu.cs.cs2263.todoListManager.view.View;

/**
 * This is different from the other states, it will not use singleton, and doesn't affect (though it should,
 * hopefully later) other states. It just triggers a popup.
 */
public class ErrorState implements State{

    String message;
    Object[] missingFields;
    String finalMessage;

    public ErrorState(String message) {
        this(message, null, null);
    }

    public ErrorState(String message, String[] missingFields) {
        this(message, missingFields, null);
    }

    public ErrorState(String message, String[] missingFields, String finalMessage) {
        this.message = message;
        this.missingFields = missingFields;
        this.finalMessage = finalMessage;
        String mid = "";
        if (missingFields != null) {
            mid = ":\r\n";
            for (String s : missingFields) {
                if (s != null) mid += (s + ", ");
            }
            if (mid.length() > 2) {
                mid = mid.substring(0, mid.length() - 2) + ".";
            }
        }

        View.errorMsg(message + mid + (finalMessage == null ? "" : ("\r\n" + finalMessage)));
    }

    @Override
    public void run() {
        //View.errorMsg(message + (missingFields == null ? "" : missingFields) + (finalMessage == null ? "" : ("\r\n" + finalMessage)));
        /* create error window with format:
        <message> <missingFields (comma separated)>
        <finalMessage>
        ie.
        <Account creation failed due to missing fields:> <password>
        <Please fill in all required fields before continuing>
        */
    }
}
