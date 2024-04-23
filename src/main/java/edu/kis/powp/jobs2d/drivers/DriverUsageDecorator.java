package edu.kis.powp.jobs2d.drivers;
import edu.kis.powp.jobs2d.Job2dDriver;

import java.util.logging.Logger;

public class DriverUsageDecorator implements Job2dDriver {

    private final int PRINT_WAIT_TIME_MS = 300;
    private boolean isPrintAvailable = true;
    private int currentX = 0, currentY = 0;
    private double headDistance = 0, opDistance = 0;
    private final Job2dDriver driver;

    public DriverUsageDecorator(Job2dDriver driver) {
        this.driver = driver;
    }

    @Override
    public void setPosition(int x, int y) {
        opDistance += getUpdatedUsage(x, y);
        setCurrentCoords(x, y);

        driver.setPosition(x, y);
    }

    @Override
    public void operateTo(int x, int y) {
        headDistance += getUpdatedUsage(x, y);
        opDistance += getUpdatedUsage(x, y);
        setCurrentCoords(x, y);

        printUsage();
        driver.operateTo(x, y);
    }

    private void setCurrentCoords(int x, int y) {
        currentX = x;
        currentY = y;
    }

    private void printUsage() {
        if (!isPrintAvailable) { return; }

        isPrintAvailable = false;
        new Thread(() -> {
            try {
                Thread.sleep(PRINT_WAIT_TIME_MS);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            logger.info("Usage: \n" + "   Op distance: " + opDistance + "\n   Head distance: " + headDistance);
            isPrintAvailable = true;
        }).start();
    }

    private double getUpdatedUsage(int endX, int endY) {
        return Math.sqrt(Math.pow(endX - currentX, 2) + Math.pow(endY - currentY, 2));
    }
}

