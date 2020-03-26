package net.smourad.signcts;

import net.smourad.signcts.display.SignDetector;
import net.smourad.signcts.file.BusColorYML;
import net.smourad.signcts.file.TramColorYML;
import org.bukkit.plugin.java.JavaPlugin;

public class SignCTS extends JavaPlugin {

    private static SignCTS instance;

    public static SignCTS getInstance() {
        return instance;
    }

    private HttpRequest httpRequest;
    private TramColorYML tramColor;
    private BusColorYML busColor;

    @Override
    public void onEnable() {
        instance = this;

        loadConfigs();
        connect();

        SignDetector detector = new SignDetector(this);
        detector.init();
    }

    @Override
    public void onDisable() {}

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    private void loadConfigs() {
        saveDefaultConfig();

        tramColor = new TramColorYML(this);
        tramColor.create();

        busColor = new BusColorYML(this);
        busColor.create();
    }

    private void connect() {
        String webServiceToken = getConfig().getString("connection.token");
        String webServiceURL = getConfig().getString("connection.url");
        String password = getConfig().getString("connection.password");

        httpRequest = new HttpRequest(webServiceURL, webServiceToken, password);
    }

    public TramColorYML getTramColor() {
        return tramColor;
    }

    public BusColorYML getBusColor() {
        return busColor;
    }

}