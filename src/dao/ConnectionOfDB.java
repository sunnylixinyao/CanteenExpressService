package dao;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author lixinyao
 * @create 2020-12-2020/12/14-16:01
 **/
public class ConnectionOfDB {
    private static String url = null;
    private static String driver = null;
    private static String user = null;
    private static String password = null;

    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet ret = null;
    private int resultUpdate = -1;

    static {
        try {
            Properties prop = new Properties();
            try {
                InputStream in = ConnectToDatabase.class.getClassLoader()
                        .getResourceAsStream("db.properties");

                prop.load(in);

                url = prop.getProperty("url");
                driver = prop.getProperty("name");
                user = prop.getProperty("user");
                password = prop.getProperty("password");
                in.close();

            } catch (Exception e) {
                System.out.println(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connectToDatabase() {
        try {
            Class.forName(driver);//
            conn = DriverManager.getConnection(url, user, password);//
            conn.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // return SQL result set
    public ResultSet getResultSet(String sql) {
        try {
            pst = conn.prepareStatement(sql);//
            ret = pst.executeQuery();
            conn.commit();
        } catch (SQLException e) {
            try{
                conn.rollback();
            } catch(SQLException rollbackError) {
                System.out.println(rollbackError.getMessage());
            }
            e.printStackTrace();
        }
        return ret;
    }

    public int getResultUpdate(String sql) {// 0:success 1:Duplicate data
        // -1:other error
        try {
            pst = conn.prepareStatement(sql);// prepare sql statement
            resultUpdate = pst.executeUpdate();
            resultUpdate = 0;
            conn.commit();
        } catch (SQLException e) {
            try{
                conn.rollback();
            } catch(SQLException rollbackError) {
                System.out.println(rollbackError.getMessage());
            }
            if (e.getMessage().substring(0, 15).equals("Duplicate entry")) { // insert失败，重复数据
                resultUpdate = 1;
            } else {
                System.out.println(e.getMessage());
                resultUpdate = -1;
            }
        }
        return resultUpdate;
    }

    public int updateTransaction(ArrayList<String> sqlBatch) {
        try {
            for (int i = 0; i < sqlBatch.size(); i++) {
                pst = conn.prepareStatement(sqlBatch.get(i));// prepare sql statement
                resultUpdate = pst.executeUpdate();
                resultUpdate = 0;
            }
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
                if (e.getMessage().substring(0, 15).equals("Duplicate entry")) { // insert失败，重复数据
                    resultUpdate = 1;
                } else {
                    resultUpdate = -1;
                }
            } catch (SQLException rollbackError) {
                System.out.println(rollbackError.getMessage());
                resultUpdate = -1;
            }
            System.out.println(e.getMessage());
        }
        return resultUpdate;
    }

    // 仅用于select语句，select的结果，transform SQL result set into ArrayList<String>
    // 返回值包含列名和对应的值
    public ArrayList<String> getResultSetString(String sql) { // 需要修订，将列名也存储
        ArrayList<String> retString = new ArrayList<String>();
        try {
            ret = this.getResultSet(sql); // 调用查询方法，获得结果集
            ResultSetMetaData rsmd = ret.getMetaData();
            if(ret.next()) {
                ret.beforeFirst();
                while(ret.next()) {
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        retString.add(rsmd.getColumnLabel(i)); //列名 as值。如果没有指定as，则是数据库的列名
                        retString.add(ret.getString(rsmd.getColumnLabel(i))); // 值
                    }
                }
            } else {
                retString= null;// 查询无数据
            }
        } catch (SQLException e) {
            retString= null;// 异常返回
            e.printStackTrace();
        }
        return retString;
    }

    public void retClose() {
        try {
            if(ret != null) {
                this.ret.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void pstClose() {
        try {
            this.pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void connClose() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
