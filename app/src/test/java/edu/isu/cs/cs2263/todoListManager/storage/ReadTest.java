package edu.isu.cs.cs2263.todoListManager.storage;

import edu.isu.cs.cs2263.todoListManager.model.objects.account.AdminAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.UserAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;
import edu.isu.cs.cs2263.todoListManager.model.objects.taskList.TaskList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ReadTest {

    /**
     * NOTE: These will royally screw up the data if run after legit users have been created.
     *
     * @author Brandon Watkins
     */
    @BeforeEach
    void setUp() {
        // delete userData folder (and its contents)
        Write.deleteFolder(Paths.get("").toAbsolutePath().normalize().toString() + "/app/userData/");
        // delete counter folder (and its contents)
        Write.deleteFolder(Paths.get("").toAbsolutePath().normalize().toString() + "/app/counters/");
        File file = new File("./app");
        file.delete();
    }

    /**
     * NOTE: These will royally screw up the data if run after legit users have been created.
     *
     * @author Brandon Watkins
     */
    @AfterEach
    void tearDown() {
        // delete userData folder (and its contents)
        Write.deleteFolder(Paths.get("").toAbsolutePath().normalize().toString() + "/app/userData/");
        // delete counter folder (and its contents)
        Write.deleteFolder(Paths.get("").toAbsolutePath().normalize().toString() + "/app/counters/");
        File file = new File("./app");
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