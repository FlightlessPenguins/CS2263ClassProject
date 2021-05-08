/**
 * @author Brandon Watkins
 * 3/31/2021
 */
package edu.isu.cs.cs2263.todoListManager.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.isu.cs.cs2263.todoListManager.App;
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
        File file = new File(Paths.get("").toAbsolutePath().normalize().toString() + "/" + path);
        try {
            createDirectory(Paths.get("").toAbsolutePath().normalize().toString() + "/" + path);
            file.createNewFile();
            //System.out.println("Creating file: " + Paths.get("").toAbsolutePath().normalize().toString() + "/" + path);
        } catch (Exception ex) {
            System.out.printf("\r\nstorage.Write.createFile() failed with exception: %s: %s", ex.getMessage(), Paths.get("").toAbsolutePath().normalize().toString() + "/" + path);
        } finally {
            if (App.LOGGING_ENABLED) System.out.println("Creating new file: " + Paths.get("").toAbsolutePath().normalize().toString() + "/" + path + ".");
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
        else dir = Paths.get("").toAbsolutePath().normalize().toString() + "/app/";

        File directory = new File(dir);
        if (!directory.exists()) directory.mkdirs();
        if (App.LOGGING_ENABLED) System.out.println("Creating directory: " + dir + ".");
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
        if (App.LOGGING_ENABLED) System.out.println("Incrementing " + path + " counter to: " + counter);
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
        //System.out.println("Overwriting file: " + path);
        File file = createFile("app/" + path);
        FileWriter writer = null;
        if (file.exists()) {
            try {
                //createDirectory(Paths.get("").toAbsolutePath().normalize().toString() + "/app/" + path);
                if (App.LOGGING_ENABLED) System.out.println("Overwriting file: " + "app/" + path + ".json" + ".");
                writer = new FileWriter(Paths.get("").toAbsolutePath().normalize().toString() + "/app/" + path);
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
            if (App.LOGGING_ENABLED) System.out.println("Writing " + o.getClass().getSimpleName() + " to " + "/app/" + path + ".json" + ".");
            File file = createFile("app/" + path + ".json");
            writer = Files.newBufferedWriter(Paths.get(Paths.get("").toAbsolutePath().normalize().toString() + "/app/" + path + ".json"));
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
        writeObjectToFile(user, "userData/" + user.getID());
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
        // writeObjectToFile(AccountListState.instance(), "accounts/"); // currently broken anyway, not worth spending the time to fix it.
    }

    /**
     * Deletes all files and directories nested inside the given path, and the given path itself.
     *
     * @param directoryPath (String) The path to the directory to be deleted.
     *
     * @author Brandon Watkins
     */
    public static void deleteFolder(String directoryPath) {
        File file = new File(directoryPath);
        if (file.exists()) {
            String[] files = file.list();
            File nestedFile = null;
            // if it is a directory
            if (files != null) {
                for (String f : files) {
                    nestedFile = new File(file.getAbsolutePath() + "/" + f);
                    if (nestedFile.exists()) {
                        deleteFolder(nestedFile.getAbsolutePath());
                        if (App.LOGGING_ENABLED) {
                            if (nestedFile.isDirectory()) System.out.printf("\r\nDeleted folder: %s", nestedFile.getAbsolutePath());
                            else System.out.printf("\r\nDeleted file: %s", nestedFile.getAbsolutePath());
                        }
                        nestedFile.delete();
                    }
                }
            }
            if (App.LOGGING_ENABLED) System.out.printf("\r\nDeleted folder: %s", file.getAbsolutePath());
            file.delete();
        }
    }

}
