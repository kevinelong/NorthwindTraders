package org.example;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/*
CREATE
READ - 1st one
UPDATE
DELETE
 */

public class CRUD {
    protected String host;
    protected String port;
    protected String schema;
    protected String MYSQL_DB_URL;

    //creds
    protected String MYSQL_DB_USERNAME;
    protected String MYSQL_DB_PASSWORD;
    protected MysqlDataSource mysqlDS;

    protected Connection con = null;
    protected Statement stmt = null;

    CRUD(String host, String port, String schema, String username, String password) {
        this.host = host;
        this.port = port;
        this.schema = schema;
        this.MYSQL_DB_USERNAME = username;
        this.MYSQL_DB_PASSWORD = password;
        this.MYSQL_DB_URL = String.format("jdbc:mysql://%s:%s/%s", host, port, schema);
        init();
    }

    protected void init() {
        mysqlDS = new MysqlDataSource();
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

    public ResultSet read(String sql) {
        ResultSet rs = null;

        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return rs;
    }


    protected void printAll(ResultSet rs, String[] fields) {
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
    public ArrayList<HashMap> getData(String sql, String[] fields) {
        var list = new ArrayList<HashMap>();
        ResultSet rs = read(sql);
        if (null == rs) {
            System.out.println("NO RESULTS");
            return list;
        }
        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            var item = new HashMap<String,String>();

            try {
                for (String f : fields) {
                    item.put(f, rs.getString(f));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            list.add(item);
        }
        try {
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
    public void close() {
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
