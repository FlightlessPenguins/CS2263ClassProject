/**
 * @author Brandon Watkins
 * 3/31/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.objects.taskList;

import edu.isu.cs.cs2263.todoListManager.model.objects.section.Section;
import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class TaskListIterator implements Iterator<Task> {

    private List<Task> tasks;
    private int counter = 0;

    /**
     * Creates a TaskListIterator.
     *
     * @param taskList (TaskList) The TaskList of Tasks to iterate over.
     *
     * @author Brandon Watkins
     */
    public TaskListIterator(TaskList taskList) {
        this.tasks = new ArrayList();
        addTaskListToTasks(taskList);
    }

    /**
     * Adds a TaskList's Tasks to tasks, to iterate over.
     *
     * @param taskList (TaskList) The TaskList containing the Tasks to iterate through.
     *
     * @author Brandon Watkins
     */
    private void addTaskListToTasks(TaskList taskList) {
        addSectionsToTasks(taskList);
        List<TaskList> tl = taskList.getSubTaskLists();
        if (tl == null) return;
        for (TaskList list : taskList.getSubTaskLists()) {
            addSectionsToTasks(list);
        }
    }

    /**
     * Adds a Section's Tasks to tasks, to iterate over.
     *
     * @param taskList (TaskList) The TaskList whose Sections' Tasks are to be iterated over.
     *
     * @author Brandon Watkins
     */
    private void addSectionsToTasks(TaskList taskList) {
        for (Section section : taskList.getSections()) {
            addTasksToTasks(section);
        }
    }

    /**
     * Adds a Section's Tasks to tasks, to iterate over.
     *
     * @param section (Section) The Section whose Tasks are to be iterated over.
     *
     * @author Brandon Watkins
     */
    private void addTasksToTasks(Section section) {
        for (Task task : section.getTasks()) {
            tasks.add(task);
            List<Task> tasksInner = task.getSubtasks();
            if (tasksInner == null) break;
            for (Task t : tasksInner) {
                tasks.add(t);
            }
        }
    }

    /**
     * Resets the counter, allowing you to reiterate over the elements, without the expensive computations rebuilding the List.
     *
     * @author Brandon Watkins
     */
    public void reset() {
        counter = 0;
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return tasks.size() > 0 && tasks.size() - counter > 0;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public Task next() {
        if (!hasNext()) throw new NoSuchElementException("No more elements");
        return tasks.get(counter++);
    }
}
