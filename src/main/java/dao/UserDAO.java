package dao;

import model.User;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM user WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User(
                            rs.getString("username"),
                            rs.getString("passwordHash"),
                            rs.getString("userType")
                    );
                    user.setId(rs.getLong("id"));
                    return user;
                }
            }
        }
        return null;
    }
}
