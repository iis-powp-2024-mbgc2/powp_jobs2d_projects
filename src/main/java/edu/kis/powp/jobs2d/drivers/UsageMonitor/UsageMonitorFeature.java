package edu.kis.powp.jobs2d.drivers.UsageMonitor;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.UsageMonitor.enums.UsageMonitorOption;
import edu.kis.powp.jobs2d.drivers.UsageMonitor.listeners.UsageMonitorOptionListener;

public class UsageMonitorFeature {

    private UsageMonitorFeature() {}

    public static void setupUsageMonitorPlugin(Application application) {
        application.addComponentMenu(UsageMonitorFeature.class, "Usage Monitor");

        UsageMonitorOptionListener clearOptionListener = new UsageMonitorOptionListener(UsageMonitorOption.CLEAR);
        UsageMonitorOptionListener showOptionListener = new UsageMonitorOptionListener(UsageMonitorOption.SHOW);

        application.addComponentMenuElement(UsageMonitorFeature.class, "Clear all distances", clearOptionListener);
        application.addComponentMenuElement(UsageMonitorFeature.class, "Show", showOptionListener);
    }
}
