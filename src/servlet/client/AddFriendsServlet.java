package servlet.client;

import service.client.AddFriends;
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
 * @create 2021-02-2021/2/23-17:04
 **/
@WebServlet(name = "AddFriendsServlet",urlPatterns = "/addFriends")
public class AddFriendsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从req中读取前端发过来的数据
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
        int  sqlRunResult=this.getResult(realData.get(1),realData.get(3),realData.get(5));
        if (sqlRunResult==0){
            out.print("{\"RT\":" + DataResultError.M000000.getMSG() + ",\"DATA\":" + "{}"+ "}");
        }else if(sqlRunResult==1){
            out.print("{\"RT\":" + DataResultError.M000010.getMSG() + ",\"DATA\":" + "{}"+ "}");//重复数据
        }else {
            out.print("{\"RT\":" + DataResultError.M999999.getMSG() + ",\"DATA\":" + "{}"+ "}");//系统错误
        }
        out.flush();
    }
    public int getResult(String Friend_Name,String Friend_Study_id,String UserName){
        AddFriends addFriends=new AddFriends(Friend_Name,Friend_Study_id,UserName);
        int result=addFriends.generterRunSql();
        return result;
    }
}
