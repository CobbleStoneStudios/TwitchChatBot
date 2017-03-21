package com.css.cobble

import com.css.cobble.twitchChatBot.{TwitchChatClient, TwitchChatClientBuilder}
import com.typesafe.config.{Config, ConfigFactory}
import io.netty.util.internal.logging.{InternalLoggerFactory, Slf4JLoggerFactory}

object TestBot {

    val config: Config = ConfigFactory.load()

    var bot: TwitchChatClient = _

    def main(args: Array[String]): Unit = {
        InternalLoggerFactory.setDefaultFactory(Slf4JLoggerFactory.INSTANCE)

        val builder: TwitchChatClientBuilder = new TwitchChatClientBuilder()
        builder.setUsername(config.getString("twitchBot.username"))
        builder.setOAuth(config.getString("twitchBot.oauth"))
        builder.addEventListener(new TwitchEventListener)

        bot = builder.build()
    }

}
