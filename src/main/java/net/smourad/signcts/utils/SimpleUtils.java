package net.smourad.signcts.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SimpleUtils {

    public static String getActualTime() {
        return new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
    }

    public static List<Block> getNearbyBlocks(Location location, int radius) {
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
