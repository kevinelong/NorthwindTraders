package org.example;

//CONTROLLER
public class Main {
    public static void main(String[] args) {
        var data = new NorthWindDataManager(); //MODELS
        var ui = new NorthWindUI();//VIEWS

        //CONTROL - controlling the action
        ui.printList(data.getCondiments(new String[]{"ProductName"}), "ProductName");

        data.close();
    }
}