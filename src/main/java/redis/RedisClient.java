package redis;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageCodec;

/**
 * @author Wang Hao
 * @date 2021/6/21 19:05
 */
public class RedisClient {

    Bootstrap bootstrap;

    public RedisClient() {
        new Bootstrap().group(new NioEventLoopGroup(1))
            .channel(NioSocketChannel.class)
            .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {

                    ChannelPipeline pipeline = channel.pipeline();

                }
            });
    }

    static class RedisGetCommandCodec {

    }

    static class RedisGetCommand {

    }

    static class RedisSetCommand {

    }
}
