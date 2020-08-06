package xyz.oribuin.skyblock.island

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import java.util.*

data class IslandMember(val uuid: UUID, val island: Island) {
    private var player: OfflinePlayer? = null

    fun getPlayer(): OfflinePlayer? {
        if (player == null)
            player = Bukkit.getOfflinePlayer(uuid)

        return player
    }

    fun clearPlayer() {
        player = null
    }

}