package com.css.cobble.twitchChatBot.api

import com.css.cobble.twitchChatBot.ref.IrcMessageRef

class IrcMessage(rawMessage: String) {

    private val regexMatchInfo = IrcMessageRef.MESSAGE_REGEX.findAllIn(rawMessage).matchData.toArray.apply(0)

    val rawTags = Option(regexMatchInfo.group(1))
    val tags: Map[String, String] = if (rawTags.isDefined)
        rawTags.get.split(";").map(tag => {
            val kv = tag.split("=")
            kv(0) -> (if (kv.length > 1) kv(1) else "")
        }).toMap
    else
        Map()
    val rawPrefix = Option(regexMatchInfo.group(2))
    val command: String = regexMatchInfo.group(3)
    val channel = Option(regexMatchInfo.group(4))
    val content = Option(regexMatchInfo.group(5))

    override def toString: String = rawMessage

}
