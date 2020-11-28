package xyz.oribuin.skyblock.command.subcommand

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.command.SubCommand
import xyz.oribuin.skyblock.island.IslandMember
import xyz.oribuin.skyblock.library.OriCommand
import xyz.oribuin.skyblock.manager.MessageManager

class CmdTeleport(private val plugin: Skyblock, command: OriCommand) : SubCommand(command, "go", "teleport") {
    override fun executeArgument(sender: CommandSender, args: Array<String>) {
        val msg = plugin.getManager(MessageManager::class)

        if (sender !is Player) {
            msg.sendMessage(sender, "$prefix &bOnly a player can execute this command.")
            return
        }

        if (!sender.hasPermission("skyblock.teleport")) {
            msg.sendMessage(sender, "$prefix &cYou do not have access to this command.")
            return
        }

        val member = IslandMember(plugin, sender.uniqueId)

        if (!member.hasIsland) {
            msg.sendMessage(sender, "$prefix You do not have an island.")
            return
        }

        member.player.player?.teleport((member.getIsland() ?: return).spawnPoint)
        msg.sendActionMessage(sender, "&bWelcome to your Island!")
    }
}