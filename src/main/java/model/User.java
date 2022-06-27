package model;

/**
 * The User has accsess to the programm
 */
public class User extends Person {
    private long uid;
    private String username;
    private String password;

    /**
     * constructs a user from the given params.
     * @param firstName
     * @param surname
     * @param username
     * @param password
     */
    public User(String firstName, String surname, String username, String password) {
        super(firstName, surname);
        this.username = username;
        this.password = password;
    }

    /**
     * constructs a user from the given params.
     * @param firstName
     * @param surname
     * @param username
     * @param password
     */
    public User(long uid, String firstName, String surname, String username, String password) {
        super(firstName, surname);
        this.uid = uid;
        this.username = username;
        this.password = password;
    }


    public long getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
