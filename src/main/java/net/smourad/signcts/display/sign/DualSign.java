package net.smourad.signcts.display.sign;

import net.smourad.signcts.SignCTS;
import net.smourad.signcts.display.analyzer.MonitoredStopVisitInfo;
import net.smourad.signcts.display.analyzer.SignInfoAnalyzer;
import net.smourad.signcts.utils.DefaultSignFont;
import net.smourad.signcts.utils.SignUtils;
import net.smourad.signcts.utils.SimpleUtils;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.craftbukkit.v1_15_R1.block.CraftSign;
import org.bukkit.entity.Player;

import java.util.List;

public class DualSign extends SimpleSign {

    private CraftSign sign;
    private CraftSign sign2;

    public DualSign(SignCTS plugin, CraftSign sign) {
        super(plugin);
        this.sign = sign;
        this.sign2 = getDuet();
    }

    private CraftSign getDuet() {
        BlockData data = sign.getBlock().getState().getBlockData();
        BlockFace blockFace = data instanceof WallSign ? ((WallSign) data).getFacing() : ((Sign) data).getRotation();
        Block block2 = sign.getWorld().getBlockAt(sign.getLocation().add(blockFace.getModZ(), blockFace.getModY(), -blockFace.getModX()));

        return block2.getState() instanceof CraftSign ? (CraftSign) block2.getState() : null;
    }

    @Override
    public void displayToPlayer(Player player) {
        displaySignToPlayer(player, sign);
        if (sign2 != null) displaySignToPlayer(player, sign2);
    }

    public void updateText() throws Exception {
        if (sign2 == null) {
            SignUtils.cleanSign(sign);
            missingSignText(); return;
        }

        String idsae = sign.getLine(1);
        SignUtils.cleanSign(sign);
        updateHeader();

        SignInfoAnalyzer info = new SignInfoAnalyzer(getPlugin(), idsae);

        for (int i=0; i < Math.min(info.getMonitoredStopVisitLength(), 3); i++) {
            MonitoredStopVisitInfo stop = info.getMonitoredStopVisit(i);

            String line    = stop.getColoredLine();
            String name    = stop.getName();
            String time    = stop.getMinBeforeArrival();

            String name2 = "";
            if (DefaultSignFont.isSignLineOverflow(line + " " + name)) {
                List<String> strings = DefaultSignFont.cutInSignLine(name, DefaultSignFont.getStringSignLength(line + " "));
                name  = strings.get(0);
                name2 = strings.get(1);
            }

            sign .setLine(i+1, SignUtils.fillWithSpaceOnRight(line + " " + name));
            sign2.setLine(i+1, name2 + SignUtils.toFillWithSpace(name2 + time) + time);
        }

    }

    protected void updateHeader() {
        String time = SimpleUtils.getActualTime();
        String waiting = "Temps d'attente";

        sign .setLine(0, SignUtils.fillWithSpaceOnRight(ChatColor.translateAlternateColorCodes('&', (String) getPlugin().getConfig().get("sign.color.time")) + time));
        sign2.setLine(0, SignUtils.fillWithSpaceOnLeft (ChatColor.translateAlternateColorCodes('&', (String) getPlugin().getConfig().get("sign.color.waiting")) + waiting));
    }

    private void missingSignText() {
        sign.setLine(0, ChatColor.RED + "Besoin d'un autre");
        sign.setLine(1, ChatColor.RED + "panneau ici");
        sign.setLine(3, ChatColor.RED + "-------->");
    }

}
