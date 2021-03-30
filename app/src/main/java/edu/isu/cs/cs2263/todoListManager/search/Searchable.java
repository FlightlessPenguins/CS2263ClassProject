/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.search;

import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;

import java.util.List;

public interface Searchable {

    /**
     * Searches via the SearchVisitor, returning a List of Tasks matching the search term.
     *
     * @param v (SearchVisitor) The visitor used to search with.
     * @return (List<Task>) List of Tasks matching the visitor's search criteria.
     *
     * @author Brandon Watkins
     */
    public List<Task> accept(SearchVisitor v);

}
