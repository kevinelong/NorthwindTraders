package org.example;

public class Employee {
    Integer id = null;
    String firstName;
    String lastName;
    String notes;
    Employee(String firstName, String lastName, String notes){
        this.firstName = firstName;
        this.lastName = lastName;
        this.notes = notes;
    }
}
