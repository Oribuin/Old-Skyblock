package xyz.oribuin.skyblock.command.subcommand

import net.minecraft.server.v1_16_R2.PacketPlayOutWorldBorder
import net.minecraft.server.v1_16_R2.WorldBorder
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.block.Block
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer
import org.bukkit.entity.Player
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.command.OriCommand
import xyz.oribuin.skyblock.command.SubCommand
import xyz.oribuin.skyblock.island.IslandMember
import xyz.oribuin.skyblock.manager.MessageManager
import xyz.oribuin.skyblock.util.PluginUtils.prefix

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

        val member = IslandMember(plugin, sender.uniqueId)
        val craftPlayer = sender as CraftPlayer
        val island = member.getIsland() ?: return

        val worldBorder = WorldBorder()
        worldBorder.world = (island.center.world as CraftWorld).handle

        worldBorder.warningDistance = 0
        worldBorder.warningTime = 0

        worldBorder.size = island.islandRange.toDouble()
        worldBorder.setCenter(island.center.x, island.center.z)

        craftPlayer.handle.playerConnection.sendPacket(PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE))

        msg.sendActionMessage(sender, "$prefix &bNow displaying Island Border")
        Bukkit.
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