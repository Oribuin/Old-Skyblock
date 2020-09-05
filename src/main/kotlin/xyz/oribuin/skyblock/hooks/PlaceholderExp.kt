package xyz.oribuin.skyblock.hooks

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.entity.Player
import xyz.oribuin.skyblock.Skyblock

class PlaceholderExp(private val plugin: Skyblock) : PlaceholderExpansion() {

    override fun onPlaceholderRequest(player: Player, params: String): String? {
        when (params.toLowerCase()) {
            "example" -> {
                return "example"
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
