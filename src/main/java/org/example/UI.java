package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UI {
    public void printList(ArrayList<HashMap<String,String>> list, String field) {
        list.forEach(item -> System.out.println(item.get(field)));
    }
    String getString(String question){
        var in = new Scanner(System.in);
        var answer = "";
        while(answer == "") {
            System.out.printf("%s: ", question);
            answer = in.nextLine();
        }
        return answer;
    }
}
