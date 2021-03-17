package edu.isu.cs.cs2263.TodoListManager;

import java.util.ArrayList;

public class AdminAccount extends Account {

    public AdminAccount() {}

    private UserAccount resetPassword(UserAccount user, String password) {
        user.setPassword(password);
        return user;
    }

    private List<UserAccount> getUsers() {
        List<UserAccount> users = new ArrayList<>();
        Iterator<UserAccount> iterator = UserList.iterator();
        while (iterator.hasNext()) {
            users.add(iterator.next());
        }
        return users;
    }
}
