/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.state.section;

import edu.isu.cs.cs2263.todoListManager.model.context.Context;
import edu.isu.cs.cs2263.todoListManager.model.objects.section.Section;
import edu.isu.cs.cs2263.todoListManager.model.state.State;

public class SectionCreateState implements State {

    Section state;

    public void setState(Section state) {
        this.state = state;
    }

    public Section getState() {
        return state;
    }

    /**
     * Performs all necessary tasks before changing state.
     * <p>Make sure to call the context's changeState(this) by the end of run().
     *
     * @author Brandon Watkins
     */
    @Override
    public void run() {
        throw new RuntimeException("not implemented yet.");
    }

    /**
     * Helper class for singleton implementation
     *
     * @author Brandon Watkins
     */
    private static final class Helper {
        private static final State INSTANCE = new SectionCreateState();
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
