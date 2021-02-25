package service.client;

import dao.RunSQL;
import org.python.antlr.ast.Str;

import java.sql.Date;
import java.sql.Time;

/**
 * @author lixinyao
 * @create 2020-12-2020/12/17-19:55
 **/
public class ClientRegister {
    //用户注册的基本信息
    private String name;
    private String study_ID;
    private String tel;
    private String password;

    //用户注册的状态
    private int state;
    //1 注册成功
    //0 该账户已存在
    //-1 注册失败


    //书写sql语句
    private final static String sqlregister="insert into client(name,study_ID,tel_num,password,register_day,register_time,loginin_day,loginin_time) values(";
    private final static String sqlcomma=",";


    //在创建用户的同时也创建一张对应的表，来表示该用户的朋友
    private final static String sqlCreateTable="CREATE TABLE `";
    private final static String createTableByUserName="` (\n" +
            "  `friend_id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `friend_name` VARCHAR(30) NOT NULL,\n" +
            "  `friend_study_id` VARCHAR(10) NOT NULL,\n" +
            "  `frist_connect_time` TIME NOT NULL,\n" +
            "  `last_connect_time` TIME NOT NULL,\n" +
            "  PRIMARY KEY (`friend_id`)\n" +
            ") ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8";
    //声明注册时间和登录时间
    private Date register_day;
    private Time register_time;
    //构造函数
    public ClientRegister(){}
    public ClientRegister(String name,String study_ID,String tel,String password){
        this.name=name;
        this.study_ID=study_ID;
        this.tel=tel;
        this.password=password;
    }
    //在执行插入操作之前应该先去查询，看该用户是否已经注册

    //执行插入数据操作
    public int generateSql(){

        ClientJudge clientJudge=new ClientJudge(name,study_ID);
        boolean isRegister=clientJudge.generateSql();
        if(isRegister==false){
            //此时进行注册，插入语句
            state=register();
        }else{
            //说明用户已经注册过了，需要要登录
            state=1;
        }
        return state;
    }
    private int  register(){
        //获取当前系统时间
        register_day=new java.sql.Date(System.currentTimeMillis());
        register_time=new java.sql.Time(System.currentTimeMillis());
        //拼接sql语句
        String realsql=sqlregister+"'"+name+"'"+sqlcomma+"'"+study_ID+"'"+sqlcomma+"'"+tel+"'"+sqlcomma+"'"+password+"'"+sqlcomma+"'"+register_day+"'"+sqlcomma+"'"+register_time+"'"+sqlcomma+"'"+register_day+"'"+sqlcomma+"'"+register_time+"'"+")";
        RunSQL runSQL=new RunSQL();
        runSQL.updateSingle(realsql);
        String createTableFriends=sqlCreateTable+name+"_friends"+ createTableByUserName;
        RunSQL runsql=new RunSQL();
        runsql.updateSingle(createTableFriends);
        System.out.println("创建好了对应的朋友表格"+runsql.getUpdateResult());
        int result=runSQL.getUpdateResult();
        System.out.println("插入结果是"+result);
        //0成功
        //-1失败
        return result;
    }
}
