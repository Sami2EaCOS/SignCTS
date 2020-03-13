package net.smourad.signcts.file;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class CustomConfigYML {

    private File customConfigFile;
    private FileConfiguration customConfig;
    private JavaPlugin plugin;
    private String name;

    public CustomConfigYML(JavaPlugin plugin, String name) {
        this.name = name;
        this.plugin = plugin;
    }


    public FileConfiguration get() {
        return this.customConfig;
    }

    public void save() {
        try {
            get().save(customConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDefault() {
        if (!this.customConfigFile.exists()) {
            plugin.saveResource(name + ".yml", true);
        }
    }

    public void create() {
        customConfigFile = new File(plugin.getDataFolder(), name + ".yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            plugin.saveResource(name + ".yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
