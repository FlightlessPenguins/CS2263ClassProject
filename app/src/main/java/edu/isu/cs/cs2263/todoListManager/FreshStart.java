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

        TaskList school = new TaskList("School", "School Stuff", "For when the default tasklist just isn't good enough.", null, null, false);
        user.addTaskList(school);

        Calendar date1 = Calendar.getInstance();
        date1.set(2021, 5, 1, 15, 30);
        user.getTaskLists().addTask(new Task("Appointment with Dr. Green", "123 Main St.", null, date1, null, null));

        Section cs2263 = new Section("CS 2263", null);
        school.addSection(cs2263);

        Task lecture1 = new Task("Lecture", "Lecture 15", null, null, null, null);
        Task homework1 = new Task("Homework", null, null, null, null, null);
        Task project = new Task("Project", "Meet Mon., Wed., Fri.", null, null, null, null);
        Calendar date2 = Calendar.getInstance();
        date2.set(2021, 5, 3, 10, 15);
        project.setDueDate(date2);
        cs2263.addTask(lecture1);
        cs2263.addTask(homework1);
        cs2263.addTask(project);

        Section cs1337 = new Section("CS 1337", null);
        school.addSection(cs1337);

        Task finalExam1 = new Task("Final Exam", "Room 101", null, null, null, null);
        Calendar date3 = Calendar.getInstance();
        date3.set(2021, 5, 11, 8, 0);
        finalExam1.setDueDate(date3);
        cs1337.addTask(finalExam1);

        Section cs1187 = new Section("CS 1187", null);
        school.addSection(cs1187);

        Task lecture2 = new Task("Lecture", "Lecture 15", null, null, null, null);
        Task homework2 = new Task("Homework", null, null, null, null, null);
        Task finalExam2 = new Task("Final Exam", "Room 315", null, null, null, null);
        Calendar date4 = Calendar.getInstance();
        date4.set(2021, 5, 1, 12, 30);
        finalExam2.setDueDate(date4);
        cs1187.addTask(lecture2);
        cs1187.addTask(homework2);
        cs1187.addTask(finalExam2);

        Section info3380 = new Section("INFO 3380", null);
        school.addSection(info3380);

        Task lecture3 = new Task("Lecture", "Lecture 15", null, null, null, null);
        Task emailTeacher = new Task("Email Teacher", null, null, null, null, null);
        Task finalExam3 = new Task("Final Exam", "Room 212", null, null, null, null);
        Calendar date5 = Calendar.getInstance();
        date5.set(2021, 5, 1, 9, 0);
        finalExam3.setDueDate(date5);
        info3380.addTask(lecture3);
        info3380.addTask(emailTeacher);
        info3380.addTask(finalExam3);

        TaskList errands = new TaskList("Errands", "Errands to run", null, null, null, false);
        user.addTaskList(errands);

        Section pocatello = new Section("Pocatello", null);
        errands.addSection(pocatello);

        Task dougsGift = new Task("Drop off Doug's bday gift", "315 main st.", null, null, null, null);
        pocatello.addTask(dougsGift);

        Section idahoFalls = new Section("Idaho Falls", null);
        errands.addSection(idahoFalls);

        Task movies = new Task("Return borrowed movies", "217 main st.", null, null, null, null);
        idahoFalls.addTask(movies);

        Section bills = new Section("Bills", null);
        errands.addSection(bills);

        Task utilities = new Task("Utilities", null, null, null, null, null);
        bills.addTask(utilities);

        Task cellPhone = new Task("Cell Phone", null, null, null, null, null);
        bills.addTask(cellPhone);

        Task creditCard = new Task("Credit Cards", null, null, null, null, null);
        bills.addTask(creditCard);

        Task creditCard2 = new Task("Capital One Credit Card", null, null, null, null, null);
        creditCard.addSubTask(creditCard2);

        Task creditCard3 = new Task("Discover Credit Card", null, null, null, null, null);
        creditCard.addSubTask(creditCard3);


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
