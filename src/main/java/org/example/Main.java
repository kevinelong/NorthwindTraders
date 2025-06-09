package org.example;
//import java.sql.*;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
CREATE
READ - 1st one
UPDATE
DELETE
 */
//import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
class CRUD {
    protected static String host = "localhost";
    protected static String port = "3306";
    protected static String schema = "northwind";
    protected static String MYSQL_DB_URL = String.format("jdbc:mysql://%s:%s/%s", host, port, schema);

    //creds
    protected static String MYSQL_DB_USERNAME = "root";
    protected static String MYSQL_DB_PASSWORD = "S!mpl312";
    protected static MysqlDataSource mysqlDS = new MysqlDataSource();

    protected static Connection con = null;
    protected static Statement stmt = null;
    protected static ResultSet rs = null;

    CRUD(String host, String port, String schema, String username, String password) {
        this.host = host;
        this.port = port;
        this.schema = schema;
        this.MYSQL_DB_USERNAME = username;
        this.MYSQL_DB_PASSWORD = password;
        this.MYSQL_DB_URL = String.format("jdbc:mysql://%s:%s/%s", host, port, schema);
        init();
    }
    protected static void init() {
        mysqlDS.setURL(MYSQL_DB_URL);
        mysqlDS.setUser(MYSQL_DB_USERNAME);
        mysqlDS.setPassword(MYSQL_DB_PASSWORD);
        try {
            con = mysqlDS.getConnection();
            stmt = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet read(String sql) {
        try {
            con = mysqlDS.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return rs;
    }


    protected static void printAll(ResultSet rs, String[] fields) {
        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                for (String f : fields) {
                    System.out.println(f + ": " + rs.getString(f));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        var db = new CRUD("localhost", "3306", "northwind", "root", "S!mpl312");
        ResultSet rs = db.read("""
                SELECT EmployeeID, LastName, FirstName
                FROM Employees
                ORDER BY LastName, FirstName""");
        if (null == rs) {
            System.out.println("NO RESULTS");
            return;
        }
        db.printAll(rs, new String[]{"EmployeeID", "LastName", "FirstName"});


        ResultSet rs2 = db.read("SELECT ProductName FROM products");
        if (null == rs2) {
            System.out.println("NO RESULTS");
            return;
        }
        db.printAll(rs2, new String[]{"ProductName"});

    }
}