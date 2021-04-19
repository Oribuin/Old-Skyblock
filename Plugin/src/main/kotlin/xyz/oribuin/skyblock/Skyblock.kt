package xyz.oribuin.skyblock

import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import xyz.oribuin.orilibrary.OriPlugin

class Skyblock : OriPlugin() {

    override fun enablePlugin() {

    }

    override fun disablePlugin() {

    }

    /**
     * Check if the server has a plugin enabled.
     *
     * @param pluginName The plugin
     * @return true if plugin is enabled.
     */
    private fun hasPlugin(pluginName: String): Boolean {
        val plugin = server.pluginManager.getPlugin(pluginName)
        if (plugin == null || !plugin.isEnabled) {
            logger.severe("Please install $pluginName to use this plugin.")
            logger.severe("Disabling...")
            server.pluginManager.disablePlugin(this)
            return false
        }
        return true
    }


    companion object {

        fun getEconomy(): Economy {
            return Bukkit.getServicesManager().getRegistration(Economy::class.java)!!.provider
        }

    }
}