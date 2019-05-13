package com.lianup.catalina.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class HttpInitializer extends ChannelInitializer<Channel>{

//    private final SslContext context;
//
//    public HttpInitializer(SslContext context) {
//        this.context = context;
//    }

    protected void initChannel(Channel ch) throws Exception {
//        SSLEngine engine = context.newEngine(ch.alloc());
//        ch.pipeline().addLast("ssl", new SslHandler(engine));
        ch.pipeline().addLast("httpCoder", new HttpServerCodec());
        ch.pipeline().addLast("httpAggregator", new HttpObjectAggregator(64 * 1024));
        ch.pipeline().addLast("chunkTransfer", new ChunkedWriteHandler());
        ch.pipeline().addLast("IdleStateHandler", new IdleStateHandler(0, 0, 60, TimeUnit.SECONDS));
        ch.pipeline().addLast("HeatBeatHandler", new HeartbeatHandler());
        ch.pipeline().addLast("StaticHandler", new StaticHandler());
        ch.pipeline().addLast("ServletHandler", new ServletHandler());
    }


}
