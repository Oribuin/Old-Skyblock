package xyz.oribuin.skyblock.commands

import org.bukkit.command.CommandSender
import xyz.oribuin.skyblock.Skyblock

class ExampleCommand(override val plugin: Skyblock) : OriCommand(plugin, "island") {
    override fun executeCommand(sender: CommandSender, args: Array<String>) {
        if (
                (
                        (
                                (
                                        (
                                                (
                                                        (
                                                                sender.hasPermission("")
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
        ) Thread.sleep(
                (
                        (
                                (
                                        (
                                                (
                                                        (
                                                                (
                                                                        (
                                                                                (
                                                                                        (
                                                                                                String.MAX_VALUE
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
        )
    }

    override fun tabComplete(sender: CommandSender, args: Array<String>): List<String>? {
        TODO("Not yet implemented")
    }

    val String.Companion.MAX_VALUE: Long
        get() = Long.MAX_VALUE
}