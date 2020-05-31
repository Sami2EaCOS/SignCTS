package net.smourad.signcts;

import net.smourad.signcts.display.SignDetector;
import net.smourad.signcts.event.PlayerJoinSignUpdate;
import net.smourad.signcts.event.shovel.RightClickWithHolyShovel;
import net.smourad.signcts.file.BusColorYML;
import net.smourad.signcts.file.TramColorYML;
import net.smourad.signcts.utils.HttpRequest;
import org.bukkit.plugin.java.JavaPlugin;

public class SignCTS extends JavaPlugin {

    private static SignCTS instance;

    public static SignCTS getInstance() {
        return instance;
    }

    private HttpRequest httpRequest;
    private TramColorYML tramColor;
    private BusColorYML busColor;
    private SignDetector detector;

    @Override
    public void onEnable() {
        instance = this;

        loadConfigs();
        loadListener();

        connect();

        detector = new SignDetector();
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

    private void loadListener() {
        getServer().getPluginManager().registerEvents(new RightClickWithHolyShovel(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinSignUpdate(), this);
    }

    public TramColorYML getTramColor() {
        return tramColor;
    }

    public BusColorYML getBusColor() {
        return busColor;
    }

    public SignDetector getDetector() {
        return detector;
    }
}