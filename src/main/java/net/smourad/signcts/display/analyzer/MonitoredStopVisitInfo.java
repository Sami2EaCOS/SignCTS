package net.smourad.signcts.display.analyzer;

import com.google.gson.JsonObject;
import net.smourad.signcts.SignCTS;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MonitoredStopVisitInfo {

    private JsonObject info;
    private SignCTS plugin;

    public MonitoredStopVisitInfo(SignCTS plugin, JsonObject info) {
        this.info = info;
        this.plugin = plugin;
    }

    public String getVehicle() {
        return getMonitoredVehicleJourney().get("VehicleMode").getAsString();
    }

    public String getName() {
        return getMonitoredVehicleJourney().get("DestinationShortName").getAsString();
    }

    public Date getDepartureTime() throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(getMonitoredVehicleJourney().getAsJsonObject("MonitoredCall").get("ExpectedDepartureTime").getAsString());
    }

    public Date getRecordedAtTime() throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(info.get("RecordedAtTime").getAsString());
    }

    public String getLineName() {
        return getMonitoredVehicleJourney().get("PublishedLineName").getAsString();
    }

    public String getColoredLine() {
        FileConfiguration colorFileConfiguration = getVehicle().equals("tram") ? plugin.getTramColor().get() : plugin.getBusColor().get();
        String line = getLineName();
        String color = (String) colorFileConfiguration.get("ligne." + line);

        if (color != null) {
            return ChatColor.translateAlternateColorCodes('&',  color) + line + ChatColor.RESET;
        } else {
            return line;
        }

    }

    public String getMinBeforeArrival() throws ParseException {
        long diffInMillies = getDepartureTime().getTime() - getRecordedAtTime().getTime() ;
        long minutes = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return (minutes > 9 ? minutes : "0" + minutes) + " min";
    }

    private JsonObject getMonitoredVehicleJourney() {
        return info.get("MonitoredVehicleJourney").getAsJsonObject();
    }
}
