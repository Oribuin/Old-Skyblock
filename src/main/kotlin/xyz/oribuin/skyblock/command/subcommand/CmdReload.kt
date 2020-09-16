package xyz.oribuin.skyblock.command.subcommand

import org.bukkit.command.CommandSender
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.command.OriCommand
import xyz.oribuin.skyblock.command.SubCommand
import xyz.oribuin.skyblock.manager.MessageManager
import xyz.oribuin.skyblock.util.PluginUtils.prefix

class CmdReload(private val plugin: Skyblock, command: OriCommand) : SubCommand(command, "reload") {
    override fun executeArgument(sender: CommandSender, args: Array<String>) {
        val messageManager = plugin.getManager(MessageManager::class)

        if (!sender.hasPermission("skyblock.reload")) {
            messageManager.sendMessage(sender, "$prefix &cYou do not have access to this command.")
            return
        }
        this.plugin.reload()
        messageManager.sendMessage(sender, "$prefix &bYou have reloaded Skyblock v${plugin.description.version} by Oribuin.")
    }
}