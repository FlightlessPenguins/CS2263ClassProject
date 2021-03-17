package edu.isu.cs.cs2263.TodoListManager;

public class NullAccount extends Account {

    public NullAccount() {
        super.setID(-1);
        super.setEmail("");
        super.setPassword("");
        super.setFirstName("");
        super.setLastName("");
    }

    @Override
    public Account setID(int id) {
        return this;
    }

    @Override
    public Account setEmail(String email) {
        return this;
    }

    @Override
    public Account setPassword(String password) {
        return this;
    }

    @Override
    public Account setFirstName(String firstName) {
        return this;
    }

    @Override
    public Account setLastName(String lastName) {
        return this;
    }

    @Override
    public Boolean verifyCredentials(String passwordAttempt) {
        return false;
    }
}
