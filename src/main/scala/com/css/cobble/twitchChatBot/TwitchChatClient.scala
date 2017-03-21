package com.css.cobble.twitchChatBot

import java.util

import com.css.cobble.twitchChatBot.api.{ITwitchChatClient, ITwitchChatEventListener}
import io.netty.bootstrap.Bootstrap
import io.netty.buffer.Unpooled
import io.netty.channel._
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.{DelimiterBasedFrameDecoder, MessageToMessageEncoder}
import io.netty.handler.codec.string.{StringDecoder, StringEncoder}
import io.netty.util.concurrent.{Future, GenericFutureListener}

class TwitchChatClient(host: String, port: Int = 6667, username: String, oAuth: String, tagsCap: Boolean = true, membershipCap: Boolean = true, commandsCap: Boolean = true, eventListeners: Array[ITwitchChatEventListener]) extends ITwitchChatClient {

    val workerGroup: EventLoopGroup = new NioEventLoopGroup()

    var channel: Channel = _

    try {
        val bootstrap: Bootstrap = new Bootstrap()
        bootstrap.group(workerGroup)
        bootstrap.channel(classOf[NioSocketChannel])
        bootstrap.option[java.lang.Boolean](ChannelOption.SO_KEEPALIVE, true)
        bootstrap.handler(new ChannelInitializer[SocketChannel]() {
            override def initChannel(channel: SocketChannel): Unit = {
                channel.pipeline().addLast("line splitter", new DelimiterBasedFrameDecoder(4096, Unpooled.wrappedBuffer(Array[Byte]('\r', '\n'))))
                channel.pipeline().addLast("string decoder", new StringDecoder())
                channel.pipeline().addLast("handler", new TwitchChatChannelHandler())
                channel.pipeline().addLast("string encoder", new StringEncoder())
                channel.pipeline().addLast("add line breaks", new MessageToMessageEncoder[String]() {
                    override def encode(ctx: ChannelHandlerContext, msg: String, out: util.List[AnyRef]): Unit = {
                        out.add(msg + "\r\n")
                    }
                })
            }
        })
        val channelFuture: ChannelFuture = bootstrap.connect(host, port).sync()

        println("Connected")

        channel = channelFuture.channel()

        channel.writeAndFlush(s"PASS $oAuth")
        channel.writeAndFlush(s"NICK $username")

        if (tagsCap)
            channel.writeAndFlush("CAP REQ :twitch.tv/tags")
        if (membershipCap)
            channel.writeAndFlush("CAP REQ :twitch.tv/membership")
        if (commandsCap)
            channel.writeAndFlush("CAP REQ :twitch.tv/commands")

        channelFuture.channel().closeFuture().addListener(new GenericFutureListener[Future[_ >: Void]] {
            override def operationComplete(future: Future[_ >: Void]): Unit = {
                if (future.isDone)
                    shutdown()
            }
        })

    } catch {
        case e: Exception =>
            shutdown()
            throw e
    }

    def shutdown(): Unit = {
        workerGroup.shutdownGracefully()
    }

    def join(channelName: String): Unit = {
        channel.writeAndFlush(s"JOIN #$channelName")
    }

    def sendMsg(channel: String, message: String): Unit = {
        this.channel.writeAndFlush(s"PRIVMSG #$channel :$message")
    }

}
