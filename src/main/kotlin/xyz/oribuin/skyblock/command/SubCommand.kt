package xyz.oribuin.skyblock.command

import org.bukkit.command.CommandSender
import xyz.oribuin.skyblock.library.OriCommand

abstract class SubCommand(private val oriCommand: OriCommand, vararg val names: String) {

    val prefix = "<g:#ec008c:#fc6767:l>&lSkyblock &7»"

    abstract fun executeArgument(sender: CommandSender, args: Array<String>)
}