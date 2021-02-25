package service.client;

import dao.RunSQL;
import util.JsonTools;

import java.util.ArrayList;

/**
 * @author lixinyao
 * @create 2021-02-2021/2/22-15:10
 **/
public class GetCity {
    private  String  provinceName;
    private final static String sqlGetCityByProvinceID="select * from city where province_id =";
    public GetCity(String provinceName){
        this.provinceName=provinceName;
    }
    public String generateRunSql(){
        ArrayList<String> resultString=new ArrayList<>();
        GetProvinceIDByName getProvinceIDByName=new GetProvinceIDByName(provinceName);
        int ProvinceId=getProvinceIDByName.generterRunSQL();
        String realSql=sqlGetCityByProvinceID+ProvinceId;
        //开始执行数据库
        RunSQL runSQL=new RunSQL();
        runSQL.findSingle(realSql);
        resultString=runSQL.getFindResult();
        System.out.println("resultString"+resultString);
        //将查询的结果Array数组转换为JSON字符串
        String runsqlReuslt= JsonTools.toJson(resultString,4);
        System.out.println("runsqlResult"+runsqlReuslt);
        //将结果返回给LogininServlet
        return runsqlReuslt;
    }
}
