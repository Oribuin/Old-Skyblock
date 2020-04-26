package xyz.oribuin.skyblock.managers;

import xyz.oribuin.skyblock.Skyblock;

public abstract class Manager {

    protected final Skyblock plugin;

    public Manager(Skyblock plugin) {
        this.plugin = plugin;
    }

    public abstract void reload();
}
