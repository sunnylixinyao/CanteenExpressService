package service.client;

import dao.ConnectionOfDB;
import dao.RunSQL;
import org.python.antlr.ast.Str;
import util.JsonTools;

import javax.print.DocFlavor;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * @author lixinyao
 * @create 2021-02-2021/2/23-15:11
 **/
public class GetFriendsData {
    private String username;
    private final static String sqlGetFriendsByName="select * from ";
    public GetFriendsData(){}
    public GetFriendsData(String username){
        this.username=username;
    }
    public String generateRunSql(){
        String tableName=username+"_friends";
        String finalSql=sqlGetFriendsByName+tableName;
        ArrayList<String> resultString=new ArrayList<>();
        //开始执行数据库
        String runsqlReuslt="";
        RunSQL runSQL=new RunSQL();
        runSQL.findSingle(finalSql);
        resultString=runSQL.getFindResult();
        System.out.println("resultString"+resultString);
        //将查询的结果Array数组转换为JSON字符串
        runsqlReuslt= JsonTools.toJson(resultString,5);
        System.out.println("runsqlResult"+runsqlReuslt);
        //将结果返回给getProvinceServlet
        return runsqlReuslt;
    }
}
