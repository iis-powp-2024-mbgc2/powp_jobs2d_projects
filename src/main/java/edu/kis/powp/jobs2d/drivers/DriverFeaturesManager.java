package edu.kis.powp.jobs2d.drivers;

import edu.kis.legacy.drawer.shape.line.BasicLine;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.observer.Publisher;

public class DriverFeaturesManager extends DriverManager {
    private DriversComposite currentDriver = new DriversComposite();
    private final Publisher changePublisher = new Publisher();
    private final static Job2dDriver PREVIEW_DRIVER = new LineDriverAdapter(null, new BasicLine(),"preview");

    public synchronized void setCurrentDriver(Job2dDriver driver) {
        if (currentDriver.hasDriver(driver)) {
            currentDriver.removeDriver(driver);
        }
        else {
            currentDriver.removeDriver(PREVIEW_DRIVER);
            currentDriver.addDriver(driver);
        }
        changePublisher.notifyObservers();
    }

    public synchronized Job2dDriver getCurrentDriver() {
        return currentDriver;
    }

    public synchronized Job2dDriver getCurrentDriver(Job2dDriver driver) {
        DriversComposite driversCompositeToReturn = new DriversComposite(currentDriver.getList());
        driversCompositeToReturn.removeDriver(driver);
        return currentDriver;
    }

    public Publisher getChangePublisher() {
        return changePublisher;
    }
}
