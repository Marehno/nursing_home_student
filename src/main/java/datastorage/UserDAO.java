package datastorage;

import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO extends DAOimp<User>{

    public UserDAO(Connection connection) {
        super(connection);
    }

    /**
     * Insert the values in the User with an SQL-Statement
     * @param user
     * @return the values from the class User
     */
    @Override
    protected String getCreateStatementString(User user) {
        return String.format("Insert into uid, username, password Values(%s, %s, %s)", user.getUid(), user.getUsername(), user.getPassword());
    }

    /**
     * The method selects all Users
     * @param userKey
     * @return all Users from the class User
     */
    @Override
    protected String getReadByIDStatementString(long userKey) {
        return String.format("Select * from User where uid = %d", userKey);
    }

    @Override
    protected User getInstanceFromResultSet(ResultSet set) throws SQLException {
        return new User(set.getString(1), set.getString(2), set.getString(3), set.getString(4));
    }

    @Override
    protected String getReadAllStatementString() {
        return "Select * from User";
    }

    @Override
    protected ArrayList<User> getListFromResultSet(ResultSet userSet) throws SQLException {
        ArrayList<User> userList = new ArrayList<>();
        User newUser;
        while (userSet.next()) {
            newUser = new User(userSet.getInt(1), userSet.getString(2), userSet.getString(3), userSet.getString(4), userSet.getString(5));
            userList.add(newUser);
        }
        return userList;
    }

    @Override
    protected String getUpdateStatementString(User user) {
        return String.format("Update User set (firstname = '%s', surname = '%s', username = '%s', password = '%s') where uid = %d", user.getFirstName(), user.getSurname(), user.getUsername(), user.getPassword());
    }

    @Override
    protected String getDeleteStatementString(long key) {
        return String.format("Delete from User where uid = '%d'", key);
    }
}
