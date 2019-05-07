package com.lianup.catalina;

import com.lianup.catalina.handler.ServletHandler;
import com.lianup.catalina.handler.StaticHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedWriteHandler;


public class BootStrap {

    public void start(int port) throws Exception {

        // Boss线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // Worker线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // Netty服务
            ServerBootstrap server = new ServerBootstrap();
            // 链路式编程
            server.group(bossGroup, workerGroup)
                    // 主线程处理类
                    .channel(NioServerSocketChannel.class)
                    // 子线程处理类 , Handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // 客户端初始化处理
                        protected void initChannel(SocketChannel client) throws Exception {
                            // HttpResponseEncoder 编码器
//                            client.pipeline().addLast(new HttpServerCodec());
                            client.pipeline().addLast(new HttpResponseEncoder());
                            // HttpRequestDecoder 解码器
                            client.pipeline().addLast(new HttpRequestDecoder());
                            // 业务逻辑处理
//                            client.pipeline().addLast(new MyHandler());
                            client.pipeline().addLast(new HttpObjectAggregator(64 * 1024));
                            //块传输
                            client.pipeline().addLast(new ChunkedWriteHandler());
                            client.pipeline().addLast(new StaticHandler());
//                            client.pipeline().addLast(new ServletHandler());
                            // 估计是自己写的request和response有些东西不足,导致无法write
//                            client.pipeline().addLast(new StaticHandler());
                        }

                    })
                    // 针对主线程的配置 分配线程最大数量 128
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 针对子线程的配置 保持长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 启动服务器 sync 同步阻塞
            ChannelFuture f = server.bind(port).sync();
            System.out.println("my NettyTomcat Startd Port: " + port);
            f.channel().closeFuture().sync();
        } finally {
            // 关闭线程池
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        try {
            new BootStrap().start(8080);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
