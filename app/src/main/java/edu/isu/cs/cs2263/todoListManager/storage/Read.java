/**
 * @author Brandon Watkins
 * 3/31/2021
 */
package edu.isu.cs.cs2263.todoListManager.storage;

import com.google.gson.Gson;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
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
    public static String readSingleLineFile(String path) {
        Scanner reader = null;
        String output = null;
        try {
            File file = Write.createFile(path);
            if (!file.exists()) return null;
            reader = new Scanner(file);
            output = readNextLineFromFile(reader);
            if (reader != null) reader.close();
        } catch (Exception ex) {
            System.out.printf("storage.Read.readSingleLineFile() failed with exception: %s", ex.getMessage());
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
    public static int readNextID(String path) {
        String output = readSingleLineFile("./counters/" + path + ".txt");
        if (output == null) output = "0";
        Write.incrementCounter("./counters/" + path + ".txt", Integer.parseInt(output));
        return Integer.parseInt(output);
    }

    public static int getNextID(Object objectYouNeedAnIdFor) {
        if (objectYouNeedAnIdFor == null) return -1;
        else {
            String name = objectYouNeedAnIdFor.getClass().getName();
            if (name != null) name = name.substring(0, 1).toLowerCase() + name.substring(1);
            if (name == "userAccount" || name == "adminAccount" || name == "nullAccount") name = "account";
            return readNextID(name);
        }
        /*
        if (objectYouNeedAnIdFor instanceof Account) return readNextID("account");
        else if (objectYouNeedAnIdFor instanceof Section) return readNextID("section");
        else if (objectYouNeedAnIdFor instanceof Task) return readNextID("task");
        else if (objectYouNeedAnIdFor instanceof TaskList) return readNextID("taskList");
        else return readNextID(Object.class.getName());*/
    }

    /**
     * Reads the next line from a file.
     *
     * @param reader (Scanner) To read the file with.
     * @return (String) The next line read from the file.
     *
     * @author Brandon Watkins
     */
    public static String readNextLineFromFile(Scanner reader) {//private
        try {
            return reader.hasNextLine() ? reader.nextLine() : null;
        } catch (Exception ex) {
            System.out.printf("storage.Read.readNextLineFromFile() failed with exception: %s", ex.getMessage());
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
    public static Object readObjectFromFile(Class c, String path) {//private
        Object object = null;
        Reader reader = null;
        try {
            if (!(new File(path + ".json")).exists()) return null;
            reader = Files.newBufferedReader(Paths.get(path + ".json"));
            if (reader == null) return null;
            Gson gson = new Gson();
            object = gson.fromJson(reader, c);
        } catch (Exception ex) {
            System.out.printf("storage.Read.readObjectFromFile() failed with exception: %s", ex.getMessage());
        }
        return object;
    }

    /**
     * Reads User-specific data from file.
     *
     * @param userID (int) The user's ID to read in.
     * @return (Account) The account belonging to the given ID.
     *
     * @author Brandon Watkins
     */
    public static Account readUserData(int userID) {//private
        Object o = readObjectFromFile(Account.class, "./userData/" + userID + ".json");
        if (o != null && o instanceof Account) return (Account)o;
        else return null;
    }

    /**
     * Reads all of the user data in from files.
     *
     * @return (List<Account>) List of all Accounts.
     *
     * @author Brandon Watkins
     */
    public static List<Account> readAllUserData() {
        List<Account> accounts = new ArrayList();
        File directory = new File("./userData/");
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                try {
                    Object o = readUserData(Integer.parseInt(file.getName()));
                    if (o != null && o instanceof Account) accounts.add((Account) o);
                } catch (Exception ex) {
                    System.out.printf("storage.Read.readAllUserData() failed to read file (%s) with exception: %s", file.getName(), ex.getMessage());
                }
            }
        }
        return accounts;
    }

}
