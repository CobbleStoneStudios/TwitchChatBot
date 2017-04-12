package com.css.cobble.twitchChatBot

import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter, ChannelOutboundHandlerAdapter, ChannelPromise}

object TwitchChatLoggingHandler {

    class Inbound extends ChannelInboundHandlerAdapter {
        override def channelRead(ctx: ChannelHandlerContext, msg: Object): Unit = {
            logger.debug(s"> $msg")
        }

        override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
            cause.printStackTrace()
            ctx.close()
        }
    }

    class Outbound extends ChannelOutboundHandlerAdapter {
        override def write(ctx: ChannelHandlerContext, msg: Object, promise: ChannelPromise): Unit = {
            logger.debug(s"< $msg")
        }

        override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
            cause.printStackTrace()
            ctx.close()
        }
    }

}
