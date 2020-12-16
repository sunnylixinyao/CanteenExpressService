package servlet.client;

import service.client.ClientLogin;
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
 * @create 2020-12-2020/12/3-16:36
 **/
@WebServlet(name = "ClientLoginServlet",urlPatterns = "/clientLogin")
public class ClientLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("servlet已经被调用clientLogin");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从req中读取前端发过来的数据
        //data:{id,password}
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
        String sqlRunResult=this.getResult(realData.get(1),realData.get(3));
        System.out.println("final result"+sqlRunResult);
        //写入结果
        try {
            if (!sqlRunResult.isEmpty()) {
              sqlRunResult = "{\"RT\":" + DataResultError.M000000.getMSG() + ",\"DATA\":" + sqlRunResult + "}";//成功
                out.print(sqlRunResult); //成功
            } else {
                out.print("{\"RT\":" + DataResultError.M000001.getMSG() + ",\"DATA\":" + "{}"+ "}"); //请求成功，但是没有该账号
            }
        } catch (Exception e) {
            out.print("{\"RT\":" + DataResultError.M999999.getMSG() + ",\"DATA\":" + "{}"+ "}"); //其他系统错误
        }
        out.flush();
    }
    private String getResult(String IDorTel,String Password){
        //判断用户传来的账号是学号还是手机号码
        //若是学号，Flag为1，若是手机号码，Flag为2
        int Flag;
        if(IDorTel.length()<10){
            //长度小于10为学号
            Flag=1;
        }else {
            Flag=2;
        }
        ClientLogin clientLogin=new ClientLogin(IDorTel,Password,Flag);//声明一个Client对象
        String result=clientLogin.generateRunSql(Flag);
        return result;
    }
}