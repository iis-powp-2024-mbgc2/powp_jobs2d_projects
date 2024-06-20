package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.drivers.visitor.DriverVisitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class DriversComposite implements VisitableJob2dDriver {

    private List<VisitableJob2dDriver> list;

    public DriversComposite() {
        this.list = new ArrayList<>();
    }

    public DriversComposite(List<VisitableJob2dDriver> list) {
        this.list = list;
    }

    public void addDriver(VisitableJob2dDriver driver) {
        this.list.add(driver);
    }

    public boolean removeDriver(VisitableJob2dDriver driver) {
        return list.remove(driver);
    }

    public int driversCount() { return this.list.size(); }

    @Override
    public void setPosition(int x, int y) {
        for (VisitableJob2dDriver driver : list) {
            driver.setPosition(x, y);
        }
    }

    @Override
    public void operateTo(int x, int y) {
        for (VisitableJob2dDriver driver : list) {
            driver.operateTo(x, y);
        }
    }

    public Iterator<VisitableJob2dDriver> getIterator() { return this.list.iterator(); }

    @Override
    public void accept(DriverVisitor visitor) {
        visitor.visit(this);
    }

    public String toString() {
        return list.stream()
                .map(VisitableJob2dDriver::toString)
                .collect(Collectors.joining(", ", "Composite of ", ""));
    }
}
