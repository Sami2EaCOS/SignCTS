package net.smourad.signcts.utils;

import org.bukkit.Material;

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
}
