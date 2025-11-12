package model;

public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;

    public Customer(String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getAddress() { return address; }
    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + address + ")";
    }
}
