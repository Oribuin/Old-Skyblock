package xyz.oribuin.skyblock

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import xyz.oribuin.skyblock.commands.CmdIsland
import xyz.oribuin.skyblock.commands.OriCommand
import xyz.oribuin.skyblock.listeners.GeneralListeners
import xyz.oribuin.skyblock.managers.*
import xyz.oribuin.skyblock.utils.FileUtils
import xyz.oribuin.skyblock.utils.HexUtils
import kotlin.reflect.KClass

class Skyblock : JavaPlugin() {

    // Manager reflection taken from RoseStacker.
    private var managers: MutableMap<KClass<out Manager>, Manager> = HashMap()

    override fun onEnable() {
        this.getManager(ConfigManager::class)
        this.getManager(DataManager::class)
        this.getManager(HookManager::class)
        this.getManager(IslandManager::class)
        this.getManager(MessageManager::class)
        this.getManager(WorldManager::class)

        // Register plugin commands
        this.registerCommands(CmdIsland(this))

        // Register plugin listeners
        this.registerListeners(GeneralListeners(this))

        this.reload()
        this.saveDefaultConfig()
        createSchematics("desert", "ice", "default", "mesa", "mushroom", "nether", "plains")



        Bukkit.getScheduler().runTaskTimer(this, Runnable {
            Bukkit.getOnlinePlayers().forEach { player ->
                val islandManager = getManager(IslandManager::class)

                player.sendMessage(HexUtils.colorify("<r:0.5>Is on island: ${islandManager.isOnOwnIsland(player)}"))
            }
        }, 0, 1)

    }

    private fun registerCommands(vararg commands: OriCommand) {
        for (cmd in commands) {
            cmd.register()
        }
    }

    private fun registerListeners(vararg listeners: Listener) {
        for (listener in listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this)
        }
    }

    private fun createSchematics(vararg schems: String) {
        for (schematic in schems)
            FileUtils.createDirFile(this, "schematics", "$schematic.schematic")
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
