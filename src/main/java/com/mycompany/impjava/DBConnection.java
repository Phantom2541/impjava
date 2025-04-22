package com.mycompany.impjava;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class DBConnection {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure you have the MySQL JDBC driver
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/implibrary", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed!");
            return null;
        }
    }    
}
