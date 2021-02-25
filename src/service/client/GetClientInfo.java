package service.client;

import dao.RunSQL;
import org.python.antlr.ast.Str;
import util.JsonTools;

import java.util.ArrayList;

/**
 * @author lixinyao
 * @create 2021-02-2021/2/23-18:01
 **/
public class GetClientInfo {
    private final static String sqlGetClientInfoByName="select * from client where name='";
    private String name;
    public GetClientInfo(){}
    public GetClientInfo(String name){
        this.name=name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public String generateSql(){
        ArrayList<String> resultString=new ArrayList<>();
         String finalSql=sqlGetClientInfoByName+name+"'";
        //开始执行数据库
        RunSQL runSQL=new RunSQL();
        runSQL.findSingle(finalSql);
        resultString=runSQL.getFindResult();
        System.out.println("resultString"+resultString);
        //将查询的结果Array数组转换为JSON字符串
        String runsqlReuslt= JsonTools.toJson(resultString,9);
        System.out.println("runsqlResult"+runsqlReuslt);
        //将结果返回给LogininServlet
        return runsqlReuslt;
    }
}
