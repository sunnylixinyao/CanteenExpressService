package servlet.client;

import service.client.GetFriendsData;
import util.DataResultError;
import util.JsonTools;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * @author lixinyao
 * @create 2021-02-2021/2/23-14:55
 **/
//从前端获取该用户的名称，前往数据库中查询，获取到该用户的好友，并将结果返回
@WebServlet(name = "GetFriendsDataServlet",urlPatterns = "/getFriendsData")
public class GetFriendsDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        BufferedReader br = new BufferedReader(
                new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        String data = sb.toString();
        PrintWriter out = response.getWriter();//写入到response中
        System.out.println("request: " + data + "\n");//获取到移动端传来的数据
        //将json数据转换为Array数组
        ArrayList<String> realData = JsonTools.jsonToArray(data);
        System.out.println("RealData"+realData);
        String sqlRunResult=this.getResult(realData.get(1));
       try {
           if(sqlRunResult==null){
               out.print("{\"RT\":" + DataResultError.M000008.getMSG() + ",\"DATA\":" + "{}"+ "}");//说明该用户没有好友
           }
           if(sqlRunResult!=null){
               sqlRunResult = "{\"RT\":" + DataResultError.M000000.getMSG() + ",\"DATA\":" + sqlRunResult + "}";//成功
               out.print(sqlRunResult);
           }
       }catch (Exception e){
           out.print("{\"RT\":" + DataResultError.M999999.getMSG() + ",\"DATA\":" + "{}"+ "}"); //其他系统错误
       }
       out.flush();
    }
    public String getResult(String name){
        GetFriendsData getFriendsData=new GetFriendsData(name);
        String result=getFriendsData.generateRunSql();
        return  result;
    }
}
