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
        for(IVisitableDriver driver : driversComposite.getDrivers()) {
            driver.accept(this);
        }
    }

    @Override
    public void visit(LoggerDriver loggerDriver) {
        count++;
    }

    @Override
    public void visit(LineDriverAdapter lineDriverAdapter) {
        count++;
    }

    @Override
    public void visit(RealTimeDecoratorDriver realTimeDecoratorDriver) {
        count++;
    }

    @Override
    public void visit(UsageMonitorDriverDecorator usageMonitorDriverDecorator) {
        count++;
    }

    @Override
    public void visit(RecordingDriverDecorator recordingDriverDecorator) {
        count++;
    }

    @Override
    public void visit(TransformingJob2dDriverDecorator transformingJob2dDriverDecorator) {
        count++;
    }

}
