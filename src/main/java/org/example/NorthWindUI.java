package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
//VIEW
public class NorthWindUI {

    public void printList(ArrayList<HashMap<String,String>> list, String field) {
        list.forEach(item -> System.out.println(item.get(field)));
    }

}
