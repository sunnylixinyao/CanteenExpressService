package service.client;

import dao.RunSQL;

import java.util.ArrayList;

/**
 * @author lixinyao
 * @create 2021-02-2021/2/25-17:24
 **/
public class GetCityIDByName {
    private String CityName;

    private final static String sqlGetCityId="select id from city where name='";
    public GetCityIDByName(){}

    public GetCityIDByName(String CityName){
        this.CityName=CityName;
    }

    public int generateRunsql(){
        ArrayList<String> result=new ArrayList<>();
        String finalSql=sqlGetCityId+CityName+"'";
        RunSQL runSQL=new RunSQL();
        runSQL.findSingle(finalSql);
        result=runSQL.getFindResult();
        int id=Integer.parseInt(result.get(1));
        return id;
    }
}
