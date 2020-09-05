package xyz.oribuin.skyblock.menus

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.utils.FileUtils.createMenuFile
import java.io.File

abstract class Menu(private val plugin: Skyblock, private val guiName: String) {
    private val menuConfig: FileConfiguration

    init {
        createMenuFile(plugin, guiName)
        menuConfig = YamlConfiguration.loadConfiguration(menuFile)
    }

    fun reload() {
        YamlConfiguration.loadConfiguration(menuFile)
    }

    fun getGuiName(): String {
        return guiName.toLowerCase()
    }

    private val menuFile: File
        get() = File(plugin.dataFolder.toString() + File.separator + "menus", getGuiName() + ".yml")

}
