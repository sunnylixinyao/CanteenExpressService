package service.client;

import dao.RunSQL;

import java.sql.Time;

/**
 * @author lixinyao
 * @create 2021-02-2021/2/23-17:04
 **/
public class AddFriends {
    private String UserName;
    private String Friend_Name;
    private String Friend_Study_Id;

    //INSERT INTO lixinyao_friends (friend_name,friend_study_id,frist_connect_time,last_connect_time) VALUE ('hhhhh','45678487','12:00:00','12:00:00');
    private final static String sqlAddFriend="INSERT INTO ";
    private final static String sqlColnum="(friend_name,friend_study_id,frist_connect_time,last_connect_time) VALUE (";

    private Time time;

    public AddFriends(){}

    public AddFriends(String Friend_Name,String Friend_Study_Id,String UserName){
        this.UserName=UserName;
        this.Friend_Name=Friend_Name;
        this.Friend_Study_Id=Friend_Study_Id;
    }

    public int  generterRunSql(){
        time=new java.sql.Time(System.currentTimeMillis());
        //拼凑出表名
        String TableName=UserName+"_friends";
        String Vlues="'"+Friend_Name+"'"+","+"'"+Friend_Study_Id+"'"+","+"'"+time+"'"+","+"'"+time+"'"+")";
        String finalSql=sqlAddFriend+TableName+sqlColnum+Vlues;
        RunSQL runSQL=new RunSQL();
        runSQL.updateSingle(finalSql);
        int result=runSQL.getUpdateResult();
        return result;
    }
}
