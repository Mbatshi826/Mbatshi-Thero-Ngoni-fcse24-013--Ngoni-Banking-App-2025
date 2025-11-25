package dao;

import model.Customer;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public Customer create(Customer c) throws SQLException {
        String sql = "INSERT INTO customer(firstName,lastName,address) VALUES(?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getAddress());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getLong(1));
            }
            return c;
        }
    }

    public Customer findById(long id) throws SQLException {
        String sql = "SELECT * FROM customer WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return new Customer(rs.getString("firstName"), rs.getString("lastName"), rs.getString("address")) {{
                        setId(rs.getLong("id"));
                    }};
            }
            return null;
        }
    }

    public List<Customer> findAll() throws SQLException {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Customer c = new Customer(rs.getString("firstName"), rs.getString("lastName"), rs.getString("address"));
                c.setId(rs.getLong("id"));
                list.add(c);
            }
        }
        return list;
    }

    public boolean update(Customer c) throws SQLException {
        String sql = "UPDATE customer SET firstName=?, lastName=?, address=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getAddress());
            ps.setLong(4, c.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(long id) throws SQLException {
        String sql = "DELETE FROM customer WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
