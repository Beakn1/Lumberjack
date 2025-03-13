package org.beakn.lumberjack;

import org.bukkit.plugin.java.JavaPlugin;

public final class Lumberjack extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginProvider.setPlugin(this);
        LumberConfig.createConfig(this);
        getServer().getPluginManager().registerEvents(new BreakListener(), this);
        getLogger().info("Lumberjack Plugin started Successfully.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Lumberjack Plugin disabled Successfully.");
    }
}
