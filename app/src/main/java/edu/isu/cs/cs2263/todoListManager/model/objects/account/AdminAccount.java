/**
 * @author ?
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.objects.account;

import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountListState;
import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AdminAccount extends Account {

    public AdminAccount() {
        super();
    }

    private Account resetPassword(Account user, String password) {
        user.setPassword(password);
        return user;
    }

    private List<Account> getUsers() {

        List<Account> users = new ArrayList<>();
        Iterator<Account> iterator = ((AccountListState)(AccountListState.instance())).getUsers().iterator();
        while (iterator.hasNext()) {
            users.add(iterator.next());
        }
        return users;
    }
}
