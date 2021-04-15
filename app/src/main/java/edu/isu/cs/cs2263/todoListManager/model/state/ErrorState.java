package edu.isu.cs.cs2263.todoListManager.model.state;

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

    public ErrorState(String message, Object[] missingFields) {
        this(message, missingFields, null);
    }

    public ErrorState(String message, Object[] missingFields, String finalMessage) {
        this.message = message;
        this.missingFields = missingFields;
        this.finalMessage = finalMessage;
    }

    @Override
    public void run() {
        /* create error window with format:
        <message> <missingFields (comma separated)>
        <finalMessage>
        ie.
        <Account creation failed due to missing fields:> <password>
        <Please fill in all required fields before continuing>
        */
    }
}
