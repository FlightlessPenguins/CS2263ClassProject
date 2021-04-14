/**
 * @author Brandon Watkins
 * 4/8/2021
 */
package edu.isu.cs.cs2263.todoListManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.*;
import edu.isu.cs.cs2263.todoListManager.model.objects.section.Section;
import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;
import edu.isu.cs.cs2263.todoListManager.model.objects.taskList.TaskList;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountListState;
import edu.isu.cs.cs2263.todoListManager.storage.Read;
import edu.isu.cs.cs2263.todoListManager.storage.Write;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Calendar;
import java.util.Comparator;
import java.util.stream.Stream;

public class FreshStart {

    /**
     * ERASES EXISTING DATA, and initializes all data necessary for our test user (not for actual tests, it's just to hard-code an account to play with.
     *
     * @author Brandon Watkins
     */
    public static void run() {
        System.out.printf("Deleting existing userData and counters...");
        // delete userData folder (and its contents)
        Write.deleteFolder(Paths.get("").toAbsolutePath().normalize().toString() + "/app/userData/");
        // delete counter folder (and its contents)
        Write.deleteFolder(Paths.get("").toAbsolutePath().normalize().toString() + "/app/counters/");

        // create test user with username: "test@gmail.com" and password: "password" (auto-creates a default tasklist with a default section)
        System.out.println("\r\n\nCreating test user...");
        UserAccount user = new UserAccount("I was here.", null, "test@gmail.com", "password", "Brandon", "Watkins");
        System.out.printf("Test user created:\r\nID: %s\r\nEmail: %s\r\nPassword Hash: %s\r\nName: %s %s\r\nBiography: %s\r\n",
                user.getID(), user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getBiography());

        // create temporary second test user
        UserAccount user2 = new UserAccount("I was here. Again.", null, "test2@gmail.com", "password2", "Tom", "Bombadil");
        (((AccountListState) AccountListState.instance())).addAccount(user2);

        // create tasklist - "taskList2" (auto-creates a default section)
        TaskList taskList2 = new TaskList("TaskList 2", "Second tasklist", "When the default tasklist just isn't good enough.", null, null, false);
        user.addTaskList(taskList2);

        // create a subtasklist - "subtasklist" - in tasklist2
        TaskList subTaskList = new TaskList("Sub-TaskList", "Sub-tasklist.", "Some comment.", null, null, false);
        taskList2.addSubTaskList(subTaskList);

        // create section - "section1" - add to tasklist2
        Section section1 = new Section("Section 1", "Additional section.");
        taskList2.addSection(section1);

        // create 2 tasks in default tasklist/section
        Task task1 = new Task("Task 1", "task 1 description", null, null, null, null);
        Task task2 = new Task("Task 2", "task 2 description", null, null, null, null);
        user.getTaskLists().addTask(task1);
        user.getTaskLists().addTask(task2);

        // create 2 tasks in tasklist2, default section
        Task task3 = new Task("Task 3", "task 3 description", null, null, null, null);
        Task task4 = new Task("Task 4", "task 4 description", null, null, null, null);
        user.getTaskLists().getSubTaskLists().get(0).addTask(task3);
        user.getTaskLists().getSubTaskLists().get(0).addTask(task4);

        // create 2 tasks in tasklist2, section1
        Task task5 = new Task("Task 5", "task 5 description", null, null, null, null);
        Task task6 = new Task("Task 6", "task 6 description", null, null, null, null);
        task5.addDaysToDueDate(14);
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_YEAR, 14);
        task6.setDueDate(date);
        user.getTaskLists().getSubTaskLists().get(0).getSections().get(1).addTask(task5);
        user.getTaskLists().getSubTaskLists().get(0).getSections().get(1).addTask(task6);

        // create subtask in one of the tasks in tasklist2, section1
        Task task7 = new Task("Task 7", "task 7 description", null, null, null, null);
        user.getTaskLists().getSubTaskLists().get(0).getSections().get(1).getTasks().get(0).addSubTask(task7);

        // create 1 task in subtasklist, default section
        Task task8 = new Task("Task 8", "task 8 description", null, null, null, null);
        user.getTaskLists().addTask(task8);

        // create empty sub-sub-tasklist
        TaskList subSubTaskList = new TaskList("Sub-Sub-TaskList", "Sub-sub-tasklist.", "Yeah, I went there.", null, null, false);
        subTaskList.addSubTaskList(subSubTaskList);

        // create an empty section for sub-sub-tasklist
        Section section2 = new Section("Section 2", "Additional section.");
        subSubTaskList.addSection(section2);

        // Save test user
        System.out.println("\r\nSaving test user...");
        Write.writeAccountData(user);
        Write.writeAllAccountsToFile();

        // print out user data
        System.out.println("\r\nReading test user...");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(Read.readUserData(user.getID()));
        System.out.println(json);

    }

}
