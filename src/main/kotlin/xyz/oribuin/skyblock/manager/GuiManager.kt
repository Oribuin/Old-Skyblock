package xyz.oribuin.skyblock.manager

import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.library.Manager
import xyz.oribuin.skyblock.menu.Menu
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
