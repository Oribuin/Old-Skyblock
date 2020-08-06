package xyz.oribuin.skyblock.commands

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.events.IslandCreateEvent
import xyz.oribuin.skyblock.managers.ConfigManager
import xyz.oribuin.skyblock.utils.HexUtils

class CmdIsland(override val plugin: Skyblock) : OriCommand(plugin, "island") {
    override fun executeCommand(sender: CommandSender, args: Array<String>) {

        if (sender !is Player || args.isEmpty()) {
            return
        }

        val islandManager = this.plugin.islandManager
        val island = islandManager.createIsland(args[0], args[1].toLowerCase(), islandManager.getNextAvailableLocation(), sender.uniqueId, ConfigManager.Setting.SETTINGS_SIZE.int)

        sender.teleport(island.spawnPoint)
        sender.sendMessage(HexUtils.colorify("<rainbow:0.7>Created the island \"${island.name}\""))

        Bukkit.getPluginManager().callEvent(IslandCreateEvent(island))

    }

    override fun tabComplete(sender: CommandSender, args: Array<String>): List<String>? {
        return null
    }
}