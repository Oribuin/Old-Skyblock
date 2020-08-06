package xyz.oribuin.skyblock.commands

import org.apache.commons.lang.StringUtils
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.events.IslandCreateEvent
import xyz.oribuin.skyblock.managers.ConfigManager
import xyz.oribuin.skyblock.utils.StringPlaceholders
import java.io.File
import java.util.*

class CmdIsland(override val plugin: Skyblock) : OriCommand(plugin, "island") {

    override fun executeCommand(sender: CommandSender, args: Array<String>) {
        //val msg = this.plugin.messageManager

        if (args.size == 1) {
            if (args[0].toLowerCase() == "reload") {
                this.reload(sender)
            }

        }

        if (args.size >= 2) {
            when (args[0].toLowerCase()) {
                "create" -> {
                    this.createIsland(sender, args, "plains")
                }
            }
        }
    }

    private fun createIsland(sender: CommandSender, args: Array<String>, schematicName: String) {
        val msg = this.plugin.messageManager
        val islandManager = this.plugin.islandManager

        if (sender !is Player) {
            msg.sendMessage(sender, "player-only")
            return
        }

        if (args.size < 2) {
            msg.sendMessage(sender, "invalid-arguments")
            return
        }

        val islandName = java.lang.String.join(" ", *args).substring(args[0].length + 1)
        val island = islandManager.createIsland(islandName, schematicName, islandManager.getNextAvailableLocation(), sender.uniqueId, ConfigManager.Setting.SETTINGS_SIZE.int)
        sender.teleport(island.spawnPoint)

        msg.sendMessage(sender, "commands.created-island", StringPlaceholders.builder("island_name", island.name).addPlaceholder("island_type", StringUtils.capitalize(schematicName)).build())

        Bukkit.getPluginManager().callEvent(IslandCreateEvent(island))
    }

    private fun reload(sender: CommandSender) {
        val messageManager = plugin.messageManager

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
                        StringUtil.copyPartialMatches(args[1], schemList, suggestions)
                    }
                }
            }
        } else {
            return null
        }

        return suggestions
    }

    private val schemList: List<String>
        get() {
            val file = File(plugin.dataFolder, "schematics")
            val list = mutableListOf<String>()

            if (file.listFiles() == null) {
                return emptyList()
            }


            file.listFiles().forEach { schemFile ->
                if (schemFile.name.endsWith(".schem")) {
                    list.add(file.name.substring(-6))
                } else if (schemFile.name.endsWith(".schematic")) {
                    list.add(file.name.substring(-10))
                }
            }

            return list
        }
}
