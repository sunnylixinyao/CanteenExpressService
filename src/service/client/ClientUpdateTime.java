package service.client;

import dao.RunSQL;

import java.sql.Date;
import java.sql.Time;

/**
 * @author lixinyao
 * @create 2020-12-2020/12/19-15:15
 **/
public class ClientUpdateTime {
    //sql语句
   private final static String sqlClientUpdateTime="update client set loginin_day='";
    private final static String sqlloginin_time=",loginin_time='";
    private final static String sqlwhere="where name='";
    private final static String sqlwherenext="and study_ID='";
    //传递过来的name和study_ID
    private String name;
    private String study_ID;
    //获取系统当前的时间
    private Date day;
    private Time time;
    private int updateState;
    //构造函数
    public ClientUpdateTime(){}
    public ClientUpdateTime(String name,String study_ID){
        this.name=name;
        this.study_ID=study_ID;
    }
    //run sql
    public int generateRunSql(){
        //获取目前的时间
        day=new java.sql.Date(System.currentTimeMillis());
        time=new java.sql.Time(System.currentTimeMillis());
        System.out.println(day+"  "+time);
        String realsql=sqlClientUpdateTime+day+"'"+sqlloginin_time+time+"'"+sqlwhere+name+"'"+sqlwherenext+study_ID+"'";
        RunSQL runSQL=new RunSQL();
        runSQL.updateSingle(realsql);
        int runsqlResult=runSQL.getUpdateResult();
        if(runsqlResult==0){
            //说明更新成功
            updateState=1;//成功
            System.out.println("success");
        }
        return updateState;
    }
}
