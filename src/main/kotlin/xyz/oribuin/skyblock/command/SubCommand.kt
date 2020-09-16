package xyz.oribuin.skyblock.command

import org.bukkit.command.CommandSender
import xyz.oribuin.skyblock.command.OriCommand

abstract class SubCommand(private val oriCommand: OriCommand, vararg val names: String) {

    abstract fun executeArgument(sender: CommandSender, args: Array<String>)
}