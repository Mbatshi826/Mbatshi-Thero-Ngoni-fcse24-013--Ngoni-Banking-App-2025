package controller;

import dao.CustomerDAO;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Customer;

import java.sql.SQLException;

public class CustomerController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField idNumberField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private CheckBox isEmployedCheckBox;
    @FXML
    private TextField employerNameField;
    @FXML
    private TextField employerAddressField;
    @FXML
    private Label messageLabel;

    private CustomerDAO customerDAO = new CustomerDAO();

    @FXML
    private void initialize() {
        // Hide employer fields by default
        employerNameField.setVisible(false);
        employerAddressField.setVisible(false);

        // Add a listener to the checkbox to show/hide the employer fields
        isEmployedCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            employerNameField.setVisible(newValue);
            employerAddressField.setVisible(newValue);
        });
    }

    @FXML
    private void registerCustomer() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String idNumber = idNumberField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        boolean isEmployed = isEmployedCheckBox.isSelected();
        String employerName = isEmployed ? employerNameField.getText() : null;
        String employerAddress = isEmployed ? employerAddressField.getText() : null;

        // Basic validation
        if (firstName.isEmpty() || lastName.isEmpty() || idNumber.isEmpty()) {
            messageLabel.setText("First Name, Last Name, and ID Number are required.");
            return;
        }

        Customer customer = new Customer(firstName, lastName, idNumber, address, phone, email, isEmployed, employerName, employerAddress);

        try {
            customerDAO.create(customer);
            messageLabel.setText("Customer registered successfully!");
        } catch (SQLException e) {
            messageLabel.setText("Error registering customer: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
