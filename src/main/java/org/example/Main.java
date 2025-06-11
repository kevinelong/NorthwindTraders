package org.example;

import java.util.Arrays;
import java.util.stream.Collectors;

//CONTROLLER
public class Main {
    public static void main(String[] args) {
        var data = new NorthWindDataManager(); //MODELS
        var ui = new NorthWindUI();//VIEWS

        //CONTROL - controlling the action
        ui.printList(data.getCondiments(new String[]{"ProductName"}), "ProductName");
        //data.addProduct(new Product("Soda", "Tasty Beverage", 1.00));

        System.out.println("CREATING NEW EMPLOYEE");
        var rowsAffected = data.addEmployee(new Employee(
                ui.getString("FIRST NAME"),
                ui.getString("LAST NAME"),
                ui.getString("NOTES")
        ));

        data.close();




        //TODO RUN AND GET ID
    }
}