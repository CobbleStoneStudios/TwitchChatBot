package com.css.cobble.twitchChatBot

import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter, ChannelOutboundHandlerAdapter, ChannelPromise}

object TwitchChatLoggingHandler {

    final val inputPrefix: String = ">"

    final val outputPrefix: String = "<"

    class Inbound extends ChannelInboundHandlerAdapter {
        override def channelRead(ctx: ChannelHandlerContext, msg: Object): Unit = {
            botLogger.debug(s"$inputPrefix $msg")
            super.channelRead(ctx, msg)
        }

        override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
            cause.printStackTrace()
            ctx.close()
        }
    }

    class Outbound extends ChannelOutboundHandlerAdapter {
        override def write(ctx: ChannelHandlerContext, msg: scala.Any, promise: ChannelPromise): Unit = {
            botLogger.debug(s"$outputPrefix $msg")
            super.write(ctx, msg, promise)
        }

        override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
            cause.printStackTrace()
            ctx.close()
        }
    }
}
