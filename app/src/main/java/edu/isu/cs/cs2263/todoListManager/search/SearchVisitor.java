package edu.isu.cs.cs2263.todoListManager.search;

import edu.isu.cs.cs2263.todoListManager.model.objects.section.Section;
import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;
import edu.isu.cs.cs2263.todoListManager.model.objects.taskList.TaskList;

import java.util.List;

public interface SearchVisitor {

    public List<Task> visit(TaskList taskList);
    public List<Task> visit(Section section);
    public List<Task> visit(Task task);

}
