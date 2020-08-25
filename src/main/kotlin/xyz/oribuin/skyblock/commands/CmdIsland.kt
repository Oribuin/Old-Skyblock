package xyz.oribuin.skyblock.commands
import org.apache.commons.lang.StringUtils
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.events.IslandCreateEvent
import xyz.oribuin.skyblock.island.IslandMember
import xyz.oribuin.skyblock.managers.ConfigManager
import xyz.oribuin.skyblock.managers.DataManager
import xyz.oribuin.skyblock.managers.IslandManager
import xyz.oribuin.skyblock.managers.MessageManager
import xyz.oribuin.skyblock.menus.CreateIslandMenu
import xyz.oribuin.skyblock.utils.StringPlaceholders
import java.util.*
import kotlin.collections.ArrayList

class CmdIsland(override val plugin: Skyblock) : OriCommand(plugin, "island") {

    override fun executeCommand(sender: CommandSender, args: Array<String>) {

        if (args.size == 1) {
            when (args[0].toLowerCase()) {
                "reload" -> {
                    this.reload(sender)
                }

                "go", "teleport" -> {
                    if (sender !is Player)
                        return

                    val member = IslandMember(plugin, sender.uniqueId)
                    member.getIsland()?.center?.let { sender.teleport(it) }
                }

                "delete" -> {
                    if (sender !is Player)
                        return

                    val member = IslandMember(plugin, sender.uniqueId)
                    member.getIsland()?.let { plugin.getManager(DataManager::class).deleteIslandData(it) }
                }
            }
        }

        if (args.size >= 2) {
            when (args[0].toLowerCase()) {
                "create" -> {
                    this.createIsland(sender, args)
                }
            }
        }
    }

    private fun createIsland(sender: CommandSender, args: Array<String>) {
        val cooldowns: MutableMap<UUID, Long> = HashMap()

        val msg = plugin.getManager(MessageManager::class)

        if (sender !is Player) {
            msg.sendMessage(sender, "player-only")
            return
        }

        val member = IslandMember(plugin, sender.uniqueId)

        /*
        if (member.hasIsland || member.islandOwner) {
            msg.sendMessage(sender, "you-have-island")
            return
        }

         */

        if (cooldowns.containsKey(sender.uniqueId)) {
            val secondsLeft = (cooldowns[sender.uniqueId] ?: return).div(1000).plus(ConfigManager.Setting.CMD_ISLAND_CREATE_COOLDOWN.long).minus(System.currentTimeMillis().div(1000))

            if (secondsLeft > 0) {
                msg.sendMessage(sender, "cooldown", StringPlaceholders.single("cooldown", secondsLeft))
                return
            }
        }

        //cooldowns[sender.uniqueId] = System.currentTimeMillis()

        if (args.size < 2) {
            msg.sendMessage(sender, "invalid-arguments")
            return
        }

        val islandName = java.lang.String.join(" ", *args).substring(args[0].length + 1)
        CreateIslandMenu(plugin, sender, islandName).openMenu()
    }

    private fun reload(sender: CommandSender) {
        val messageManager = plugin.getManager(MessageManager::class)

        if (!sender.hasPermission("skyblock.reload")) {
            messageManager.sendMessage(sender, "invalid-permission")
            return
        }


        this.plugin.reload()
        messageManager.sendMessage(sender, "reload", StringPlaceholders.single("version", this.plugin.description.version))
    }

    override fun tabComplete(sender: CommandSender, args: Array<String>): List<String>? {

        val suggestions: MutableList<String> = ArrayList()
        if (args.isEmpty() || args.size == 1) {
            val subCommand = if (args.isEmpty()) "" else args[0]

            when (subCommand.toLowerCase()) {
                "create" -> {
                    if (sender.hasPermission("skyblock.create")) {
                        StringUtil.copyPartialMatches(subCommand, setOf("<Island-Name>"), suggestions)
                    }
                }

                "reload" -> {
                    if (sender.hasPermission("skyblock.reload")) {
                        StringUtil.copyPartialMatches(subCommand, setOf("reload"), suggestions)
                    }
                }
            }
            return null
        } else if (args.size == 2) {

            when (args[1].toLowerCase()) {
                "create" -> {
                    if (sender.hasPermission("skyblock.create")) {
                        StringUtil.copyPartialMatches(args[1], setOf("plains"), suggestions)
                    }
                }
            }
        } else {
            return null
        }

        return suggestions
    }
}