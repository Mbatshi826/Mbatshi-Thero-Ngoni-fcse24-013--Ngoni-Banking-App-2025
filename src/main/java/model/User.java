package model;

public class User {
    private long id;
    private String username;
    private String passwordHash;
    private String userType;

    public User(String username, String passwordHash, String userType) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.userType = userType;
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
