package edu.kis.powp.jobs2d.usage;

import edu.kis.powp.jobs2d.enums.DeviceManagementWarnings;


public class Tank {
    private double capacity;
    private double currentLevel;
    private LevelWarningListener levelWarningListener;

    public Tank(double capacity, LevelWarningListener levelWarningListener) {
        this.capacity = capacity;
        this.currentLevel = capacity;
        this.levelWarningListener = levelWarningListener;
    }

    public void consumeInk(double amount) {
        currentLevel -= amount * ConfigOfDeviceUsageLoader.CONSUMPTION_PER_DISTANCE;
        if (currentLevel < 0) {
            currentLevel = 0;
        }
        checkLevelAndNotify();
    }

    public void refill() {
        currentLevel = capacity;
        checkLevelAndNotify(DeviceManagementWarnings.MAX_LEVEL);
    }

    public double getCurrentLevel() {
        return currentLevel;
    }


    private void checkLevelAndNotify() {
        DeviceManagementWarnings warning = determineWarning();
        levelWarningListener.temp(warning);
    }

    private void checkLevelAndNotify(DeviceManagementWarnings warning) {
        levelWarningListener.temp(warning);
    }

    public DeviceManagementWarnings determineWarning() {
        if (currentLevel == 0) {
            return DeviceManagementWarnings.EMPTY_OUT_OF_WORK;
        } else if (currentLevel <= capacity * ConfigOfDeviceUsageLoader.WARNING_INTERVAL_REFILL) {
            return DeviceManagementWarnings.NEEDS_REFILL;
        } else if (currentLevel <= capacity * ConfigOfDeviceUsageLoader.WARNING_INTERVAL_LOW) {
            return DeviceManagementWarnings.LOW_LEVEL;
        } else if (currentLevel <= capacity * ConfigOfDeviceUsageLoader.WARNING_INTERVAL_MEDIUM) {
            return DeviceManagementWarnings.MEDIUM_LEVEL;
        } else {
            return DeviceManagementWarnings.MAX_LEVEL;
        }
    }
}
