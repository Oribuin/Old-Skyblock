package xyz.oribuin.skyblock.nms

import org.bukkit.Bukkit

object NMSAdapter {
    lateinit var handler: NMSHandler
        private set

    var isValidVersion = true

    init {
        try {
            val name = Bukkit.getServer().javaClass.getPackage().name
            val version = name.substring(name.lastIndexOf('.') + 1)
            val clazz = Class.forName("xyz.oribuin.skyblock.nms.$version.NMSHandlerImpl")
            handler = clazz.getConstructor().newInstance() as NMSHandler
        } catch (ignored: Exception) {
            isValidVersion = false
        }

    }

}