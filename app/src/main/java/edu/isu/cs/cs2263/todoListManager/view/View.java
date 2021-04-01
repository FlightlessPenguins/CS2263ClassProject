/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.view;

import edu.isu.cs.cs2263.todoListManager.controller.Controller;

public class View {



    /**
     * Helper class for singleton implementation
     *
     * @author Brandon Watkins
     */
    private static final class Helper {
        private static final View INSTANCE = new View();
    }

    /**
     * Gets this singleton's instance.
     *
     * @return This singleton's instance (concrete Context).
     *
     * @author Brandon Watkins
     */
    public static View instance() {
        return Helper.INSTANCE;
    }


    /**
     * closes app
     *
     * @author Grant Baird
     */
    public void close() {
        Controller.instance().close();
    }







}
