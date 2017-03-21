package com.css.cobble

import com.css.cobble.twitchChatBot.TwitchChatClient
import com.css.cobble.twitchChatBot.api.ITwitchChatEventListener
import com.css.cobble.twitchChatBot.api.events.OnReadyEvent

class TwitchEventListener extends ITwitchChatEventListener {
    override def onReady(event: OnReadyEvent): Unit = {
        val client: TwitchChatClient = event.getClient
        if (client != null) {
            client.join("cobbleopolis")
            client.sendMsg("cobbleopolis", "o/ ALL")
        }
    }
}
