package edu.isu.cs.cs2263.todoListManager.search;

import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
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

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class SearchTaskVisitorTest {

    private UserAccount user;
    private AccountListState als = (AccountListState)AccountListState.instance();
    private SearchTaskVisitor v = new SearchTaskVisitor("1");
    private Task task1;

    @BeforeEach
    void setUp() {
        user = new UserAccount("I was here.", null, "test@gmail.com", "password", "Brandon", "Watkins");
        (((AccountListState) AccountListState.instance())).addAccount(user);
        TaskList taskList2 = new TaskList("TaskList 2", "Second tasklist", "When the default tasklist just isn't good enough.", null, null, false);
        user.addTaskList(taskList2);
        TaskList subTaskList = new TaskList("Sub-TaskList", "Sub-tasklist.", "Some comment.", null, null, false);
        taskList2.addSubTaskList(subTaskList);
        Section section1 = new Section("Section 1", "Additional section.");
        taskList2.addSection(section1);
        task1 = new Task("Task 1", "task 1 description", null, null, null, null);
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


        Write.writeAccountData(user);
        Write.writeAllAccountsToFile();
        als.setAccounts(Read.readAllUserData());
        ((AccountContext) AccountContext.instance()).setCurrentAccount(user);
    }

    @AfterEach
    void tearDown() {
        Write.deleteFolder("app");
    }

    @Test
    void searchTerm() {
        v.setSearchTerm("2");
        assertEquals(v.getSearchTerm(), "2");
    }

    @Test
    void getSearchResults() {
        TaskList list = user.getTaskLists().search("1");
        assertTrue(list.iterator().hasNext() && list.iterator().next() == task1);
    }

    @Test
    void visit() {
    }

    @Test
    void testVisit() {
    }

    @Test
    void testVisit1() {
    }
}