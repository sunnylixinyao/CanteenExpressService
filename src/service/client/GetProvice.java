package service.client;

import dao.RunSQL;
import util.JsonTools;

import java.util.ArrayList;

/**
 * @author lixinyao
 * @create 2021-02-2021/2/22-1:01
 **/
public class GetProvice {

    private final static String  sqlGetProvice="select * from province";
    public String generateRunSql(){
        ArrayList<String> resultString=new ArrayList<>();
        //开始执行数据库
        RunSQL runSQL=new RunSQL();
        runSQL.findSingle(sqlGetProvice);
        resultString=runSQL.getFindResult();
        System.out.println("resultString"+resultString);
        //将查询的结果Array数组转换为JSON字符串
        String runsqlReuslt= JsonTools.toJson(resultString,2);
        System.out.println("runsqlResult"+runsqlReuslt);
        //将结果返回给getProvinceServlet
        return runsqlReuslt;
    }
}
