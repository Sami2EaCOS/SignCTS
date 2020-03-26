package net.smourad.signcts.display.analyzer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.smourad.signcts.SignCTS;

public class SignInfoAnalyzer {

    private JsonObject info;
    private SignCTS plugin;

    public SignInfoAnalyzer(SignCTS plugin, String idsae) throws Exception {
        this.plugin = plugin;
        this.info = plugin.getHttpRequest().readJsonFromUrl(idsae);
    }

    public MonitoredStopVisitInfo getMonitoredStopVisit(int i) {
        return new MonitoredStopVisitInfo(plugin, getMonitoredStopVisitArray().get(i).getAsJsonObject());
    }

    public int getMonitoredStopVisitLength() {
        return getMonitoredStopVisitArray().size();
    }

    private JsonArray getMonitoredStopVisitArray() {
        return info.getAsJsonObject("ServiceDelivery")
                .getAsJsonArray("StopMonitoringDelivery").get(0).getAsJsonObject()
                .getAsJsonArray("MonitoredStopVisit");
    }

}

