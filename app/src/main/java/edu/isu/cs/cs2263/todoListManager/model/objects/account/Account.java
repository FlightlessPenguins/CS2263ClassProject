/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.objects.account;

import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
import edu.isu.cs.cs2263.todoListManager.storage.Read;

import java.io.Serializable;

/**
 * Abstract Account class
 */
public abstract class Account implements Serializable {

    // I suspect we will need to change id from final, and we'll need to add a setter (Neither of these are in the class diagram)
    // in order to read in Accounts from file.

    /* Reserved IDs:
        0: NullAccount

     */

    protected int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    /**
     * Create a new account.
     *
     * @author Brandon Watkins
     */
    public Account() {}

    /**
     * Create a new account.
     *
     * @param email (String) The new user's email address.
     * @param password (String) The new user's password.
     * @param firstName (String) The new user's first name.
     * @param lastName (String) The new user's last name.
     *
     * @author Brandon Watkins
     */
    public Account(String email, String password, String firstName, String lastName) {
        this.email = email;
        setPassword(((AccountContext)AccountContext.instance()).generateHash(password));
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = Read.readNextID("account");
    }

    /**
     * Create a new account. Only used for reading a user in from a save file.
     *
     * @param email (String) The new user's email address.
     * @param password (String) The new user's password.
     * @param firstName (String) The new user's first name.
     * @param lastName (String) The new user's last name.
     * @param id (int) The user's ID.
     *
     * @author Brandon Watkins
     */
    public Account(int id, String email, String password, String firstName, String lastName) {
        this.email = email;
        setPassword(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    /**
     * Gets the user's ID number.
     *
     * @return (int) The user's ID number.
     *
     * @author Brandon Watkins
     */
    public int getID() {
        return id;
    }

    /**
     * Sets the user's ID number.
     *
     * @param id (int) The user's ID number.
     * @return (Account) The user's account.
     *
     * @author Brandon Watkins
     */
    public Account setID(int id) {
        this.id = id;
        return this;
    }

    /**
     * Gets the user's email address.
     *
     * @return (String) The user's email address.
     *
     * @author Brandon Watkins
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email (String) The user's email address.
     * @return (Account) The user's account.
     *
     * @author Brandon Watkins
     */
    public Account setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Gets the account's password.
     *
     * @return (String) Account's password
     *
     * @author Brandon Watkins
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the user's password.
     * <p>This should only be used when reading data in from a file, or with setPassword(AccountContext.instance().generateHash(password)).
     *
     * @param password (String) The user's password.
     * @return (Account) The user's account.
     *
     * @author Brandon Watkins
     */
    public Account setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Gets the user's first name.
     *
     * @return (String) The user's first name.
     *
     * @author Brandon Watkins
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the user's first name.
     *
     * @param firstName (String) The user's first name.
     * @return (Account) The user's account.
     *
     * @author Brandon Watkins
     */
    public Account setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Gets the user's last name.
     *
     * @return (String) The user's last name.
     *
     * @author Brandon Watkins
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the user's last name.
     *
     * @param lastName (String) The user's last name.
     * @return (Account) The user's account.
     *
     * @author Brandon Watkins
     */
    public Account setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Determines whether another object is equal to this object.
     *
     * @param o (Object) The object being compared to this object.
     * @return (boolean) True if the objects are deemed equal.
     * 
     * @author Brandon Watkins
     */
    public boolean equals(Object o) {
        if (o instanceof Account && ((Account)o).getID() >= 0 && this.id == ((Account)o).getID()) return true;
        return false;
    }

}
