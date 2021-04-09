package edu.isu.cs.cs2263.todoListManager.storage;

import edu.isu.cs.cs2263.todoListManager.model.objects.account.AdminAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.UserAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;
import edu.isu.cs.cs2263.todoListManager.model.objects.taskList.TaskList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ReadTest {

    /**
     * NOTE: These will royally screw up the data if run after legit users have been created.
     *
     * @author Brandon Watkins
     */
    @BeforeEach
    void setUp() {
        File file = new File("./counters/account.txt");
        file.delete();
        file = new File("./counters/account.txt");
        file.delete();
        file = new File("./counters/section.txt");
        file.delete();
        file = new File("./counters/task.txt");
        file.delete();
        file = new File("./counters/taskList.txt");
        file.delete();
        file = new File("./userData/0.json");
        file.delete();
        file = new File("./userData/1.json");
        file.delete();
        file = new File("./userData/2.json");
        file.delete();
    }

    /**
     * NOTE: These will royally screw up the data if run after legit users have been created.
     *
     * @author Brandon Watkins
     */
    @AfterEach
    void tearDown() {
        File file = new File("./counters/account.txt");
        file.delete();
        file = new File("./counters/account.txt");
        file.delete();
        file = new File("./counters/section.txt");
        file.delete();
        file = new File("./counters/task.txt");
        file.delete();
        file = new File("./counters/taskList.txt");
        file.delete();
        file = new File("./userData/0.json");
        file.delete();
        file = new File("./userData/1.json");
        file.delete();
        file = new File("./userData/2.json");
        file.delete();
    }

    @Test
    void getNextIDTest1() {
        assertEquals(0, Read.getNextID(new UserAccount()));
    }

    @Test
    void getNextIDTest2() {
        Read.getNextID(new UserAccount());
        Read.getNextID(new AdminAccount());
        assertEquals(2, Read.getNextID(new UserAccount()));
    }

    /**
     * Writes object to file, which can be read back into memory.
     */
    /*@Test
    void readObjectFromFileTest() {
        Task task = new Task("Stuff", "Things");
        Write.writeObjectToFile(task, "./userData/test");
        assertEquals(task, (Task)Read.readObjectFromFile(Task.class, "./userData/test"));
    }*/

    /**
     * Writes user to file, which can be read back into memory.
     *
     * @author Brandon Watkins
     */
    @Test
    void readUserDataTest1() {
        UserAccount act = new UserAccount("Stuff@gmail.com", "password", "Tom", "Bombadil");
        Write.writeAccountData(act);
        assertEquals(act, Read.readUserData(0));
    }

    /**
     * Writes user to file, with nested objects (TaskList), which can be read back into memory.
     *
     * @author Brandon Watkins
     */
    @Test
    void readUserDataTest2() {
        UserAccount act = new UserAccount("Stuff@gmail.com", "password", "Tom", "Bombadil");
        act.addTaskList(new TaskList());
        act.getTaskLists().addTask(new Task("Do stuff"));
        Write.writeAccountData(act);
        assertEquals(act, Read.readUserData(0));
    }

    @Test
    void readAllUserDataTest() {

    }
}