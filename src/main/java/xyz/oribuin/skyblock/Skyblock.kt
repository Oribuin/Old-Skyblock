package xyz.oribuin.skyblock

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import xyz.oribuin.skyblock.commands.CmdIsland
import xyz.oribuin.skyblock.commands.OriCommand
import xyz.oribuin.skyblock.managers.*

class Skyblock : JavaPlugin() {

    // Manager reflection taken from RoseStacker.
    private lateinit var managers: MutableMap<Class<out Manager>, Manager>

    override fun onEnable() {

        // Register plugin commands.
        this.registerCommands(CmdIsland(this))

        this.managers = LinkedHashMap()


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

    /**
     * @author Esophose
     */
    fun reload() {
        this.disableManagers()
        this.managers.values.forEach(Manager::reload)

        this.getManager(ConfigManager::class.java)
        this.getManager(DataManager::class.java)
        this.getManager(HookManager::class.java)
        this.getManager(IslandManager::class.java)
        this.getManager(MessageManager::class.java)
        this.getManager(WorldManager::class.java)
    }

    override fun onDisable() {
        this.disableManagers()
        this.managers.clear()
    }

    private fun disableManagers() {
        val managers: List<Manager> = ArrayList(managers.values)
        managers.forEach { obj: Manager -> obj.disable() }
    }

    fun <T : Manager> getManager(managerClass: Class<T>): T {
        return managers[managerClass] as T
    }
}