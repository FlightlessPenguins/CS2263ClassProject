module todoListManager.app.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.common;
    requires jdk.jshell;
    requires com.google.gson;
    exports edu.isu.cs.cs2263.todoListManager.view;
    opens edu.isu.cs.cs2263.todoListManager.model.objects.account;
    opens edu.isu.cs.cs2263.todoListManager.model.objects.section;
    opens edu.isu.cs.cs2263.todoListManager.model.objects.taskList;
    opens edu.isu.cs.cs2263.todoListManager.model.objects.task;
    opens edu.isu.cs.cs2263.todoListManager.controller;
}