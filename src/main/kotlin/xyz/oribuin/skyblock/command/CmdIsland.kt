package xyz.oribuin.skyblock.command

import org.bukkit.command.CommandSender
import org.bukkit.util.StringUtil
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.command.subcommand.CmdBorder
import xyz.oribuin.skyblock.command.subcommand.CmdCreate
import xyz.oribuin.skyblock.command.subcommand.CmdReload
import xyz.oribuin.skyblock.command.subcommand.CmdTeleport

class CmdIsland(override val plugin: Skyblock) : OriCommand(plugin, "island") {
    private val subcommands = mutableListOf<SubCommand>()

    override fun executeCommand(sender: CommandSender, args: Array<String>) {

        for (cmd in subcommands) {
            if (args.isNotEmpty() && cmd.names.contains(args[0].toLowerCase())) {
                cmd.executeArgument(sender, args)
                break
            }
        }
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

    override fun addSubCommands() {
        subcommands.addAll(listOf(CmdBorder(plugin, this), CmdCreate(plugin, this), CmdReload(plugin, this), CmdTeleport(plugin, this)))
    }
}
