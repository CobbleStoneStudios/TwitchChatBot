package com.css.cobble.twitchChatBot.api

import com.css.cobble.twitchChatBot.api.events.OnReadyEvent

trait ITwitchChatEventListener {

    def onReady(event: OnReadyEvent): Unit

}
