package net.smourad.signcts;

import com.google.gson.Gson;
import net.minecraft.server.v1_15_R1.PacketPlayOutTileEntityData;
import net.minecraft.server.v1_15_R1.PlayerConnection;
import net.smourad.signcts.utils.DefaultFontSignInfo;
import net.smourad.signcts.utils.SignUtils;
import net.smourad.signcts.utils.SimpleUtils;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.craftbukkit.v1_15_R1.block.CraftSign;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SignSetup {

    private SignCTS plugin;
    private Gson gson = new Gson();

    public SignSetup(SignCTS plugin) {
        this.plugin = plugin;
    }

    public void sendPlayerSignChange(Block block, Player player) throws Exception {
        if (!SignUtils.isSign(block.getType())) return;
        CraftSign sign = (CraftSign) block.getState();

        if (sign.getLine(0).equals("[CTS_Tram]")) {
            updateTramSign(sign, player);
        } else if (sign.getLine(0).equals("[CTS_Bus]")) {
            updateBusSign(sign, player);
        }
    }

    private void updateBusSign(CraftSign sign, Player player) throws Exception {
        String IDSAE = sign.getLine(1);

        if (IDSAE.isEmpty()) return;

        String header = "Temps d'attente";
        String space_header = SignUtils.spaceSignString(90 - DefaultFontSignInfo.getStringSignLength(header));
        sign.setLine(0, ChatColor.RED + header + space_header);

        JsonCTS json = gson.fromJson(plugin.getHttpRequest().readUrl(IDSAE), JsonCTS.class);
        for (int i=0; i<json.ServiceDelivery.StopMonitoringDelivery[0].MonitoredStopVisit.length; i++) {
            String line = "Ligne " + getVehicleType(json, i);
            String time = getVehicleTime(json, i);
            String space = SignUtils.spaceSignString(90 - DefaultFontSignInfo.getStringSignLength(line + time));
            sign.setLine(i+1, line + space + time);
        }


        PacketPlayOutTileEntityData packet = new PacketPlayOutTileEntityData(sign.getBlock().getPosition(), 9, sign.getSnapshotNBT());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

    }

    private void updateTramSign(CraftSign sign, Player player) throws Exception {
        String IDSAE = sign.getLine(1);

        if (IDSAE.isEmpty()) return;
        BlockData data = sign.getBlock().getState().getBlockData();

        BlockFace blockFace;
        if (data instanceof WallSign) {
            blockFace = ((WallSign) data).getFacing();
        } else {
            blockFace = ((Sign) data).getRotation();
        }

        Block block2 = sign.getWorld().getBlockAt(sign.getLocation().add(blockFace.getModZ(), blockFace.getModY(), -blockFace.getModX()));

        if (!SignUtils.isSign(block2.getType())) return;
        CraftSign sign2 = (CraftSign) block2.getState();

        String header_actual_time = SimpleUtils.getActualTime();
        sign.setLine(0, ChatColor.RED + header_actual_time + SignUtils.spaceSignString(90 - DefaultFontSignInfo.getStringSignLength(header_actual_time)));

        String header_time = "Temps d'attente";
        sign2.setLine(0, SignUtils.spaceSignString(90 - DefaultFontSignInfo.getStringSignLength(header_time)) + ChatColor.RED + header_time);

        JsonCTS json = gson.fromJson(plugin.getHttpRequest().readUrl(IDSAE), JsonCTS.class);
        for (int i=0; i<Math.min(json.ServiceDelivery.StopMonitoringDelivery[0].MonitoredStopVisit.length, 3); i++) {
            String type = getVehicleType(json, i);
            String name = getVehicleName(json, i);
            String time = getVehicleTime(json, i);

            String name2 = "";
            if (DefaultFontSignInfo.isNotSignLineOverflow(type + " " + name)) {
                List<String> strings = DefaultFontSignInfo.cutInSignLine(name);
                name  = strings.get(0);
                name2 = strings.get(1);
            }

            sign.setLine(i+1, getTramTypeColorText(type) + ChatColor.RESET + " " + name + SignUtils.spaceSignString(90 - DefaultFontSignInfo.getStringSignLength(type + " " + name)));
            sign2.setLine(i+1, name2 + SignUtils.spaceSignString(90 - DefaultFontSignInfo.getStringSignLength(name2 + time)) + time);
        }

        PacketPlayOutTileEntityData packet1 = new PacketPlayOutTileEntityData(sign.getBlock().getPosition(), 9, sign.getSnapshotNBT());
        PacketPlayOutTileEntityData packet2 = new PacketPlayOutTileEntityData(sign2.getBlock().getPosition(), 9, sign2.getSnapshotNBT());

        PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
        playerConnection.sendPacket(packet1);
        playerConnection.sendPacket(packet2);
    }

    private String getVehicleType(JsonCTS json , int i) {
        return json.ServiceDelivery.StopMonitoringDelivery[0].MonitoredStopVisit[i].MonitoredVehicleJourney.PublishedLineName;
    }

    private String getTramTypeColorText(String type) {
        return TramTypeColor.getTramTypeChatColor(type) + type;
    }

    private String getVehicleName(JsonCTS json , int i) {
        return json.ServiceDelivery.StopMonitoringDelivery[0].MonitoredStopVisit[i].MonitoredVehicleJourney.DestinationShortName;
    }

    private String getVehicleTime(JsonCTS json , int i) {
        long diffInMillies = Math.abs(
                json.ServiceDelivery.StopMonitoringDelivery[0].MonitoredStopVisit[i].RecordedAtTime.getTime()
                        - json.ServiceDelivery.StopMonitoringDelivery[0].MonitoredStopVisit[i].MonitoredVehicleJourney.MonitoredCall.ExpectedArrivalTime.getTime()
        );

        long minutes = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return (minutes > 9 ? minutes : "0" + minutes) + " min";
    }

    private enum TramTypeColor {
        A(ChatColor.RED),
        B(ChatColor.AQUA),
        C(ChatColor.GOLD),
        D(ChatColor.DARK_GREEN),
        E(ChatColor.LIGHT_PURPLE),
        F(ChatColor.GREEN);

        private final ChatColor color;

        TramTypeColor(ChatColor color) {
            this.color = color;
        }

        public ChatColor getColor() {
            return color;
        }

        public static ChatColor getTramTypeChatColor(String type) {
            for (TramTypeColor tramTypeColor : TramTypeColor.values()) {
                if (tramTypeColor.name().equals(type)) {
                    return tramTypeColor.getColor();
                }
            }

            return ChatColor.RESET;
        }
    }
}
