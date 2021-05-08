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
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountContext implements Context {

    public static Account CURRENT_ACCOUNT = (Account) NullAccount.instance();
    private String INFO_FILEPATH = Paths.get("").toAbsolutePath().normalize().toString() + "/app/userData/" + Integer.toString(CURRENT_ACCOUNT.getID()) + ".json";
    private String PHOTO_FILEPATH = Paths.get("").toAbsolutePath().normalize().toString() + "/app/userData/photos/" + Integer.toString(CURRENT_ACCOUNT.getID()) + ".png";

    /**
     * Gets the currently logged in account (or NullAccount)
     *
     * @return (Account) Currently logged in account.
     *
     * @author Brandon Watkins
     */
    public Account getCurrentAccount() {
        return CURRENT_ACCOUNT;
    }

    /**
     * Sets the current account to point to the given user.
     *
     * @param account (Account) The account to set as current account (The account that is currently logged in).
     * @return (AccountContext) This AccountContext.
     */
    public AccountContext setCurrentAccount(Account account) {
        this.CURRENT_ACCOUNT = account;
        this.INFO_FILEPATH = Paths.get("").toAbsolutePath().normalize().toString() + "/app/userData/" + Integer.toString(account.getID()) + ".json";
        this.PHOTO_FILEPATH = Paths.get("").toAbsolutePath().normalize().toString() + "/app/userData/photos/" + Integer.toString(account.getID()) + ".png";
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
     * Verifies that the given password attempt is correct.
     *
     * @param passwordAttempt (String) The user's password attempt.
     * @return (Boolean) True if the password attempt's hash is equal to their stored password hash.
     *
     * @author Brandon Watkins
     */
    public Boolean verifyCredentials(String passwordAttempt) {
        if (CURRENT_ACCOUNT instanceof NullAccount) return false;
        else {
            return Hashing.sha512().hashString(passwordAttempt, StandardCharsets.UTF_8).toString().equals(CURRENT_ACCOUNT.getPassword());
        }
    }

    /**
     * Generates a password hash, using the SHA-512 algorithm.
     *
     * @param stringBeforeHash (String) The password to generate a hash for.
     * @return (String) SHA-512 Hashed Password
     *
     * @author Brandon Watkins
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
        public static final AccountContext INSTANCE = new AccountContext();
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


}
