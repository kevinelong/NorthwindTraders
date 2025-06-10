package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NorthWindDataManager {
    private final CRUD db;

    NorthWindDataManager() {
        db = new CRUD("localhost", "3306", "northwind", "root", "S!mpl312");
    }

    public void printEmployees() {
        ResultSet rs = db.read("""
                SELECT EmployeeID, LastName, FirstName
                FROM Employees
                ORDER BY LastName, FirstName""");
        if (null == rs) {
            System.out.println("NO RESULTS");
            return;
        }
        db.printAll(rs, new String[]{"EmployeeID", "LastName", "FirstName"});
        try {
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void printProducts() {
        ResultSet rs2 = db.read("SELECT ProductName FROM products");
        if (null == rs2) {
            System.out.println("NO RESULTS");
            return;
        }
        db.printAll(rs2, new String[]{"ProductName"});
        try {
            rs2.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        //cleanup
        db.close();
    }
}
