package controller;

import dao.UserDAO;
import model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class LoginController {

    private UserDAO userDAO;

    public LoginController() {
        this.userDAO = new UserDAO();
    }

    public boolean login(String username, String password) {
        try {
            User user = userDAO.findByUsername(username);
            if (user != null && BCrypt.checkpw(password, user.getPasswordHash())) {
                return true; // Successful login
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Failed login
    }
}
