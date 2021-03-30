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

    public AdminAccount() {}

    private UserAccount resetPassword(UserAccount user, String password) throws ExecutionControl.NotImplementedException {
        user.setPassword(password);
        return user;
    }

    private List<UserAccount> getUsers() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("getUsers() not implemented, yet. We need to put this logic inside " +
                "the AccountListState, which needs to check account instanceof adminaccount.");
        /*
        List<UserAccount> users = new ArrayList<>();
        Iterator<UserAccount> iterator = AccountListState.iterator();
        while (iterator.hasNext()) {
            users.add(iterator.next());
        }
        return users;
        */
    }
}
