package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.SelectDriverMenuOptionListener;
import edu.kis.powp.jobs2d.drivers.UsageMonitor.observers.UsageMonitorConnectToDriverObserver;
import edu.kis.powp.jobs2d.drivers.gui.UpdateDriverInfoObserver;

public class DriverFeature {

    private static final DriverManager driverManager = new DriverManager();
    private static Application app;

    public static DriverManager getDriverManager() {
        return driverManager;
    }

    /**
     * Setup jobs2d drivers Plugin and add to application.
     *
     * @param application Application context.
     */
    public static void setupDriverPlugin(Application application) {
        app = application;
        app.addComponentMenu(DriverFeature.class, "Drivers");

        driverManager.getChangePublisher().addSubscriber(new UpdateDriverInfoObserver());
        driverManager.getChangePublisher().addSubscriber(new UsageMonitorConnectToDriverObserver());
    }

    /**
     * Add driver to context, create button in driver menu.
     *
     * @param name   Button name.
     * @param driver Job2dDriver object.
     */
    public static void addDriver(String name, Job2dDriver driver) {
        SelectDriverMenuOptionListener listener = new SelectDriverMenuOptionListener(driver, driverManager);
        app.addComponentMenuElement(DriverFeature.class, name, listener);

        setUsingDriverIfNotExist(driver);
    }

    /**
     * Update driver info.
     */
    public static void updateDriverInfo() {
        app.updateInfo(driverManager.getCurrentDriver().toString());
    }


    /**
     * Sets using driver to given in param if currently using driver is null.
     *
     * @param driver Job2dDriver object.
     */
    private static void setUsingDriverIfNotExist(Job2dDriver driver) {
        if (driverManager.getCurrentDriver() != null)
            return;

        driverManager.setCurrentDriver(driver);
    }

}
