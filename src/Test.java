import org.python.util.PythonInterpreter;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

/**
 * @author lixinyao
 * @create 2020-12-2020/12/12-17:17
 **/
public class Test {
    public static void main(String[] args) {
        System.out.println(getFourRandom());
    }
    public static String getFourRandom(){
        Random random=new Random();
        String fourRandom=random.nextInt(1000)+"";
        int randLength=fourRandom.length();
        if(randLength<4){
            for(int i=1;i<=4-randLength;i++){
                fourRandom="0"+fourRandom;
            }
        }
        return fourRandom;
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
