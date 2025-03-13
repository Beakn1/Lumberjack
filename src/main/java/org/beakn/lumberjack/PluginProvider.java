package org.beakn.lumberjack;

public class PluginProvider {

    private static Lumberjack plugin;

    public static void setPlugin(Lumberjack plugin) {
        PluginProvider.plugin = plugin;
    }

    public static Lumberjack getPlugin() {
        return plugin;
    }
}