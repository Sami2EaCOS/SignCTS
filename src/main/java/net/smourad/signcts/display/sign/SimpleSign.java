package net.smourad.signcts.display.sign;

import net.minecraft.server.v1_15_R1.PacketPlayOutTileEntityData;
import net.smourad.signcts.SignCTS;
import org.bukkit.craftbukkit.v1_15_R1.block.CraftSign;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public abstract class SimpleSign {

    private SignCTS plugin;

    public SimpleSign(SignCTS plugin) {
        this.plugin = plugin;
    }

    public abstract void displayToPlayer(Player player);
    public abstract void updateText() throws Exception;
    protected abstract void updateHeader();

    protected void displaySignToPlayer(Player player, CraftSign sign) {
        PacketPlayOutTileEntityData packet = new PacketPlayOutTileEntityData(sign.getBlock().getPosition(), 9, sign.getSnapshotNBT());
        CraftPlayer craftPlayer = (CraftPlayer) player;
        craftPlayer.getHandle().playerConnection.sendPacket(packet);
    }

    public SignCTS getPlugin() {
        return plugin;
    }

}
