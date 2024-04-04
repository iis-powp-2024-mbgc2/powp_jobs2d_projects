package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;

import java.util.ArrayList;
import java.util.List;

public class DriverController implements Job2dDriver {

    private final List<Job2dDriver> drivers;

    public DriverController() {
        this.drivers = new ArrayList<>();
    }

    public DriverController(List<Job2dDriver> drivers) {
        this.drivers = drivers;
    }

    public List<Job2dDriver> getDrivers() {
        return drivers;
    }

    public void addDriver(Job2dDriver driver) {
        drivers.add(driver);
    }

    @Override
    public void setPosition(int x, int y) {
        for (Job2dDriver driver : drivers) {
            driver.setPosition(x, y);
        }
    }

    @Override
    public void operateTo(int x, int y) {
        for (Job2dDriver driver : drivers) {
            driver.operateTo(x, y);
        }
    }
}
