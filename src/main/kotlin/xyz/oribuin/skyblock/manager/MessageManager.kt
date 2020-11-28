package xyz.oribuin.skyblock.manager

import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.hook.PlaceholderAPIHook
import xyz.oribuin.skyblock.library.HexUtils.colorify
import xyz.oribuin.skyblock.library.Manager
import xyz.oribuin.skyblock.library.StringPlaceholders
import xyz.oribuin.skyblock.library.StringPlaceholders.Companion.empty

class MessageManager(plugin: Skyblock) : Manager(plugin) {

    override fun reload() {
        // Unused
    }


    fun sendMessage(sender: CommandSender, message: String, placeholders: StringPlaceholders = empty()) {
        sender.spigot().sendMessage(*TextComponent.fromLegacyText(placeholders.apply(colorify(parsePlaceholders(sender, message)))))
    }

    fun sendActionMessage(sender: Player, message: String, placeholders: StringPlaceholders = empty()) {
        sender.spigot().sendMessage(ChatMessageType.ACTION_BAR, *TextComponent.fromLegacyText(placeholders.apply(colorify(parsePlaceholders(sender, message)))))
    }

    private fun parsePlaceholders(sender: CommandSender, message: String): String {
        return if (sender is Player)
            PlaceholderAPIHook.apply(sender, message)
        else
            message
    }

    override fun disable() {
        // Unused
    }
}
