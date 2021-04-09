/**
 * @author Brandon Watkins
 * 4/8/2021
 */
package edu.isu.cs.cs2263.todoListManager;

import com.google.gson.GsonBuilder;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.*;
import edu.isu.cs.cs2263.todoListManager.storage.Write;

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
        System.out.println("\r\nDeleting existing userData and counters...");
        // delete userData folder (and its contents)
        Write.deleteFolder(Paths.get("").toAbsolutePath().normalize().toString() + "/app/userData/");
        // delete counter folder (and its contents)
        Write.deleteFolder(Paths.get("").toAbsolutePath().normalize().toString() + "/app/counters/");
        System.out.println("");

        // create test user with username: "test@gmail.com" and password: "password" (auto-creates a default tasklist with a default section)
        System.out.println("Creating test user...");
        Account user = new UserAccount("I was here.", null, "test@gmail.com", "password", "Brandon", "Watkins");

        // create tasklist - "tasklist2" (auto-creates a default section)
        // create section - "section1" - add to tasklist2
        // create 2 tasks in default tasklist/section
        // create 2 tasks in tasklist2, default section
        // create 2 tasks in tasklist2, section1
        // create subtask in one of the tasks in tasklist2, section1
        // create a subtasklist - "subtasklist" - in tasklist2
        // create 1 task in subtasklist, default section
        System.out.printf("\r\nTest user created:\r\nID: %s\r\nEmail: %s\r\nPassword Hash: %s\r\nName: %s %s\r\nBiography: %s\r\n",
                user.getID(), user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), ((UserAccount) user).getBiography());

        // Save test user
        System.out.println("\r\nSaving test user");
        Write.writeAccountData(user);

    }

}
