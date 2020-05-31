package net.smourad.signcts.event.shovel;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.block.CraftSign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.block.*;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashSet;
import java.util.Set;

public class RightClickWithHolyShovel implements Listener {

    private Set<Block> set = new HashSet<>();

    @EventHandler
    public void holyShovelUsage(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        Material material = event.getMaterial();

        if (player.isOp() && block != null && block.getState() instanceof CraftSign) {
            if (material.equals(Material.DIAMOND_SHOVEL)) {
                if ((event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
                    setup(player, block);
                }
            }
        }
    }

    private void setup(Player player, Block block) {
        CraftSign sign = (CraftSign) block.getState();

        openSignEditor(player, block);
    }

    private void openSignEditor(Player player, Block block) {
        Location l = block.getLocation();
        /*
        TileEntitySign sign = (TileEntitySign) ((CraftWorld) l.getWorld()).getTileEntityAt(l.getBlockX(), l.getBlockY(), l.getBlockZ());
        sign.setEditable(true);
        sign.update();



        PacketPlayOutOpenSignEditor packet = new PacketPlayOutOpenSignEditor(sign.getPosition());

        CraftPlayer craftPlayer = (CraftPlayer) player;
        craftPlayer.getHandle().playerConnection.sendPacket(packet);

         */
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        System.out.println("OUI: " + event.isCancelled());
    }

}
