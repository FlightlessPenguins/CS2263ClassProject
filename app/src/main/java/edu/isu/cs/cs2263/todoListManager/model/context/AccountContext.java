/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.context;

import edu.isu.cs.cs2263.todoListManager.model.objects.account.*;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.model.state.account.*;
import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountContext implements Context {

    private Account currentAccount = (Account) NullAccount.instance();
    private final String INFO_FILEPATH = "./config/users.json";
    private final String PHOTO_FILEPATH = "./photos/" + Integer.toString(currentAccount.getID()) + ".png";

    private final List<State> states = new ArrayList<State>(
        Arrays.asList(
            AccountInfoState.instance(),
            AccountUpdateState.instance(),
            AccountCreateState.instance(),
            AccountListState.instance()
        )
    );

    /**
     * Gets the currently logged in account (or NullAccount)
     *
     * @return (Account) Currently logged in account.
     *
     * @author Brandon Watkins
     */
    public Account getCurrentAccount() {
        return currentAccount;
    }

    /**
     * Gets the file path to the user's profile info.
     *
     * @return (String) File path for the user's profile info.
     *
     * @author Brandon Watkins
     */
    public String getInfoFilepath() {
        return INFO_FILEPATH;
    }

    /**
     * Gets the file path to the user's profile photo.
     *
     * @return (String) File path to the user's profile photo.
     *
     * @author Brandon Watkins
     */
    public String getPhotoFilepath() {
        return PHOTO_FILEPATH;
    }

    /**
     * Changes the state from the current state to the state following the current state's transition.
     *
     * @param nextState The state that just finished its 'run()'.
     *
     * @author Brandon Watkins
     */
    @Override
    public void changeState(State nextState) {
        throw new RuntimeException("changeState not implemented yet.");
    }

    /**
     * Gets this singleton's instance.
     *
     * @return This singleton's instance (concrete Context).
     *
     * @author Brandon Watkins
     */
    public static Context instance() {
        throw new RuntimeException("instance not implemented yet.");
    }

    /**
     * Gets the next available ID number.
     *
     * @return (int) The next available ID number.
     *
     * @author Brandon Watkins
     */
    private int getNextID() throws ExecutionControl.NotImplementedException {
        // Read in ID counter from file
        // increment counter
        // write new counter to file
        // return counter that was read from file.
        throw new ExecutionControl.NotImplementedException("getNextID not implemented yet.");
    }

    /**
     * Verifies that the given password attempt is correct.
     *
     * @param passwordAttempt (String) The user's password attempt.
     * @return (Boolean) True if the password attempt's hash is equal to their stored password hash.
     *
     * @author Brandon Watkins
     */
    public Boolean verifyCredentials(String passwordAttempt) throws ExecutionControl.NotImplementedException {
        if (currentAccount instanceof NullAccount) return false;
        // If Password.Hash(passwordAttempt) == password return true; // or something.
        // else return false;
        throw new ExecutionControl.NotImplementedException("verifyCredentials not implemented yet.");
    }


}
