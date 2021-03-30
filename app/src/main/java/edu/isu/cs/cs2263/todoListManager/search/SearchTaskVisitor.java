package edu.isu.cs.cs2263.todoListManager.search;

import edu.isu.cs.cs2263.todoListManager.model.objects.section.Section;

public class SearchTaskVisitor implements SearchVisitor {

    private String searchTerm;
    private List<Task> searchResults;

    public SearchTaskVisitor() {}

    @Override
    public List<Task> visit(TaskList taskList) {
        return taskList.accept();
    }

    @Override
    public List<Task> visit(Section section) {
        return section.accept();
    }

    @Override
    public List<Task> visit(Task task) {
        return task.accept();
    }

}
