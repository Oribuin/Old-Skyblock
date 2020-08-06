package xyz.oribuin.skyblock.managers

import xyz.oribuin.skyblock.menus.Menu
import xyz.oribuin.skyblock.Skyblock
import java.util.*
import java.util.function.Consumer

class GuiManager(plugin: Skyblock) : Manager(plugin) {
    private val menus = LinkedList<Menu>()


    fun registerMenus() {
        // Unused
    }

    override fun reload() {
        menus.forEach(Consumer { obj: Menu -> obj.reload() })
    }

    override fun disable() {
        // Unused
    }
}