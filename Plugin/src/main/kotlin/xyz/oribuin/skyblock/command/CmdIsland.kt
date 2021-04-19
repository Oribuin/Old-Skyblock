package xyz.oribuin.skyblock.command

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import xyz.oribuin.orilibrary.command.Command
import xyz.oribuin.orilibrary.libs.jetbrains.annotations.NotNull
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.manager.DataManager
import xyz.oribuin.skyblock.manager.IslandManager
import xyz.oribuin.skyblock.nms.BorderColor
import xyz.oribuin.skyblock.nms.NMSAdapter

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

            when (args[0].toLowerCase()) {
                "border" -> {
                    val island = this.plugin.getManager(DataManager::class.java).getIsland(player)
                    if (island != null && args.size == 2) {
                        NMSAdapter.handler.sendWorldBorder(player, BorderColor.valueOf(args[1].toUpperCase()), island.size.toDouble(), island.location)
                        println("Creating world border.")
                        return
                    }

                    println("Player does not have island")
                }

                "teleport" -> {
                    val island = this.plugin.getManager(DataManager::class.java).getIsland(player)
                    if (island != null) {
                        player.teleport(island.spawnPoint.clone().add(0.0, 1.0, 0.0))
                        println("Teleporting to island.")
                        return
                    }

                    println("Player does not have island")

                }

                else -> {
                    println("Creating Island.")
                    this.plugin.getManager(IslandManager::class.java).createIsland(player, args[0])

                }

            }

        }

    }

}