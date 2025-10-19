package Util;

import Dao.Database;

import java.sql.Connection;

public class DBTestConnection {
    public static void main(String[] args) {
        try (Connection conn = Database.getConnection()) {
            System.out.println(" Connected successfully: " + conn);
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}
