package org.example;

public class EmployeeDAO extends DAO {

    EmployeeDAO(Employee employee, CRUD db) {
        this.tableName = "employees";
        this.db = db;
        this.instance = employee;
        this.fields = new DAOField[]{
                new DAOField("EmployeeID", "ID", false),
                new DAOField("firstName", "First Name", true),
                new DAOField("lastName", "Last Name", true),
                new DAOField("notes", "Notes", true),
                new DAOField("title", "Title", false)
        };
    }

    public void save() {
        //if ID then UPDATE else INSERT
//        if( ((Employee) instance).id == null){
//            db.create(this.tableName);
//        }else{
//            db.update(this.tableName);
//        }
    }
}
