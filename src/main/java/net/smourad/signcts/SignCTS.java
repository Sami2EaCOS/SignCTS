package net.smourad.signcts;

import net.smourad.signcts.file.BusColorYML;
import net.smourad.signcts.file.TramColorYML;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.block.*;

import java.util.ArrayList;
import java.util.List;

public class SignCTS extends JavaPlugin {

    private HttpRequest httpRequest;
    private TramColorYML tramColor;
    private BusColorYML busColor;

    @Override
    public void onEnable() {
        getLogger().info("Hello, SpigotMC!");
        loadFiles();
        initSignLoop();
    }

    @Override
    public void onDisable() {
        getLogger().info("See you again, SpigotMC!");
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    private void loadFiles() {
        loadConfig();

        tramColor = new TramColorYML(this);
        busColor = new BusColorYML(this);

        tramColor.create();
        busColor.create();
    }

    private void loadConfig() {
        saveDefaultConfig();
        String webServiceToken = getConfig().getString("token");
        String webServiceURL = getConfig().getString("url");
        httpRequest = new HttpRequest(webServiceURL, webServiceToken);
    }

    private void initSignLoop() {
        SignSetup signSetup = new SignSetup(this);

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    getNearbyBlocks(player.getLocation(), 40).forEach(block -> {
                        try {
                            signSetup.sendPlayerSignChange(block, player);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                });

            }
        }.runTaskTimer(this, 0L, 20L*10);
    }

    private List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    public TramColorYML getTramColor() {
        return tramColor;
    }

    public BusColorYML getBusColor() {
        return busColor;
    }
}