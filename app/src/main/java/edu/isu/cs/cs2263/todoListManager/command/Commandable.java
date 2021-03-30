package edu.isu.cs.cs2263.todoListManager.command;

public interface Commandable {

    /**
     * Command to view the item's info.
     */
    public void info();

    /**
     * Command to view the item's 'update screen'.
     */
    public void update();

    /**
     * Command to view the item's 'create screen'.
     */
    public void create();

    /**
     * Command to view the item's list of internal items. Currently only applies to the list of users.
     */
    public void list();

    /**
     * Command to cancel the current operation.
     */
    public void cancel();

    /**
     * Command to exit the program.
     */
    public void exit();

}
