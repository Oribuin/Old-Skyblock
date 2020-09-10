package xyz.oribuin.skyblock.manager

import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.hook.PlaceholderExp
import xyz.oribuin.skyblock.hook.VaultHook

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
