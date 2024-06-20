package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.jobs2d.drivers.visitor.IDriverVisitor;
import edu.kis.powp.jobs2d.drivers.visitor.IVisitableDriver;

import java.awt.geom.Point2D;
import java.util.logging.Logger;

public class UsageMonitorDriverDecorator implements IVisitableDriver {
    private final IVisitableDriver driver;
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private int lastX = 0, lastY = 0;
    private double headDistance = 0, opDistance = 0;

    public UsageMonitorDriverDecorator(DriverManager driverManager) {
        this.driver = driverManager.getCurrentDriverAndFeaturesComposite(this);
    }

    @Override
    public void setPosition(int x, int y) {
        headDistance = calcDistance(x, y);
        updateLastCoords(x, y);

        logDistance();
        driver.setPosition(x, y);
        DriverFeature.getDriverManager().getCurrentDriver().setPosition(x, y);
    }

    @Override
    public void operateTo(int x, int y) {
        headDistance += calcDistance(x, y);
        opDistance += calcDistance(x, y);
        updateLastCoords(x, y);

        logDistance();
        driver.operateTo(x, y);
        DriverFeature.getDriverManager().getCurrentDriver().setPosition(x, y);
    }

    public double getHeadDistance() {
        return headDistance;
    }

    public double getOpDistance() {
        return opDistance;
    }

    private void updateLastCoords(int x, int y) {
        lastX = x;
        lastY = y;
    }

    private double calcDistance(int x, int y) {
        return Point2D.distance(lastX, lastY, x, y);
    }

    private void logDistance() {
        logger.info(String.format("Current distance made:\n- head distance: %f\n- op distance: %f", headDistance, opDistance));
    }

    public IVisitableDriver getDriver() {
        return driver;
    }

    @Override
    public void accept(IDriverVisitor visitor) {
        visitor.visit(this);
    }
}
