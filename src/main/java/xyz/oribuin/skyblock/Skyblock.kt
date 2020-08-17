package xyz.oribuin.skyblock

import me.bristermitten.pdm.PDMBuilder
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import xyz.oribuin.skyblock.commands.CmdIsland
import xyz.oribuin.skyblock.commands.OriCommand
import xyz.oribuin.skyblock.managers.*
import xyz.oribuin.skyblock.utils.FileUtils
import kotlin.reflect.KClass

class Skyblock : JavaPlugin() {

    // Manager reflection taken from RoseStacker.
    private val managers: MutableMap<KClass<out Manager>, Manager> = HashMap()

    override fun onEnable() {
        PDMBuilder(this).build().loadAllDependencies().join()

        this.getManager(ConfigManager::class)
        this.getManager(DataManager::class)
        this.getManager(HookManager::class)
        this.getManager(IslandManager::class)
        this.getManager(MessageManager::class)
        this.getManager(WorldManager::class)

        // Register plugin commands.
        this.registerCommands(CmdIsland(this))

        // Register plugin listeners
        this.registerListeners()

        this.reload()
        this.saveDefaultConfig()

        FileUtils.createDirFile(this, "schematics", "plains.schematic")

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
        this.disableManagers()
        this.server.scheduler.cancelTasks(this)
        this.managers.values.forEach { manager -> manager.reload() }
    }

    override fun onDisable() {
        this.disableManagers()
    }

    private fun disableManagers() {
        this.managers.values.forEach { manager -> manager.disable() }
    }

    fun <M : Manager> getManager(managerClass: KClass<M>): M {
        synchronized(this.managers) {
            @Suppress("UNCHECKED_CAST")
            if (this.managers.containsKey(managerClass))
                return this.managers[managerClass] as M

            return try {
                val manager = managerClass.constructors.first().call(this)
                manager.reload()
                this.managers[managerClass] = manager
                manager
            } catch (ex: ReflectiveOperationException) {
                error("Failed to load manager for ${managerClass.simpleName}")
            }
        }
    }
}