package com.lianup.catalina.handler;

import com.lianup.catalina.http.Request;
import com.lianup.catalina.http.Response;
import com.lianup.catalina.processor.SimpleLoader;
import com.lianup.catalina.servlet.Servlet;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;
import sun.security.jca.GetInstance;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;

public class ServletHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

        String uri = request.getUri();
        SimpleLoader simpleLoader = new SimpleLoader("com.lianup.catalina.servlet.");
        Class servletClass = simpleLoader.getServletClass(uri.substring(9));
        Servlet servlet = (Servlet)servletClass.newInstance();
        Request myRequest = new Request(ctx,request);
        Response myResponse = new Response(ctx,request);
        System.out.println(request.getMethod());
        if("get".equals(request.getMethod())){
            servlet.doGet(myRequest, myResponse);
        }else {
            servlet.doPost(myRequest, myResponse);
        }
    }

    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }
}
