package service.client;

import dao.RunSQL;
import org.python.antlr.ast.Str;
import util.JsonTools;

import java.util.ArrayList;

/**
 * @author lixinyao
 * @create 2021-02-2021/2/22-15:10
 **/
public class GetSchool {
    private String CityName;
    private final static String sqlGetSchoolByID="SELECT id,school_id,school_name,school_erea FROM school WHERE city_id =";

    public GetSchool(){}

    public GetSchool(String CityName){
        this.CityName=CityName;
    }

    public String generterRunsql(){
        ArrayList<String> result=new ArrayList<>();
        GetCityIDByName getCityIDByName=new GetCityIDByName(CityName);
        int CityId=getCityIDByName.generateRunsql();
        String finalsql=sqlGetSchoolByID+CityId;
        RunSQL runSQL=new RunSQL();
        runSQL.findSingle(finalsql);
        result=runSQL.getFindResult();
        String sqlResult= JsonTools.toJson(result,4);
        return sqlResult;
    }
}
