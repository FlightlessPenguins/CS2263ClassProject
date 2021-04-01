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

    public static void incrementCounter(String path, Integer counter) {//protected
        overwriteFile(path, Integer.toString(counter + 1));
    }

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

    public static void writeUserData(Account user) {
        writeObjectToFile(user, "./userData/" + user.getID());
    }

}
