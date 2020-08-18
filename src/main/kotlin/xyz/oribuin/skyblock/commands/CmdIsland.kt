package xyz.oribuin.skyblock.commands

import me.mattstudios.mf.annotations.Command
import me.mattstudios.mf.annotations.Completion
import me.mattstudios.mf.annotations.Default
import me.mattstudios.mf.annotations.SubCommand
import me.mattstudios.mf.base.CommandBase
import org.apache.commons.lang.StringUtils
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.events.IslandCreateEvent
import xyz.oribuin.skyblock.managers.ConfigManager
import xyz.oribuin.skyblock.managers.IslandManager
import xyz.oribuin.skyblock.managers.MessageManager
import xyz.oribuin.skyblock.utils.HexUtils
import xyz.oribuin.skyblock.utils.StringPlaceholders

@Command("island")
class CmdIsland(private val plugin: Skyblock) : CommandBase() {

    private val msg = plugin.getManager(MessageManager::class)

    @Default
    fun defaultCommand(sender: CommandSender) {
        if (!sender.hasPermission("skyblock.help")) {
            msg.sendMessage(sender, "invalid-permission")
            return
        }


        for (string in msg.messageConfig.getStringList("help-message")) {
            sender.sendMessage(HexUtils.colorify(string))
        }

        if (sender is Player) {
            sender.playSound(sender.location, Sound.ENTITY_ARROW_HIT_PLAYER, 50f, 1f)
        }
    }

    @SubCommand("create")
    @Completion("<name>")
    fun createIsland(sender: CommandSender, args: List<String>, schematicName: String) {
        val islandManager = plugin.getManager(IslandManager::class)

        if (sender !is Player) {
            msg.sendMessage(sender, "player-only")
            return
        }

        if (args.size < 2) {
            msg.sendMessage(sender, "invalid-arguments")
            return
        }
        val islandName = args[1].toLowerCase()
        val island = islandManager.createIsland(islandName, schematicName, sender.uniqueId, ConfigManager.Setting.SETTINGS_SIZE.int)
        sender.teleport(island.spawnPoint)

        msg.sendMessage(sender, "commands.created-island", StringPlaceholders.builder("island_name", island.name).addPlaceholder("island_type", StringUtils.capitalize(schematicName)).build())

        Bukkit.getPluginManager().callEvent(IslandCreateEvent(island))
    }

    @SubCommand("admin reload")
    fun reloadCommand(sender: CommandSender) {
        if (!sender.hasPermission("skyblock.admin.reload")) {
            msg.sendMessage(sender, "invalid-permission")
            return
        }

        msg.sendMessage(sender, "reload", StringPlaceholders.single("version", plugin.description.version))
        plugin.reload()
    }
}