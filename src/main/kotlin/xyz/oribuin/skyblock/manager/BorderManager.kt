package xyz.oribuin.skyblock.manager

import net.minecraft.server.v1_16_R2.PacketPlayOutWorldBorder
import net.minecraft.server.v1_16_R2.WorldBorder
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.island.Island

class BorderManager(plugin: Skyblock) : Manager(plugin) {
    override fun reload() {
        Bukkit.getScheduler().cancelTask(scheduleBorderTask().taskId)
        scheduleBorderTask()
    }

    override fun disable() {
        Bukkit.getScheduler().cancelTasks(plugin)
    }

    private fun scheduleBorderTask(): BukkitTask {
        val task = Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            Bukkit.getOnlinePlayers().forEach { player ->
                val islandManager = plugin.getManager(IslandManager::class)

                this.playBlueBorder(player, islandManager.getIslandOn(player)?: return@Runnable)
            }
        }, 0, 10)

        return task
    }

    private fun playBlueBorder(player: Player, island: Island) {
        val worldBorder = WorldBorder()
        worldBorder.world = (island.center.world as CraftWorld).handle

        worldBorder.warningDistance = 0
        worldBorder.warningTime = 0

        worldBorder.size = island.islandRange.toDouble()
        worldBorder.setCenter(island.center.x, island.center.z)

        (player as CraftPlayer).handle.playerConnection.sendPacket(PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE))

    }

}