/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.observer;

public interface Observer {

    /**
     * This method is called whenever the observed object is changed.
     *
     * @param o (Observable) The observable object.
     * @param arg (Object) The argument passed to the notifyObservers method, can be null.
     *
     * @author Brandon Watkins
     */
    public void update(Observable o, Object arg);

}
