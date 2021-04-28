/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.state;

public interface State {

    /**
     * Performs all necessary tasks before changing state.
     * <p>Make sure to call the context's changeState(nextState) by the end of run().
     *
     * @author Brandon Watkins
     * @param state
     * @param args
     */
    public void setNextState(State state, Object args);

}
