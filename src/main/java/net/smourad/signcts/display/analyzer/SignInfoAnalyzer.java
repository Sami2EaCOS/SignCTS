package net.smourad.signcts.display.analyzer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.smourad.signcts.SignCTS;

public class SignInfoAnalyzer {

    private JsonObject info;
    private String idsae;

    public SignInfoAnalyzer(String idsae) {
        this.idsae = idsae;
        this.info = null;
    }

    public void update() throws Exception {
        this.info = SignCTS.getInstance().getHttpRequest().readJsonFromUrl(idsae);
    }

    public MonitoredStopVisitInfo getMonitoredStopVisit(int i) {
        return new MonitoredStopVisitInfo(SignCTS.getInstance(), getMonitoredStopVisitArray().get(i).getAsJsonObject());
    }

    public int getMonitoredStopVisitLength() {
        return getMonitoredStopVisitArray().size();
    }

    public boolean havePlannedVisit() {
        return info != null || getMonitoredStopVisitArray() != null;
    }

    private JsonArray getMonitoredStopVisitArray() {
        return info.getAsJsonObject("ServiceDelivery")
                .getAsJsonArray("StopMonitoringDelivery").get(0).getAsJsonObject()
                .getAsJsonArray("MonitoredStopVisit");
    }

}

