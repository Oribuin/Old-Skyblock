package xyz.oribuin.skyblock.island

import org.bukkit.Location
import java.util.*

data class Island(var id: Int, var owner: UUID, var location: Location, var name: String) {
    var size = 200
    var spawnPoint = location
    var locked = false
    var members = mutableListOf<Member>()
    var coops = mutableListOf<UUID>()
}
