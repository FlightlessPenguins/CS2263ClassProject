/**
 * @author Brandon Watkins
 * 4/8/2021
 */
package edu.isu.cs.cs2263.todoListManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;
import java.util.stream.Stream;

public class FreshStart {

    /**
     * ERASES EXISTING DATA, and initializes all data necessary for our test user (not for actual tests, it's just to hard-code an account to play with.
     *
     * @author Brandon Watkins
     */
    public static void run() {
        // delete userData folder (and its contents)
        delete("app/userData/");

        // delete counter folder (and its contents)
        delete("app/counters/");

        // create test user with username: "test" and password: "password" (auto-creates a default tasklist with a default section)
        // create tasklist - "tasklist2" (auto-creates a default section)
        // create section - "section1" - add to tasklist2
        // create 2 tasks in default tasklist/section
        // create 2 tasks in tasklist2, default section
        // create 2 tasks in tasklist2, section1
        // create subtask in one of the tasks in tasklist2, section1
        // create a subtasklist - "subtasklist" - in tasklist2
        // create 1 task in subtasklist, default section

    }

    /**
     * Deletes all files and directories nested inside the given path, and the given path itself.
     *
     * @param directoryPath (String) The path to the directory to be deleted.
     *
     * @author Brandon Watkins
     */
    private static void delete(String directoryPath) {
        File file = new File(directoryPath);
        System.out.printf("\r\nFile path: %s", file.getAbsolutePath());
        if (file.exists()) {
            String[] files = file.list();
            File nestedFile = null;
            for (String f : files) {
                nestedFile = new File(f);
                System.out.printf("\r\n     File path: %s", nestedFile.getAbsolutePath());
                if (nestedFile.exists() && nestedFile.isDirectory()) {
                    delete(f);
                }
                nestedFile.delete();
            }
            file.delete();
        }
    }

    /**
     * deletes a specific file/directory (that cannot have any children).
     *
     * @param path (Path) The path to the file or directory to be deleted.
     *
     * @author Brandon Watkins
     */
    private static void deleteDirectory(Path path) {
        try {
            Files.delete(path);
        } catch (IOException ex) {
            System.out.printf("\r\nFreshStart.deleteDirectory() failed due to exception: %s", ex.getMessage());
        }
    }
}
