/**
 * @author Brandon Watkins
 */
package edu.isu.cs.cs2263.todoListManager.model.context;

import com.google.common.hash.Hashing;
import edu.isu.cs.cs2263.todoListManager.FreshStart;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.NullAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.UserAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.section.Section;
import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;
import edu.isu.cs.cs2263.todoListManager.model.objects.taskList.TaskList;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountListState;
import edu.isu.cs.cs2263.todoListManager.storage.Read;
import edu.isu.cs.cs2263.todoListManager.storage.Write;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class AccountContextTest {

    private AccountListState als = (AccountListState)AccountListState.instance();
    private int testsRun = 0;
    private UserAccount user;

    /**
     * creates a couple test users, writes them to file.
     *
     * @author Brandon Watkins
     */
    @BeforeEach
    void setUp() {
        if (testsRun == 0) {
            user = new UserAccount("I was here.", null, "test@gmail.com", "password", "Brandon", "Watkins");
            UserAccount user2 = new UserAccount("I was here. Again.", null, "test2@gmail.com", "password2", "Tom", "Bombadil");
            (((AccountListState) AccountListState.instance())).addAccount(user2);
            TaskList taskList2 = new TaskList("TaskList 2", "Second tasklist", "When the default tasklist just isn't good enough.", null, null, false);
            user.addTaskList(taskList2);
            TaskList subTaskList = new TaskList("Sub-TaskList", "Sub-tasklist.", "Some comment.", null, null, false);
            taskList2.addSubTaskList(subTaskList);
            Section section1 = new Section("Section 1", "Additional section.");
            taskList2.addSection(section1);
            Task task1 = new Task("Task 1", "task 1 description", null, null, null, null);
            Task task2 = new Task("Task 2", "task 2 description", null, null, null, null);
            user.getTaskLists().addTask(task1);
            user.getTaskLists().addTask(task2);
            Task task3 = new Task("Task 3", "task 3 description", null, null, null, null);
            Task task4 = new Task("Task 4", "task 4 description", null, null, null, null);
            user.getTaskLists().getSubTaskLists().get(0).addTask(task3);
            user.getTaskLists().getSubTaskLists().get(0).addTask(task4);
            Task task5 = new Task("Task 5", "task 5 description", null, null, null, null);
            Task task6 = new Task("Task 6", "task 6 description", null, null, null, null);
            task5.addDaysToDueDate(14);
            Calendar date = Calendar.getInstance();
            date.add(Calendar.DAY_OF_YEAR, 14);
            task6.setDueDate(date);
            user.getTaskLists().getSubTaskLists().get(0).getSections().get(1).addTask(task5);
            user.getTaskLists().getSubTaskLists().get(0).getSections().get(1).addTask(task6);
            Task task7 = new Task("Task 7", "task 7 description", null, null, null, null);
            user.getTaskLists().getSubTaskLists().get(0).getSections().get(1).getTasks().get(0).addSubTask(task7);
            Task task8 = new Task("Task 8", "task 8 description", null, null, null, null);
            user.getTaskLists().addTask(task8);
            TaskList subSubTaskList = new TaskList("Sub-Sub-TaskList", "Sub-sub-tasklist.", "Yeah, I went there.", null, null, false);
            subTaskList.addSubTaskList(subSubTaskList);
            Section section2 = new Section("Section 2", "Additional section.");
            subSubTaskList.addSection(section2);
        }

        Write.writeAccountData(user);
        Write.writeAllAccountsToFile();
        als.setAccounts(Read.readAllUserData());
        ((AccountContext) AccountContext.instance()).setCurrentAccount(user);
        testsRun++;
    }

    /**
     * Deletes all data created during setup.
     *
     * @author Brandon Watkins
     */
    @AfterEach
    void tearDown() {
        Write.deleteFolder("app");
    }

    /**
     * Checking that the user we just created has been properly stored in account context.
     *
     * @author Brandon Watkins
     */
    @Test
    void currentAccount() {
        assertEquals(user, AccountContext.CURRENT_ACCOUNT);
    }

    /**
     * Checking that the path returned is as expected.
     *
     * @author Brandon Watkins
     */
    @Test
    void getInfoFilepath() {
        assertEquals((((AccountContext)AccountContext.instance())).getInfoFilepath(),
                Paths.get("").toAbsolutePath().normalize().toString() + "/app/userData/" +
                        Integer.toString((((AccountContext)AccountContext.instance())).getCurrentAccount().getID()) + ".json");
    }

    /**
     * Checking that the path returned is as expected.
     *
     * @author Brandon Watkins
     */
    @Test
    void getPhotoFilepath() {
        assertEquals((((AccountContext)AccountContext.instance())).getPhotoFilepath(),
                Paths.get("").toAbsolutePath().normalize().toString() + "/app/userData/photos/" + Integer.toString(AccountContext.CURRENT_ACCOUNT.getID()) + ".png");
    }

    @Test
    void changeState() {
    }

    /**
     * Correctly verifies valid credentials.
     *
     * @author Brandon Watkins
     */
    @Test
    void verifyCredentials1() {
        user = null;
        AccountContext.CURRENT_ACCOUNT = NullAccount.instance();
        for (Account account : als.getAccountsBackdoor()) {
            String email = account.getEmail();
            String password = account.getPassword();
            String attempt = ((AccountContext) AccountContext.instance()).generateHash("password");
            if (account.getEmail().equals("test@gmail.com") && account.getPassword().equals(((AccountContext) AccountContext.instance()).generateHash("password"))) {
                ((AccountContext) AccountContext.instance()).setCurrentAccount(account);
            }
        }
        assertTrue((((AccountContext)AccountContext.instance()).verifyCredentials("password")), "invalid password attempt: " +
                ((AccountContext) AccountContext.instance()).generateHash("password"));
    }

    /**
     * Correctly fails with invalid credentials.
     *
     * @author Brandon Watkins
     */
    @Test
    void verifyCredentials2() {
        for (Account account : als.getAccountsBackdoor()) {
            if (account.getEmail().equals("test@gmail.com") && account.getPassword().equals(((AccountContext) AccountContext.instance()).generateHash("password"))) {
                ((AccountContext) AccountContext.instance()).setCurrentAccount(account);
            }
        }
        assertFalse((((AccountContext)AccountContext.instance()).verifyCredentials("Password")));
    }

    /**
     * 2 consecutive hashes match.
     *
     * @author Brandon Watkins
     */
    @Test
    void generateHash1() {
        assertEquals((((AccountContext)AccountContext.instance())).generateHash("password"),
                (((AccountContext)AccountContext.instance())).generateHash("password"));
    }

    /**
     * Hashes are case sensitive.
     *
     * @author Brandon Watkins
     */
    @Test
    void generateHash2() {
        assertNotEquals((((AccountContext)AccountContext.instance())).generateHash("password"),
                (((AccountContext)AccountContext.instance())).generateHash("Password"));
    }

    /**
     * Hash works, in general.
     *
     * @author Brandon Watkins
     */
    @Test
    void generateHash3() {
        assertTrue((((AccountContext)AccountContext.instance())).generateHash("password") != "password");
    }

    /**
     * Creates an instance of AccountContext.
     *
     * @author Brandon Watkins
     */
    @Test
    void instance() {
        assertTrue(AccountContext.instance() instanceof AccountContext);
    }

}