package servlet.client;

import service.client.ClientRegister;
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
 * @create 2020-12-2020/12/17-19:18
 **/
@WebServlet(name = "ClientRegister",urlPatterns = "/clientRegister")
public class ClientRegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前端传来的JSON数据
        //
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
        int result=this.getResult(realData.get(1),realData.get(5),realData.get(7),realData.get(3));
        String returndata;
        //开始向前端传递数据
        if(result==0){
            //说明成功
            returndata = "{\"RT\":" + DataResultError.M000000.getMSG()+"}";//成功
        }else if(result==1){
            //用户已经注册过账号
            returndata = "{\"RT\":" + DataResultError.M000004.getMSG()+"}";//已注册
        }else {
            returndata = "{\"RT\":" + DataResultError.M999999.getMSG()+"}";//其他错误
        }
        out.print(returndata);
        out.flush();
    }
    private int  getResult(String name,String study_ID,String tel,String password){
        ClientRegister clientRegister=new ClientRegister(name,study_ID,tel,password);
        int result=clientRegister.generateSql();
        return result;
    }
}
