package xyz.oribuin.skyblock.util

object PluginUtils {

    /**
     * @author Esophose
     *
     * Gets the smallest positive integer greater than 0 from a list
     *
     * @param existingIds The list containing non-available ids
     * @return The smallest positive integer not in the given list
     */
    fun getNextReportId(existingIds: Collection<Int>): Int {
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

}