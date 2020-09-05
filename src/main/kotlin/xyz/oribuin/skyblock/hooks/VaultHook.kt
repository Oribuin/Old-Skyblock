package xyz.oribuin.skyblock.hooks

import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.permission.Permission
import org.bukkit.Bukkit
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.managers.Manager

class VaultHook(plugin: Skyblock) : Manager(plugin) {
    override fun reload() {
        // Unused
    }

    override fun disable() {
        // Unused
    }

    fun setupEconomy() {
        if (Bukkit.getServer().pluginManager.getPlugin("Vault") == null) return
        val rsp = Bukkit.getServer().servicesManager.getRegistration(Economy::class.java) ?: return
        vaultEco = rsp.provider
    }

    fun setupPermissions() {
        if (Bukkit.getServer().pluginManager.getPlugin("Vault") == null) return
        val rsp = Bukkit.getServer().servicesManager.getRegistration(Permission::class.java) ?: return
        permission = rsp.provider
    }

    companion object {
        var vaultEco: Economy? = null
            private set
        var permission: Permission? = null
            private set

    }
}
