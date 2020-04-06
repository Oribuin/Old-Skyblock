package xyz.oribuin.skyblock.menus;

import dev.esophose.guiframework.GuiFramework;
import dev.esophose.guiframework.gui.ClickAction;
import dev.esophose.guiframework.gui.GuiButton;
import dev.esophose.guiframework.gui.GuiContainer;
import dev.esophose.guiframework.gui.GuiSize;
import dev.esophose.guiframework.gui.screen.GuiScreen;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.oribuin.skyblock.Skyblock;

import java.util.ArrayList;
import java.util.List;

public class MenuIslandCreation {

    private Skyblock plugin = Skyblock.getInstance();
    private GuiFramework guiFramework;
    private GuiContainer guiContainer;
    private Player player;

    public MenuIslandCreation(Player player) {
        this.guiFramework = GuiFramework.instantiate(this.plugin);
        this.guiContainer = null;
        this.player = player;
    }

    public void openFor() {
        if (this.isInvalid())
            buildGui();
        this.guiContainer.openFor(player);
    }

    public void buildGui() {
        this.guiContainer = new GuiContainer();

        List<Integer> borderSlots = new ArrayList<>();
        for (int i = 0; i <= 8; i++) borderSlots.add(i);
        for (int i = 18; i <= 26; i++) borderSlots.add(i);
        borderSlots.add(9);
        borderSlots.add(17);

        GuiScreen mainScreen = new GuiScreen(this.guiContainer, GuiSize.ROWS_THREE)
                .setTitle("Select your island");


        ItemStack borderItem = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        ItemMeta borderMeta = borderItem.getItemMeta();
        if (borderMeta != null) {
            borderMeta.setDisplayName(" ");
            borderMeta.addItemFlags(ItemFlag.values());
            borderItem.setItemMeta(borderMeta);
        }

        for (int slot : borderSlots)
            mainScreen.addItemStackAt(slot, borderItem);


        mainScreen.addButtonAt(10, new GuiButton()
                .setIcon(Material.GRASS_BLOCK)
                .setName("&bBasic Island")
                .setLore(" ", " &f» &bClassic Skyblock Island", " &f» &bPerfect Starting Island!")
                .setGlowing(true)
                .setClickAction(event -> ClickAction.NOTHING));

        mainScreen.addButtonAt(11, new GuiButton()
                .setIcon(Material.CACTUS)
                .setName("&bDesert Island")
                .setLore(" ", " &f» &bDesert Themed Island", " &f» &bPerfect for any sand lovers!")
                .setGlowing(true)
                .setClickAction(event -> ClickAction.NOTHING));

        mainScreen.addButtonAt(12, new GuiButton()
                .setIcon(Material.ICE)
                .setName("&bIce Island")
                .setLore(" ", " &f» &bIce Themed Island", " &f» &bPerfect for winter lovers!")
                .setGlowing(true)
                .setClickAction(event -> ClickAction.NOTHING));

        mainScreen.addButtonAt(13, new GuiButton()
                .setIcon(Material.DEAD_BUSH)
                .setName("&bMesa Island")
                .setLore(" ", " &f» &bMesa Themed Island", " &f» &bPerfect for pottery!")
                .setGlowing(true)
                .setClickAction(event -> ClickAction.NOTHING));

        mainScreen.addButtonAt(14, new GuiButton()
                .setIcon(Material.RED_MUSHROOM_BLOCK)
                .setName("&bMushroom Island")
                .setLore(" ", " &f» &bMushroom Themed Island", " &f» &bPerfect for soup lovers!")
                .setGlowing(true)
                .setClickAction(event -> ClickAction.NOTHING));

        mainScreen.addButtonAt(15, new GuiButton()
                .setIcon(Material.LAVA_BUCKET)
                .setName("&bNether Island")
                .setLore(" ", " &f» &bNether Themed Island", " &f» &bPerfect for hotties:tm:")
                .setGlowing(true)
                .setClickAction(event -> ClickAction.NOTHING));

        mainScreen.addButtonAt(10, new GuiButton()
                .setIcon(Material.GRASS_BLOCK)
                .setName("&bBasic Island")
                .setLore(" ", " &f» &bClassic Skyblock Island", " &f» &bPerfect Starting Island")
                .setGlowing(true)
                .setClickAction(event -> ClickAction.NOTHING));

        mainScreen.addButtonAt(10, new GuiButton()
                .setIcon(Material.GRASS_BLOCK)
                .setName("&bBasic Island")
                .setLore(" ", " &f» &bClassic Skyblock Island", " &f» &bPerfect Starting Island")
                .setGlowing(true)
                .setClickAction(event -> ClickAction.NOTHING));
    }

    public boolean isInvalid() {
        return this.guiContainer == null || !this.guiFramework.getGuiManager().getActiveGuis().contains(this.guiContainer);
    }
}
