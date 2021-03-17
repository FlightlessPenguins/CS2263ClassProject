package edu.isu.cs.cs2263.TodoListManager;

public interface SearchVisitor {

    public List<Task> visit(TaskList taskList);
    public List<Task> visit(Section section);
    public List<Task> visit(Task task);

}
