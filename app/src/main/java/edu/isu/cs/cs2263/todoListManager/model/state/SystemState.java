package edu.isu.cs.cs2263.todoListManager.model.state;

import edu.isu.cs.cs2263.todoListManager.model.state.taskList.TaskListUpdateState;

public class SystemState implements State {

    public enum SystemStateEnum {
        Login, Register, Profile, Home
    }

    private SystemStateEnum state = SystemStateEnum.Login;

    public SystemState setState(SystemStateEnum state) {
        this.state = state;
        return this;
    }

    public SystemStateEnum getState() {
        return state;
    }

    @Override
    public void run() {

    }

    /**
     * Helper class for singleton implementation
     *
     * @author Brandon Watkins
     */
    private static final class Helper {
        private static final State INSTANCE = new SystemState();
    }

    /**
     * Gets this singleton's instance.
     *
     * @return This singleton's instance (concrete Context).
     *
     * @author Brandon Watkins
     */
    public static State instance() {
        return Helper.INSTANCE;
    }
}