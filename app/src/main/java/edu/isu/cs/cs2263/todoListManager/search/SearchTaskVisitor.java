/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.search;

import edu.isu.cs.cs2263.todoListManager.model.objects.section.Section;
import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;
import edu.isu.cs.cs2263.todoListManager.model.objects.taskList.TaskList;

import java.util.ArrayList;
import java.util.List;

public class SearchTaskVisitor implements SearchVisitor {

    private String searchTerm = null;
    private List<Task> searchResults = new ArrayList();

    public SearchTaskVisitor() {}

    public SearchTaskVisitor(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public SearchTaskVisitor setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
        return this;
    }

    public List<Task> getSearchResults() {
        return searchResults;
    }

    public SearchTaskVisitor setSearchResults(List<Task> tasks) {
        this.searchResults = tasks;
        return this;
    }

    /**
     * Searches a taskList for searchTerm
     *
     * @param taskList (TaskList) The tasklist to search through.
     * @return (List<Task>) List of tasks matching searchTerm within the taskList.
     *
     * @author Brandon Watkins
     */
    public List<Task> visit(TaskList taskList, String searchTerm) {
        return taskList.accept(this);
    }

    /**
     * Searches a taskList for searchTerm
     *
     * @param section (Section) The section to search through.
     * @return (List<Task>) List of tasks matching searchTerm within the section.
     *
     * @author Brandon Watkins
     */
    public List<Task> visit(Section section, String searchTerm) {
        return section.accept(this);
    }

    /**
     * Searches a taskList for searchTerm
     *
     * @param task (Task) The task to search through.
     * @return (List<Task>) List of tasks matching searchTerm within the task.
     *
     * @author Brandon Watkins
     */
    public List<Task> visit(Task task, String searchTerm) {
        return task.accept(this);
    }

}
