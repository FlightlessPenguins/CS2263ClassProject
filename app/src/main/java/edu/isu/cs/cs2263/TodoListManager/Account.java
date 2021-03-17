package edu.isu.cs.cs2263.TodoListManager;

public abstract class Account {

    // I suspect we will need to change id from final, and we'll need to add a setter (Neither of these are in the class diagram)
    // in order to read in Accounts from file.
    protected int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private final String INFO_FILEPATH = "./config/users.json";
    private final String PHOTO_FILEPATH = "./photos/" + Integer.toString(id) + ".png";

    public Account() {}

    public Account(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = getNextID();
    }

    public Account(int id, String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    public String getInfoFilepath() {
        return INFO_FILEPATH;
    }

    public String getPhotoFilepath() {
        return PHOTO_FILEPATH;
    }

    public int getID() {
        return id;
    }

    public Account setID(int id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Account setEmail(String email) {
        this.email = email;
        return this;
    }

    public Account setPassword(String password) {
        // this.password = Password.Hash(password); // or something.
        // return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Account setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Account setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    private int getNextID() {
        // Read in ID counter from file
        // increment counter
        // write new counter to file
        // return counter that was read from file.
    }

    public Boolean verifyCredentials(String passwordAttempt) {
        // If Password.Hash(passwordAttempt) == password return true; // or something.
        // else return false;
    }

    */
}
