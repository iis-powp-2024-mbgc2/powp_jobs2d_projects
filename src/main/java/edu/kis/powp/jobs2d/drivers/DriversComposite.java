package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.visitor.DriverVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DriversComposite implements Job2dDriver {

    private List<Job2dDriver> list;

    public DriversComposite() {
        this.list = new ArrayList<>();
    }

    public DriversComposite(List<Job2dDriver> list) {
        this.list = list;
    }

    public void addDriver(Job2dDriver driver) {
        this.list.add(driver);
    }

    public boolean removeDriver(Job2dDriver driver) {
        return list.remove(driver);
    }

    public int driversCount() { return this.list.size(); }

    @Override
    public void setPosition(int x, int y) {
        for (Job2dDriver driver : list) {
            driver.setPosition(x, y);
        }
    }

    @Override
    public void operateTo(int x, int y) {
        for (Job2dDriver driver : list) {
            driver.operateTo(x, y);
        }
    }

    public String toString() {
        return list.stream()
                .map(Job2dDriver::toString)
                .collect(Collectors.joining(", ", "Composite of ", ""));
    }

    public void accept(DriverVisitor visitor) {
        visitor.visit(this);
    }
}
