package edu.isu.cs.cs2263.todoListManager.storage;

import com.google.gson.Gson;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;

import java.io.File;
import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Read {

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

    public static int readNextID(String path) {
        String output = readSingleLineFile("./counters/" + path + ".txt");
        if (output == null) output = "0";
        Write.incrementCounter("./counters/" + path + ".txt", Integer.parseInt(output));
        return Integer.parseInt(output);
    }

    public static String readNextLineFromFile(Scanner reader) {//private
        try {
            return reader.hasNextLine() ? reader.nextLine() : null;
        } catch (Exception ex) {
            System.out.printf("storage.Read.readNextLineFromFile() failed with exception: %s", ex.getMessage());
        }
        return null;
    }

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

    public static Account readUserData(int userID) {//private
        Object o = readObjectFromFile(Account.class, "./userData/" + userID + ".json");
        if (o != null && o instanceof Account) return (Account)o;
        else return null;
    }

    public static List<Account> readAllUserData() {
        List<Account> accounts = new ArrayList();
        Boolean proceed = true;
        int counter = 0;
        try {
            while (proceed) {
                Object o = readUserData(counter++);
                if (o != null && o instanceof Account) accounts.add((Account) o);
                else proceed = false;
            }
        } catch (Exception ex) {
            System.out.printf("storage.Read.readAllUserData() failed with exception: %s", ex.getMessage());
        }
        return accounts;
    }

}
