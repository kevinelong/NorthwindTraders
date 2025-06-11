package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public ArrayList<HashMap<String, String>> getProductsFromCategory(String category, String[] fields) {
        ArrayList<HashMap<String, String>> list = db.searchData("SELECT ProductName FROM products WHERE CategoryID = ?", fields, category);
        return list;
    }

    public ArrayList<HashMap<String, String>> getBeverages(String[] fields) {
        return getProductsFromCategory("1", fields);
    }

    public ArrayList<HashMap<String, String>> getCondiments(String[] fields) {
        return getProductsFromCategory("2", fields);
    }

    public Integer addEmployee(Employee employee) {

        String[] fields = new String[]{"firstName", "lastName", "notes"};
        String[] values = new String[]{employee.firstName, employee.lastName, employee.notes};

        Integer rowsAffected = db.create("Employees", fields, values);
        System.out.println(rowsAffected);
        return rowsAffected;
    }

    public void close() {
        //cleanup
        db.close();
    }

    public void updateEmployee(HashMap<String, String> pairs, int id) {
        db.update("Employees", pairs, id, "EmployeeID");
    }

    public void deleteEmployee(int id){
        db.delete("Employees", id, "EmployeeID");
    }
}
