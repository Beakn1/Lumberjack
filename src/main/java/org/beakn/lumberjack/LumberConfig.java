package org.beakn.lumberjack;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LumberConfig {

    private static File configFile;
    private static FileConfiguration config;

    public static void createConfig(Lumberjack plugin) {
        configFile = new File(plugin.getDataFolder(), "lumber_config.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        config.addDefault("progressiveBreak", true);
        config.addDefault("particles", true);
        config.addDefault("sound", true);
        config.options().copyDefaults(true);
        saveCustomConfig(plugin);
    }

    private static void saveCustomConfig(Lumberjack plugin) {
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Error when saving Lumberjack config.");
            e.printStackTrace();
        }
    }

    public static boolean isProgressiveBreakEnabled() {
        return config.getBoolean("progressiveBreak", true);
    }

    public static boolean isParticlesEnabled() {
        return config.getBoolean("particles", true);
    }

    public static boolean isSoundEnabled() {
        return config.getBoolean("sound", true);
    }
}
