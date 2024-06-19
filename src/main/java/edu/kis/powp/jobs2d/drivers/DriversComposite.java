package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.drivers.visitor.DriverVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DriversComposite implements IDriver {

    private List<IDriver> list;

    public DriversComposite() {
        this.list = new ArrayList<>();
    }

    public DriversComposite(List<IDriver> list) {
        this.list = list;
    }

    public void addDriver(IDriver driver) {
        this.list.add(driver);
    }

    public boolean removeDriver(IDriver driver) {
        return list.remove(driver);
    }

    public int driversCount() { return this.list.size(); }

    @Override
    public void setPosition(int x, int y) {
        for (IDriver driver : list) {
            driver.setPosition(x, y);
        }
    }

    @Override
    public void operateTo(int x, int y) {
        for (IDriver driver : list) {
            driver.operateTo(x, y);
        }
    }

    @Override
    public void accept(DriverVisitor visitor) {
        visitor.visit(this);
        list.forEach(it -> it.accept(visitor));
    }

    public String toString() {
        return list.stream()
                .map(IDriver::toString)
                .collect(Collectors.joining(", ", "Composite of ", ""));
    }
}
