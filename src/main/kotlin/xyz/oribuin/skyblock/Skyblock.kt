package xyz.oribuin.skyblock

import me.bristermitten.pdm.PDMBuilder
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import xyz.oribuin.skyblock.command.CmdIsland
import xyz.oribuin.skyblock.command.OriCommand
import xyz.oribuin.skyblock.listener.GeneralListeners
import xyz.oribuin.skyblock.manager.*
import xyz.oribuin.skyblock.util.FileUtils
import kotlin.reflect.KClass

class Skyblock : JavaPlugin() {

    private var managers = mutableMapOf<KClass<out Manager>, Manager>()

    override fun onLoad() {
        PDMBuilder(this).build().loadAllDependencies().join()
    }

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

        /*
        Bukkit.getScheduler().runTaskTimer(this, Runnable {
            Bukkit.getOnlinePlayers().forEach { player ->
                val islandManager = getManager(IslandManager::class)

                player.sendMessage(HexUtils.colorify("<r:0.5>Is on island: ${islandManager.isOnOwnIsland(player)}"))
            }
        }, 0, 1)
         */
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
        managers.values.forEach { manager -> manager.reload() }
    }

    override fun onDisable() {
        this.disableManagers()
    }

    private fun disableManagers() {
        managers.values.forEach { manager -> manager.disable() }
    }

    fun <M : Manager> getManager(managerClass: KClass<M>): M {
        synchronized(managers) {
            @Suppress("UNCHECKED_CAST")
            if (managers.containsKey(managerClass))
                return managers[managerClass] as M

            return try {
                val manager = managerClass.constructors.first().call(this)
                manager.reload()
                managers[managerClass] = manager
                manager
            } catch (ex: ReflectiveOperationException) {
                error("Failed to load manager for ${managerClass.simpleName}")
            }
        }
    }
}
