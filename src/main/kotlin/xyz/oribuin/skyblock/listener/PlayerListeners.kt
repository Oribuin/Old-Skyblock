package xyz.oribuin.skyblock.listener

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.event.player.*
import org.bukkit.inventory.EquipmentSlot
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.island.IslandMember
import xyz.oribuin.skyblock.manager.ConfigManager
import xyz.oribuin.skyblock.manager.MessageManager
import xyz.oribuin.skyblock.util.PluginUtils

class PlayerListeners(private val plugin: Skyblock) : Listener {
    private val msg = plugin.getManager(MessageManager::class)

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onBedEnter(event: PlayerBedEnterEvent) {
        val member = IslandMember(plugin, event.player.uniqueId)

        if (!member.hasIsland)
            return

        if (!member.onIsland(member.getIsland() ?: return)) {
            msg.sendActionMessage(event.player, "listener-messages.cant-enter-bed")
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onDropItem(event: PlayerDropItemEvent) {
        val member = IslandMember(plugin, event.player.uniqueId)

        if (!member.hasIsland)
            return

        if (!member.onIsland(member.getIsland() ?: return)) {
            msg.sendActionMessage(event.player, "listener-messages.cant-drop-items")
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onBucketFill(event: PlayerBucketFillEvent) {
        val member = IslandMember(plugin, event.player.uniqueId)

        if (!member.hasIsland)
            return

        if (!member.onIsland(member.getIsland() ?: return)) {
            msg.sendActionMessage(event.player, "listener-messages.cant-use-bucket")
            event.isCancelled = true
        }
    }


    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onBucketEmpty(event: PlayerBucketEmptyEvent) {
        val member = IslandMember(plugin, event.player.uniqueId)

        if (!member.hasIsland)
            return

        if (!member.onIsland(member.getIsland() ?: return)) {
            msg.sendActionMessage(event.player, "listener-messages.cant-use-bucket")
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onYeet(event: ProjectileLaunchEvent) {
        if (event.entity !is Player)
            return

        val player = event.entity as Player

        val member = IslandMember(plugin, player.uniqueId)
        if (!member.hasIsland)
            return

        if (!member.onIsland(member.getIsland() ?: return)) {
            msg.sendActionMessage(player, "listener-messages.cant-throw-projectile")
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onCatchChonk(event: PlayerFishEvent) {
        val member = IslandMember(plugin, event.player.uniqueId)

        if (!member.hasIsland)
            return

        if (!member.onIsland(member.getIsland() ?: return)) {
            msg.sendActionMessage(event.player, "listener-messages.cant-fish")
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onHarvest(event: PlayerHarvestBlockEvent) {
        val member = IslandMember(plugin, event.player.uniqueId)

        if (!member.hasIsland)
            return

        if (!member.onIsland(member.getIsland() ?: return)) {
            msg.sendActionMessage(event.player, "listener-messages.cant-harvest-block")
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onInteractEntity(event: PlayerInteractEntityEvent) {
        val member = IslandMember(plugin, event.player.uniqueId)

        if (!member.hasIsland)
            return

        if (!member.onIsland(member.getIsland() ?: return)) {
            msg.sendActionMessage(event.player, "listener-messages.cant-interact")
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onInteractEntity(event: PlayerInteractEvent) {
        val member = IslandMember(plugin, event.player.uniqueId)
        if (event.hand != EquipmentSlot.HAND)
            return

        if (!member.hasIsland)
            return

        if (!member.onIsland(member.getIsland() ?: return)) {
            msg.sendActionMessage(event.player, "listener-messages.cant-interact")
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onPickup(event: EntityPickupItemEvent) {
        if (event.entity !is Player)
            return

        val member = IslandMember(plugin, event.entity.uniqueId)
        if (!member.hasIsland)
            return

        if (!member.onIsland(member.getIsland() ?: return)) {
            PluginUtils.createDelay(event.entity as Player, 120, { msg.sendActionMessage(event.entity as Player, "listener-messages.cant-pickup-item") }, null)
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onShear(event: PlayerShearEntityEvent) {
        val member = IslandMember(plugin, event.player.uniqueId)

        if (!member.hasIsland)
            return

        if (!member.onIsland(member.getIsland() ?: return)) {
            msg.sendActionMessage(event.player, "listener-messages.cant-shear")
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onSteal(event: PlayerTakeLecternBookEvent) {
        val member = IslandMember(plugin, event.player.uniqueId)

        if (!member.hasIsland)
            return

        if (!member.onIsland(member.getIsland() ?: return)) {
            msg.sendActionMessage(event.player, "listener-messages.cant-take-book")
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onBookEdit(event: PlayerEditBookEvent) {
        val member = IslandMember(plugin, event.player.uniqueId)

        if (!member.hasIsland)
            return

        if (!member.onIsland(member.getIsland() ?: return)) {
            msg.sendActionMessage(event.player, "listener-messages.cant-edit-book")
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onDeath(event: PlayerDeathEvent) {
        val member = IslandMember(plugin, event.entity.uniqueId)

        if (!member.hasIsland)
            return

        if (!member.onIsland(member.getIsland() ?: return) && ConfigManager.Setting.SETTINGS_KEEP_INVENTORY.boolean) {
            event.keepInventory = true
        }

    }
}