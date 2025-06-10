package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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
        var fields = new String[]{"ProductName"};
        ArrayList<HashMap> list = db.getData("SELECT ProductName FROM products", fields);
        list.forEach(item -> System.out.println(item.get("ProductName")));
    }

    public void close() {
        //cleanup
        db.close();
    }
}
