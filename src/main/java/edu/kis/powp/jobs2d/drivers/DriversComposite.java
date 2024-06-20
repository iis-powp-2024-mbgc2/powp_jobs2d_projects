package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.visitor.IDriverVisitor;
import edu.kis.powp.jobs2d.drivers.visitor.IVisitableDriver;

import java.util.*;
import java.util.stream.Collectors;

public class DriversComposite implements IVisitableDriver {
    private final Map<Integer, IVisitableDriver> map;
    private DriverManager driverManager;

    public DriversComposite(DriverManager driverManager) {
        this.map = new TreeMap<>();
        this.driverManager = driverManager;
    }

    public DriversComposite(Map<Integer, IVisitableDriver> list, DriverManager driverManager) {
        this.map = list;
        this.driverManager = driverManager;
    }

    public void addDriver(int position, IVisitableDriver driver) {
        this.map.put(position, driver);
    }

    public boolean removeDriver(int position) {
        return map.remove(position) != null;
    }

    public Map<Integer, IVisitableDriver> getMap() {
        return map;
    }

    @Override
    public void setPosition(int x, int y) {
        executeOperation(Job2dDriver::setPosition, x, y);
    }

    @Override
    public void operateTo(int x, int y) {
        executeOperation(Job2dDriver::operateTo, x, y);
    }

    private void executeOperation(DriversCompositeOperation driversCompositeOperation, int x, int y) {
        int startX = driverManager.getCurrentDriver().getStartX();
        int startY = driverManager.getCurrentDriver().getStartY();
        int counter = 0;
        for (Job2dDriver driver : map.values()) {
            if (counter != 0) {
                x = driverManager.getCurrentDriver().getStartX();
                y = driverManager.getCurrentDriver().getStartY();
                driverManager.getCurrentDriver().setStartX(startX);
                driverManager.getCurrentDriver().setStartY(startY);
            }
            driversCompositeOperation.executeOperation(driver, x, y);
            counter++;
        }
    }

    private interface DriversCompositeOperation {
        void executeOperation(Job2dDriver driver, int startX, int startY);
    }

    public String toString() {
        return map.values().stream()
                .map(Job2dDriver::toString)
                .collect(Collectors.joining(", ", "Composite of ", ""));
    }

    @Override
    public void accept(IDriverVisitor visitor) {
        visitor.visit(this);
    }

    public List<IVisitableDriver> getDrivers() {
        return new ArrayList<>(map.values());
    }
}
