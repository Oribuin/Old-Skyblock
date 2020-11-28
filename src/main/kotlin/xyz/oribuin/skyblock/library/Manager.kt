package xyz.oribuin.skyblock.library

abstract class Manager(val plugin: OriPlugin) {
    abstract fun reload()
    abstract fun disable()
}