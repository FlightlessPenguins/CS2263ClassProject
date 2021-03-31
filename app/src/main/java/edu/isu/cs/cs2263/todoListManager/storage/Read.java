package edu.isu.cs.cs2263.todoListManager.storage;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Read {

    public static String readSingleLineFile(String path) {
        Scanner reader = null;
        String output = null;
        try {
            File file = createFile(path);
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
        incrementCounter("./counters/" + path + ".txt", Integer.parseInt(output));
        return Integer.parseInt(output);
    }

    private static File createFile(String path) {
        File file = new File(path + ".txt");
        try {
            file.createNewFile();
        } catch (Exception ex) {
            System.out.printf("storage.Read.createFile() failed with exception: %s", ex.getMessage());
        } finally {
            return file;
        }
    }

    private static String readNextLineFromFile(Scanner reader) {
        try {
            return reader.hasNextLine() ? reader.nextLine() : null;
        } catch (Exception ex) {
            System.out.printf("storage.Read.readNextLineFromFile() failed with exception: %s", ex.getMessage());
        }
        return null;
    }

    private static void incrementCounter(String path, Integer counter) {
        overwriteFile(path, Integer.toString(counter + 1));
    }

    private static void overwriteFile(String path, String contents) {
        File file = createFile(path);
        FileWriter writer = null;
        if (file.exists()) {
            try {
                writer = new FileWriter(path);
                writer.write(contents);
                if (writer != null) writer.close();
            } catch (Exception ex) {
                System.out.printf("storage.Read.overwriteFile() failed with exception: %s", ex.getMessage());
            }
        }
    }
}
