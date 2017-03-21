package com.css.cobble.twitchChatBot

import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}

class TwitchChatChannelHandler extends ChannelInboundHandlerAdapter {

    override def channelRead(ctx: ChannelHandlerContext, msg: Object): Unit = {
        println(s"Message: $msg")
    }

    override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
        cause.printStackTrace()
        ctx.close()
    }


}
