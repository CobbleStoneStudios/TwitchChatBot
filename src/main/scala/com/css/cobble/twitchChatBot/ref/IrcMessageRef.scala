package com.css.cobble.twitchChatBot.ref

import scala.util.matching.Regex

object IrcMessageRef {

    final val MESSAGE_REGEX: Regex = "^(?:[@](\\S+) )?(?:[:](\\S+) )?(\\S+)(?: (?!:)(\\S+))?(?: [:](.+))?".r //TODO Fix for MODE commands

}
