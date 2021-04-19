package xyz.oribuin.skyblock.manager

import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import xyz.oribuin.orilibrary.manager.Manager
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.island.Island
import xyz.oribuin.skyblock.util.PluginUtils

class IslandManager(private val plugin: Skyblock) : Manager(plugin) {

    val world = this.plugin.getManager(WorldManager::class.java).world
    val data = this.plugin.getManager(DataManager::class.java)

    override fun enable() {

    }

    override fun disable() {

    }

    fun createIsland(player: Player, name: String): Boolean {

        // TODO, Remove this.
        if (this.data.getIsland(player) != null) {
            return false
        }

        val newIsland = Island(PluginUtils.getNextId(data.islands.map { it.id }.toList()), player.uniqueId, PluginUtils.getNextIslandLocation(data.islands.size, world), name)
        island.size = size
    }

}