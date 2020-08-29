package xyz.oribuin.skyblock.managers

import org.bukkit.Location
import xyz.oribuin.skyblock.Skyblock

// I suck at maths so this is required because i will easily forget what im doing
open class MathManager(plugin: Skyblock): Manager(plugin) {
    override fun reload() {
        // Unused
    }

    override fun disable() {
        // Unused
    }

    /**
     * Get a cuboid from location and radius
     *
     * @param center The center of the cuboid
     * @param radiusX The radius of the cuboid in direction X
     * @param radiusY The radius of the cuboid in direction Y
     * @param radiusZ The radius of the cuboid in direction Z
     *
     * @return min & max array value (Min = getCuboid[0], Max = getCuboid[1])
     */
    fun getCuboid(center: Location, radiusX: Int, radiusY: Int, radiusZ: Int): Array<Location> {
        val min = center.clone().subtract(radiusX.toDouble(), radiusY.toDouble(), radiusZ.toDouble())
        val max = center.clone().add(radiusX.toDouble(), radiusY.toDouble(), radiusZ.toDouble())
        return arrayOf(min, max)
    }


}