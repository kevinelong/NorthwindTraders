package org.example;

public abstract class DAO {
    Object instance;
    DAOField[] fields;
    CRUD db;
    String tableName;

    public void save(){
        //if ID then UPDATE else INSERT
    }
}
