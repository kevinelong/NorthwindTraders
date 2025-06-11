package org.example;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

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
    //CREATE
    public Integer create(String tableName, String[] fields, String[] values){

        String df = Arrays.stream(fields).map(s -> String.format("`%s`",s)).collect(Collectors.joining(","));
        String dv = Arrays.stream(values).map(s -> String.format("'%s'",s)).collect(Collectors.joining(","));

        String sqlInsert = String.format("INSERT INTO %s (%s) VALUES (%s);", tableName, df, dv);
        System.out.println(sqlInsert);
        Integer id = null;
        try {
            id = stmt.executeUpdate(sqlInsert);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }
    //READ
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

    public ResultSet search(String sql, String needleSought) {
        ResultSet rs = null;
        try {
            var ps = con.prepareStatement(sql);
            ps.setString(1,needleSought);
            rs = ps.executeQuery();
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

    public ArrayList<HashMap<String,String>> convert(ResultSet rs, String[] fields) {
        var list = new ArrayList<HashMap<String,String>>();

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

    public ArrayList<HashMap<String,String>> searchData(String sql, String[] fields, String needleSought) {
        return convert(search(sql, needleSought), fields);
    }

    public ArrayList<HashMap<String,String>> getData(String sql, String[] fields) {
        return convert(read(sql), fields);
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
