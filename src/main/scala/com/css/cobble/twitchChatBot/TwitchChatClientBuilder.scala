package com.css.cobble.twitchChatBot

import com.css.cobble.twitchChatBot.api.ITwitchChatEventListener
import com.css.cobble.twitchChatBot.api.events.OnReadyEvent

import scala.collection.mutable.ArrayBuffer

class TwitchChatClientBuilder {

    private var host: String = "irc.chat.twitch.tv"

    private var port: Int = 6667

    private var username: String = ""

    private var oAuth: String = ""

    private var tagsCap: Boolean = true

    private var membershipCap: Boolean = true

    private var commandsCap: Boolean = true

    private var eventListeners: ArrayBuffer[ITwitchChatEventListener] = ArrayBuffer()

    def setHost(host: String): Unit = this.host = host

    def setPort(port: Int): Unit = this.port = 6667

    def setUsername(username: String): Unit = this.username = username

    def setOAuth(oAuth: String): Unit = {
        if (!oAuth.startsWith("oauth:"))
            this.oAuth = s"oauth:$oAuth"
        else
            this.oAuth = oAuth
    }

    def setTagsCap(tagsCap: Boolean): Unit = this.tagsCap = tagsCap

    def setMembershipCap(membershipCap: Boolean): Unit = this.membershipCap = membershipCap

    def setCommandsCap(commandsCap: Boolean): Unit = this.commandsCap = commandsCap

    def addEventListener(eventListener: ITwitchChatEventListener): Unit = this.eventListeners += eventListener

    def getHost: String = this.host

    def getPort: Int = this.port

    def getUsername: String = this.username

    def getOAuth: String = this.oAuth

    def getTagsCap: Boolean = this.tagsCap

    def getMembershipCap: Boolean = this.membershipCap

    def getCommandsCap: Boolean = this.commandsCap

    def getEventListeners: Array[ITwitchChatEventListener] = this.eventListeners.toArray

    def build(): TwitchChatClient = new TwitchChatClient(host, port, username, oAuth, tagsCap, membershipCap, commandsCap, this.eventListeners.toArray)
}
