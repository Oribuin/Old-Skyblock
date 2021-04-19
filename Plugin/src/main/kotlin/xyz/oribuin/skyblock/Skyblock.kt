package xyz.oribuin.skyblock

import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import xyz.oribuin.orilibrary.OriPlugin
import xyz.oribuin.skyblock.command.CmdIsland
import xyz.oribuin.skyblock.manager.DataManager
import xyz.oribuin.skyblock.manager.IslandManager
import xyz.oribuin.skyblock.manager.WorldManager
import xyz.oribuin.skyblock.nms.NMSAdapter

class Skyblock : OriPlugin() {

    override fun enablePlugin() {

        // Check if server has vault
        if (!hasPlugin("Vault")) return

        // Create the world
       this.getManager(WorldManager::class.java).createWorld(this.config.getString("world-name") ?: "skyblock_islands")

        // Load Plugin Managers Asynchronously
        this.server.scheduler.runTaskAsynchronously(this, Runnable {
            this.getManager(DataManager::class.java)
            this.getManager(IslandManager::class.java)

        })

        // Load Commands
        CmdIsland(this).register(null, null)

        // Load Listeners
        // TODO

        // Load GUIS
        // TODO
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