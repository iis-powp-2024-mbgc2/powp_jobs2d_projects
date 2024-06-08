package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.drivers.visitor.IDriverVisitor;
import edu.kis.powp.jobs2d.drivers.visitor.IVisitableDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DriversComposite implements IVisitableDriver {

    private List<IVisitableDriver> list;

    public DriversComposite() {
        this.list = new ArrayList<>();
    }

    public DriversComposite(List<IVisitableDriver> list) {
        this.list = list;
    }

    public void addDriver(IVisitableDriver driver) {
        this.list.add(driver);
    }

    public boolean removeDriver(IVisitableDriver driver) {
        return list.remove(driver);
    }

    @Override
    public void setPosition(int x, int y) {
        for (IVisitableDriver driver : list) {
            driver.setPosition(x, y);
        }
    }

    @Override
    public void operateTo(int x, int y) {
        for (IVisitableDriver driver : list) {
            driver.operateTo(x, y);
        }
    }

    public String toString() {
        return list.stream()
                .map(IVisitableDriver::toString)
                .collect(Collectors.joining(", ", "Composite of ", ""));
    }

    public List<IVisitableDriver> getDrivers() {
        return list;
    }

    @Override
    public void accept(IDriverVisitor visitor) {
        visitor.visit(this);
    }
}
