package net.smourad.signcts.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.block.CraftSign;

public class SignUtils {

    public static boolean isSign(Material m) {
        return     m.equals(Material.OAK_WALL_SIGN)
                || m.equals(Material.ACACIA_WALL_SIGN)
                || m.equals(Material.BIRCH_WALL_SIGN)
                || m.equals(Material.DARK_OAK_WALL_SIGN)
                || m.equals(Material.JUNGLE_WALL_SIGN)
                || m.equals(Material.SPRUCE_WALL_SIGN)
                || m.equals(Material.OAK_SIGN)
                || m.equals(Material.ACACIA_SIGN)
                || m.equals(Material.BIRCH_SIGN)
                || m.equals(Material.DARK_OAK_SIGN)
                || m.equals(Material.JUNGLE_SIGN)
                || m.equals(Material.SPRUCE_SIGN);
    }

    public static String spaceSignString(int length) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length / DefaultSignFont.getDefaultFontSignInfo(' ').getLength(); ++i) {
            builder.append(" ");
        }

        return builder.toString();
    }

    public static String toFillWithSpace(String str) {
        return spaceSignString(90 - DefaultSignFont.getStringSignLength(ChatColor.stripColor(str)));
    }

    public static String fillWithSpaceOnRight(String str) {
        return str + spaceSignString(90 - DefaultSignFont.getStringSignLength(ChatColor.stripColor(str)));
    }

    public static String fillWithSpaceOnLeft(String str) {
        return spaceSignString(90 - DefaultSignFont.getStringSignLength(ChatColor.stripColor(str))) + str;
    }

    public static void cleanSign(CraftSign sign) {
        for (int i=0; i<4; ++i) {
            sign.setLine(i, " ");
        }
    }
}
