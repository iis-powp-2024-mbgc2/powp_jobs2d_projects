package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.LoggerDriver;

import java.util.ArrayList;

/**
 * Driver manager provides means to setup the driver. It also enables other
 * components and features of the application to react on configuration changes.
 */
public class DriverManager {

    private final ArrayList<DriverObserver> observers = new ArrayList<>();

    private ObservableDriver currentDriver = new ObservableDriver(new LoggerDriver(), observers);

    /**
     * @param driver Set the driver as current.
     */
    public synchronized void setCurrentDriver(Job2dDriver driver) {
        currentDriver = new ObservableDriver(driver, observers);
    }

    /**
     * @return Current driver.
     */
    public synchronized Job2dDriver getCurrentDriver() {
        return currentDriver;
    }

    public ArrayList<DriverObserver> getObservers() { return this.observers; };
}
