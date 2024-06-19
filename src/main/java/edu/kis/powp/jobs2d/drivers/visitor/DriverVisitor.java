package edu.kis.powp.jobs2d.drivers.visitor;

import edu.kis.powp.jobs2d.drivers.*;

public interface DriverVisitor {
    void visit(LoggerDriver loggerDriver);
    void visit(DriversComposite driverComposite);
    void visit(RecordingDriverDecorator recordingDriverDecorator);
    void visit(UsageMonitorDriverDecorator usageMonitorDriverDecorator);
}
