/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.isu.cs.cs2263.todoListManager;

import com.google.common.hash.Hashing;
import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountListState;
import edu.isu.cs.cs2263.todoListManager.storage.Read;
import edu.isu.cs.cs2263.todoListManager.view.View;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class App {

    // Make sure this (LOGGING_ENABLED) is set to false whenever you push, especially for our submission. It will print read/write logs, and
    // will wipe all data every time it's run, otherwise.
    public static final Boolean LOGGING_ENABLED = false;
    private static final Boolean ERASE_ALL_DATA = false;

    public static void main(String[] args) {
        // Make sure this (runTests) is commented out whenever you push, especially for our submission. It will wipe all data every time it's run, otherwise.
        if (LOGGING_ENABLED && ERASE_ALL_DATA) FreshStart.run();

        Application.launch(View.instance().getClass());
    }

    /**
     * Runs a bunch of tests and builds a test user for us to play with.
     *
     * @author Brandon Watkins
     */
    private static void runTests() {
        FreshStart.run();// This will ERASE all user data, and generate a test user. Do NOT USE once we're done testing the app.

        // Read in all accounts to AccountListState
        AccountListState als = (AccountListState)AccountListState.instance();
        als.setAccounts(Read.readAllUserData());

        // Retrieve test user by username and password. Note this requires that you setAccounts(readAll), like above, first.
        // You can use verifyCredentials instead, if the user is already stored as the current user.
        for (Account account : als.getAccountsBackdoor()) {
            if (account.getEmail().equals("test@gmail.com") && account.getPassword().equals(
                    ((AccountContext)AccountContext.instance()).generateHash("password"))
            ) {
                ((AccountContext)AccountContext.instance()).setCurrentAccount(account);
            }
        }

        // gets the newly set account
        Account account = ((AccountContext)AccountContext.instance()).getCurrentAccount();
        // or use
        // AccountContext.CURRENT_ACCOUNT

        // checking the password (password)
        String actualPassword = account.getPassword();
        String attemptedPassword = Hashing.sha512().hashString("password", StandardCharsets.UTF_8).toString();
        // invalid password
        Boolean verified = ((AccountContext)AccountContext.instance()).verifyCredentials("Password");
        // valid password
        Boolean verified2 = ((AccountContext)AccountContext.instance()).verifyCredentials("password");


        int breakPoint = 2;
    }
}