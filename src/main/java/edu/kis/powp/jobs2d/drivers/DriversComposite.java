package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DriversComposite implements Job2dDriver {

    private List<Job2dDriver> list;
    private DriverManager driverManager;

    public DriversComposite(DriverManager driverManager) {
        this.list = new ArrayList<>();
        this.driverManager = driverManager;
    }

    public DriversComposite(List<Job2dDriver> list, DriverManager driverManager) {
        this.list = list;
        this.driverManager = driverManager;
    }

    public void addDriver(Job2dDriver driver) {
        this.list.add(driver);
    }

    public boolean removeDriver(Job2dDriver driver) {
        return list.remove(driver);
    }

    public List<Job2dDriver> getList() {
        return list;
    }

    @Override
    public void setPosition(int x, int y) {
        int startXAtStart = driverManager.getCurrentDriver().getStartX();
        int startYAtStart = driverManager.getCurrentDriver().getStartY();
        for (int counter = 0; counter < list.size(); counter++) {
            Job2dDriver driver = list.get(counter);
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
        }
    }

    @Override
    public void operateTo(int x, int y) {
        int startXAtStart = driverManager.getCurrentDriver().getStartX();
        int startYAtStart = driverManager.getCurrentDriver().getStartY();
        for (int counter = 0; counter < list.size(); counter++) {
            Job2dDriver driver = list.get(counter);
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
        }
    }

    public String toString() {
        return list.stream()
                .map(Job2dDriver::toString)
                .collect(Collectors.joining(", ", "Composite of ", ""));
    }
}