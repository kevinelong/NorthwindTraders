package org.example;

public class DAOField {
    String name;
    String displayName;
    boolean required; //nullable

    DAOField(String name){
        this.name = name;
        this.displayName = name;
        this.required = true;
    }

    DAOField(String name, String displayName, boolean required){
        this.name = name;
        this.displayName = displayName;
        this.required = required;
    }
}
