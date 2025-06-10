package org.example;

public class Main {
    public static void main(String[] args) {
        var nw = new NorthWindDataManager();
        nw.printEmployees();
        nw.printProducts();
        nw.close();
    }
}