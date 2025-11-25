package controller;

import dao.UserDAO;
import model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class LoginController {
    private UserDAO userDAO = new UserDAO();

    public boolean authenticate(String username, String password) {
        try {
            User user = userDAO.findByUsername(username);
            if (user != null) {
                return BCrypt.checkpw(password, user.getPasswordHash());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
