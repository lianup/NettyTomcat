package com.lianup.catalina.servlet;

import com.lianup.catalina.http.Request;
import com.lianup.catalina.http.Response;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public class MyServlet extends Servlet {

    public void doGet(Request request, Response response) {
        try {
            // 返回相应的uri
            response.write(request.getUri());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doPost(Request request, Response response) {
        doGet(request,response);
    }

}
