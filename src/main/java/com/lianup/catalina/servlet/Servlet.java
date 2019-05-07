package com.lianup.catalina.servlet;

import com.lianup.catalina.http.Request;
import com.lianup.catalina.http.Response;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public abstract class Servlet {

    public abstract void doGet(Request request, Response response);

    public abstract void doPost(Request request,Response response);

}
