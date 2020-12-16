import org.python.util.PythonInterpreter;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author lixinyao
 * @create 2020-12-2020/12/12-17:17
 **/
public class Test {
    //mysql驱动包名
    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    //数据库连接地址.0

    private static final String URL = "jdbc:mysql://localhost:3306/canteenexpress";
    //用户名,更换成你自己的用户名，此处为root用户
    private static final String USER_NAME = "root";
    //密码，更换成你自己设定的密码，此处为:admin
    private static final String PASSWORD = "123456789";
    public static void main(String[] args){
        Connection connection = null;
        try {
            //加载mysql的驱动类
            Class.forName(DRIVER_NAME);
            //获取数据库连接
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            //mysql查询语句
            String sql = "SELECT name FROM client";
            PreparedStatement prst = connection.prepareStatement(sql);
            //结果集
            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
                System.out.println("用户名:" + rs.getString("name"));
            }
            rs.close();
            prst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    }
    /*
     Properties props=new Properties();
        props.put("python.home","D:\\jython\\jythoninstall\\Lib");
        props.put("python.console.encoding","UTF-8");
        props.put("python.security.respectJavaAccessibility","false");
        props.put("python.import.site","false");
        Properties properties=System.getProperties();
        PythonInterpreter.initialize(properties,props,new String[0]);
        PythonInterpreter interpreter=new PythonInterpreter();
        interpreter.exec("print('hello word')");
        interpreter.execfile("D:\\python\\pythontest\\test.py");
     */
