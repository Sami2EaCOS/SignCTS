package net.smourad.signcts.event;

import net.smourad.signcts.SignCTS;
import net.smourad.signcts.display.sign.DualSign;
import net.smourad.signcts.display.sign.MonoSign;
import net.smourad.signcts.display.sign.SimpleSign;
import net.smourad.signcts.utils.SimpleUtils;
import org.bukkit.craftbukkit.v1_15_R1.block.CraftSign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinSignUpdate implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        SimpleUtils.getNearbyBlocks(player.getLocation(), (int) SignCTS.getInstance().getConfig().get("display.range")).forEach(block -> {
            if (block.getState() instanceof CraftSign) {
                CraftSign sign = (CraftSign) block.getState();
                if (sign.getLine(0).equals("[CTS]")) {
                    try {
                        SimpleSign s = sign.getLine(2).equalsIgnoreCase("double") ? new DualSign(sign) : new MonoSign(sign);
                        s.updateText();
                        s.displayToPlayer(player);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
