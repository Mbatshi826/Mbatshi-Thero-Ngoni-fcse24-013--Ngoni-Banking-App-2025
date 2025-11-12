package controller;

import dao.CustomerDAO;
import model.Customer;

import java.sql.SQLException;

public class LoginController {
    // For assignment demo we keep authentication simple (no hashed passwords)
    public boolean authenticate(String username, String password) {
        // Example: check static admin credential OR you could query DB for staff
        return "admin".equalsIgnoreCase(username) && "1234".equals(password);
    }

    // Example: allow creating staff/customer accounts via controller if needed
    public Customer createCustomer(String firstName, String lastName, String address) throws SQLException {
        Customer c = new Customer(firstName, lastName, address);
        CustomerDAO dao = new CustomerDAO();
        return dao.create(c);
    }
}
