package edu.kis.powp.jobs2d.drivers;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.line.BasicLine;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.adapter.Line2dDriver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.observer.Publisher;

import java.util.*;

/**
 * Driver manager provides means to setup the driver. It also enables other
 * components and features of the application to react on configuration changes.
 */
public class DriverManager {
    private Line2dDriver currentDriver = new LineDriverAdapter(new DrawPanelController(), new BasicLine(), "Setup driver");
    private DriversComposite currentFeaturesComposite = new DriversComposite(this);
    private final Publisher changePublisher = new Publisher();

    /**
     * @param driver Set the driver as current.
     */
    public synchronized void setCurrentDriver(Line2dDriver driver) {
        currentDriver = driver;
        changePublisher.notifyObservers();
    }

    public synchronized void toggleFeature(int position, Job2dDriver driver) {
        if (!currentFeaturesComposite.removeDriver(position))
            currentFeaturesComposite.addDriver(position, driver);
        changePublisher.notifyObservers();
    }
    /**
     * @return Current driver.
     */
    public synchronized Line2dDriver getCurrentDriver() {
        return this.currentDriver;
    }

    public synchronized DriversComposite getCurrentDriverAndFeaturesComposite(Job2dDriver driver) {
        Map<Integer, Job2dDriver> returnList = new TreeMap<>();
        for (Integer position : currentFeaturesComposite.getMap().keySet()) {
            Job2dDriver feature = currentFeaturesComposite.getMap().get(position);
            if (feature.equals(driver))
                break;
            returnList.put(position, feature);
        }

        return new DriversComposite(returnList, this);
    }

    public synchronized DriversComposite getCurrentDriverAndFeaturesComposite() {
        Map<Integer, Job2dDriver> returnList = new TreeMap<>(this.currentFeaturesComposite.getMap());
        returnList.put(1000, currentDriver);

        return new DriversComposite(returnList, this);
    }

    public Publisher getChangePublisher() {
        return changePublisher;
    }
}