package edu.kis.powp.jobs2d.drivers.UsageMonitor;

import edu.kis.powp.jobs2d.Job2dDriver;

import java.awt.geom.Point2D;
import java.util.logging.Logger;

public class UsageMonitorDriverDecorator implements Job2dDriver {

    private final Job2dDriver driver;

    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private int lastX = 0, lastY = 0;


    public UsageMonitorDriverDecorator(Job2dDriver driver) {
        this.driver = driver;
    }


    @Override
    public void setPosition(int x, int y) {
        calcAndStoreHeadDistance(x, y);

        updateLastCoords(x, y);

        driver.setPosition(x, y);
    }

    @Override
    public void operateTo(int x, int y) {
        calcAndStoreHeadDistance(x, y);
        calcAndStoreOpDistance(x, y);

        updateLastCoords(x, y);

        driver.operateTo(x, y);
    }

    private void updateLastCoords(int x, int y) {
        lastX = x;
        lastY = y;
    }

    private double calcDistance(int x, int y) {
        return Point2D.distance(lastX, lastY, x, y);
    }


    private void calcAndStoreHeadDistance(int x, int y) {
        double distance = calcDistance(x, y);
        UsageMonitorStorage.addHeadDistance(distance);
    }

    private void calcAndStoreOpDistance(int x, int y) {
        double distance = calcDistance(x, y);
        UsageMonitorStorage.addOpDistance(distance);
    }

}
