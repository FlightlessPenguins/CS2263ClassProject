package edu.isu.cs.cs2263.todoListManager.model.state;

import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.UserAccount;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountLoginState;
import edu.isu.cs.cs2263.todoListManager.model.state.taskList.TaskListUpdateState;
import edu.isu.cs.cs2263.todoListManager.observer.Observable;

public class SystemState extends Observable implements State {

    /*public enum SystemStateEnum {
        LoginForm,
        //Register,
        RegisterForm,
        Profile,
        ProfileForm,
        AccountList,
        Home,
        TaskList,
        TaskListForm,
        Section,
        SectionForm,
        Task,
        TaskForm

    }

    private SystemStateEnum state = SystemStateEnum.LoginForm;

    public SystemState setState(SystemStateEnum state) {
        this.state = state;
        return this;
    }

    public SystemStateEnum getState() {
        return state;
    }*/

    private State state = AccountLoginState.instance();
    private State previousState = null;

    public State setState(State state) {
        previousState = this.state;
        this.state = state;
        setChanged();
        notifyObservers(this.state);
        return this;
    }

    public State getState() {
        if (state == null) state = AccountLoginState.instance();
        return state;
    }

    public State getPreviousState() {
        state = previousState;
        previousState = null;
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
