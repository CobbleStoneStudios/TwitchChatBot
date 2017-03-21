package com.css.cobble.twitchChatBot.api.events

import com.css.cobble.twitchChatBot.TwitchChatClient

abstract class Event(client: TwitchChatClient) {

    def getClient: TwitchChatClient = this.client

}
