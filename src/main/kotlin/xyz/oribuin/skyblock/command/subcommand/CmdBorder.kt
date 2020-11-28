package xyz.oribuin.skyblock.command.subcommand

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.command.SubCommand
import xyz.oribuin.skyblock.library.OriCommand
import xyz.oribuin.skyblock.manager.MessageManager

class CmdBorder(private val plugin: Skyblock, command: OriCommand) : SubCommand(command, "border") {
    override fun executeArgument(sender: CommandSender, args: Array<String>) {
        val msg = plugin.getManager(MessageManager::class)

        if (sender !is Player) {
            msg.sendMessage(sender, "$prefix &bOnly a player can execute this command.")
            return
        }

        if (!sender.hasPermission("skyblock.border")) {
            msg.sendMessage(sender, "$prefix &cYou do not have access to this command.")
            return
        }

        msg.sendActionMessage(sender, "$prefix &bThis does nothing lol")
    }

    private fun getBlocks(start: Block, radius: Int): List<Block> {
        val blocks = mutableListOf<Block>()

        var x = start.location.x - radius
        while (x <= start.location.x + radius) {

            var z = start.location.z - radius

            while (z <= start.location.z + radius) {
                val loc = Location(start.world, x, 100.0, z)

                blocks.add(loc.block)
                z++
            }
            x++
        }
        return blocks
    }
}