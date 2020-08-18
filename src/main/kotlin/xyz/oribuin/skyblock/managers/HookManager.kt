package xyz.oribuin.skyblock.managers

import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.hooks.PlaceholderExp
import xyz.oribuin.skyblock.hooks.VaultHook

class HookManager(plugin: Skyblock) : Manager(plugin) {
    override fun reload() {
        this.registerPAPI()
        this.registerVault()
    }


    private fun registerVault() {
        if (plugin.server.pluginManager.getPlugin("Vault") != null) {
            val vaultHook = VaultHook(plugin)
            vaultHook.setupEconomy()
            vaultHook.setupPermissions()
        }
    }

    private fun registerPAPI() {
        if (plugin.server.pluginManager.getPlugin("PlaceholderAPI") != null) {
            PlaceholderExp(plugin).register()
        }
    }

    override fun disable() {
        // Unused
    }

}