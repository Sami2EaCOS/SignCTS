package net.smourad.signcts.display;

import net.smourad.signcts.SignCTS;
import net.smourad.signcts.display.sign.DualSign;
import net.smourad.signcts.display.sign.MonoSign;
import net.smourad.signcts.display.sign.SimpleSign;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.block.CraftSign;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignDetector {

    private SignCTS plugin;

    public SignDetector(SignCTS plugin) {
        this.plugin = plugin;
    }

    public void init() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Map<Block, SimpleSign> loaded = new HashMap<>();

                Bukkit.getOnlinePlayers().forEach(player -> {
                    getNearbyBlocks(player.getLocation(), (int) plugin.getConfig().get("display.range")).forEach(block -> {
                        if (loaded.containsKey(block)) {
                            loaded.get(block).displayToPlayer(player);
                        } else {
                            SimpleSign sign = checkAndSetup(block);

                            if (sign != null) {
                                loaded.put(block, sign);
                                try {
                                    sign.updateText();
                                    sign.displayToPlayer(player);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                });

            }
        }.runTaskTimer(plugin, 0L, 20L * (int) plugin.getConfig().get("display.interval"));
    }

    private SimpleSign checkAndSetup(Block block) {
        if (block.getState() instanceof CraftSign) {
            CraftSign sign = (CraftSign) block.getState();
            if (sign.getLine(0).equals("[CTS]")) {
                return sign.getLine(2).equalsIgnoreCase("double") ? new DualSign(plugin, sign) : new MonoSign(plugin, sign);
            }
        }

        return null;
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
}
