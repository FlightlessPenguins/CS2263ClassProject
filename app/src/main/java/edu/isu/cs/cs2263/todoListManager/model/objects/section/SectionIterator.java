/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.objects.section;

import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SectionIterator implements Iterator<Task> {

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        throw new RuntimeException("not implemented yet.");
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public Task next() {
        throw new RuntimeException("not implemented yet.");
    }
}
