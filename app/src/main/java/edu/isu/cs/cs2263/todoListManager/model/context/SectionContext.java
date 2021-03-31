/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.context;

import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.model.state.section.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SectionContext implements Context {

    private final List<State> states = new ArrayList<State>(
            Arrays.asList(
                    SectionInfoState.instance(),
                    SectionUpdateState.instance(),
                    SectionCreateState.instance()
            )
    );

    /**
     * Changes the state from the current state to the state following the current state's transition.
     *
     * @param nextState The state that just finished its 'run()'.
     *
     * @author Brandon Watkins
     */
    @Override
    public void changeState(State nextState) {
        throw new RuntimeException("not implemented yet.");
    }

    /**
     * Helper class for singleton implementation
     *
     * @author Brandon Watkins
     */
    private static final class Helper {
        private static final Context INSTANCE = new SectionContext();
    }

    /**
     * Gets this singleton's instance.
     *
     * @return This singleton's instance (concrete Context).
     *
     * @author Brandon Watkins
     */
    public static Context instance() {
        return Helper.INSTANCE;
    }

}
