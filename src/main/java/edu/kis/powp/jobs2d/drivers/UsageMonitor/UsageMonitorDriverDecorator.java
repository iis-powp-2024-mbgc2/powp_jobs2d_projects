package edu.kis.powp.jobs2d.drivers.UsageMonitor;

import edu.kis.powp.jobs2d.extended_driver_options.DriverOptionDecorator;

import java.awt.geom.Point2D;

public class UsageMonitorDriverDecorator extends DriverOptionDecorator {
    private int lastX = 0, lastY = 0;


    public UsageMonitorDriverDecorator() {
    }


    @Override
    public void setPosition(int x, int y) {
        calcAndStoreHeadDistance(x, y);

        updateLastCoords(x, y);

        super.driver.setPosition(x, y);
    }

    @Override
    public void operateTo(int x, int y) {
        calcAndStoreHeadDistance(x, y);
        calcAndStoreOpDistance(x, y);

        updateLastCoords(x, y);

        super.driver.operateTo(x, y);
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
