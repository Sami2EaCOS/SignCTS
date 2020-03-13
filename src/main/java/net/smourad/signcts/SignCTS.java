package net.smourad.signcts;

import com.google.gson.Gson;
import net.smourad.signcts.file.BusColorYML;
import net.smourad.signcts.file.TramColorYML;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.block.*;

import java.util.ArrayList;
import java.util.List;

public class SignCTS extends JavaPlugin {

    private Gson gson;
    private HttpRequest httpRequest;
    private TramColorYML tramColor;
    private BusColorYML busColor;

    @Override
    public void onEnable() {
        getLogger().info("Hello, SpigotMC!");

        loadConfigs();
        connect();

        initSignLoop();
    }

    @Override
    public void onDisable() {
        getLogger().info("See you again, SpigotMC!");
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    private void loadConfigs() {
        saveDefaultConfig();

        tramColor = new TramColorYML(this);
        tramColor.create();

        busColor = new BusColorYML(this);
        busColor.create();
    }

    private void connect() {
        String webServiceToken = getConfig().getString("connection.token");
        String webServiceURL = getConfig().getString("connection.url");
        String password = getConfig().getString("connection.password");

        httpRequest = new HttpRequest(webServiceURL, webServiceToken, password);
    }

    private void initSignLoop() {
        SignSetup signSetup = new SignSetup(this);

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    getNearbyBlocks(player.getLocation(), (int) getConfig().get("display.range")).forEach(block -> {
                        try {
                            signSetup.sendPlayerSignChange(block, player);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                });

            }
        }.runTaskTimer(this, 0L, 20L * (int) getConfig().get("display.interval"));
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

    public Gson getGson() {
        return gson;
    }
}