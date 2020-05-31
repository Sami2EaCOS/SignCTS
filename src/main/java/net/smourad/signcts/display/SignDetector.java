package net.smourad.signcts.display;

import net.smourad.signcts.SignCTS;
import net.smourad.signcts.display.analyzer.SignInfoUpdater;
import net.smourad.signcts.display.sign.DualSign;
import net.smourad.signcts.display.sign.MonoSign;
import net.smourad.signcts.display.sign.SimpleSign;
import net.smourad.signcts.utils.SimpleUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.block.CraftSign;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class SignDetector {

    private SignInfoUpdater updater;

    public SignDetector() {
        init();
        updater = new SignInfoUpdater();
    }

    private void init() {
        Map<CraftSign, SimpleSign> loaded = new HashMap<>();

        new BukkitRunnable() {
            @Override
            public void run() {
                loaded.clear();
                int range = (int) SignCTS.getInstance().getConfig().get("display.range");

                Bukkit.getOnlinePlayers().forEach(player -> {
                    SimpleUtils.getNearbyBlocks(player.getLocation(), range).stream().filter(block -> block.getState() instanceof CraftSign).forEach(block -> {
                        CraftSign sign = (CraftSign) block.getState();

                        if (loaded.containsKey(sign)) {
                            loaded.get(sign).displayToPlayer(player);
                        } else {
                            SimpleSign s = checkAndSetup(sign);

                            if (s != null) {
                                loaded.put(sign, s);
                                try {
                                    s.updateText();
                                    s.displayToPlayer(player);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                });
            }
        }.runTaskTimer(SignCTS.getInstance(), 0L, 20L * SignCTS.getInstance().getConfig().getInt("display.interval"));
    }

    private SimpleSign checkAndSetup(CraftSign sign) {
        return sign.getLine(0).equals("[CTS]") ? sign.getLine(2).equalsIgnoreCase("double") ? new DualSign(sign) : new MonoSign(sign) : null;
    }

    public SignInfoUpdater getUpdater() {
        return updater;
    }
}
