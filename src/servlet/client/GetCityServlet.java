package servlet.client;

import service.client.GetCity;
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
 * @create 2021-02-2021/2/22-15:10
 **/
@WebServlet(name = "GetCityServlet",urlPatterns = "/getCity")
public class GetCityServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        //从req中读取前端发过来的数据
        //data:{id,name}
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
        //获取数据传入到getResul函数中
        String sqlRunResult=this.getResult(realData.get(2));
        System.out.println("final result"+sqlRunResult);
        try {
            if (!sqlRunResult.isEmpty()) {
                ArrayList<String> update= JsonTools.jsonToArray(sqlRunResult);
                System.out.println(update.toString());
                //修改返回的JSON数据
                sqlRunResult = "{\"RT\":" + DataResultError.M000000.getMSG() + ",\"DATA\":" + sqlRunResult + "}";//成功
                out.print(sqlRunResult); //向前端返回成功，并携带数据
            } else {
                out.print("{\"RT\":" + DataResultError.M000006.getMSG() + ",\"DATA\":" + "{}"+ "}");//没有城市信息
            }
        } catch (Exception e) {
            out.print("{\"RT\":" + DataResultError.M999999.getMSG() + ",\"DATA\":" + "{}"+ "}"); //其他系统错误
        }
        out.flush();
    }
    private String getResult(String provinceName){
        GetCity city=new GetCity(provinceName);
        String result=city.generateRunSql();
        return result;
    }
}
