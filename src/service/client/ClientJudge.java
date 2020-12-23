package service.client;

import dao.RunSQL;

import java.util.ArrayList;

/**
 * @author lixinyao
 * @create 2020-12-2020/12/22-17:48
 **/
//用来判断用户账号是否已经注册过
    //通过姓名和学号的方式去做一个校验
public class ClientJudge {
    //用户的姓名和学号
    private String name;
    private String study_ID;

    //sql查询语句
    private final static String sqlJudgeByNID="SELECT COUNT(id) FROM CLIENT WHERE name='";
    private final static String sqlByStudy_ID="and study_ID='";

    //构造函数
    public ClientJudge(){ }
    public ClientJudge(String name,String stduy_ID){
        this.name=name;
        this.study_ID=stduy_ID;
    }

    //查询
    public boolean generateSql(){
        //拼接sql语句
        String realsql=sqlJudgeByNID+name+"'"+sqlByStudy_ID+study_ID+"'";
        ArrayList<String> resultdata=new ArrayList<>();
        RunSQL runSQL=new RunSQL();
        runSQL.findSingle(realsql);
        resultdata=runSQL.getFindResult();
        boolean isRegister;
//        for (int i = 0; i < resultdata.size(); i++) {
//            System.out.println("data is"+resultdata.get(i));
//        }
        if(resultdata.get(1).equals("0")){
            //为0说明查询没有结果
            isRegister=false;
        }else{
            isRegister=true;
        }
       // System.out.println("isRegister"+isRegister);
        System.out.println("用户是否登录的判断结果为"+isRegister);
        return isRegister;
    }
}
