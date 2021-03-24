package edu.isu.cs.cs2263.TodoListManager;

public class UserList {
    //Instance Variables
    List<UserAccount> users;

    //Constructor
    private UserList(){}

    //Methods
    public List<UserAccount> getUsers() {
        return users;
    }

    public UserList addUser(){
        return this;
    }

    public UserAccount removeUser(int userID){
        return userAccount;
    }
    public Iterator<UserAccount> iterator(){

    }
}
