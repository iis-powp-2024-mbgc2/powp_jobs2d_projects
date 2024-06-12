package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DriversComposite implements Job2dDriver {

    private final Map<Integer, Job2dDriver> map;
    private DriverManager driverManager;

    public DriversComposite(DriverManager driverManager) {
        this.map = new TreeMap<>();
        this.driverManager = driverManager;
    }

    public DriversComposite(Map<Integer, Job2dDriver> list, DriverManager driverManager) {
        this.map = list;
        this.driverManager = driverManager;
    }

    public void addDriver(int position, Job2dDriver driver) {
        this.map.put(position, driver);
    }

    public boolean removeDriver(int position) {
        return map.remove(position) != null;
    }

    public Map<Integer, Job2dDriver> getMap() {
        return map;
    }

    @Override
    public void setPosition(int x, int y) {
        int startXAtStart = driverManager.getCurrentDriver().getStartX();
        int startYAtStart = driverManager.getCurrentDriver().getStartY();
        int counter = 0;
        for (Job2dDriver driver : map.values()) {
            if (counter == 0) {
                driver.setPosition(x, y);
            }
            else {
                int startX = driverManager.getCurrentDriver().getStartX();
                int startY = driverManager.getCurrentDriver().getStartY();
                driverManager.getCurrentDriver().setStartX(startXAtStart);
                driverManager.getCurrentDriver().setStartY(startYAtStart);
                driver.setPosition(startX, startY);
            }
            counter++;
        }
    }

    @Override
    public void operateTo(int x, int y) {
        int startXAtStart = driverManager.getCurrentDriver().getStartX();
        int startYAtStart = driverManager.getCurrentDriver().getStartY();
        int counter = 0;
        for (Job2dDriver driver : map.values()) {
            if (counter == 0) {
                driver.operateTo(x, y);
            }
            else {
                int startX = driverManager.getCurrentDriver().getStartX();
                int startY = driverManager.getCurrentDriver().getStartY();
                driverManager.getCurrentDriver().setStartX(startXAtStart);
                driverManager.getCurrentDriver().setStartY(startYAtStart);
                driver.operateTo(startX, startY);
            }
            counter ++;
        }
    }

    public String toString() {
        return map.values().stream()
                .map(Job2dDriver::toString)
                .collect(Collectors.joining(", ", "Composite of ", ""));
    }
}