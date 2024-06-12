package edu.kis.powp.jobs2d.drivers.visitor;

import edu.kis.powp.jobs2d.drivers.*;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.transformators.TransformingJob2dDriverDecorator;

public class DriverCountingVisitor implements IDriverVisitor {

    private int count = 0;

    public int getCount() {
        return count;
    }

    @Override
    public void visit(DriversComposite driversComposite) {
        int tmpCount = 0;
        count = 0;
        for (IVisitableDriver driver : driversComposite.getDrivers()) {
            driver.accept(this);
            tmpCount += count;
        }
        count = tmpCount;
    }

    @Override
    public void visit(LoggerDriver loggerDriver) {
        count = 1;
    }

    @Override
    public void visit(LineDriverAdapter lineDriverAdapter) {
        count = 1;
    }

    @Override
    public void visit(RealTimeDecoratorDriver realTimeDecoratorDriver) {
        count = 0;
        realTimeDecoratorDriver.getDriver().accept(this);
        count++;
    }

    @Override
    public void visit(UsageMonitorDriverDecorator usageMonitorDriverDecorator) {
        count = 0;
        usageMonitorDriverDecorator.getDriver().accept(this);
        count++;
    }

    @Override
    public void visit(RecordingDriverDecorator recordingDriverDecorator) {
        count = 0;
        recordingDriverDecorator.getDriver().accept(this);
        count++;
    }

    @Override
    public void visit(TransformingJob2dDriverDecorator transformingJob2dDriverDecorator) {
        count = 0;
        transformingJob2dDriverDecorator.getDriver().accept(this);
        count++;
    }

}
