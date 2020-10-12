package xyz.oribuin.skyblock.hook

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.island.IslandMember

class PlaceholderExp(private val plugin: Skyblock) : PlaceholderExpansion() {

    override fun onPlaceholderRequest(player: Player, params: String): String? {
        val member = IslandMember(plugin, player.uniqueId)

        when (params.toLowerCase()) {
            "island_name" -> {
                return member.getIsland()?.name
            }

            "island_size" -> {
                return member.getIsland()?.islandRange.toString()
            }

            "has_island" -> {
                return member.hasIsland.toString()
            }

            "owner" -> {
                return member.getIsland()?.owner?.let { Bukkit.getOfflinePlayer(it).name }
            }
        }

        return null
    }

    override fun persist(): Boolean {
        return true
    }

    override fun getIdentifier(): String {
        return plugin.description.name.toLowerCase()
    }

    override fun getAuthor(): String {
        return plugin.description.authors[0]
    }

    override fun getVersion(): String {
        return plugin.description.version
    }
}
