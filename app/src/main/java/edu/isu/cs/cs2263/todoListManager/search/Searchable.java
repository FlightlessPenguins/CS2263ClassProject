package edu.isu.cs.cs2263.todoListManager.search;

public interface Searchable {

    public List<Task> accept(SearchVisitor v);

}
