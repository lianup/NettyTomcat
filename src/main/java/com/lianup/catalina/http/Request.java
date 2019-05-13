package com.lianup.catalina.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {

    private ChannelHandlerContext ctx;

    private FullHttpRequest r;

    public Request(ChannelHandlerContext ctx, FullHttpRequest r) {
        this.ctx = ctx;
        this.r = r;
    }
    public HttpHeaders getHeaders(){
        return r.headers();
    }


    public String getUri() {
        return r.uri();
    }

    public String getMethod() {
        return r.method().name();
    }

    public Map<String, List<String>> getParameters() {
        QueryStringDecoder decoder = new QueryStringDecoder(r.uri());
        return decoder.parameters();
    }

    public Map<String, String> getParameter(){
        final Map<String, String> params = new HashMap<>();

        if("GET".equalsIgnoreCase(getMethod())){
            QueryStringDecoder getDecoder = new QueryStringDecoder(r.uri());
            // entry.getValue()是一个List, 只取第一个元素
            for (Map.Entry<String, List<String>> entry : getDecoder.parameters().entrySet()) {
                params.put(entry.getKey(), entry.getValue().get(0));
            }
        }else if("POST".equalsIgnoreCase(getMethod())){
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(r);
            decoder.offer(r);

            List<InterfaceHttpData> parmList = decoder.getBodyHttpDatas();

            for (InterfaceHttpData parm : parmList) {
                Attribute data = (Attribute) parm;
                try {
                    params.put(data.getName(), data.getValue());
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }else{
            throw new InvalidParameterException("不合法的方法!");
        }
        return params;
    }

}
