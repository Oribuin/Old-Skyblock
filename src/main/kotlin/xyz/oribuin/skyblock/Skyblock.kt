package xyz.oribuin.skyblock

import org.bukkit.Bukkit
import xyz.oribuin.skyblock.command.CmdIsland
import xyz.oribuin.skyblock.library.FileUtils
import xyz.oribuin.skyblock.library.HexUtils.colorify
import xyz.oribuin.skyblock.library.OriPlugin
import xyz.oribuin.skyblock.listener.BlockListeners
import xyz.oribuin.skyblock.listener.PlayerListeners
import xyz.oribuin.skyblock.manager.*

class Skyblock : OriPlugin() {
    override fun enablePlugin() {
        this.getManager(ConfigManager::class)
        this.getManager(DataManager::class)
        this.getManager(BorderManager::class)
        this.getManager(HookManager::class)
        this.getManager(IslandManager::class)
        this.getManager(MessageManager::class)
        this.getManager(WorldManager::class)

        // Register plugin commands
        this.registerCommands(CmdIsland(this))

        // Register plugin listeners
        this.registerListeners(
                BlockListeners(this),

                PlayerListeners(this)
        )

        this.reload()
        this.saveDefaultConfig()
        createSchematics("desert", "ice", "default", "mesa", "mushroom", "nether", "plains")

        Bukkit.getScheduler().runTaskTimer(this, Runnable {
            Bukkit.getOnlinePlayers().forEach { player ->
                val islandManager = getManager(IslandManager::class)

                player.sendMessage(colorify("<r:0.5>Is on island: ${(islandManager.getIslandOn(player)?: return@forEach).name}"))
            }
        }, 0, 1)
    }

    override fun disablePlugin() {
        // Unused

    }

    private fun createSchematics(vararg schems: String) {
        for (schematic in schems)
            FileUtils.createDirFile(this, "schematics", "$schematic.schematic")
    }
}
