package edu.isu.cs.cs2263.todoListManager.observer;

import java.util.ArrayList;
import java.util.List;

public class Observable {

    private List<Observer> observers = new ArrayList<>();
    private Boolean changed = false;

    /**
     * Adds an observer to the set of observers for this object, provided that it is not the same as some observer already in the set.
     * @param o (Observer) The observer.
     */
    public void addObserver(Observer o) throws NullPointerException {
        if (o == null) throw new NullPointerException("Observable.addObserver received null Observer o parameter.");
        else if(!observers.contains(o)) observers.add(o);
    }

    /**
     * Indicates that this object has no longer changed, or that it has already notified all of its observers of its most recent change,
     * so that the hasChanged method will now return false.
     */
    protected void clearChanged() {
        changed = false;
    }

    /**
     * Returns the number of observers of this Observable object.
     * @return (int) The number of observers of this Observable object.
     */
    public int countObservers() {
        return observers.size();
    }

    /**
     * Deletes an observer from the set of observers of this object.
     * @param o (Observer) The observer to delete.
     */
    public void deleteObserver(Observer o) {
        if (observers.contains(o)) observers.remove(o);
    }

    /**
     * Clears the observer list so that this object no longer has any observers.
     */
    public void deleteObservers() {
        observers.clear();
    }

    /**
     * Tests if this object has changed.
     * @return (Boolean) True if this object has changed, ie., if setChanged has been called since the last clearChanged.
     */
    public Boolean hasChanged() {
        return changed;
    }

    /**
     * If this object has changed, as indicated by the hasChanged method,
     * then notify all of its observers and then call the clearChanged method to indicate that this object has no longer changed.
     * <p>Each observer has its update method called with two arguments: this observable object and null. In other words,
     * this method is equivalent to: notifyObservers(null)
     */
    public void notifyObservers() {
        notifyObservers(null);
    }

    /**
     * If this object has changed, as indicated by the hasChanged method,
     * then notify all of its observers and then call the clearChanged method to indicate that this object has no longer changed.
     * <p>Each observer has its update method called with two arguments: this observable object and the arg argument.
     * @param arg (Object) The specific value to check?
     */
    public void notifyObservers(Object arg) {
        for (Observer o : observers) {
            o.update(this, arg);
        }
        clearChanged();
    }

    /**
     * Marks this Observable object as having been changed; the hasChanged method will now return true.
     */
    protected void setChanged() {
        changed = true;
    }

}
