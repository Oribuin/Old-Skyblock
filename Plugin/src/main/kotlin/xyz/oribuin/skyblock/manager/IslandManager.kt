package xyz.oribuin.skyblock.manager

import org.bukkit.entity.Player
import xyz.oribuin.orilibrary.manager.Manager
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.island.Island
import xyz.oribuin.skyblock.util.PluginUtils

class IslandManager(private val plugin: Skyblock) : Manager(plugin) {

    private val world = this.plugin.getManager(WorldManager::class.java).world
    private val data = this.plugin.getManager(DataManager::class.java)

    override fun enable() {
        // Unused
    }

    override fun disable() {
        // Unused
    }

    /**
     * Create & Save a player's island.
     *
     * @param player The player.
     * @param name   The island name.
     * @return The player's new island.
     */
    fun createIsland(player: Player, name: String): Island? {

        // TODO, Remove this.
        if (this.data.getIsland(player) != null) {
            println("${player.name} Has an island.")
            return null
        }

        val island = Island(PluginUtils.getNextId(data.islands.map { it.id }.toList()), player.uniqueId, PluginUtils.getNextIslandLocation(data.islands.size, world), name)
        island.size = this.plugin.config.getInt("island-size")

        this.data.saveIsland(island)
        return island
    }

}