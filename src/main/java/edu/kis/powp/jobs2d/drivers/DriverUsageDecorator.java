package edu.kis.powp.jobs2d.drivers;
import edu.kis.powp.jobs2d.Job2dDriver;

import java.util.logging.Logger;

public class DriverUsageDecorator implements Job2dDriver {

    private final int PRINT_WAIT_TIME_MS = 300;
    private boolean isPrintAvailable = true;
    private int startX = 0, startY = 0;
    private double headDistance = 0, opDistance = 0;
    private final Job2dDriver driver;

    public DriverUsageDecorator(Job2dDriver driver) {
        this.driver = driver;
    }

    @Override
    public void setPosition(int x, int y) {
        driver.setPosition(x, y);
        headDistance += getUpdatedUsage(x, y);
    }

    @Override
    public void operateTo(int x, int y) {
        driver.operateTo(x, y);
        headDistance += getUpdatedUsage(x, y);
        opDistance += getUpdatedUsage(x, y);
        printUsage();
    }

    private void printUsage() {
        if (!isPrintAvailable) { return; }

        isPrintAvailable = false;
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(PRINT_WAIT_TIME_MS);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            logger.info("Usage: \n" + "   Op distance: " + opDistance + "\n   Head distance: " + headDistance);
            isPrintAvailable = true;
        });
        thread.start();
    }

    private double getUpdatedUsage(int endX, int endY) {
        return Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
    }
}

