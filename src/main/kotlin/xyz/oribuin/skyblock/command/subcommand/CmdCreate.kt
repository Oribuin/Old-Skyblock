package xyz.oribuin.skyblock.command.subcommand

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.command.SubCommand
import xyz.oribuin.skyblock.library.OriCommand
import xyz.oribuin.skyblock.manager.ConfigManager
import xyz.oribuin.skyblock.manager.MessageManager
import xyz.oribuin.skyblock.menu.CreateIslandMenu
import java.util.*

class CmdCreate(private val plugin: Skyblock, command: OriCommand) : SubCommand(command, "create") {
    override fun executeArgument(sender: CommandSender, args: Array<String>) {
        val cooldowns: MutableMap<UUID, Long> = HashMap()

        val msg = plugin.getManager(MessageManager::class)

        if (sender !is Player) {
            msg.sendMessage(sender, "$prefix &bOnly a player can execute this command.")
            return
        }

        if (!sender.hasPermission("skyblock.create")) {
            msg.sendMessage(sender, "$prefix &cYou do not have access to this command.")
            return
        }

        if (args.size < 2) {
            msg.sendMessage(sender, "$prefix &bPlease include the right amount of arguments.")
            return
        }

//        val member = IslandMember(plugin, sender.uniqueId)
//        if (member.hasIsland || member.islandOwner) {
//            msg.sendMessage(sender, "you-have-island")
//            return
//        }

        if (cooldowns.containsKey(sender.uniqueId)) {
            val secondsLeft = (cooldowns[sender.uniqueId] ?: return).div(1000).plus(ConfigManager.Setting.CMD_ISLAND_CREATE_COOLDOWN.long).minus(System.currentTimeMillis().div(1000))

            if (secondsLeft > 0) {
                msg.sendMessage(sender, "$prefix &bPlease wait ${secondsLeft}s until you type this command again.")
                return
            }
        }

        //cooldowns[sender.uniqueId] = System.currentTimeMillis()

        val islandName = java.lang.String.join(" ", *args).substring(args[0].length + 1)
        CreateIslandMenu(plugin, sender, islandName).openMenu()
    }
}