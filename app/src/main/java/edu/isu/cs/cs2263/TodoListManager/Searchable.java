package edu.isu.cs.cs2263.TodoListManager;

public interface Searchable {

    public List<Task> accept(SearchVisitor v);

}
