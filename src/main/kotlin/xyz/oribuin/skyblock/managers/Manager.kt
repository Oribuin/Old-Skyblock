package xyz.oribuin.skyblock.managers

import xyz.oribuin.skyblock.Skyblock

abstract class Manager(protected val plugin: Skyblock) {
    abstract fun reload()

    abstract fun disable()
}
