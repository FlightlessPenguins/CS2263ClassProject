/**
 * @author Brandon Watkins
 * 3/31/2021
 */
package edu.isu.cs.cs2263.todoListManager.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;

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
    public static File createFile(String path) {//private
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (Exception ex) {
            System.out.printf("storage.Write.createFile() failed with exception: %s", ex.getMessage());
        } finally {
            return file;
        }
    }

    /**
     * Increases the counter for the given counter name (path), writes to file.
     *
     * @param path (String) File path / counter name.
     * @param counter (Integer) The previous counter value.
     *
     * @author Brandon Watkins
     */
    public static void incrementCounter(String path, Integer counter) {//protected
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
    public static void overwriteFile(String path, String contents) {//private
        File file = createFile(path);
        FileWriter writer = null;
        if (file.exists()) {
            try {
                writer = new FileWriter(path);
                writer.write(contents);
                if (writer != null) writer.close();
            } catch (Exception ex) {
                System.out.printf("storage.Write.overwriteFile() failed with exception: %s", ex.getMessage());
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
    public static void writeObjectToFile(Object o, String path) {//private
        Writer writer;
        Gson gson;
        try {
            File file = createFile(path + ".json");
            writer = Files.newBufferedWriter(Paths.get(path + ".json"));
            gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(o, writer);
            if (writer != null) writer.close();
        } catch (Exception ex) {
            System.out.printf("storage.Write.writeObjectToFile() failed with exception: %s", ex.getMessage());
        }
    }

    /**
     * Writes user-specific data to file.
     *
     * @param user (Account) The account to store in the file.
     *
     * @author Brandon Watkins
     */
    public static void writeUserData(Account user) {
        writeObjectToFile(user, "./userData/" + user.getID());
    }

}
