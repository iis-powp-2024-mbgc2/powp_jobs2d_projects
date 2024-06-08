package edu.kis.powp.jobs2d.drivers;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.line.BasicLine;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.observer.Publisher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Driver manager provides means to setup the driver. It also enables other
 * components and features of the application to react on configuration changes.
 */
public class DriverManager {
    private LineDriverAdapter currentDriver = new LineDriverAdapter(new DrawPanelController(), new BasicLine(), "Setup driver");
    private DriversComposite currentFeaturesComposite = new DriversComposite(this);
    private final Publisher changePublisher = new Publisher();

    /**
     * @param driver Set the driver as current.
     */
    public synchronized void setCurrentDriver(LineDriverAdapter driver) {
        currentDriver = driver;
        changePublisher.notifyObservers();
    }

    public synchronized void updateDriversComposite(Job2dDriver driver) {
        if (!currentFeaturesComposite.removeDriver(driver))
            currentFeaturesComposite.addDriver(driver);
        changePublisher.notifyObservers();
    }
    /**
     * @return Current driver.
     */
    public synchronized LineDriverAdapter getCurrentDriver() {
        return this.currentDriver;
    }

    public synchronized DriversComposite getCurrentDriverAndFeaturesComposite(Job2dDriver driver) {
        List<Job2dDriver> returnList = new ArrayList<>();
        for (Job2dDriver feature : this.currentFeaturesComposite.getList()) {
            if (feature.equals(driver))
                break;
            returnList.add(feature);
        }

        return new DriversComposite(returnList, this);
    }

    public synchronized DriversComposite getCurrentDriverAndFeaturesComposite() {
        List<Job2dDriver> returnList = new ArrayList<>(this.currentFeaturesComposite.getList());
        returnList.add(currentDriver);

        return new DriversComposite(returnList, this);
    }

    public Publisher getChangePublisher() {
        return changePublisher;
    }
}