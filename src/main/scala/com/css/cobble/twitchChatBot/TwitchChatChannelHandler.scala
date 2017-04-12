package com.css.cobble.twitchChatBot

import com.css.cobble.twitchChatBot.ref.MessageRef
import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}

class TwitchChatChannelHandler extends ChannelInboundHandlerAdapter {

    override def channelRead(ctx: ChannelHandlerContext, msg: Object): Unit = {
        val msgStr: String = msg.asInstanceOf[String]
        if(msgStr.contains(MessageRef.PING_MSG))
            ctx.channel().writeAndFlush(MessageRef.PONG_MSG)
        println(s"Message: $msg")
    }

    override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
        cause.printStackTrace()
        ctx.close()
    }


}
