package xyz.oribuin.skyblock.island

import org.bukkit.Location
import java.util.*

data class Island(val id: Int, var name: String, var center: Location, var owner: UUID, val islandRange: Int) {
    var isLocked: Boolean = false
    var spawnPoint: Location = center
}