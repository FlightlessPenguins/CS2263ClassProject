/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.objects.account;

public class NullAccount extends Account {

    /**
     * Creates a NullAccount
     *
     * @author Brandon Watkins
     */
    private NullAccount() {
        super.setID(0);
        super.setEmail("");
        //super.setPassword("");
        super.setFirstName("");
        super.setLastName("");
        throw new RuntimeException("NullAccount(password) not implemented yet.");
    }

    /*
        These overrides are just keeping anyone from modifying any of the values.
     */

    /**
     * Keeps from setting the user's ID
     *
     * @param id (int) The user's ID number.
     * @return (Account) The user's account.
     *
     * @author Brandon Watkins
     */
    @Override
    public Account setID(int id) {
        return this;
    }

    /**
     * Keeps from setting the user's email address.
     *
     * @param email (String) The user's email address.
     * @return (Account) The user's account.
     *
     * @author Brandon Watkins
     */
    @Override
    public Account setEmail(String email) {
        return this;
    }

    /**
     * Keeps from setting the user's password.
     *
     * @param password (String) The user's password.
     * @return (Account) The user's account.
     *
     * @author Brandon Watkins
     */
    @Override
    public Account setPassword(String password) {
        return this;
    }

    /**
     * Keeps from setting the user's first name.
     *
     * @param firstName (String) The user's first name.
     * @return (Account) The user's account.
     *
     * @author Brandon Watkins
     */
    @Override
    public Account setFirstName(String firstName) {
        return this;
    }

    /**
     * Keeps from setting the user's last name.
     *
     * @param lastName (String) The user's last name.
     * @return (Account) The user's account.
     *
     * @author Brandon Watkins
     */
    @Override
    public Account setLastName(String lastName) {
        return this;
    }

    /**
     * Gets this singleton's instance.
     *
     * @return This singleton's instance (concrete Context).
     *
     * @author Brandon Watkins
     */
    public static Object instance() {
        return null;
    }
}
