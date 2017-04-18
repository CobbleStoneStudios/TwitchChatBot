import com.css.cobble.twitchChatBot.api.IrcMessage
import com.css.cobble.twitchChatBot.ref.{IrcCommands, IrcMessageRef, TagKeys}

class IrcMessageSpec extends UnitSpec {

    val samplePrivMsgText: String = "@badges=broadcaster/1,premium/1;color=#005FCC;display-name=Cobbleopolis;emotes=;id=aeeec5ec-d02b-432c-9f30-7ee56e27b744;mod=0;room-id=23858424;sent-ts=1492536186058;subscriber=0;tmi-sent-ts=1492536188668;turbo=0;user-id=23858424;user-type= :cobbleopolis!cobbleopolis@cobbleopolis.tmi.twitch.tv PRIVMSG #cobbleopolis :Hello, World!"

    "An IrcMessage" when {
        "given a PRIVMSG message" should {
            "be able to be constructed" in {
                noException should be thrownBy new IrcMessage(samplePrivMsgText)
            }

            "not have empty tags" in {
                val ircMessage = new IrcMessage(samplePrivMsgText)
                ircMessage.rawTags.value should not be empty
                ircMessage.tags should not be empty
            }

            "have the proper tags" in {
                val tags: Map[String, String] = new IrcMessage(samplePrivMsgText).tags

                assert(tags.contains(TagKeys.BADGES))
                tags.get(TagKeys.BADGES).value shouldBe "broadcaster/1,premium/1"

                assert(tags.contains(TagKeys.COLOR))
                tags.get(TagKeys.COLOR).value shouldBe "#005FCC"

                assert(tags.contains(TagKeys.DISPLAY_NAME))
                tags.get(TagKeys.DISPLAY_NAME).value shouldBe "Cobbleopolis"

                assert(tags.contains(TagKeys.EMOTES))
                tags.get(TagKeys.EMOTES).value shouldBe empty

                assert(tags.contains(TagKeys.ID))
                tags.get(TagKeys.ID).value shouldBe "aeeec5ec-d02b-432c-9f30-7ee56e27b744"

                assert(tags.contains(TagKeys.MOD_STATUS))
                tags.get(TagKeys.MOD_STATUS).value shouldBe "0"

                assert(tags.contains(TagKeys.ROOM_ID))
                tags.get(TagKeys.ROOM_ID).value shouldBe "23858424"

                assert(tags.contains(TagKeys.SENT_TS))
                tags.get(TagKeys.SENT_TS).value shouldBe "1492536186058"

                assert(tags.contains(TagKeys.SUBSCRIBER_STATUS))
                tags.get(TagKeys.SUBSCRIBER_STATUS).value shouldBe "0"

                assert(tags.contains(TagKeys.TMI_SENT_TS))
                tags.get(TagKeys.TMI_SENT_TS).value shouldBe "1492536188668"

                assert(tags.contains(TagKeys.TURBO_STATUS))
                tags.get(TagKeys.TURBO_STATUS).value shouldBe "0"

                assert(tags.contains(TagKeys.USER_ID))
                tags.get(TagKeys.USER_ID).value shouldBe "23858424"

                assert(tags.contains(TagKeys.USER_TYPE))
                tags.get(TagKeys.USER_TYPE).value shouldBe empty
            }

            "have proper rawPrefix value" in {
                new IrcMessage(samplePrivMsgText).rawPrefix.value shouldBe "cobbleopolis!cobbleopolis@cobbleopolis.tmi.twitch.tv"
            }

            "have proper command value" in {
                new IrcMessage(samplePrivMsgText).command shouldBe IrcCommands.PRIVATE_MESSAGE
            }

            "have proper channel value" in {
                new IrcMessage(samplePrivMsgText).channel.value shouldBe "#cobbleopolis"
            }

            "have proper content value" in {
                new IrcMessage(samplePrivMsgText).content.value shouldBe "Hello, World!"
            }
        }


    }

}
