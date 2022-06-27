package controller;


import datastorage.DAOFactory;
import datastorage.UserDAO;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.User;
import utils.EncryptAndDecrypt;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LoginController {

    public TextField txtUsername;
    public PasswordField txtPassword;
    public Button btnLogin;

    //String username = this.txtUsername.getText();
    //String password = this.txtPassword.getText();

    public void userLogin() throws SQLException, IOException {
        if (this.txtUsername.getText() != null && this.txtPassword.getText() != null) {
            isUserValid();
        }
    }
    public void isUserValid() throws SQLException, IOException {
        EncryptAndDecrypt encryptAndDecrypt = new EncryptAndDecrypt();
        UserDAO dao = DAOFactory.getDAOFactory().createUserDAO();
        List<User> allUser = dao.readAll();
        Main main = new Main();

        //String pwhash = encryptAndDecrypt.hash(this.txtPassword.getText().toCharArray(), "bla".getBytes()).toString();
        for (User user : allUser) {
            if(this.txtUsername.getText().equals(user.getUsername()) && encryptAndDecrypt.hash(this.txtPassword.getText().toCharArray(), "bla".getBytes()).equals(user.getPassword())) {
                byte[] test = encryptAndDecrypt.hash(user.getPassword().toCharArray(), "bla".getBytes());
                System.out.println(test);
                main.changeScene("/MainWindowView.fxml");
            }
        }
    }

    public void txtKey(KeyEvent keyEvent) {
        //test
    }
}