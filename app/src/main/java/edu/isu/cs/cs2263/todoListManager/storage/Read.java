/**
 * @author Brandon Watkins
 * 3/31/2021
 */
package edu.isu.cs.cs2263.todoListManager.storage;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.AdminAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.UserAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.section.Section;
import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;
import edu.isu.cs.cs2263.todoListManager.model.objects.taskList.TaskList;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Read {

    /**
     * Reads a file containing a single line of text.
     *
     * @param path (String) File path.
     * @return (String) The file's contents.
     *
     * @author Brandon Watkins
     */
    private static String readSingleLineFile(String path) {
        Scanner reader = null;
        String output = null;
        try {
            //System.out.println("Reading single line file: " + path);
            File file = Write.createFile(path);
            if (!file.exists() || file.isDirectory()) return null;
            reader = new Scanner(file);
            output = readNextLineFromFile(reader);
            if (reader != null) reader.close();
        } catch (Exception ex) {
            System.out.printf("\r\nstorage.Read.readSingleLineFile() failed with exception: %s", ex.getMessage());
        } finally {
            return output;
        }
    }

    /**
     * Reads the next counter from file.
     *
     * @param path (String) The counter's name (excluding pre/suffix - ie. "test").
     * @return (int) The counter for the given counter name.
     *
     * @author Brandon Watkins
     */
    private static int readNextID(String path) {
        String output = readSingleLineFile("app/counters/" + path + ".txt");
        if (output == null) output = "0";
        Write.incrementCounter("counters/" + path + ".txt", Integer.parseInt(output));
        return Integer.parseInt(output);
    }

    /**
     * Gets the next available ID from it's associated file, and increments the counter.
     *
     * @param objectYouNeedAnIdFor (Object) The object you need an ID for.
     * @return (int) The next available ID for this type of object, or -1 if null object was received.
     *
     * @author Brandon Watkins
     */
    public static int getNextID(Object objectYouNeedAnIdFor) {
        if (objectYouNeedAnIdFor == null) return -1;
        else {
            String name = objectYouNeedAnIdFor.getClass().getSimpleName();
            if (name != null) name = name.substring(0, 1).toLowerCase() + name.substring(1);
            if (name.contains("Account")) name = "account";
            return readNextID(name);
        }
    }

    /**
     * Reads the next line from a file.
     *
     * @param reader (Scanner) To read the file with.
     * @return (String) The next line read from the file.
     *
     * @author Brandon Watkins
     */
    private static String readNextLineFromFile(Scanner reader) {
        try {
            return reader.hasNextLine() ? reader.nextLine() : null;
        } catch (Exception ex) {
            System.out.printf("\r\nstorage.Read.readNextLineFromFile() failed with exception: %s", ex.getMessage());
        }
        return null;
    }

    /**
     * Reads a json object from a file.
     *
     * @param c (Class) Class to convert the json object into.
     * @param path (String) Path to json file.
     * @return (Object) Object that the json was converted into.
     *
     * @author Brandon Watkins
     */
    private static Object readObjectFromFile(Class c, String path) {
        Object object = null;
        Reader reader = null;
        try {
            if (!(new File(Paths.get("").toAbsolutePath().normalize().toString() + "/" + path + ".json")).exists()) return null;
            reader = Files.newBufferedReader(Paths.get(Paths.get("").toAbsolutePath().normalize().toString() + "/" + path + ".json"));
            if (reader == null) return null;
            Gson gson = new Gson();
            object = (Object)(gson.fromJson(reader, c));
            if (reader != null) reader.close();
        } catch (Exception ex) {
            System.out.printf("\r\nstorage.Read.readObjectFromFile() failed with exception: %s", ex.getMessage());
        }
        return object;
    }

    /**
     * Determines which account type the given user id belongs to.
     *
     * @param userID (int) The user's ID number.
     * @return (Account) The account belonging to the user with the given ID. (Null) If no matching user was found.
     *
     * @author Brandon Watkins
     */
    private static Account determineAccountType(int userID) {
        Class c = null;
        try {
            File file = Write.createFile("app/userData/" + userID + ".json");
            if (!file.exists() || file.isDirectory()) return null;
            Reader reader = Files.newBufferedReader(Paths.get(Paths.get("").toAbsolutePath().normalize().toString() + "/app/userData/" + userID + ".json"));
            JsonElement obj = JsonParser.parseReader(reader);
            if (reader != null) reader.close();
            String str = obj.toString();
            c = userOrAdmin(str);
            return (Account) readObjectFromFile(c, "app/userData/" + userID);
        } catch (Exception ex) {
            System.out.printf("\r\nstorage.Read.determineAccountType() failed with exception: %s", ex.getMessage());
        }
        return null;
    }

    /**
     * Determines whether a json object is an admin or a user.
     *
     * @param jsonString (String) The JSON string to search through.
     * @return (Class) The concrete Account class stored in the JSON string.
     *
     * @author Brandon Watkins
     */
    private static Class userOrAdmin(String jsonString) {
        // User Account
        if (jsonString.contains("taskLists")) {
            return UserAccount.class;
        }
        // Admin Account
        else {
            return AdminAccount.class;
        }
    }

    /**
     * Reads User-specific data from file.
     *
     * @param userID (int) The user's ID to read in.
     * @return (Account) The account belonging to the given ID.
     *
     * @author Brandon Watkins
     */
    public static Account readUserData(int userID) {
        Account account = determineAccountType(userID);
        if (account != null && (account instanceof UserAccount || account instanceof AdminAccount)) return account;
        else return null;
    }

    /**
     * Reads all of the user data in from files.
     * <p>Use only once, when app opens.
     *
     * @return (List<Account>) List of all Accounts.
     *
     * @author Brandon Watkins
     */
    public static List<Account> readAllUserData() {
        List<Account> accounts = new ArrayList();
        File directory = new File(Paths.get("").toAbsolutePath().normalize().toString() + "/app/userData/");
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                try {
                    String accountFileName = file.getName();
                    if (accountFileName.length() > 5) accountFileName = accountFileName.substring(0, accountFileName.length() - 5);
                    Account account = readUserData(Integer.parseInt(accountFileName));
                    if (account != null && account instanceof Account) accounts.add((Account) account);
                } catch (Exception ex) {
                    System.out.printf("\r\nstorage.Read.readAllUserData() failed to read file (%s) with exception: %s", file.getName(), ex.getMessage());
                }
            }
        }
        return accounts;
    }

}
