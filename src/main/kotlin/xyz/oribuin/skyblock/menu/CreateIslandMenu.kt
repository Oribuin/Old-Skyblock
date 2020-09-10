package xyz.oribuin.skyblock.menu

import dev.rosewood.guiframework.GuiFactory
import dev.rosewood.guiframework.GuiFramework
import dev.rosewood.guiframework.gui.ClickAction
import dev.rosewood.guiframework.gui.GuiButton
import dev.rosewood.guiframework.gui.GuiSize
import org.apache.commons.lang.StringUtils
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.manager.ConfigManager
import xyz.oribuin.skyblock.manager.IslandManager
import xyz.oribuin.skyblock.manager.MessageManager
import xyz.oribuin.skyblock.util.HexUtils
import xyz.oribuin.skyblock.util.StringPlaceholders

class CreateIslandMenu(private val plugin: Skyblock, private val player: Player, private val islandName: String) {
    private val guiFramework = GuiFramework.instantiate(plugin)
    private val container = GuiFactory.createContainer().setTickRate(2)

    companion object {
        var instance: CreateIslandMenu? = null
            private set
    }

    private fun buildMenu() {

        val screen = GuiFactory.createScreen(container, GuiSize.ROWS_THREE)
                .setTitle(HexUtils.colorify("Select your island design!"))

        val borderSlots: List<Int> = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26)

        val borderItem = ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE)
        val borderMeta = borderItem.itemMeta
        if (borderMeta != null) {
            borderMeta.setDisplayName(" ")
            borderItem.itemMeta = borderMeta
        }

        borderSlots.forEach { slot -> screen.addItemStackAt(slot, borderItem) }

        screen.addButtonAt(10, getIslandDesignButton("default",
                listOf(" &f» &bIsland Type: &fDefault",
                        " &f» &bName: &f$islandName",
                        " ",
                        "&bClick to create your Island!"), Material.OAK_SAPLING))

        screen.addButtonAt(11, getIslandDesignButton("plains",
                listOf(" &f» &bIsland Type: &fPlains",
                        " &f» &bName: &f$islandName",
                        " ",
                        "&bClick to create your Island!"), Material.GRASS_BLOCK))

        screen.addButtonAt(12, getIslandDesignButton("ice",
                listOf(" &f» &bIsland Type: &fIce",
                        " &f» &bName: &f$islandName",
                        " ",
                        "&bClick to create your Island!"), Material.ICE))

        screen.addButtonAt(13, getIslandDesignButton("desert",
                listOf(" &f» &bIsland Type: &fDesert",
                        " &f» &bName: &f$islandName",
                        " ",
                        "&bClick to create your Island!"), Material.SAND))

        screen.addButtonAt(14, getIslandDesignButton("Mesa",
                listOf(" &f» &bIsland Type: &fMesa",
                        " &f» &bName: &f$islandName",
                        " ",
                        "&bClick to create your Island!"), Material.BRICK))

        screen.addButtonAt(15, getIslandDesignButton("Mushroom",
                listOf(" &f» &bIsland Type: &fDesert",
                        " &f» &bName: &f$islandName",
                        " ",
                        "&bClick to create your Island!"), Material.RED_MUSHROOM))

        screen.addButtonAt(16, getIslandDesignButton("Nether",
                listOf(" &f» &bIsland Type: &fNether",
                        " &f» &bName: &f$islandName",
                        " ",
                        "&bClick to create your Island!"), Material.MUSIC_DISC_PIGSTEP))

        container.addScreen(screen)
        guiFramework.guiManager.registerGui(container)
    }

    private fun getIslandDesignButton(name: String, lore: List<String>, material: Material): GuiButton {
        val loreFormatted = mutableListOf<String>()
        for (string in lore)
            loreFormatted.add(HexUtils.colorify(string))


        return GuiFactory.createButton()
                .setName(HexUtils.colorify("#49fff6${StringUtils.capitalize(name)} Island"))
                .setLore(loreFormatted)
                .setGlowing(true)
                .setIcon(material)
                .setClickSound(Sound.ENTITY_ARROW_HIT_PLAYER)
                .setClickAction({ event: InventoryClickEvent ->
                    val pplayer = event.whoClicked as Player

                    val island = plugin.getManager(IslandManager::class).createIsland(islandName, name, pplayer.uniqueId, ConfigManager.Setting.SETTINGS_SIZE.int)

                    plugin.getManager(MessageManager::class).sendMessage(pplayer, "commands.created-island", StringPlaceholders.builder("island_name", island.name).addPlaceholder("island_type", StringUtils.capitalize(name)).build())

                    ClickAction.CLOSE
                })
    }

    fun openMenu() {
        if (isInvalid) buildMenu()
        container.openFor(player)
    }

    private val isInvalid: Boolean
        get() = !guiFramework.guiManager.activeGuis.contains(container)

}
