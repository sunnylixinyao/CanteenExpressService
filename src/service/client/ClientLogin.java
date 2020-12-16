package service.client;

import dao.RunSQL;
import util.JsonTools;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author lixinyao
 * @create 2020-12-2020/12/11-17:41
 **/
public class ClientLogin {
    private final static String sqlClientLogin="select name,loginin_day,loginin_time from client where password='";
    private final static String sqlByStudyID="and study_ID='";
    private final static String sqlByTel="tel_num='";

    private String study_ID;
    private String tel;
    private String password;
    //构造函数
    public ClientLogin(String IDorTell,String Password,int Flag){
        this.password=Password;
        if (Flag==1){
            //输入为学号
            this.study_ID=IDorTell;
        }else {
            //输入为电话号码
            this.tel=IDorTell;
        }
    }
    public String generateRunSql(int Flag){
        ArrayList<String> resultString=new ArrayList<>();
        //拼接sql语句
        String realSql;
        if(Flag==1){
            realSql=sqlClientLogin+password+"'"+sqlByStudyID+study_ID+"'";
        }else{
            realSql=sqlClientLogin+password+"'"+sqlByTel+tel+"'";
        }
        //开始执行数据库
        RunSQL runSQL=new RunSQL();
        runSQL.findSingle(realSql);
        resultString=runSQL.getFindResult();
        System.out.println("resultString"+resultString);
        //将查询的结果Array数组转换为JSON字符串
        String runsqlReuslt= JsonTools.toJson(resultString,3);
        System.out.println("runsqlResult"+runsqlReuslt);
        //将结果返回给LogininServlet
        return runsqlReuslt;
    }
}
