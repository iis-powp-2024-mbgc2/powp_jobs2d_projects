package edu.kis.powp.jobs2d.usage;

import edu.kis.powp.jobs2d.enums.DeviceManagementWarnings;
import java.util.ArrayList;
import java.util.List;

public class Tank {
    private double capacity;
    private double currentLevel;
    private List<TankObserver> observers = new ArrayList<>();

    public Tank(double capacity) {
        this.capacity = capacity;
        this.currentLevel = capacity;
    }

    public void addObserver(TankObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(TankObserver observer) {
        observers.remove(observer);
    }

    public void consumeInk(double amount) {
        currentLevel -= amount * ConfigOfDeviceUsageLoader.CONSUMPTION_PER_DISTANCE;
        if (currentLevel < 0) {
            currentLevel = 0;
        }
        notifyObservers();
    }

    public void refill() {
        currentLevel = capacity;
        notifyObservers(DeviceManagementWarnings.MAX_LEVEL);
    }

    public double getCurrentLevel() {
        return currentLevel;
    }

    private void notifyObservers() {
        DeviceManagementWarnings warning = determineWarning();
        for (TankObserver observer : observers) {
            observer.update(warning);
        }
    }

    private void notifyObservers(DeviceManagementWarnings warning) {
        for (TankObserver observer : observers) {
            observer.update(warning);
        }
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
