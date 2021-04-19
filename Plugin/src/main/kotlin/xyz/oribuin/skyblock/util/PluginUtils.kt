package xyz.oribuin.skyblock.util

import org.bukkit.Location
import org.bukkit.World
import kotlin.math.floor
import kotlin.math.sqrt

object PluginUtils {

    /**
     * @author Esophose
     *
     * Gets the smallest positive integer greater than 0 from a list
     *
     * @param existingIds The list containing non-available ids
     * @return The smallest positive integer not in the given list
     */
    fun getNextId(existingIds: Collection<Int>): Int {
        val copy = existingIds.sorted().toMutableList()
        copy.removeIf { it <= 0 }

        var current = 1
        for (i in copy) {
            if (i == current) {
                current++
            } else break
        }

        return current
    }

    /**
     * @author  https://stackoverflow.com/a/19287714
     * Code happily stolen from Esophose
     */
    fun getNextIslandLocation(locationId: Int, world: World?): Location {
        if (locationId == 0)
            return Location(world, 0.0, 65.0, 0.0)

        val n = locationId - 1
        val r = floor((sqrt(n + 1.0) - 1) / 2) + 1
        val p = (8 * r * (r - 1)) / 2
        val en = r * 2
        val a = (1 + n - p) % (r * 8)

        var x = 0.0
        var z = 0.0

        when (floor(a / (r * 2)).toInt()) {
            0 -> {
                x = a - r
                z = -r
            }
            1 -> {
                x = r
                z = (a % en) - r
            }
            2 -> {
                x = r - (a % en)
                z = r
            }
            3 -> {
                x = -r
                z = r - (a % en)
            }
        }

        return Location(world, x * 350, 65.0, z * 350, 180f, 0f)
    }

}