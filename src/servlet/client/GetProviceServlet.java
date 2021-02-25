package servlet.client;

import service.client.ClientUpdateTime;
import service.client.GetProvice;
import util.DataResultError;
import util.JsonTools;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * @author lixinyao
 * @create 2021-02-2021/2/22-0:31
 **/
@WebServlet(name = "GetProviceServlet",urlPatterns = "/getProvice")
public class GetProviceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();//写入到response中
        //获取数据传入到getResul函数中
        String sqlRunResult=this.getResult();
        try {
            if (!sqlRunResult.isEmpty()) {
                ArrayList<String> update= JsonTools.jsonToArray(sqlRunResult);
                System.out.println(update.toString());
                //修改返回的JSON数据
                sqlRunResult = "{\"RT\":" + DataResultError.M000000.getMSG() + ",\"DATA\":" + sqlRunResult + "}";//成功
                out.print(sqlRunResult); //向前端返回成功，并携带数据
            } else {
                out.print("{\"RT\":" + DataResultError.M000005.getMSG() + ",\"DATA\":" + "{}"+ "}");//没有省份信息
            }
        } catch (Exception e) {
            out.print("{\"RT\":" + DataResultError.M999999.getMSG() + ",\"DATA\":" + "{}"+ "}"); //其他系统错误
        }
        out.flush();
    }
    private String getResult(){
        GetProvice getProvice=new GetProvice();
        String result=getProvice.generateRunSql();
        return result;
    }
}
