/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.objects.task;

import edu.isu.cs.cs2263.todoListManager.model.context.Context;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.search.SearchVisitor;
import edu.isu.cs.cs2263.todoListManager.search.Searchable;

import java.util.List;

public class Task implements Searchable {

    /**
     * Searches via the SearchVisitor, returning a List of Tasks matching the search term.
     *
     * @param v (SearchVisitor) The visitor used to search with.
     * @return (List < Task >) List of Tasks matching the visitor's search criteria.
     */
    @Override
    public List<Task> accept(SearchVisitor v) {
        throw new RuntimeException("accept not implemented, yet.");
    }
}
