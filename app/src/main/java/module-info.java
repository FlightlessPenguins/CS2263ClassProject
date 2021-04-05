module todoListManager.app.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.common;
    requires jdk.jshell;
    requires com.google.gson;
    exports edu.isu.cs.cs2263.todoListManager.view;
    opens edu.isu.cs.cs2263.todoListManager.controller;
}