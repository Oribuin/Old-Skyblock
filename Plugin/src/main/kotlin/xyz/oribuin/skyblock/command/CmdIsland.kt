package xyz.oribuin.skyblock.command

import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import xyz.oribuin.orilibrary.command.Command
import xyz.oribuin.orilibrary.libs.jetbrains.annotations.NotNull
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.island.Island
import xyz.oribuin.skyblock.manager.DataManager
import xyz.oribuin.skyblock.manager.WorldManager
import xyz.oribuin.skyblock.util.PluginUtils

@Command.Info(
    name = "island",
    description = "Main command for the plugin.",
    aliases = ["is"],
    permission = "oriskyblock.use",
    playerOnly = true,
    usage = "/oriskyblock <name>"
)

class CmdIsland(private val plugin: Skyblock) : Command(plugin) {

    override fun runFunction(sender: @NotNull CommandSender, label: @NotNull String, args: Array<String>) {
        val player = sender as Player

        if (args.isNotEmpty()) {
            val size = this.plugin.config.getInt("island-size")
            val world = this.plugin.getManager(WorldManager::class.java).world
            val data = this.plugin.getManager(DataManager::class.java)

            val island = Island(PluginUtils.getNextId(data.islands.map { it.id }.toList()), player.uniqueId, PluginUtils.getNextIslandLocation(data.islands.size, world), args[0])
            island.size = size

            this.plugin.getManager(DataManager::class.java).saveIsland(island)
            island.location.block.type = Material.BEDROCK

            val loc = island.location.clone()
            player.teleport(loc.add(0.0, 1.0, 0.0))
            player.sendMessage("Welcome to your island.")
            return
        }

    }

}