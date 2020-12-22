package servlet.client;

import service.client.ClientVerifyCode;
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
 * @create 2020-12-2020/12/19-18:13
 **/
@WebServlet(name = "ClientVerifyCodeServlet",urlPatterns = "/clientVerifyCodeServlet")
public class ClientVerifyCodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前端传来的JSON数据
        //用户已经点击获取验证码的按钮
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
        ClientVerifyCode clientVerifyCode=new ClientVerifyCode();
        String verifyCode=clientVerifyCode.getFourRandom();
        System.out.println("verifyCode"+verifyCode);
        try {
            if(!verifyCode.isEmpty()){
                //说明验证码获取成功
                verifyCode = "{\"RT\":" + DataResultError.M000000.getMSG() + ",\"DATA\":" +"\"" +verifyCode +"\"" + "}";//成功
                out.print(verifyCode);
            }else {
                out.print("{\"RT\":" + DataResultError.M000003.getMSG() + ",\"DATA\":" + "{}"+ "}"); //没有获取到验证码
            }
        }catch (Exception e){
            out.print("{\"RT\":" + DataResultError.M999999.getMSG() + ",\"DATA\":" + "{}"+ "}"); //其他系统错误
        }
        out.flush();
    }
}
