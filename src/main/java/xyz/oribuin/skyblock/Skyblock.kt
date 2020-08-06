package xyz.oribuin.skyblock

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import xyz.oribuin.skyblock.commands.CmdIsland
import xyz.oribuin.skyblock.commands.OriCommand
import xyz.oribuin.skyblock.managers.*

class Skyblock : JavaPlugin() {

    lateinit var configManager: ConfigManager
    lateinit var dataManager: DataManager
    lateinit var hookManager: HookManager
    lateinit var islandManager: IslandManager
    lateinit var messageManager: MessageManager
    lateinit var worldManager: WorldManager

    override fun onEnable() {

        // Register plugin commands.
        this.registerCommands(CmdIsland(this))


        // Register managers
        this.configManager = ConfigManager(this)
        this.dataManager = DataManager(this)
        this.hookManager = HookManager(this)
        this.islandManager = IslandManager(this)
        this.messageManager = MessageManager(this)
        this.worldManager = WorldManager(this)


        this.saveDefaultConfig()
        this.reload()
    }

    private fun registerCommands(vararg commands: OriCommand) {
        for (cmd in commands) {
            cmd.registerCommand()
        }
    }

    private fun registerListeners(vararg listeners: Listener) {
        for (listener in listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this)
        }
    }

    fun reload() {
        this.configManager.reload()
        this.dataManager.reload()
        this.hookManager.reload()
        this.islandManager.reload()
        this.messageManager.reload()
        this.worldManager.reload()
    }

    override fun onDisable() {
        this.disable()
    }

    private fun disable() {
        this.configManager.disable()
        this.dataManager.disable()
        this.hookManager.disable()
        this.islandManager.disable()
        this.messageManager.disable()
        this.worldManager.disable()
    }
}