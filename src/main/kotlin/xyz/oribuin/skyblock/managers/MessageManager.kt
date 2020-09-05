package xyz.oribuin.skyblock.managers

import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.hooks.PlaceholderAPIHook
import xyz.oribuin.skyblock.utils.FileUtils.createFile
import xyz.oribuin.skyblock.utils.HexUtils.colorify
import xyz.oribuin.skyblock.utils.StringPlaceholders
import xyz.oribuin.skyblock.utils.StringPlaceholders.Companion.empty
import java.io.File

class MessageManager(plugin: Skyblock) : Manager(plugin) {
    lateinit var messageConfig: FileConfiguration

    override fun reload() {
        createFile(plugin, MESSAGE_CONFIG)
        messageConfig = YamlConfiguration.loadConfiguration(File(plugin.dataFolder, MESSAGE_CONFIG))

        for (value in MsgSettings.values()) {
            if (messageConfig.get(value.key) == null) {
                messageConfig.set(value.key, value.defaultValue)
            }
            value.load(messageConfig)
        }

        messageConfig.save(File(plugin.dataFolder, MESSAGE_CONFIG))
    }


    @JvmOverloads
    fun sendMessage(sender: CommandSender, messageId: String, placeholders: StringPlaceholders = empty()) {
        if (messageConfig.getString(messageId) == null) {
            sender.spigot().sendMessage(*TextComponent.fromLegacyText(colorify("#ff4072$messageId is null in messages.yml")))
            return
        }

        if (messageConfig.getString(messageId)!!.isNotEmpty()) {
            val msg = messageConfig.getString("prefix") + placeholders.apply(messageConfig.getString(messageId)!!)
            sender.spigot().sendMessage(*TextComponent.fromLegacyText(colorify(parsePlaceholders(sender, msg))))
        }
    }

    private fun parsePlaceholders(sender: CommandSender, message: String): String {
        return if (sender is Player)
            PlaceholderAPIHook.apply(sender, message)
        else
            message
    }

    companion object {
        private const val MESSAGE_CONFIG = "messages.yml"
    }

    override fun disable() {
        // Unused
    }

    enum class MsgSettings(val key: String, val defaultValue: Any) {
        // Misc Stuff
        PREFIX("prefix", "<rainbow:0.7>Skyblock &f» "),
        COOLDOWN("cooldown", "&bYou cannot execute this command for another &f%cooldown% &bseconds!"),
        RELOAD("reload", "&bYou have reloaded Skyblock (&f%version%&b)"),

        // Command success messages
        CMD_CREATED_ISLAND("commands.created-island", "&bYou have successfully created your Island,  %island_name%."),

        // Listener Messages
        CANT_ENTER_PORTAL("listener-messages.cant-enter-portal", "&bThe nether/end is not enabled when on an Island."),

        // Help Menu
        HELP_MESSAGE("help-message", listOf(
                "<rainbow:0.7>Skyblock &f» &bCommands",
                " &f• &b/island create &f<Name> #8E54E9- &bCreate an island..",
                " ",
                " &f» &bPlugin created by <g:#4776E6:#8E54E9>Oribuin"
        )),

        // Error Messages
        INVALID_PERMISSION("invalid-permission", "&cYou do not have permission for this command."),
        INVALID_PLAYER("invalid-player", "&cThat is not a valid player."),
        INVALID_ARGUMENTS("invalid-arguments", "&cYou have provided invalid arguments."),
        HAS_BYPASS("has-bypass", "&cYou cannot report this player."),
        PLAYER_ONLY("only-player", "&cONly a player can execute this command."),
        UKNOWN_COMMAND("unknown-command", "&cAn unknown command was entered."),
        PLAYER_HAS_ISLAND("player-has-island", "&b%player% already has an Island."),
        YOU_HAVE_ISLAND("you-have-island", "&bYou already have an island.");


        private var value: Any? = null

        /**
         * Gets the setting as a boolean
         *
         * @return The setting as a boolean
         */
        val boolean: Boolean
            get() = value as Boolean

        /**
         * @return the setting as a String
         */
        val string: String
            get() = value as String

        /**
         * @return the setting as a string list
         */
        val stringList: List<*>
            get() = value as List<*>

        /**
         * Loads the value from the config and caches it
         */
        fun load(config: FileConfiguration) {
            value = config[key]
        }

    }
}
