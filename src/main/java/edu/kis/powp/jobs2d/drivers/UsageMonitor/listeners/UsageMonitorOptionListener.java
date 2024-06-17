package edu.kis.powp.jobs2d.drivers.UsageMonitor.listeners;

import edu.kis.powp.jobs2d.drivers.UsageMonitor.UsageMonitorStorage;
import edu.kis.powp.jobs2d.drivers.UsageMonitor.enums.UsageMonitorOption;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class UsageMonitorOptionListener implements ActionListener {
    private UsageMonitorOption option;
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public UsageMonitorOptionListener(UsageMonitorOption option) {
        this.option = option;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (option) {
            case CLEAR:
                clearUsageMonitorDistances();
                break;

            case SHOW:
                logDistance();
                break;

            default:
                break;
        }
    }

    private void clearUsageMonitorDistances() {
        UsageMonitorStorage.resetAllDistances();
        logger.info("Usage monitor all distances cleared");
    }

    private void logDistance() {
        logger.info(String.format("Current distance made:\n- head distance: %f\n- op distance: %f", UsageMonitorStorage.getHeadDistance(), UsageMonitorStorage.getOpDistance()));
    }
}
