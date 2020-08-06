package xyz.oribuin.skyblock.island

import org.bukkit.Location
import java.util.*

data class Island(val name: String, val center: Location, val owner: UUID, val islandRange: Int) {
    var isLocked: Boolean = false
    var spawnPoint: Location = center
}