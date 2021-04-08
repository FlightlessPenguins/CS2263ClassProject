/**
 * @author Brandon Watkins
 * 3/31/2021
 */
package edu.isu.cs.cs2263.todoListManager.storage;

import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.AdminAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.UserAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;
import edu.isu.cs.cs2263.todoListManager.model.objects.taskList.TaskList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Most of these tests are for private methods. You'll have to switch all of the Read/Write methods to public if you want
 * to run the tests that have been commented out.
 */
class WriteTest {

    /**
     * NOTE: These will royally screw up the data if run after legit users have been created.
     *
     * @author Brandon Watkins
     */
    @BeforeEach
    void setUp() {
        File file = new File("test.txt");
        file.delete();
        file = new File("./counters/test.txt");
        file.delete();
        file = new File("./counters/task.txt");
        file.delete();
        file = new File("./counters/userAccount.txt");
        file.delete();
        file = new File("./counters/section.txt");
        file.delete();
        file = new File("./counters/taskList.txt");
        file.delete();
        file = new File("./counters/adminAccount.txt");
        file.delete();
        file = new File("./counters/string.txt");
        file.delete();
        file = new File("./userData/0.json");
        file.delete();
        file = new File("./userData/1.json");
        file.delete();
        file = new File("./userData/test.json");
        file.delete();
    }

    /**
     * NOTE: These will royally screw up the data if run after legit users have been created.
     *
     * @author Brandon Watkins
     */
    @AfterEach
    void tearDown() {
        File file = new File("test.txt");
        file.delete();
        file = new File("./counters/test.txt");
        file.delete();
        file = new File("./counters/task.txt");
        file.delete();
        file = new File("./counters/userAccount.txt");
        file.delete();
        file = new File("./counters/section.txt");
        file.delete();
        file = new File("./counters/taskList.txt");
        file.delete();
        file = new File("./counters/adminAccount.txt");
        file.delete();
        file = new File("./counters/string.txt");
        file.delete();
        file = new File("./userData/0.json");
        file.delete();
        file = new File("./userData/1.json");
        file.delete();
        file = new File("./userData/test.json");
        file.delete();
    }

    /**
     * Creates a .txt file
     *
     * @author Brandon Watkins
     */
    @Test
    void createFileTest1() {
        Write.createFile("test.txt");
        File file = new File("test.txt");
        assertEquals(true, file.exists());
    }

    /**
     * Deletes a .txt file. Ensures blank slate between tests.
     *
     * @author Brandon Watkins
     */
    @Test
    void createFileTest2() {
        Write.createFile("test.txt");
        File file = new File("test.txt");
        file.delete();
        assertEquals(false, file.exists());
    }

    /**
     * Creates multiple .txt files without issues.
     *
     * @author Brandon Watkins
     */
    @Test
    void createFileTest3() {
        Write.createFile("test.txt");
        Write.createFile("test.txt");
    }

    /**
     * Doesn't erase contents of file when createFile() is called on an existing file.
     *
     * @author Brandon Watkins
     */
    /*@Test
    void createFileTest4() {
        Write.createFile("test.txt");
        Write.overwriteFile("test.txt", "Stuff and things.");
        Write.createFile("test.txt");
        assertEquals(Read.readSingleLineFile("test.txt"), "Stuff and things.");
    }*/

    /**
     * incrementing a new counter initializes to zero.
     *
     * @author Brandon Watkins
     */
    @Test
    void incrementCounterTest1() {
        assertEquals(0, Read.getNextID("test"));
    }

    /**
     * Incrementing a new counter twice results in a one.
     *
     * @author Brandon Watkins
     */
    /*@Test
    void incrementCounterTest2() {
        Read.readNextID("test");
        assertEquals(1, Read.readNextID("test"));
    }*/

    /**
     * creates new file if doesn't exist.
     *
     * @author Brandon Watkins
     */
    /*@Test
    void overwriteFileTest1() {
        Write.overwriteFile("test.txt", "Stuff and things.");
        assertEquals(Read.readSingleLineFile("test.txt"), "Stuff and things.");
    }*/

    /**
     * Overwrites existing file content.
     *
     * @author Brandon Watkins
     */
    /*@Test
    void overwriteFileTest2() {
        Write.overwriteFile("test.txt", "Stuff and things.");
        Write.overwriteFile("test.txt", "Stuff and things again.");
        assertEquals(Read.readSingleLineFile("test.txt"), "Stuff and things again.");
    }*/

    /**
     * Writes object to file, which can be read back into memory.
     *
     * @author Brandon Watkins
     */
    /*@Test
    void writeObjectToFileTest1() {
        Task task = new Task("Stuff", "Things");
        Write.writeObjectToFile(task, "./userData/test");
        assertEquals(task, Read.readObjectFromFile(Task.class, "./userData/test"));
    }*/

    /**
     * Writes user to file, which can be read back into memory.
     *
     * @author Brandon Watkins
     */
    /*@Test
    void writeObjectToFileTest2() {
        UserAccount act = new UserAccount("Stuff@gmail.com", "password", "Tom", "Bombadil");
        Write.writeObjectToFile(act, "./userData/0");
        assertEquals(act, Read.readObjectFromFile(UserAccount.class, "./userData/0"));
    }*/

    /**
     * Writes user to file, with nested objects (TaskList), which can be read back into memory.
     *
     * @author Brandon Watkins
     */
    /*@Test
    void writeObjectToFileTest3() {
        UserAccount act = new UserAccount("Stuff@gmail.com", "password", "Tom", "Bombadil");
        act.addTaskList(new TaskList());
        act.getTaskLists().addTask(new Task("Do stuff"));
        Write.writeObjectToFile(act, "./userData/0");
        assertEquals(act, Read.readObjectFromFile(UserAccount.class, "./userData/0"));
    }*/

    /**
     * Writes user to file, with nested objects (TaskList), which can be read back into memory.
     *
     * @author Brandon Watkins
     */
    @Test
    void writeAccountDataTest() {
        UserAccount act = new UserAccount("Stuff@gmail.com", "password", "Tom", "Bombadil");
        UserAccount act2 = new UserAccount("Stuff2@gmail.com", "password2", "Tom2", "Bombadil2");
        act.addTaskList(new TaskList());
        act.getTaskLists().addTask(new Task("Do stuff"));
        Write.writeAccountData(act);
        assertEquals(act, Read.readUserData(act.getID()));
    }

}