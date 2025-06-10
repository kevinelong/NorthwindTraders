package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
//MODEL
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

    public ArrayList<HashMap<String,String>> getProductsFromCategory(String category, String[] fields) {
        ArrayList<HashMap<String,String>> list = db.searchData("SELECT ProductName FROM products WHERE CategoryID = ?", fields, category);
        return list;
    }
    public ArrayList<HashMap<String,String>> getBeverages(String[] fields) {
        return getProductsFromCategory("1", fields);
    }
    public ArrayList<HashMap<String,String>> getCondiments(String[] fields) {
        return getProductsFromCategory("2", fields);
    }

    public void close() {
        //cleanup
        db.close();
    }
}
