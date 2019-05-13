package com.lianup.catalina.servlet;

import com.lianup.catalina.http.Request;
import com.lianup.catalina.http.Response;
import com.lianup.catalina.http.Request;
import com.lianup.catalina.http.Response;
import io.netty.handler.codec.http.HttpHeaders;
import java.util.Map;


/**
 * Servlet测试类
 */
public class RegisterServlet extends Servlet{


    private static final long serialVersionUID = 1123261262294483141L;

    @Override
    public void doGet(Request request, Response response){
        Map<String, String> parameter =  request.getParameter();
        for(Map.Entry<String, String> entry : parameter.entrySet()){
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println("parse user register  method :" + request.getMethod());
        System.out.println("parse user register cookies:");
        System.out.println("parse http header:");
        HttpHeaders headers = request.getHeaders();
        System.out.println("cookie " + headers.get("Age"));

        try {
            response.write("register successfully!");
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void doPost(Request request,Response response){
        doGet(request,response);
    }
}
