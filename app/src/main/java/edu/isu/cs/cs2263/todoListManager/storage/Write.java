/**
 * @author Brandon Watkins
 * 3/31/2021
 */
package edu.isu.cs.cs2263.todoListManager.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountListState;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Write {

    /**
     * Creates a file, if and only if it doesn't already exist.
     *
     * @param path (String) File path to create a file at.
     * @return (File) The file it created, or found.
     *
     * @author Brandon Watkins
     */
    protected static File createFile(String path) {
        File file = new File(path);
        try {
            createDirectory(path);
            file.createNewFile();
        } catch (Exception ex) {
            System.out.printf("\r\nstorage.Write.createFile() failed with exception: %s", ex.getMessage());
        } finally {
            return file;
        }
    }

    /**
     * Creates a directory if one doesn't already exist at the given path.
     *
     * @param path (String) The filepath to the directory (including the directory itself).
     * @return (File) The file (directory) located at the given path.
     *
     * @author Brandon Watkins
     */
    private static File createDirectory(String path) {
        String[] dirs = path.split("\\/");
        String dir = "";
        // path contains path, but no filename
        if ((dirs.length == 1 && (dirs[0] == "." || !dirs[0].contains("."))) ||
                (dirs.length > 1 && !dirs[dirs.length - 1].contains("."))) {
            for (int i = 0; i < dirs.length; i++) {
                dir += (dirs[i] + "/");
            }
        }
        // path contains both a path and filename
        else if (dirs.length > 1 && dirs[dirs.length -1].contains(".")) {
            for (int i = 0; i < dirs.length - 1; i++) {
                dir += (dirs[i] + "/");
            }
        }
        // else no directory path given
        else dir = "./";

        File directory = new File(dir);
        if (!directory.exists()) directory.mkdirs();
        return new File(dir);
    }

    /**
     * Increases the counter for the given counter name (path), writes to file.
     *
     * @param path (String) File path / counter name.
     * @param counter (Integer) The previous counter value.
     *
     * @author Brandon Watkins
     */
    protected static void incrementCounter(String path, Integer counter) {
        overwriteFile(path, Integer.toString(counter + 1));
    }

    /**
     * Overwrites all file contents. Creates a new file if it doesn't already exist.
     *
     * @param path (String) File path for the file to overwrite.
     * @param contents (String) The new file contents.
     *
     * @author Brandon Watkins
     */
    private static void overwriteFile(String path, String contents) {
        File file = createFile(path);
        FileWriter writer = null;
        if (file.exists()) {
            try {
                writer = new FileWriter(path);
                writer.write(contents);
                if (writer != null) writer.close();
            } catch (Exception ex) {
                System.out.printf("\r\nstorage.Write.overwriteFile() failed with exception: %s", ex.getMessage());
            }
        }
    }

    /**
     * Writes an object to file.
     *
     * @param o (Object) The object to write to file.
     * @param path (String) The file path to store the object at.
     *
     * @author Brandon Watkins
     */
    private static void writeObjectToFile(Object o, String path) {
        Writer writer;
        Gson gson;
        try {


            File file = createFile(path + ".json");
            writer = Files.newBufferedWriter(Paths.get(path + ".json"));
            gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(o, writer);
            if (writer != null) writer.close();
        } catch (Exception ex) {
            System.out.printf("\r\nstorage.Write.writeObjectToFile() failed with exception: %s", ex.getMessage());
        }
    }

    /**
     * Writes user-specific data to file.
     *
     * @param user (Account) The account to store in the file.
     *
     * @author Brandon Watkins
     */
    public static void writeAccountData(Account user) {
        writeObjectToFile(user, "./userData/" + user.getID());
    }

    /**
     * Writes all accounts to file(s).
     * <p>Use (Questionably) when you close the app, and whenever admin updates an account.
     *
     * @author Brandon Watkins
     */
    public static void writeAllAccountsToFile() {
        // Makes it easier to find user-specific data, premature optimization though.
        for (Account account : (((AccountListState)(AccountListState.instance()))).getAccountsBackdoor()) {
            writeAccountData(account);
        }
        // writes the account list state, containing all accounts, which in turn contain all other data needing to be saved.
        writeObjectToFile(AccountListState.instance(), "./accounts/");
    }

}
