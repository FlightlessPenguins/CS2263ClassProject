package edu.isu.cs.cs2263.todoListManager.model.state;

import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountLoginState;
import edu.isu.cs.cs2263.todoListManager.observer.Observable;

public class SystemState extends Observable implements State {

    private State state = AccountLoginState.instance();

    public State setState(State state) {
        this.state = state;
        setChanged();
        notifyObservers(this.state);
        return this;
    }

    public State getState() {
        if (state == null) state = AccountLoginState.instance();
        return state;
    }

    @Override
    public void setNextState(State state, Object args) {
        state.setNextState(state, args);
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
