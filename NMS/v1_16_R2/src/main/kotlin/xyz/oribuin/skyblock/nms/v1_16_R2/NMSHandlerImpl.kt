package xyz.oribuin.skyblock.nms.v1_16_R2

import net.minecraft.server.v1_16_R2.PacketPlayOutWorldBorder
import net.minecraft.server.v1_16_R2.WorldBorder
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer
import org.bukkit.entity.Player
import xyz.oribuin.skyblock.nms.BorderColor
import xyz.oribuin.skyblock.nms.NMSHandler

class NMSHandlerImpl : NMSHandler {

    override fun sendWorldBorder(player: Player, color: BorderColor, size: Double, center: Location) {
        val worldBorder = WorldBorder()
        worldBorder.world = (center.world as CraftWorld).handle
        worldBorder.setCenter(center.blockX + 0.5, center.blockZ + 0.5)

        if (color == BorderColor.OFF) {
            worldBorder.size = Double.MAX_VALUE
        } else {
            worldBorder.size = size
        }

        worldBorder.warningDistance = 0
        worldBorder.warningTime = 0

        if (color == BorderColor.RED) {
            worldBorder.transitionSizeBetween(size, size - 1.0, 20000000L)
        } else if (color == BorderColor.GREEN) {
            worldBorder.transitionSizeBetween(size - 0.1, size, 20000000L)
        }

        (player as CraftPlayer).handle.playerConnection.sendPacket(PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE))
    }

}
