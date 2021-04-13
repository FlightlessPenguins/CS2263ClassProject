/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.objects.section;

import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class SectionIterator implements Iterator<Task> {

    private List<Task> tasks;
    private int counter = 0;

    public SectionIterator(Section section) {
        tasks = section.getTasks();
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
