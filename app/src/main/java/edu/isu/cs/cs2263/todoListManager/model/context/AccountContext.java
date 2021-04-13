/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.context;

import com.google.common.hash.Hashing;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.*;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.model.state.account.*;
import jdk.jshell.spi.ExecutionControl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountContext implements Context {

    private State currentState;
    private State[][] transitions;
    private final List<State> states = new ArrayList<State>(
        Arrays.asList(
            AccountInfoState.instance(),
            AccountUpdateState.instance(),
            AccountCreateState.instance(),
            AccountListState.instance()
        )
    );
    private Account currentAccount = (Account) NullAccount.instance();
    private final String INFO_FILEPATH = "./config/users.json";
    private final String PHOTO_FILEPATH = "./photos/" + Integer.toString(currentAccount.getID()) + ".png";

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
     * Sets the current account to point to the given user.
     *
     * @param account (Account) The account to set as current account (The account that is currently logged in).
     * @return (AccountContext) This AccountContext.
     */
    public AccountContext setCurrentAccount(Account account) {
        this.currentAccount = account;
        return this;
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
     * Verifies that the given password attempt is correct.
     *
     * @param passwordAttempt (String) The user's password attempt.
     * @return (Boolean) True if the password attempt's hash is equal to their stored password hash.
     *
     * @author Brandon Watkins
     */
    public Boolean verifyCredentials(String passwordAttempt) {
        if (currentAccount instanceof NullAccount) return false;
        else {
            return Hashing.sha512().hashString(passwordAttempt, StandardCharsets.UTF_8).toString().equals(currentAccount.getPassword());
        }
    }

    /**
     * Generates a password hash, using the SHA-512 algorithm.
     *
     * @param stringBeforeHash (String) The password to generate a hash for.
     * @return (String) SHA-512 Hashed Password
     *
     * @author Brandon Watkins
     * @author https://www.baeldung.com/java-password-hashing
     */
    public String generateHash(String stringBeforeHash) {
        try {
            return Hashing.sha512().hashString(stringBeforeHash, StandardCharsets.UTF_8).toString();
        } catch (Exception e) {
            throw new RuntimeException("Wasn't able to hash password, shutting down to avoid password compromise.");
        }
    }

    /**
     * Helper class for singleton implementation
     *
     * @author Brandon Watkins
     */
    private static final class Helper {
        private static final AccountContext INSTANCE = new AccountContext();
    }

    /**
     * Gets this singleton's instance.
     *
     * @return This singleton's instance (concrete Context).
     *
     * @author Brandon Watkins
     */
    public static Context instance() {
        return Helper.INSTANCE;
    }

    /**
     * Use to get Current User Account when a userAccount is needed, not an Account. May need to be deleted. IDK
     *
     * @author Grant Baird
     */
    public UserAccount getCurrentUserAccount() { return (UserAccount) currentAccount; }

}
