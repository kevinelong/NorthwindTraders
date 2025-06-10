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

        data.close();


        //input
        String[] fields = {"firstName", "lastName", "notes"};
        String[] values = {"Kevin", "Long", "Geek at large."};
        String tableName = "Employees";

        //get insert sql?
//        "INSERT INTO table_name (column1, column2, column3, ...)\n" +
//                "VALUES (value1, value2, value3, ...);";
        //delimited by commas
        String df = Arrays.stream(fields).map(s -> String.format("`%s`",s)).collect(Collectors.joining(","));
        String dv = Arrays.stream(values).map(s -> String.format("'%s'",s)).collect(Collectors.joining(","));

        String sqlInsert = String.format("INSERT INTO %s (%s) VALUES (%s);", tableName, df, dv);
        System.out.println(sqlInsert);

        //TODO RUN AND GET ID
    }
}