package edu.kis.powp.jobs2d.drivers.UsageMonitor.observers;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.UsageMonitor.UsageMonitorDriverDecorator;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.observer.Subscriber;

public class UsageMonitorConnectToDriverObserver implements Subscriber {

    @Override
    public void update() {
        DriverManager driverManager = DriverFeature.getDriverManager();

        Job2dDriver currentDriver = driverManager.getCurrentDriver();
        Job2dDriver usageMonitor = new UsageMonitorDriverDecorator(currentDriver);

        driverManager.setCurrentDriver(usageMonitor);
    }

}
