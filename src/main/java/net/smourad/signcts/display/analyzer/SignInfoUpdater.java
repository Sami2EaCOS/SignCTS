package net.smourad.signcts.display.analyzer;

import net.smourad.signcts.SignCTS;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class SignInfoUpdater {

    private Map<String, SignInfoAnalyzer> signInfo = new HashMap<>();

    public SignInfoUpdater() {
        init();
    }

    private void init() {
       new BukkitRunnable() {
           @Override
           public void run() {
               for (SignInfoAnalyzer signInfoAnalyzer : signInfo.values()) {
                   try {
                       signInfoAnalyzer.update();
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
           }
       }.runTaskTimerAsynchronously(SignCTS.getInstance(), 0L, 20L * SignCTS.getInstance().getConfig().getInt("update.interval"));
    }

    public SignInfoAnalyzer getSignInfo(String idsae) {
        if (!signInfo.containsKey(idsae)) {
            signInfo.put(idsae, new SignInfoAnalyzer(idsae));
        }

        return signInfo.get(idsae);
    }
}
