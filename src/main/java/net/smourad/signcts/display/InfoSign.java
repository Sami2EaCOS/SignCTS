package net.smourad.signcts.display;

import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.block.CraftSign;
import org.bukkit.entity.Player;

public abstract class InfoSign {

    private CraftSign sign;

    public InfoSign(Block block) {
        sign = (CraftSign) block.getState();
    }

    public abstract void displayToPlayer(Player player);
}
