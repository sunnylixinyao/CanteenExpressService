package service.client;

import dao.RunSQL;
import util.JsonTools;

import java.util.ArrayList;

/**
 * @author lixinyao
 * @create 2021-02-2021/2/22-15:30
 **/
//通过前端得到的省份名称去得到这个省份对应的ID

public class GetProvinceIDByName {
    private String provinceName;
    private final static String sqlGetProvinceID="select id from province where name ='";
    public GetProvinceIDByName(String provinceName){
        this.provinceName=provinceName;
    }
    public int generterRunSQL(){
        ArrayList<String> resultString=new ArrayList<>();
        //拼接sql语句
        String realSql=sqlGetProvinceID+provinceName+"'";
        //开始执行数据库
        RunSQL runSQL=new RunSQL();
        runSQL.findSingle(realSql);
        resultString=runSQL.getFindResult();
        System.out.println("resultString"+resultString);
        //将查询的结果Array数组转换为JSON字符串
        String runsqlReuslt= JsonTools.toJson(resultString,1);
        int province_id=Integer.parseInt(resultString.get(1));
        System.out.println("provinceID is "+runsqlReuslt);
        //将结果返回给LogininServlet
        return province_id;
    }
}
