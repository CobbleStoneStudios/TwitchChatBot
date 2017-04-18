package com.css.cobble.twitchChatBot

import com.css.cobble.twitchChatBot.ref.TwitchMessageRef
import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}

class TwitchChatChannelHandler extends ChannelInboundHandlerAdapter {

    override def channelRead(ctx: ChannelHandlerContext, msg: Object): Unit = {
        val msgStr: String = msg.asInstanceOf[String]
        if(msgStr.contains(TwitchMessageRef.PING_MSG))
            ctx.channel().writeAndFlush(TwitchMessageRef.PONG_MSG)
        println(s"Message: $msg")
    }

    override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
        cause.printStackTrace()
        ctx.close()
    }


}
